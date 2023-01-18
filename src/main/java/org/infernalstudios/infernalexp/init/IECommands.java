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

package org.infernalstudios.infernalexp.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.util.NetherTeleportCommandUtil;

import java.util.Collection;

public class IECommands {

    private static void netherSpawnCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        String commandString = "setdimensionspawn";

        // First the command is defined if no player is supplied, setting the user's spawn, then if there are players supplied as targets
        dispatcher.register(Commands.literal(commandString).requires(commandSource -> commandSource.hasPermission(2)).executes(command -> {
            ServerPlayer player = command.getSource().getPlayerOrException();
            BlockPos pos = player.blockPosition();
            Level world = player.getCommandSenderWorld();
            String dimension = world.dimension().location().toString();

            // Sets the player's spawnpoint and spawn dimension
            player.setRespawnPosition(world.dimension(), pos, 0.0F, true, false);

            // Prints out the location of the new spawnpoint, including player name, coordinates, rotation, and dimension
            command.getSource().sendSuccess(new TranslatableComponent(InfernalExpansion.MOD_ID + ".commands.setdimensionspawn.success.single", player.getDisplayName(), pos.getX(), pos.getY(), pos.getZ(), 0.0F, dimension), true);
            return 1;
        }).then(Commands.argument("players", EntityArgument.players()).executes(command -> {
            Collection<ServerPlayer> players = EntityArgument.getPlayers(command, "players");
            BlockPos pos = command.getSource().getPlayerOrException().blockPosition();
            Level world = command.getSource().getPlayerOrException().getCommandSenderWorld();
            String dimension = world.dimension().location().toString();

            // Sets each player's spawnpoint and spawn dimension
            for (ServerPlayer player : players) {
                player.setRespawnPosition(world.dimension(), pos, 0.0F, true, false);
            }

            // Prints out the location of the new spawnpoint, including player if only one player targeted or how many if multiple, coordinates, rotation, and dimension
            if (players.size() == 1) {
                command.getSource().sendSuccess(new TranslatableComponent(InfernalExpansion.MOD_ID + ".commands.setdimensionspawn.success.single", players.iterator().next().getDisplayName(), pos.getX(), pos.getY(), pos.getZ(), 0.0F, dimension), true);
            } else {
                command.getSource().sendSuccess(new TranslatableComponent(InfernalExpansion.MOD_ID + ".commands.setdimensionspawn.success.multiple", players.size(), pos.getX(), pos.getY(), pos.getZ(), 0.0F, dimension), true);
            }
            return players.size();
        })));
    }

    private static void dimensionTeleportCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        String commandString = "ntp";

        dispatcher.register(Commands.literal(commandString).requires(commandSource -> commandSource.hasPermission(3)).executes(command -> {
            ServerPlayer player = command.getSource().getPlayerOrException();
            MinecraftServer server = command.getSource().getServer();
            ServerLevel targetWorld = player.getCommandSenderWorld().dimension() == Level.NETHER ? server.getLevel(Level.OVERWORLD) : server.getLevel(Level.NETHER);

            // Safeguard the teleport to not teleport outside the world border
            WorldBorder worldborder = targetWorld.getWorldBorder();

            // Makes sure world border is within bounds and min/max is safely within the world border
            double minX = Math.max(-2.9999872E7D, worldborder.getMinX() + 16.0D);
            double minZ = Math.max(-2.9999872E7D, worldborder.getMinZ() + 16.0D);
            double maxX = Math.min(2.9999872E7D, worldborder.getMaxX() - 16.0D);
            double maxZ = Math.min(2.9999872E7D, worldborder.getMaxZ() - 16.0D);

            // Adds support to modded dimensions
            double coordinateDifference = DimensionType.getTeleportationScale(player.level.dimensionType(), targetWorld.dimensionType());

            // MathHelper.clamp() returns the middle value (in this case, prevents teleporting outside the world border)
            BlockPos baseTeleportLocation = new BlockPos(
                Mth.clamp(player.getX() * coordinateDifference, minX, maxX),
                player.getY(),
                Mth.clamp(player.getZ() * coordinateDifference, minZ, maxZ));

            BlockPos safeTeleportLocation = NetherTeleportCommandUtil.getSafePosition(targetWorld, baseTeleportLocation);

            if (safeTeleportLocation == null) {
                return 0;
            }

            player.teleportTo(targetWorld, safeTeleportLocation.getX(), safeTeleportLocation.getY(), safeTeleportLocation.getZ(), player.getViewYRot(0.0F), player.getViewXRot(0.0F));

            return 1;
        }));
    }

    private static void mapNearbyBiomes(CommandDispatcher<CommandSourceStack> dispatcher) {
        String commandString = "map_biomes";

        dispatcher.register(Commands.literal(commandString)
            .requires(commandSource -> commandSource.hasPermission(3))
            .then(Commands.argument("radius", IntegerArgumentType.integer(1))
                .executes(command -> {
                        int radius = IntegerArgumentType.getInteger(command, "radius");
                        Vec3 pos = command.getSource().getPosition().add(0, -1, 0);

                        for (int x = -radius; x <= radius; x++) {
                            for (int y = -radius; y <= radius; y++) {
                                BlockState state = switch (command.getSource().getLevel().getBiome(new BlockPos(pos.add(x * 16, 0, y * 16))).unwrapKey().get().location().toString()) {
                                    case "minecraft:basalt_deltas" -> Blocks.COAL_BLOCK.defaultBlockState();
                                    case "infernalexp:delta_shores" -> Blocks.BASALT.defaultBlockState();
                                    default -> Blocks.RED_TERRACOTTA.defaultBlockState();
                                };

                                command.getSource().getLevel().setBlock(new BlockPos(pos.add(x, 0, y)), state, 2);
                            }
                        }

                        return 1;
                    }
                )));
    }

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dimensionTeleportCommand(dispatcher);
        netherSpawnCommand(dispatcher);
        mapNearbyBiomes(dispatcher);
    }

}
