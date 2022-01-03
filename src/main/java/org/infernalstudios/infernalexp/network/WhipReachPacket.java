/*
 * Copyright 2022 Infernal Studios
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.infernalstudios.infernalexp.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import org.infernalstudios.infernalexp.init.IEItems;

import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

public class WhipReachPacket {
    private final UUID playerUUID;
    private final int targetEntityID;
    private final ItemStack stack;
    private static final Random random = new Random();


    public WhipReachPacket(UUID playerUUID, int target, ItemStack stack) {
        this.playerUUID = playerUUID;
        this.targetEntityID = target;
        this.stack = stack;
    }

    public static void encode(WhipReachPacket message, FriendlyByteBuf buffer) {
        buffer.writeUUID(message.playerUUID);
        buffer.writeVarInt(message.targetEntityID);
        buffer.writeItem(message.stack);
    }

    public static WhipReachPacket decode(FriendlyByteBuf buffer) {
        return new WhipReachPacket(buffer.readUUID(), buffer.readVarInt(), buffer.readItem());
    }

    public static void handle(WhipReachPacket message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Player playerEntity = context.get().getSender();
            playerEntity.getCommandSenderWorld().playSound(playerEntity, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F));

            if (playerEntity != null && playerEntity.getServer() != null) {
                ServerPlayer player = playerEntity.getServer().getPlayerList().getPlayer(message.playerUUID);
                Entity target = playerEntity.getCommandSenderWorld().getEntity(message.targetEntityID);

                double reach = player.getAttributeValue(ForgeMod.REACH_DISTANCE.get()) + 1.0D;

                if (player != null && target != null) {
                    if (player.distanceToSqr(target) < (reach * reach) * player.getAttackStrengthScale(0.0F)) {
                        player.attack(target);
                        player.getCommandSenderWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F));

                        int knockbackLevel = message.getActiveKnockback(message.stack);

                        // Change the first float value to change the amount of knockback on hit
                        ((LivingEntity) target).knockback(1.0F + knockbackLevel * 0.5F, Mth.sin(player.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float) Math.PI / 180F)));
                    }
                }
            }
        });

        context.get().setPacketHandled(true);
    }

    private int getActiveKnockback(ItemStack stack) {
        if (stack.getItem() != IEItems.BLINDSIGHT_TONGUE_WHIP.get()) {
            return 0;
        }
        ListTag listnbt = stack.getEnchantmentTags();

        for(int i = 0; i < listnbt.size(); ++i) {
            CompoundTag compoundNBT = listnbt.getCompound(i);

            if (compoundNBT.getString("id").equals("minecraft:knockback")) {
                return Mth.clamp(compoundNBT.getInt("lvl"), 0, 2);
            }
        }

        return 0;
    }
}
