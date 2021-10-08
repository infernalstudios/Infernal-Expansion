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

package org.infernalstudios.infernalexp.init;

import org.infernalstudios.infernalexp.InfernalExpansion;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;

public class IETags {

    public static class Blocks {

        public static final Tag.Named<Block> BASE_STONE_CANYON = tag("base_stone_canyon");
        public static final Tag.Named<Block> BASE_STONE_SHORES = tag("base_stone_shores");
        public static final Tag.Named<Block> DULLTHORNS_GROUND = tag("dullthorns_ground");
        public static final Tag.Named<Block> GLOW_FIRE_BASE_BLOCKS = tag("glow_fire_base_blocks");
        public static final Tag.Named<Block> MAGMA_CUBE_AVOID_BLOCKS = tag("magma_cube_avoid_blocks");
        public static final Tag.Named<Block> BURIED_BONE_BASE_BLOCKS = tag("buried_bone_base_blocks");
        public static final Tag.Named<Block> PLANTED_QUARTZ_BASE_BLOCKS = tag("planted_quartz_base_blocks");
        public static final Tag.Named<Block> ANGER_CRIMSON_SHROOMLOIN_BLOCKS = tag("anger_crimson_shroomloin_blocks");
        public static final Tag.Named<Block> ANGER_WARPED_SHROOMLOIN_BLOCKS = tag("anger_warped_shroomloin_blocks");
        public static final Tag.Named<Block> ANGER_LUMINOUS_SHROOMLOIN_BLOCKS = tag("anger_luminous_shroomloin_blocks");
        public static final Tag.Named<Block> ANGER_RED_SHROOMLOIN_BLOCKS = tag("anger_red_shroomloin_blocks");
        public static final Tag.Named<Block> ANGER_BROWN_SHROOMLOIN_BLOCKS = tag("anger_brown_shroomloin_blocks");
        public static final Tag.Named<Block> EMBODY_FALL_BLOCKS = tag("embody_fall_blocks");

        private static Tag.Named<Block> tag(String name) {
            return BlockTags.createOptional(new ResourceLocation(InfernalExpansion.MOD_ID, name));
        }
    }

    public static class Items {

        public static final Tag.Named<Item> GLOWSILK_REPAIR_BLACKLIST = tag("glowsilk_repair_blacklist");

        private static Tag.Named<Item> tag(String name) {
            return ItemTags.createOptional(new ResourceLocation(InfernalExpansion.MOD_ID, name));
        }
    }
}
