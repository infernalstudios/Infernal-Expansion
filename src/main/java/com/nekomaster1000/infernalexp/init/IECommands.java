package com.nekomaster1000.infernalexp.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;

import com.nekomaster1000.infernalexp.util.NetherTeleportCommandUtil;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.server.ServerWorld;

public class IECommands {

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

        dispatcher.register(Commands.literal(commandString).redirect(source));
    }

    public static void registerCommands(CommandDispatcher<CommandSource> dispatcher) {
        dimensionTeleportCommand(dispatcher);
    }
}
