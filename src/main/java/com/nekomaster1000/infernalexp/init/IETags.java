package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

public class IETags {

    public static class Blocks {

        public static final ITag.INamedTag<Block> BASE_STONE_CANYON = tag("base_stone_canyon");
        public static final ITag.INamedTag<Block> BASE_STONE_SHORES = tag("base_stone_shores");
        public static final ITag.INamedTag<Block> GLOW_FIRE_BASE_BLOCKS = tag("glow_fire_base_blocks");
        public static final ITag.INamedTag<Block> MAGMA_CUBE_REPELLENTS = tag("magma_cube_repellents");

        private static ITag.INamedTag<Block> tag(String name) {
            return BlockTags.createOptional(new ResourceLocation(InfernalExpansion.MOD_ID, name));
        }
    }
}
