package com.nekomaster1000.infernalexp.blocks;

import com.nekomaster1000.infernalexp.tileentities.GlowCampfireTileEntity;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.CampfireBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class GlowCampfireBlock extends CampfireBlock {
    public GlowCampfireBlock(boolean smokey, int fireDamage, AbstractBlock.Properties properties) {
        super(smokey, fireDamage, properties);
    }

    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new GlowCampfireTileEntity();
    }
}
