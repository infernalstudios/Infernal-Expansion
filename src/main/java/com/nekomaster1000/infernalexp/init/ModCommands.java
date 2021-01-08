package com.nekomaster1000.infernalexp.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.spawner.WorldEntitySpawner;

public class ModCommands {

    private static void dimensionTeleportCommand(CommandDispatcher<CommandSource> dispatcher) {
        String commandString = "ntp";

        LiteralCommandNode<CommandSource> source = dispatcher.register(Commands.literal(commandString).executes(command -> {
            ServerPlayerEntity player = command.getSource().asPlayer();

            if (player.hasPermissionLevel(3)) {
                if(player.getEntityWorld().getDimensionKey() ==  World.THE_NETHER) {
                    int yValue = player.getServer().getWorld(World.OVERWORLD).getChunk(player.getPosition()).getTopBlockY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (int) player.getPosX(), (int) player.getPosZ());
                    player.teleport(command.getSource().getServer().getWorld(World.OVERWORLD), player.getPosX(), yValue + 1, player.getPosZ(), player.getYaw(0.0F), player.getPitch(0.0F));

                }
                else{
                    int yValue = player.getServer().getWorld(World.THE_NETHER).getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, player.getPosition()).getY();
                    player.teleport(command.getSource().getServer().getWorld(World.THE_NETHER), player.getPosX(), yValue + 1, player.getPosZ(), player.getYaw(0.0F), player.getPitch(0.0F));
                }
                return 1;
            } else {
                player.sendMessage(new StringTextComponent("You aren't a high enough permission level to use that command."), player.getUniqueID());
                return 0;
            }
        }));

        dispatcher.register(Commands.literal(commandString).redirect(source));
    }

    public static void registerCommands(CommandDispatcher<CommandSource> dispatcher) {
        dimensionTeleportCommand(dispatcher);
    }
}
