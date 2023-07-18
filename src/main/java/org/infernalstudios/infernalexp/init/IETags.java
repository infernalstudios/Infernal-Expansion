/*
 * Copyright 2022 Infernal Studios
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

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import org.infernalstudios.infernalexp.InfernalExpansion;

public class IETags {

    @SuppressWarnings("unused")
    public static class Blocks {

        public static final TagKey<Block> BASE_STONE_CANYON = tag("base_stone_canyon");
        public static final TagKey<Block> BASE_STONE_SHORES = tag("base_stone_shores");
        public static final TagKey<Block> DULLTHORNS_GROUND = tag("dullthorns_ground");
        public static final TagKey<Block> GLOW_FIRE_BASE_BLOCKS = tag("glow_fire_base_blocks");
        public static final TagKey<Block> MAGMA_CUBE_AVOID_BLOCKS = tag("magma_cube_avoid_blocks");
        public static final TagKey<Block> BURIED_BONE_BASE_BLOCKS = tag("buried_bone_base_blocks");
        public static final TagKey<Block> PLANTED_QUARTZ_BASE_BLOCKS = tag("planted_quartz_base_blocks");
        public static final TagKey<Block> ANGER_CRIMSON_SHROOMLOIN_BLOCKS = tag("anger_crimson_shroomloin_blocks");
        public static final TagKey<Block> ANGER_WARPED_SHROOMLOIN_BLOCKS = tag("anger_warped_shroomloin_blocks");
        public static final TagKey<Block> ANGER_LUMINOUS_SHROOMLOIN_BLOCKS = tag("anger_luminous_shroomloin_blocks");
        public static final TagKey<Block> ANGER_RED_SHROOMLOIN_BLOCKS = tag("anger_red_shroomloin_blocks");
        public static final TagKey<Block> ANGER_BROWN_SHROOMLOIN_BLOCKS = tag("anger_brown_shroomloin_blocks");
        public static final TagKey<Block> EMBODY_FALL_BLOCKS = tag("embody_fall_blocks");
        public static final TagKey<Block> LUMINOUS_FUNGUS_SPAWNABLE_ON_BLOCKS = tag("luminous_fungus_spawnable_on_blocks");
        public static final TagKey<Block> LUMINOUS_FUNGUS_BASE_BLOCKS = tag("luminous_fungus_base_blocks");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(InfernalExpansion.MOD_ID, name));
        }

    }

    public static class Items {

        public static final TagKey<Item> GLOWSILK_REPAIR_BLACKLIST = tag("glowsilk_repair_blacklist");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(InfernalExpansion.MOD_ID, name));
        }
    }

    public static class Biomes {

        public static final TagKey<Biome> HAS_GLOWSTONE_CANYON_RUIN = tag("has_structure/glowstone_canyon_ruin");
        public static final TagKey<Biome> HAS_SOUL_SAND_VALLEY_RUIN = tag("has_structure/soul_sand_valley_ruin");
        public static final TagKey<Biome> HAS_BASTION_OUTPOST = tag("has_structure/bastion_outpost");
        public static final TagKey<Biome> HAS_BASALT_GIANT_MOAI = tag("has_structure/basalt_giant_moai");
        public static final TagKey<Biome> HAS_STRIDER_ALTAR = tag("has_structure/strider_altar");

        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(InfernalExpansion.MOD_ID, name));
        }

    }

    public static class Structures {

        public static final TagKey<ConfiguredStructureFeature<?, ?>> NO_INTERSECTING_FEATURES = tag("no_intersecting_features");

        private static TagKey<ConfiguredStructureFeature<?, ?>> tag(String name) {
            return TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation(InfernalExpansion.MOD_ID, name));
        }

    }

}
