package com.nekomaster1000.infernalexp.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class ModCommands {

    private static void dimensionTeleportCommand(CommandDispatcher<CommandSource> dispatcher) {
        String commandString = "ntp";

        LiteralCommandNode<CommandSource> source = dispatcher.register(Commands.literal(commandString).executes(command -> {
            ServerPlayerEntity player = command.getSource().asPlayer();

            if (player.hasPermissionLevel(3)) {
                if(player.getEntityWorld().getDimensionKey() ==  World.THE_NETHER) {
                    player.teleport(command.getSource().getServer().getWorld(World.OVERWORLD), player.getPosX(), player.getPosY(), player.getPosZ(), player.getYaw(0.0F), player.getPitch(0.0F));
                    return 1;
                }
                else{
                    player.teleport(command.getSource().getServer().getWorld(World.THE_NETHER), player.getPosX(), player.getPosY(), player.getPosZ(), player.getYaw(0.0F), player.getPitch(0.0F));
                    return 1;
                }
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
