package com.nekomaster1000.infernalexp.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class GlowCampfireBlock extends CampfireBlock {
    public GlowCampfireBlock(boolean smokey, int fireDamage, Properties properties) {
        super(smokey, fireDamage, properties);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new CampfireTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}
