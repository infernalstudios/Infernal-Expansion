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

package com.nekomaster1000.infernalexp.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.util.NetherTeleportCommandUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.server.ServerWorld;

import java.util.Collection;

public class IECommands {

    private static void netherSpawnCommand(CommandDispatcher<CommandSource> dispatcher) {
        String commandString = "setdimensionspawn";

        // First the command is defined if no player is supplied, setting the user's spawn, then if there are players supplied as targets
        LiteralCommandNode<CommandSource> source = dispatcher.register(Commands.literal(commandString).requires(commandSource -> commandSource.hasPermissionLevel(2)).executes(command -> {
            ServerPlayerEntity player = command.getSource().asPlayer();
            BlockPos pos = player.getPosition();
            World world = player.getEntityWorld();
            String dimension = world.getDimensionKey().getLocation().toString();

            // Sets the player's spawnpoint and spawn dimension
            player.func_242111_a(world.getDimensionKey(), pos, 0.0F, true, false);

            // Prints out the location of the new spawnpoint, including player name, coordinates, rotation, and dimension
            command.getSource().sendFeedback(new TranslationTextComponent(InfernalExpansion.MOD_ID + ".commands.setdimensionspawn.success.single", player.getDisplayName(), pos.getX(), pos.getY(), pos.getZ(), 0.0F, dimension), true);
            return 1;
        }).then(Commands.argument("players", EntityArgument.players()).executes(command -> {
            Collection<ServerPlayerEntity> players = EntityArgument.getPlayers(command, "players");
            BlockPos pos = command.getSource().asPlayer().getPosition();
            World world = command.getSource().asPlayer().getEntityWorld();
            String dimension = world.getDimensionKey().getLocation().toString();

            // Sets each player's spawnpoint and spawn dimension
            for (ServerPlayerEntity player : players) {
                player.func_242111_a(world.getDimensionKey(), pos, 0.0F, true, false);
            }

            // Prints out the location of the new spawnpoint, including player if only one player targeted or how many if multiple, coordinates, rotation, and dimension
            if (players.size() == 1) {
                command.getSource().sendFeedback(new TranslationTextComponent(InfernalExpansion.MOD_ID + ".commands.setdimensionspawn.success.single", players.iterator().next().getDisplayName(), pos.getX(), pos.getY(), pos.getZ(), 0.0F, dimension), true);
            } else {
                command.getSource().sendFeedback(new TranslationTextComponent(InfernalExpansion.MOD_ID + ".commands.setdimensionspawn.success.multiple", players.size(), pos.getX(), pos.getY(), pos.getZ(), 0.0F, dimension), true);
            }
            return players.size();
        })));
    }

    private static void dimensionTeleportCommand(CommandDispatcher<CommandSource> dispatcher) {
        String commandString = "ntp";

        LiteralCommandNode<CommandSource> source = dispatcher.register(Commands.literal(commandString).requires(commandSource -> commandSource.hasPermissionLevel(3)).executes(command -> {
            ServerPlayerEntity player = command.getSource().asPlayer();
            MinecraftServer server = command.getSource().getServer();
            ServerWorld targetWorld = player.getEntityWorld().getDimensionKey() == World.THE_NETHER ? server.getWorld(World.OVERWORLD) : server.getWorld(World.THE_NETHER);

            // Safeguard the teleport to not teleport outside the world border
            WorldBorder worldborder = targetWorld.getWorldBorder();

            // Makes sure world border is within bounds and min/max is safely within the world border
            double minX = Math.max(-2.9999872E7D, worldborder.minX() + 16.0D);
            double minZ = Math.max(-2.9999872E7D, worldborder.minZ() + 16.0D);
            double maxX = Math.min(2.9999872E7D, worldborder.maxX() - 16.0D);
            double maxZ = Math.min(2.9999872E7D, worldborder.maxZ() - 16.0D);

            // Adds support to modded dimensions
            double coordinateDifference = DimensionType.getCoordinateDifference(player.world.getDimensionType(), targetWorld.getDimensionType());

            // MathHelper.clamp() returns the middle value (in this case, prevents teleporting outside the world border)
            BlockPos baseTeleportLocation = new BlockPos(
                MathHelper.clamp(player.getPosX() * coordinateDifference, minX, maxX),
                player.getPosY(),
                MathHelper.clamp(player.getPosZ() * coordinateDifference, minZ, maxZ));

            BlockPos safeTeleportLocation = NetherTeleportCommandUtil.getSafePosition(targetWorld, baseTeleportLocation);

            if (safeTeleportLocation == null) {
                return 0;
            }

            player.teleport(targetWorld, safeTeleportLocation.getX(), safeTeleportLocation.getY(), safeTeleportLocation.getZ(), player.getYaw(0.0F), player.getPitch(0.0F));

            return 1;
        }));
    }

    public static void registerCommands(CommandDispatcher<CommandSource> dispatcher) {
        dimensionTeleportCommand(dispatcher);
        netherSpawnCommand(dispatcher);
    }
}
