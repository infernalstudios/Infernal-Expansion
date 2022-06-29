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

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.network.NetworkEvent;
import org.infernalstudios.infernalexp.init.IEItems;

import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

public class WhipReachPacket {
    private final UUID playerUUID;
    private static final Random random = new Random();


    public WhipReachPacket(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public static void encode(WhipReachPacket message, PacketBuffer buffer) {
        buffer.writeUniqueId(message.playerUUID);
    }

    public static WhipReachPacket decode(PacketBuffer buffer) {
        return new WhipReachPacket(buffer.readUniqueId());
    }

    public static void handle(WhipReachPacket message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            PlayerEntity player = context.get().getSender();

            // Change the value added here to adjust the reach of the charge attack of the whip, must also be changed in WhipReachPacket
            double reach = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue() + 1.0D;

            Vector3d eyePos = player.getEyePosition(1.0F);
            Vector3d lookVec = player.getLookVec();
            Vector3d reachVec = eyePos.add(lookVec.mul(reach, reach, reach));

            AxisAlignedBB playerBox = player.getBoundingBox().expand(lookVec.scale(reach)).grow(1.0D, 1.0D, 1.0D);
            EntityRayTraceResult entityTraceResult = ProjectileHelper.rayTraceEntities(player, eyePos, reachVec, playerBox, (target) -> !target.isSpectator() && target.isLiving(), reach * reach);
            BlockRayTraceResult blockTraceResult = player.getEntityWorld().rayTraceBlocks(new RayTraceContext(eyePos, reachVec, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, player));

            if (entityTraceResult != null) {
                double distance = eyePos.squareDistanceTo(entityTraceResult.getHitVec());
                if (distance < reach * reach && distance < eyePos.squareDistanceTo(blockTraceResult.getHitVec())) {
                    player.ticksSinceLastSwing = (int) player.getCooldownPeriod();

                    player.getEntityWorld().playSound(player, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F));

                    if (player != null && player.getServer() != null) {
                        ServerPlayerEntity serverPlayer = player.getServer().getPlayerList().getPlayerByUUID(message.playerUUID);
                        Entity target = entityTraceResult.getEntity();

                        if (serverPlayer != null && target != null) {
                            if (serverPlayer.getDistanceSq(target) < (reach * reach) * serverPlayer.getCooledAttackStrength(0.0F)) {
                                serverPlayer.attackTargetEntityWithCurrentItem(target);
                                serverPlayer.getEntityWorld().playSound(null, serverPlayer.getPosX(), serverPlayer.getPosY(), serverPlayer.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F));}
                        }
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
        ListNBT listnbt = stack.getEnchantmentTagList();

        for(int i = 0; i < listnbt.size(); ++i) {
            CompoundNBT compoundNBT = listnbt.getCompound(i);

            if (compoundNBT.getString("id").equals("minecraft:knockback")) {
                return MathHelper.clamp(compoundNBT.getInt("lvl"), 0, 2);
            }
        }

        return 0;
    }
}
