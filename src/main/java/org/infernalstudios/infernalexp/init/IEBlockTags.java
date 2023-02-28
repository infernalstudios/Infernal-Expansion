/*
 * Copyright 2023 Infernal Studios
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

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.data.DataProviderCollection;
import org.infernalstudios.infernalexp.data.providers.IETagsProviders;

public class IEBlockTags {

    public static final DataProviderCollection<TagKey<Block>, IETagsProviders<Block>> TAGS = new DataProviderCollection<>();

    public static final TagKey<Block> BASE_STONE_CANYON = tag("base_stone_canyon", IETagsProviders.simple(
        IEBlocks.DIMSTONE.get(), IEBlocks.DULLSTONE.get()
    ));
    public static final TagKey<Block> BASE_STONE_SHORES = tag("base_stone_shores", IETagsProviders.simple(
        IEBlocks.SILT.get(), Blocks.BASALT
    ));
    public static final TagKey<Block> DULLTHORNS_GROUND = tag("dullthorns_ground", IETagsProviders.simple(
        IEBlocks.DIMSTONE.get(), IEBlocks.DULLSTONE.get(), IEBlocks.DULLTHORNS.get(), IEBlocks.GLOWDUST_SAND.get(),
        Blocks.COARSE_DIRT, Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM, Blocks.DIRT, Blocks.FARMLAND, Blocks.GLOWSTONE,
        Blocks.GRASS_BLOCK, Blocks.MYCELIUM, Blocks.PODZOL, Blocks.RED_SAND, Blocks.SAND, Blocks.SOUL_SAND, Blocks.SOUL_SOIL
    ));
    public static final TagKey<Block> GLOW_FIRE_BASE_BLOCKS = tag("glow_fire_base_blocks", IETagsProviders.simple(
        IEBlocks.GLOWDUST_SAND.get(), IEBlocks.GLOWDUST_STONE.get(), IEBlocks.GLOWDUST_STONE_BRICKS.get(), IEBlocks.GLOWDUST_STONE_BRICK_SLAB.get(),
        IEBlocks.GLOWDUST_STONE_BRICK_STAIRS.get(), IEBlocks.TRAPPED_GLOWDUST_SAND.get(), IEBlocks.LUMINOUS_WART_BLOCK.get()
    ));
    public static final TagKey<Block> MAGMA_CUBE_AVOID_BLOCKS = tag("magma_cube_avoid_blocks", IETagsProviders.simple(
        IEBlocks.GLOW_LANTERN.get(), IEBlocks.GLOW_TORCH.get(), IEBlocks.GLOW_TORCH_WALL.get(),
        IEBlocks.GLOW_CAMPFIRE.get(), IEBlocks.GLOW_FIRE.get()
    ));
    public static final TagKey<Block> BURIED_BONE_BASE_BLOCKS = tag("buried_bone_base_blocks", IETagsProviders.simple(
        IEBlocks.GLOWDUST_SAND.get(), IEBlocks.CRIMSON_FUNGUS_CAP.get(), IEBlocks.WARPED_FUNGUS_CAP.get(), IEBlocks.LUMINOUS_FUNGUS_CAP.get(),
        IEBlocks.SILT.get(), IEBlocks.RUBBLE.get(), Blocks.SAND, Blocks.RED_SAND, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
        Blocks.PODZOL, Blocks.MYCELIUM, Blocks.NETHERRACK, Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM, Blocks.NETHER_WART_BLOCK,
        Blocks.WARPED_WART_BLOCK, Blocks.SOUL_SAND, Blocks.SOUL_SOIL
    ));
    public static final TagKey<Block> PLANTED_QUARTZ_BASE_BLOCKS = tag("planted_quartz_base_blocks", (provider, tag) -> {
        provider.createTag(tag.get())
            .addTag(BlockTags.DIRT).addTag(BlockTags.SAND).addTag(BlockTags.NYLIUM).addTag(BlockTags.BASE_STONE_NETHER)
            .addTag(BlockTags.TERRACOTTA).addTag(Tags.Blocks.END_STONES).addTag(Tags.Blocks.COBBLESTONE).addTag(Tags.Blocks.SANDSTONE)
            .addTag(Tags.Blocks.ORES).addTag(Tags.Blocks.STONE).addTag(Tags.Blocks.NETHERRACK)
            .add(IEBlocks.GLOWDUST_SAND.get(), Blocks.QUARTZ_BLOCK, Blocks.CHISELED_QUARTZ_BLOCK, Blocks.SMOOTH_QUARTZ, Blocks.QUARTZ_BRICKS,
                Blocks.QUARTZ_PILLAR, Blocks.NETHER_QUARTZ_ORE, Blocks.SOUL_SAND, Blocks.SOUL_SOIL, Blocks.GLOWSTONE, Blocks.WHITE_GLAZED_TERRACOTTA,
                Blocks.ORANGE_GLAZED_TERRACOTTA, Blocks.MAGENTA_GLAZED_TERRACOTTA, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, Blocks.YELLOW_GLAZED_TERRACOTTA,
                Blocks.LIME_GLAZED_TERRACOTTA, Blocks.PINK_GLAZED_TERRACOTTA, Blocks.GRAY_GLAZED_TERRACOTTA, Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA,
                Blocks.CYAN_GLAZED_TERRACOTTA, Blocks.PURPLE_GLAZED_TERRACOTTA, Blocks.BLUE_GLAZED_TERRACOTTA, Blocks.BROWN_GLAZED_TERRACOTTA,
                Blocks.GREEN_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA, Blocks.BLACK_GLAZED_TERRACOTTA);
    });
    public static final TagKey<Block> ANGER_CRIMSON_SHROOMLOIN_BLOCKS = tag("anger_crimson_shroomloin_blocks", IETagsProviders.simple(
        IEBlocks.CRIMSON_FUNGUS_CAP.get(), Blocks.CRIMSON_ROOTS, Blocks.CRIMSON_FUNGUS
    ));
    public static final TagKey<Block> ANGER_WARPED_SHROOMLOIN_BLOCKS = tag("anger_warped_shroomloin_blocks", IETagsProviders.simple(
        IEBlocks.WARPED_FUNGUS_CAP.get(), Blocks.WARPED_ROOTS, Blocks.WARPED_FUNGUS
    ));
    public static final TagKey<Block> ANGER_LUMINOUS_SHROOMLOIN_BLOCKS = tag("anger_luminous_shroomloin_blocks", IETagsProviders.simple(
        IEBlocks.LUMINOUS_FUNGUS.get(), IEBlocks.LUMINOUS_FUNGUS_CAP.get(), IEBlocks.DULLTHORNS_BLOCK.get(), IEBlocks.DULLTHORNS.get()
    ));
    public static final TagKey<Block> ANGER_RED_SHROOMLOIN_BLOCKS = tag("anger_red_shroomloin_blocks", IETagsProviders.simple(
        Blocks.RED_MUSHROOM_BLOCK, Blocks.MUSHROOM_STEM
    ));
    public static final TagKey<Block> ANGER_BROWN_SHROOMLOIN_BLOCKS = tag("anger_brown_shroomloin_blocks", IETagsProviders.simple(
        Blocks.BROWN_MUSHROOM_BLOCK, Blocks.MUSHROOM_STEM
    ));
    public static final TagKey<Block> EMBODY_FALL_BLOCKS = tag("embody_fall_blocks", IETagsProviders.simple(
        IEBlocks.SOUL_SOIL_PATH.get(), Blocks.SOUL_SAND, Blocks.SOUL_SOIL
    ));
    public static final TagKey<Block> LUMINOUS_FUNGUS_SPAWNABLE_ON_BLOCKS = tag("luminous_fungus_spawnable_on_blocks", IETagsProviders.simple(
        IEBlocks.DULLSTONE.get(), IEBlocks.GLOWDUST_SAND.get()
    ));
    public static final TagKey<Block> LUMINOUS_FUNGUS_BASE_BLOCKS = tag("luminous_fungus_base_blocks", IETagsProviders.simple(
        IEBlocks.GLOWDUST_SAND.get(), IEBlocks.DIMSTONE.get(), IEBlocks.DULLSTONE.get(), IEBlocks.DULLTHORNS.get(),
        Blocks.SAND, Blocks.RED_SAND, Blocks.GRASS, Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.MYCELIUM, Blocks.CRIMSON_NYLIUM,
        Blocks.WARPED_NYLIUM, Blocks.SOUL_SOIL, Blocks.GLOWSTONE
    ));
    public static final TagKey<Block> SINK_HOLE_CARVABLE_BLOCKS = tag("sink_hole_carvable_blocks", IETagsProviders.simple(
        IEBlocks.DULLSTONE.get(), IEBlocks.DIMSTONE.get(), IEBlocks.GLOWDUST_SAND.get(), IEBlocks.GLOWDUST.get(),
        IEBlocks.GLOWDUST_STONE.get(), Blocks.GLOWSTONE, Blocks.BLACKSTONE, Blocks.LAVA, Blocks.NETHERRACK
    ));

    private static TagKey<Block> tag(String name, IETagsProviders.TagProviderConsumer<Block> tagProvider) {
        TagKey<Block> tag = BlockTags.create(new ResourceLocation(InfernalExpansion.MOD_ID, name));
        TAGS.addProvider(() -> tag, tagProvider);
        return tag;
    }

}
