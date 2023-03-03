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

import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.data.DataProviderCollection;
import org.infernalstudios.infernalexp.data.providers.IETagProviders;

public class IEBlockTags {

    public static final DataProviderCollection<TagKey<Block>, TagsProvider<Block>> TAGS = new DataProviderCollection<>();

    public static final TagKey<Block> BASE_STONE_CANYON = tag("base_stone_canyon", IETagProviders.simple(
        IEBlocks.DIMSTONE.get(), IEBlocks.DULLSTONE.get()
    ));
    public static final TagKey<Block> BASE_STONE_SHORES = tag("base_stone_shores", IETagProviders.simple(
        IEBlocks.SILT.get(), Blocks.BASALT
    ));
    public static final TagKey<Block> DULLTHORNS_GROUND = tag("dullthorns_ground", IETagProviders.simple(
        IEBlocks.DIMSTONE.get(), IEBlocks.DULLSTONE.get(), IEBlocks.DULLTHORNS.get(), IEBlocks.GLOWDUST_SAND.get(),
        Blocks.COARSE_DIRT, Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM, Blocks.DIRT, Blocks.FARMLAND, Blocks.GLOWSTONE,
        Blocks.GRASS_BLOCK, Blocks.MYCELIUM, Blocks.PODZOL, Blocks.RED_SAND, Blocks.SAND, Blocks.SOUL_SAND, Blocks.SOUL_SOIL
    ));
    public static final TagKey<Block> GLOW_FIRE_BASE_BLOCKS = tag("glow_fire_base_blocks", IETagProviders.simple(
        IEBlocks.GLOWDUST_SAND.get(), IEBlocks.GLOWDUST_STONE.get(), IEBlocks.GLOWDUST_STONE_BRICKS.get(), IEBlocks.GLOWDUST_STONE_BRICK_SLAB.get(),
        IEBlocks.GLOWDUST_STONE_BRICK_STAIRS.get(), IEBlocks.TRAPPED_GLOWDUST_SAND.get(), IEBlocks.LUMINOUS_WART_BLOCK.get()
    ));
    public static final TagKey<Block> MAGMA_CUBE_AVOID_BLOCKS = tag("magma_cube_avoid_blocks", IETagProviders.simple(
        IEBlocks.GLOW_LANTERN.get(), IEBlocks.GLOW_TORCH.get(), IEBlocks.GLOW_TORCH_WALL.get(),
        IEBlocks.GLOW_CAMPFIRE.get(), IEBlocks.GLOW_FIRE.get()
    ));
    public static final TagKey<Block> BURIED_BONE_BASE_BLOCKS = tag("buried_bone_base_blocks", IETagProviders.simple(
        IEBlocks.GLOWDUST_SAND.get(), IEBlocks.CRIMSON_FUNGUS_CAP.get(), IEBlocks.WARPED_FUNGUS_CAP.get(), IEBlocks.LUMINOUS_FUNGUS_CAP.get(),
        IEBlocks.SILT.get(), IEBlocks.RUBBLE.get(), Blocks.SAND, Blocks.RED_SAND, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT,
        Blocks.PODZOL, Blocks.MYCELIUM, Blocks.NETHERRACK, Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM, Blocks.NETHER_WART_BLOCK,
        Blocks.WARPED_WART_BLOCK, Blocks.SOUL_SAND, Blocks.SOUL_SOIL
    ));
    public static final TagKey<Block> PLANTED_QUARTZ_BASE_BLOCKS = tag("planted_quartz_base_blocks", (provider, tag) -> {
        provider.tag(tag.get())
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
    public static final TagKey<Block> ANGER_CRIMSON_SHROOMLOIN_BLOCKS = tag("anger_crimson_shroomloin_blocks", IETagProviders.simple(
        IEBlocks.CRIMSON_FUNGUS_CAP.get(), Blocks.CRIMSON_ROOTS, Blocks.CRIMSON_FUNGUS
    ));
    public static final TagKey<Block> ANGER_WARPED_SHROOMLOIN_BLOCKS = tag("anger_warped_shroomloin_blocks", IETagProviders.simple(
        IEBlocks.WARPED_FUNGUS_CAP.get(), Blocks.WARPED_ROOTS, Blocks.WARPED_FUNGUS
    ));
    public static final TagKey<Block> ANGER_LUMINOUS_SHROOMLOIN_BLOCKS = tag("anger_luminous_shroomloin_blocks", IETagProviders.simple(
        IEBlocks.LUMINOUS_FUNGUS.get(), IEBlocks.LUMINOUS_FUNGUS_CAP.get(), IEBlocks.DULLTHORNS_BLOCK.get(), IEBlocks.DULLTHORNS.get()
    ));
    public static final TagKey<Block> ANGER_RED_SHROOMLOIN_BLOCKS = tag("anger_red_shroomloin_blocks", IETagProviders.simple(
        Blocks.RED_MUSHROOM_BLOCK, Blocks.MUSHROOM_STEM
    ));
    public static final TagKey<Block> ANGER_BROWN_SHROOMLOIN_BLOCKS = tag("anger_brown_shroomloin_blocks", IETagProviders.simple(
        Blocks.BROWN_MUSHROOM_BLOCK, Blocks.MUSHROOM_STEM
    ));
    public static final TagKey<Block> EMBODY_FALL_BLOCKS = tag("embody_fall_blocks", IETagProviders.simple(
        IEBlocks.SOUL_SOIL_PATH.get(), Blocks.SOUL_SAND, Blocks.SOUL_SOIL
    ));
    public static final TagKey<Block> LUMINOUS_FUNGUS_SPAWNABLE_ON_BLOCKS = tag("luminous_fungus_spawnable_on_blocks", IETagProviders.simple(
        IEBlocks.DULLSTONE.get(), IEBlocks.GLOWDUST_SAND.get()
    ));
    public static final TagKey<Block> LUMINOUS_FUNGUS_BASE_BLOCKS = tag("luminous_fungus_base_blocks", IETagProviders.simple(
        IEBlocks.GLOWDUST_SAND.get(), IEBlocks.DIMSTONE.get(), IEBlocks.DULLSTONE.get(), IEBlocks.DULLTHORNS.get(),
        Blocks.SAND, Blocks.RED_SAND, Blocks.GRASS, Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.MYCELIUM, Blocks.CRIMSON_NYLIUM,
        Blocks.WARPED_NYLIUM, Blocks.SOUL_SOIL, Blocks.GLOWSTONE
    ));
    public static final TagKey<Block> SINK_HOLE_CARVABLE_BLOCKS = tag("sink_hole_carvable_blocks", IETagProviders.simple(
        IEBlocks.DULLSTONE.get(), IEBlocks.DIMSTONE.get(), IEBlocks.GLOWDUST_SAND.get(), IEBlocks.GLOWDUST.get(),
        IEBlocks.GLOWDUST_STONE.get(), Blocks.GLOWSTONE, Blocks.BLACKSTONE, Blocks.LAVA, Blocks.NETHERRACK
    ));

    static {
        overrideVanilla("base_stone_nether", IETagProviders.simple(IEBlocks.DIMSTONE.get(), IEBlocks.DULLSTONE.get()));
        overrideVanilla("buttons", IETagProviders.simple(
            IEBlocks.SMOOTH_GLOWSTONE_BUTTON.get(), IEBlocks.SMOOTH_DIMSTONE_BUTTON.get(), IEBlocks.SMOOTH_DULLSTONE_BUTTON.get(),
            IEBlocks.BASALT_BUTTON.get(), IEBlocks.SOUL_SLATE_BUTTON.get()
        ));
        overrideVanilla("campfire", IETagProviders.simple(IEBlocks.GLOW_CAMPFIRE.get()));
        overrideVanilla("logs", IETagProviders.simple(
            IEBlocks.LUMINOUS_HYPHAE.get(), IEBlocks.LUMINOUS_STEM.get(),
            IEBlocks.STRIPPED_LUMINOUS_HYPHAE.get(), IEBlocks.STRIPPED_LUMINOUS_STEM.get()
        ));
        overrideVanilla("needs_diamond_tool", IETagProviders.simple(IEBlocks.GLOWSILK_COCOON.get()));
        overrideVanilla("slabs", IETagProviders.simple(
            IEBlocks.SMOOTH_GLOWSTONE_SLAB.get(), IEBlocks.SMOOTH_DIMSTONE_SLAB.get(), IEBlocks.SMOOTH_DULLSTONE_SLAB.get(),
            IEBlocks.GLOWSTONE_BRICK_SLAB.get(), IEBlocks.DIMSTONE_BRICK_SLAB.get(), IEBlocks.DULLSTONE_BRICK_SLAB.get(),
            IEBlocks.GLOWDUST_STONE_SLAB.get(), IEBlocks.GLOWDUST_STONE_BRICK_SLAB.get(), IEBlocks.BASALT_SLAB.get(),
            IEBlocks.BASALT_COBBLED_SLAB.get(), IEBlocks.POLISHED_BASALT_SLAB.get(), IEBlocks.POLISHED_BASALT_TILES_SLAB.get(),
            IEBlocks.BASALT_BRICK_SLAB.get(), IEBlocks.SOUL_SAND_SLAB.get(), IEBlocks.SOUL_SOIL_SLAB.get(),
            IEBlocks.SOUL_STONE_SLAB.get(), IEBlocks.SOUL_SLATE_SLAB.get(), IEBlocks.SOUL_STONE_BRICK_SLAB.get(),
            IEBlocks.SOUL_SLATE_BRICK_SLAB.get()
        ));
        overrideVanilla("soul_fire_base_blocks", IETagProviders.simple(
            IEBlocks.SOUL_SAND_SLAB.get(), IEBlocks.SOUL_SAND_STAIRS.get(), IEBlocks.SOUL_SOIL_SLAB.get(), IEBlocks.SOUL_SOIL_STAIRS.get(),
            IEBlocks.SOUL_STONE.get(), IEBlocks.SOUL_STONE_SLAB.get(), IEBlocks.SOUL_STONE_STAIRS.get(),
            IEBlocks.SOUL_SLATE.get(), IEBlocks.SOUL_SLATE_SLAB.get(), IEBlocks.SOUL_SLATE_STAIRS.get(), IEBlocks.SOUL_SLATE_PRESSURE_PLATE.get(),
            IEBlocks.SOUL_STONE_BRICKS.get(), IEBlocks.SOUL_STONE_BRICK_SLAB.get(), IEBlocks.SOUL_STONE_BRICK_STAIRS.get(),
            IEBlocks.CRACKED_SOUL_STONE_BRICKS.get(), IEBlocks.CHISELED_SOUL_STONE_BRICKS.get(), IEBlocks.CHARGED_CHISELED_SOUL_STONE_BRICKS.get(),
            IEBlocks.SOUL_SLATE_BRICKS.get(), IEBlocks.SOUL_SLATE_BRICK_SLAB.get(), IEBlocks.SOUL_SLATE_BRICK_STAIRS.get(),
            IEBlocks.CRACKED_SOUL_SLATE_BRICKS.get(), IEBlocks.CHISELED_SOUL_SLATE_BRICKS.get(), IEBlocks.CHARGED_CHISELED_SOUL_SLATE_BRICKS.get()
        ));
        overrideVanilla("soul_speed_blocks", IETagProviders.simple(
            IEBlocks.SOUL_SAND_SLAB.get(), IEBlocks.SOUL_SAND_STAIRS.get(), IEBlocks.SOUL_SOIL_SLAB.get(), IEBlocks.SOUL_SOIL_STAIRS.get(),
            IEBlocks.SOUL_STONE.get(), IEBlocks.SOUL_STONE_SLAB.get(), IEBlocks.SOUL_STONE_STAIRS.get(),
            IEBlocks.SOUL_SLATE.get(), IEBlocks.SOUL_SLATE_SLAB.get(), IEBlocks.SOUL_SLATE_STAIRS.get(), IEBlocks.SOUL_SLATE_PRESSURE_PLATE.get(),
            IEBlocks.SOUL_STONE_BRICKS.get(), IEBlocks.SOUL_STONE_BRICK_SLAB.get(), IEBlocks.SOUL_STONE_BRICK_STAIRS.get(),
            IEBlocks.CRACKED_SOUL_STONE_BRICKS.get(), IEBlocks.CHISELED_SOUL_STONE_BRICKS.get(), IEBlocks.CHARGED_CHISELED_SOUL_STONE_BRICKS.get(),
            IEBlocks.SOUL_SLATE_BRICKS.get(), IEBlocks.SOUL_SLATE_BRICK_SLAB.get(), IEBlocks.SOUL_SLATE_BRICK_STAIRS.get(),
            IEBlocks.CRACKED_SOUL_SLATE_BRICKS.get(), IEBlocks.CHISELED_SOUL_SLATE_BRICKS.get(), IEBlocks.CHARGED_CHISELED_SOUL_SLATE_BRICKS.get()
        ));
        overrideVanilla("stairs", IETagProviders.simple(
            IEBlocks.SMOOTH_GLOWSTONE_STAIRS.get(), IEBlocks.SMOOTH_DIMSTONE_STAIRS.get(), IEBlocks.SMOOTH_DULLSTONE_STAIRS.get(),
            IEBlocks.GLOWSTONE_BRICK_STAIRS.get(), IEBlocks.DIMSTONE_BRICK_STAIRS.get(), IEBlocks.DULLSTONE_BRICK_STAIRS.get(),
            IEBlocks.GLOWDUST_STONE_STAIRS.get(), IEBlocks.GLOWDUST_STONE_BRICK_STAIRS.get(), IEBlocks.BASALT_STAIRS.get(),
            IEBlocks.BASALT_BRICK_STAIRS.get(), IEBlocks.SOUL_SAND_STAIRS.get(), IEBlocks.SOUL_SOIL_STAIRS.get(),
            IEBlocks.SOUL_STONE_STAIRS.get(), IEBlocks.SOUL_SLATE_STAIRS.get(), IEBlocks.SOUL_STONE_BRICK_STAIRS.get(),
            IEBlocks.SOUL_SLATE_BRICK_STAIRS.get()
        ));
        overrideVanilla("stone_pressure_plates", IETagProviders.simple(
            IEBlocks.SMOOTH_GLOWSTONE_PRESSURE_PLATE.get(), IEBlocks.POLISHED_BASALT_PRESSURE_PLATE.get(), IEBlocks.SOUL_SLATE_PRESSURE_PLATE.get()
        ));
        overrideVanilla("strider_warm_blocks", IETagProviders.simple(Blocks.MAGMA_BLOCK, IEBlocks.BASALTIC_MAGMA.get()));
        overrideVanilla("walls", IETagProviders.simple(
            IEBlocks.GLOWSTONE_BRICK_WALL.get(), IEBlocks.DIMSTONE_BRICK_WALL.get(), IEBlocks.DULLSTONE_BRICK_WALL.get(),
            IEBlocks.GLOWDUST_STONE_WALL.get(), IEBlocks.GLOWDUST_STONE_BRICK_WALL.get(), IEBlocks.BASALT_WALL.get(),
            IEBlocks.BASALT_BRICK_WALL.get(), IEBlocks.SOUL_STONE_WALL.get(), IEBlocks.SOUL_STONE_BRICK_WALL.get(),
            IEBlocks.SOUL_SLATE_BRICK_WALL.get()
        ));
        overrideVanilla("wart_blocks", IETagProviders.simple(IEBlocks.LUMINOUS_WART_BLOCK.get()));

        overrideVanilla("mineable/hoe", IETagProviders.simple(
            IEBlocks.LUMINOUS_WART_BLOCK.get(), IEBlocks.CRIMSON_FUNGUS_CAP.get(), IEBlocks.WARPED_FUNGUS_CAP.get(),
            IEBlocks.LUMINOUS_FUNGUS_CAP.get(), IEBlocks.DULLTHORNS_BLOCK.get(), IEBlocks.GLOWSILK_COCOON.get()
        ));
        overrideVanilla("mineable/pickaxe", IETagProviders.simple(
            IEBlocks.DIMSTONE.get(), IEBlocks.DULLSTONE.get(), IEBlocks.SMOOTH_GLOWSTONE.get(), IEBlocks.SMOOTH_DIMSTONE.get(),
            IEBlocks.SMOOTH_DULLSTONE.get(), IEBlocks.GLOWSTONE_BRICKS.get(), IEBlocks.DIMSTONE_BRICKS.get(),
            IEBlocks.DULLSTONE_BRICKS.get(), IEBlocks.CRACKED_GLOWSTONE_BRICKS.get(), IEBlocks.CRACKED_DIMSTONE_BRICKS.get(),
            IEBlocks.CRACKED_DULLSTONE_BRICKS.get(), IEBlocks.CHISELED_GLOWSTONE_BRICKS.get(), IEBlocks.CHISELED_DIMSTONE_BRICKS.get(),
            IEBlocks.CHISELED_DULLSTONE_BRICKS.get(), IEBlocks.SMOOTH_GLOWSTONE_SLAB.get(), IEBlocks.SMOOTH_GLOWSTONE_VERTICAL_SLAB.get(),
            IEBlocks.SMOOTH_GLOWSTONE_STAIRS.get(), IEBlocks.SMOOTH_GLOWSTONE_BUTTON.get(), IEBlocks.SMOOTH_GLOWSTONE_PRESSURE_PLATE.get(),
            IEBlocks.SMOOTH_DIMSTONE_SLAB.get(), IEBlocks.SMOOTH_DIMSTONE_VERTICAL_SLAB.get(), IEBlocks.SMOOTH_DIMSTONE_STAIRS.get(),
            IEBlocks.SMOOTH_DIMSTONE_BUTTON.get(), IEBlocks.SMOOTH_DULLSTONE_SLAB.get(), IEBlocks.SMOOTH_DULLSTONE_VERTICAL_SLAB.get(),
            IEBlocks.SMOOTH_DULLSTONE_STAIRS.get(), IEBlocks.SMOOTH_DULLSTONE_BUTTON.get(), IEBlocks.GLOWSTONE_BRICK_SLAB.get(),
            IEBlocks.GLOWSTONE_BRICK_VERTICAL_SLAB.get(), IEBlocks.GLOWSTONE_BRICK_STAIRS.get(), IEBlocks.GLOWSTONE_BRICK_WALL.get(),
            IEBlocks.DIMSTONE_BRICK_SLAB.get(), IEBlocks.DIMSTONE_BRICK_VERTICAL_SLAB.get(), IEBlocks.DIMSTONE_BRICK_STAIRS.get(),
            IEBlocks.DIMSTONE_BRICK_WALL.get(), IEBlocks.DULLSTONE_BRICK_SLAB.get(), IEBlocks.DULLSTONE_BRICK_VERTICAL_SLAB.get(),
            IEBlocks.DULLSTONE_BRICK_STAIRS.get(), IEBlocks.DULLSTONE_BRICK_WALL.get(), IEBlocks.GLOWDUST_STONE.get(),
            IEBlocks.GLOWDUST_STONE_SLAB.get(), IEBlocks.GLOWDUST_STONE_VERTICAL_SLAB.get(), IEBlocks.GLOWDUST_STONE_STAIRS.get(),
            IEBlocks.GLOWDUST_STONE_WALL.get(), IEBlocks.GLOWDUST_STONE_BRICKS.get(), IEBlocks.GLOWDUST_STONE_BRICK_SLAB.get(),
            IEBlocks.GLOWDUST_STONE_BRICK_VERTICAL_SLAB.get(), IEBlocks.GLOWDUST_STONE_BRICK_STAIRS.get(), IEBlocks.GLOWDUST_STONE_BRICK_WALL.get(),
            IEBlocks.CRACKED_GLOWDUST_STONE_BRICKS.get(), IEBlocks.CHISELED_GLOWDUST_STONE_BRICKS.get(), IEBlocks.CRUMBLING_BLACKSTONE.get(),
            IEBlocks.BASALT_SLAB.get(), IEBlocks.BASALT_VERTICAL_SLAB.get(), IEBlocks.BASALT_STAIRS.get(), IEBlocks.BASALT_WALL.get(),
            IEBlocks.BASALT_BUTTON.get(), IEBlocks.POLISHED_BASALT_PRESSURE_PLATE.get(), IEBlocks.POLISHED_BASALT_SLAB.get(),
            IEBlocks.POLISHED_BASALT_VERTICAL_SLAB.get(), IEBlocks.POLISHED_BASALT_TILES.get(), IEBlocks.POLISHED_BASALT_TILES_SLAB.get(),
            IEBlocks.POLISHED_BASALT_TILES_VERTICAL_SLAB.get(), IEBlocks.BASALT_BRICKS.get(), IEBlocks.BASALT_BRICK_SLAB.get(),
            IEBlocks.BASALT_BRICK_VERTICAL_SLAB.get(), IEBlocks.BASALT_BRICK_STAIRS.get(), IEBlocks.BASALT_BRICK_WALL.get(),
            IEBlocks.CRACKED_BASALT_BRICKS.get(), IEBlocks.CHISELED_BASALT_BRICKS.get(), IEBlocks.MAGMATIC_CHISELED_BASALT_BRICKS.get(),
            IEBlocks.BASALT_IRON_ORE.get(), IEBlocks.BASALTIC_MAGMA.get(), IEBlocks.SOUL_STONE.get(), IEBlocks.SOUL_STONE_SLAB.get(),
            IEBlocks.SOUL_STONE_VERTICAL_SLAB.get(), IEBlocks.SOUL_STONE_STAIRS.get(), IEBlocks.SOUL_STONE_WALL.get(),
            IEBlocks.SOUL_SLATE.get(), IEBlocks.SOUL_SLATE_SLAB.get(), IEBlocks.SOUL_SLATE_VERTICAL_SLAB.get(), IEBlocks.SOUL_SLATE_STAIRS.get(),
            IEBlocks.SOUL_SLATE_BUTTON.get(), IEBlocks.SOUL_SLATE_PRESSURE_PLATE.get(), IEBlocks.SOUL_STONE_BRICKS.get(),
            IEBlocks.SOUL_STONE_BRICK_SLAB.get(), IEBlocks.SOUL_STONE_BRICK_VERTICAL_SLAB.get(), IEBlocks.SOUL_STONE_BRICK_STAIRS.get(),
            IEBlocks.SOUL_STONE_BRICK_WALL.get(), IEBlocks.CRACKED_SOUL_STONE_BRICKS.get(), IEBlocks.CHISELED_SOUL_STONE_BRICKS.get(),
            IEBlocks.CHARGED_CHISELED_SOUL_STONE_BRICKS.get(), IEBlocks.SOUL_SLATE_BRICKS.get(), IEBlocks.SOUL_SLATE_BRICK_SLAB.get(),
            IEBlocks.SOUL_SLATE_BRICK_VERTICAL_SLAB.get(), IEBlocks.SOUL_SLATE_BRICK_STAIRS.get(), IEBlocks.SOUL_SLATE_BRICK_WALL.get(),
            IEBlocks.CRACKED_SOUL_SLATE_BRICKS.get(), IEBlocks.CHISELED_SOUL_SLATE_BRICKS.get(), IEBlocks.CHARGED_CHISELED_SOUL_SLATE_BRICKS.get(),
            IEBlocks.GLOW_LANTERN.get(), IEBlocks.BURIED_BONE.get(), IEBlocks.PLANTED_QUARTZ.get(),
            IEBlocks.CRIMSON_NYLIUM_PATH.get(), IEBlocks.WARPED_NYLIUM_PATH.get()
        ));
        overrideVanilla("mineable/shovel", IETagProviders.simple(
            IEBlocks.GLOWDUST.get(), IEBlocks.GLOWDUST_SAND.get(), IEBlocks.TRAPPED_GLOWDUST_SAND.get(), IEBlocks.SOUL_SOIL_PATH.get(),
            IEBlocks.RUBBLE.get(), IEBlocks.SILT.get(), IEBlocks.BASALT_COBBLED.get(), IEBlocks.BASALT_COBBLED_SLAB.get(),
            IEBlocks.BASALT_COBBLED_VERTICAL_SLAB.get(), IEBlocks.SOUL_SAND_SLAB.get(), IEBlocks.SOUL_SAND_VERTICAL_SLAB.get(),
            IEBlocks.SOUL_SAND_STAIRS.get(), IEBlocks.SOUL_SOIL_SLAB.get(), IEBlocks.SOUL_SOIL_VERTICAL_SLAB.get(),
            IEBlocks.SOUL_SOIL_STAIRS.get()
        ));

        override("forge", "needs_wood_tool", IETagProviders.simple(IEBlocks.PLANTED_QUARTZ.get()));
        override("forge", "ores/iron", IETagProviders.simple(IEBlocks.BASALT_IRON_ORE.get()));
        override("quark", "vertical_slab", IETagProviders.simple(
            IEBlocks.SMOOTH_GLOWSTONE_VERTICAL_SLAB.get(), IEBlocks.SMOOTH_DIMSTONE_VERTICAL_SLAB.get(), IEBlocks.SMOOTH_DULLSTONE_VERTICAL_SLAB.get(),
            IEBlocks.GLOWSTONE_BRICK_VERTICAL_SLAB.get(), IEBlocks.DIMSTONE_BRICK_VERTICAL_SLAB.get(), IEBlocks.DULLSTONE_BRICK_VERTICAL_SLAB.get(),
            IEBlocks.GLOWDUST_STONE_VERTICAL_SLAB.get(), IEBlocks.GLOWDUST_STONE_BRICK_VERTICAL_SLAB.get(), IEBlocks.BASALT_COBBLED_VERTICAL_SLAB.get(),
            IEBlocks.BASALT_VERTICAL_SLAB.get(), IEBlocks.POLISHED_BASALT_VERTICAL_SLAB.get(), IEBlocks.POLISHED_BASALT_TILES_VERTICAL_SLAB.get(),
            IEBlocks.BASALT_BRICK_VERTICAL_SLAB.get(), IEBlocks.SOUL_SAND_VERTICAL_SLAB.get(), IEBlocks.SOUL_SOIL_VERTICAL_SLAB.get(),
            IEBlocks.SOUL_STONE_VERTICAL_SLAB.get(), IEBlocks.SOUL_SLATE_VERTICAL_SLAB.get(), IEBlocks.SOUL_STONE_BRICK_VERTICAL_SLAB.get(),
            IEBlocks.SOUL_SLATE_BRICK_VERTICAL_SLAB.get()
        ));
    }

    private static TagKey<Block> tag(String name, IETagProviders.TagProviderConsumer<Block> tagProvider) {
        TagKey<Block> tag = BlockTags.create(new ResourceLocation(InfernalExpansion.MOD_ID, name));
        TAGS.addProvider(() -> tag, tagProvider);
        return tag;
    }

    private static void override(String namespace, String name, IETagProviders.TagProviderConsumer<Block> tagProvider) {
        TAGS.addProvider(() -> BlockTags.create(new ResourceLocation(namespace, name)), tagProvider);
    }

    private static void overrideVanilla(String name, IETagProviders.TagProviderConsumer<Block> tagProvider) {
        override("minecraft", name, tagProvider);
    }

}
