package com.nekomaster1000.infernalexp.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class DullthornsBlockItem extends BlockItemBase {

    public DullthornsBlockItem(Block block) {
        super(block);
    }

    @Override
    @Nullable
    public BlockItemUseContext getBlockItemUseContext(BlockItemUseContext context) {
        if (context.getFace() == Direction.UP) {
            return context;
        }

        World world = context.getWorld();
        Direction placedirection = context.isInside() ? context.getFace() : context.getFace().getOpposite();
        BlockPos placepos = context.getPos().offset(placedirection);
        int worldHeight = world.getHeight();
        BlockState dullthorns = this.getBlock().getDefaultState();

        while (world.getBlockState(placepos) == dullthorns) {
            placepos = placepos.up();

            // Prevent placing outside world
            if (!world.isRemote && !World.isValid(placepos)) {
                PlayerEntity player = context.getPlayer();
                if (player instanceof ServerPlayerEntity && placepos.getY() >= worldHeight) {
                   SChatPacket schatpacket = new SChatPacket((new TranslationTextComponent("build.tooHigh", worldHeight)).mergeStyle(TextFormatting.RED), ChatType.GAME_INFO, Util.DUMMY_UUID);
                   ((ServerPlayerEntity) player).connection.sendPacket(schatpacket);
                }
                return null;
             }
        }

        if (world.isAirBlock(placepos)) {
            return BlockItemUseContext.func_221536_a(context, placepos, placedirection);
        } else {
            return null;
        }

    }
}
