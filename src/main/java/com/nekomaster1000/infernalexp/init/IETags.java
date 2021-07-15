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
        public static final ITag.INamedTag<Block> MAGMA_CUBE_AVOID_BLOCKS = tag("magma_cube_avoid_blocks");
        public static final ITag.INamedTag<Block> ANGER_CRIMSON_BLOCKS = tag("anger_crimson_blocks");
        public static final ITag.INamedTag<Block> ANGER_WARPED_BLOCKS = tag("anger_warped_blocks");
        public static final ITag.INamedTag<Block> ANGER_LUMINOUS_BLOCKS = tag("anger_luminous_blocks");
        public static final ITag.INamedTag<Block> ANGER_RED_MUSHROOM_BLOCKS = tag("anger_red_blocks");
        public static final ITag.INamedTag<Block> ANGER_BROWN_MUSHROOM_BLOCKS = tag("anger_brown_blocks");

        private static ITag.INamedTag<Block> tag(String name) {
            return BlockTags.createOptional(new ResourceLocation(InfernalExpansion.MOD_ID, name));
        }
    }
}
