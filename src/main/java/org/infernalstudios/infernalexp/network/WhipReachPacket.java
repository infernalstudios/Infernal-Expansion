/*
 * Copyright 2021 Infernal Studios
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

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

public class WhipReachPacket {
    private final UUID playerUUID;
    private final int targetEntityID;
    private static final Random random = new Random();


    public WhipReachPacket(UUID playerUUID, int target) {
        this.playerUUID = playerUUID;
        this.targetEntityID = target;
    }

    public static void encode(WhipReachPacket message, PacketBuffer buffer) {
        buffer.writeUniqueId(message.playerUUID);
        buffer.writeVarInt(message.targetEntityID);
    }

    public static WhipReachPacket decode(PacketBuffer buffer) {
        return new WhipReachPacket(buffer.readUniqueId(), buffer.readVarInt());
    }

    public static void handle(WhipReachPacket message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            PlayerEntity playerEntity = context.get().getSender();
            playerEntity.getEntityWorld().playSound(playerEntity, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F));

            if (playerEntity != null && playerEntity.getServer() != null) {
                ServerPlayerEntity player = playerEntity.getServer().getPlayerList().getPlayerByUUID(message.playerUUID);
                Entity target = playerEntity.getEntityWorld().getEntityByID(message.targetEntityID);

                double reach = player.getAttributeValue(ForgeMod.REACH_DISTANCE.get()) + 1.0D;

                if (player != null && target != null) {
                    if (player.getDistanceSq(target) < (reach * reach) * player.getCooledAttackStrength(0.0F)) {
                        player.attackTargetEntityWithCurrentItem(target);
                        player.getEntityWorld().playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F));

                        // Change the first float value to change the amount of knockback on hit
                        ((LivingEntity) target).applyKnockback(EnchantmentHelper.getKnockbackModifier(player) * 0.5F, MathHelper.sin(player.rotationYaw * ((float) Math.PI / 180F)), -MathHelper.cos(player.rotationYaw * ((float) Math.PI / 180F)));
                    }
                }
            }
        });

        context.get().setPacketHandled(true);
    }
}
