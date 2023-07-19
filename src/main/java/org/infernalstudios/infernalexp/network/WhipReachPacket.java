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

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

public class WhipReachPacket {
    private final UUID playerUUID;
    private static final Random random = new Random();


    public WhipReachPacket(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public static void encode(WhipReachPacket message, FriendlyByteBuf buffer) {
        buffer.writeUUID(message.playerUUID);
    }

    public static WhipReachPacket decode(FriendlyByteBuf buffer) {
        return new WhipReachPacket(buffer.readUUID());
    }

    public static void handle(WhipReachPacket message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Player player = context.get().getSender();

            // Change the value added here to adjust the reach of the charge attack of the whip, must also be changed in WhipReachPacket
            double reach = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue() + 1.0D;

            Vec3 eyePos = player.getEyePosition(1.0F);
            Vec3 lookVec = player.getViewVector(1.0F);
            Vec3 reachVec = eyePos.add(lookVec.multiply(reach, reach, reach));

            AABB playerBox = player.getBoundingBox().expandTowards(lookVec.scale(reach)).inflate(1.0D, 1.0D, 1.0D);
            EntityHitResult entityTraceResult = ProjectileUtil.getEntityHitResult(player, eyePos, reachVec, playerBox, (target) -> !target.isSpectator() && target.isAlive(), reach * reach);
            BlockHitResult blockTraceResult = player.getCommandSenderWorld().clip(new ClipContext(eyePos, reachVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

            if (entityTraceResult != null) {
                double distance = eyePos.distanceToSqr(entityTraceResult.getLocation());
                if (distance < reach * reach && distance < eyePos.distanceToSqr(blockTraceResult.getLocation())) {
                    player.attackStrengthTicker = (int) player.getCurrentItemAttackStrengthDelay();

                    player.getCommandSenderWorld().playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F));

                    if (player != null && player.getServer() != null) {
                        ServerPlayer serverPlayer = player.getServer().getPlayerList().getPlayer(message.playerUUID);
                        Entity target = entityTraceResult.getEntity();

                        if (serverPlayer != null && target != null) {
                            if (serverPlayer.distanceToSqr(target) < (reach * reach) * serverPlayer.getAttackStrengthScale(0.0F)) {
                                serverPlayer.attack(target);
                                serverPlayer.getCommandSenderWorld().playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F));}
                        }
                    }

                }
            }
        });

        context.get().setPacketHandled(true);
    }
}
