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

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StoneButtonBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.blocks.BasaltIronOreBlock;
import org.infernalstudios.infernalexp.blocks.BasalticMagmaBlock;
import org.infernalstudios.infernalexp.blocks.BuriedBoneBlock;
import org.infernalstudios.infernalexp.blocks.CrumblingBlackstoneBlock;
import org.infernalstudios.infernalexp.blocks.DullthornsBlock;
import org.infernalstudios.infernalexp.blocks.DullthornsBlockBlock;
import org.infernalstudios.infernalexp.blocks.FungusCapBlock;
import org.infernalstudios.infernalexp.blocks.GlowCampfireBlock;
import org.infernalstudios.infernalexp.blocks.GlowFireBlock;
import org.infernalstudios.infernalexp.blocks.GlowSandBlock;
import org.infernalstudios.infernalexp.blocks.GlowTorchBlock;
import org.infernalstudios.infernalexp.blocks.GlowWallTorchBlock;
import org.infernalstudios.infernalexp.blocks.GlowdustBlock;
import org.infernalstudios.infernalexp.blocks.LightUpPressurePlateBlock;
import org.infernalstudios.infernalexp.blocks.LuminousFungusBlock;
import org.infernalstudios.infernalexp.blocks.NetherCarpetBlock;
import org.infernalstudios.infernalexp.blocks.NetherrackPathBlock;
import org.infernalstudios.infernalexp.blocks.PlantedQuartzBlock;
import org.infernalstudios.infernalexp.blocks.ShroomlightFungusBlock;
import org.infernalstudios.infernalexp.blocks.SoulSoilPathBlock;
import org.infernalstudios.infernalexp.blocks.TrappedGlowSandBlock;
import org.infernalstudios.infernalexp.blocks.VerticalSlabBlock;
import org.infernalstudios.infernalexp.data.DataGenDeferredRegister;
import org.infernalstudios.infernalexp.data.providers.IEBlockProviders;

import javax.annotation.Nullable;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

@SuppressWarnings("unused")
public class IEBlocks {

//    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, InfernalExpansion.MOD_ID);
    public static final DataGenDeferredRegister<Block, BlockStateProvider> BLOCKS = new DataGenDeferredRegister<>(ForgeRegistries.BLOCKS);

  /*			single file in /blockstates pulls from (potentially multiple files in)
    			/model/block, which pulls from /textures/blocks all the different ways a block can look.
    			(If a block is animated, pull the name of the animation texture.)
    			/item pulls from the individual json file in /model/block.

    			If the block is a SLAB, STAIR, BUTTON, PRESSURE PLATE, WALL, or WART BLOCK, add it to the corresponding tag file
    			in data/minecraft.tags.

				If the block is a VERTICAL SLAB, add it to the corresponding tag file for quark in data/quark.tags.
				
				Make sure tags for blocks have both .tags.block and .tags.items if they exist in the respective categories.

    			When that's done, make sure they have a LOOT TABLE, found in data/infernalexp/loot_tables.
    			For RECIPES, make sure the block is craftable in some form if not a biome-building block.
    			If it's a type of stone or has bricks, give it a STONE-CUTTER recipe.
    			Furnace Recipes are included in the recipes folder. */

    // Blocks
    public static final RegistryObject<Block> DIMSTONE = registerBlockWithDefaultItem("dimstone", () -> new Block(getProperties(Material.GLASS, 1.8F, 2.0F).sound(IESoundEvents.DIMSTONE_TYPE).requiresCorrectToolForDrops().lightLevel(value -> 12)), IEBlockProviders.enumerateVariants(25));
    public static final RegistryObject<Block> DULLSTONE = registerBlockWithDefaultItem("dullstone", () -> new Block(getProperties(Material.GLASS, 1.5F, 6.0F).sound(IESoundEvents.DULLSTONE_TYPE).requiresCorrectToolForDrops()), IEBlockProviders.simple());
    public static final RegistryObject<Block> SMOOTH_GLOWSTONE = registerBlockWithDefaultItem("smooth_glowstone", () -> new Block(getProperties(Material.GLASS, 1.5F, 6.0F).sound(SoundType.GLASS).requiresCorrectToolForDrops().lightLevel(value -> 15)), IEBlockProviders.simple());
    public static final RegistryObject<Block> SMOOTH_DIMSTONE = registerBlockWithDefaultItem("smooth_dimstone", () -> new Block(getProperties(DIMSTONE.get()).strength(1.5F, 6.0F).lightLevel(value -> 12)), IEBlockProviders.simple());
    public static final RegistryObject<Block> SMOOTH_DULLSTONE = registerBlockWithDefaultItem("smooth_dullstone", () -> new Block(getProperties(DULLSTONE.get()).strength(1.5F, 6.0F)), IEBlockProviders.simple());
    public static final RegistryObject<Block> GLOWSTONE_BRICKS = registerBlockWithDefaultItem("glowstone_bricks", () -> new Block(getProperties(SMOOTH_GLOWSTONE.get())), IEBlockProviders.simple());
    public static final RegistryObject<Block> DIMSTONE_BRICKS = registerBlockWithDefaultItem("dimstone_bricks", () -> new Block(getProperties(SMOOTH_DIMSTONE.get())), IEBlockProviders.simple());
    public static final RegistryObject<Block> DULLSTONE_BRICKS = registerBlockWithDefaultItem("dullstone_bricks", () -> new Block(getProperties(SMOOTH_DULLSTONE.get())), IEBlockProviders.simple());

    public static final RegistryObject<Block> CRACKED_GLOWSTONE_BRICKS = registerBlockWithDefaultItem("cracked_glowstone_bricks", () -> new Block(getProperties(SMOOTH_GLOWSTONE.get())), IEBlockProviders.simple());
    public static final RegistryObject<Block> CRACKED_DIMSTONE_BRICKS = registerBlockWithDefaultItem("cracked_dimstone_bricks", () -> new Block(getProperties(SMOOTH_DIMSTONE.get())), IEBlockProviders.simple());
    public static final RegistryObject<Block> CRACKED_DULLSTONE_BRICKS = registerBlockWithDefaultItem("cracked_dullstone_bricks", () -> new Block(getProperties(SMOOTH_DULLSTONE.get())), IEBlockProviders.simple());

    public static final RegistryObject<Block> CHISELED_GLOWSTONE_BRICKS = registerBlockWithDefaultItem("chiseled_glowstone_bricks", () -> new Block(getProperties(SMOOTH_GLOWSTONE.get())), IEBlockProviders.simple());
    public static final RegistryObject<Block> CHISELED_DIMSTONE_BRICKS = registerBlockWithDefaultItem("chiseled_dimstone_bricks", () -> new Block(getProperties(SMOOTH_DIMSTONE.get())), IEBlockProviders.simple());
    public static final RegistryObject<Block> CHISELED_DULLSTONE_BRICKS = registerBlockWithDefaultItem("chiseled_dullstone_bricks", () -> new Block(getProperties(SMOOTH_DULLSTONE.get())), IEBlockProviders.simple());

    public static final RegistryObject<Block> SMOOTH_GLOWSTONE_SLAB = registerBlockWithDefaultItem("smooth_glowstone_slab", () -> new SlabBlock(getProperties(SMOOTH_GLOWSTONE.get())), IEBlockProviders.slab(SMOOTH_GLOWSTONE));
    public static final RegistryObject<Block> SMOOTH_GLOWSTONE_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("smooth_glowstone_vertical_slab", () -> new VerticalSlabBlock(getProperties(SMOOTH_GLOWSTONE.get())), "quark", IEBlockProviders.verticalSlab(SMOOTH_GLOWSTONE));
    public static final RegistryObject<Block> SMOOTH_GLOWSTONE_STAIRS = registerBlockWithDefaultItem("smooth_glowstone_stairs", () -> new StairBlock(() -> SMOOTH_GLOWSTONE.get().defaultBlockState(), getProperties(SMOOTH_GLOWSTONE.get())), IEBlockProviders.stairs(SMOOTH_GLOWSTONE));
    public static final RegistryObject<Block> SMOOTH_GLOWSTONE_BUTTON = registerBlockWithDefaultItem("smooth_glowstone_button", () -> new StoneButtonBlock(getProperties(Material.DECORATION, 0.5F).sound(SoundType.GLASS).lightLevel(value -> 15).noCollission()), IEBlockProviders.button(SMOOTH_GLOWSTONE));
    public static final RegistryObject<Block> SMOOTH_GLOWSTONE_PRESSURE_PLATE = registerBlockWithDefaultItem("smooth_glowstone_pressure_plate", () -> new LightUpPressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, getProperties(SMOOTH_GLOWSTONE.get()).lightLevel(getLightValueLit(15))), IEBlockProviders.pressurePlate(SMOOTH_GLOWSTONE));

    public static final RegistryObject<Block> SMOOTH_DIMSTONE_SLAB = registerBlockWithDefaultItem("smooth_dimstone_slab", () -> new SlabBlock(getProperties(SMOOTH_DIMSTONE.get())), IEBlockProviders.slab(SMOOTH_DIMSTONE));
    public static final RegistryObject<Block> SMOOTH_DIMSTONE_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("smooth_dimstone_vertical_slab", () -> new VerticalSlabBlock(getProperties(SMOOTH_DIMSTONE.get())), "quark", IEBlockProviders.verticalSlab(SMOOTH_DIMSTONE));
    public static final RegistryObject<Block> SMOOTH_DIMSTONE_STAIRS = registerBlockWithDefaultItem("smooth_dimstone_stairs", () -> new StairBlock(() -> SMOOTH_DIMSTONE.get().defaultBlockState(), getProperties(SMOOTH_DIMSTONE.get())), IEBlockProviders.stairs(SMOOTH_DIMSTONE));
    public static final RegistryObject<Block> SMOOTH_DIMSTONE_BUTTON = registerBlockWithDefaultItem("smooth_dimstone_button", () -> new StoneButtonBlock(getProperties(Material.DECORATION, 0.5F).sound(SoundType.GLASS).lightLevel(value -> 12).noCollission()), IEBlockProviders.button(SMOOTH_DIMSTONE));

    public static final RegistryObject<Block> SMOOTH_DULLSTONE_SLAB = registerBlockWithDefaultItem("smooth_dullstone_slab", () -> new SlabBlock(getProperties(SMOOTH_DULLSTONE.get())), IEBlockProviders.slab(SMOOTH_DULLSTONE));
    public static final RegistryObject<Block> SMOOTH_DULLSTONE_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("smooth_dullstone_vertical_slab", () -> new VerticalSlabBlock(getProperties(SMOOTH_DULLSTONE.get())), "quark", IEBlockProviders.verticalSlab(SMOOTH_DULLSTONE));
    public static final RegistryObject<Block> SMOOTH_DULLSTONE_STAIRS = registerBlockWithDefaultItem("smooth_dullstone_stairs", () -> new StairBlock(() -> SMOOTH_DULLSTONE.get().defaultBlockState(), getProperties(SMOOTH_DULLSTONE.get())), IEBlockProviders.stairs(SMOOTH_DULLSTONE));
    public static final RegistryObject<Block> SMOOTH_DULLSTONE_BUTTON = registerBlockWithDefaultItem("smooth_dullstone_button", () -> new StoneButtonBlock(getProperties(Material.DECORATION, 0.5F).sound(IESoundEvents.DULLSTONE_TYPE).noCollission()), IEBlockProviders.button(SMOOTH_DULLSTONE));

    public static final RegistryObject<Block> GLOWSTONE_BRICK_SLAB = registerBlockWithDefaultItem("glowstone_brick_slab", () -> new SlabBlock(getProperties(GLOWSTONE_BRICKS.get())), IEBlockProviders.slab(GLOWSTONE_BRICKS));
    public static final RegistryObject<Block> GLOWSTONE_BRICK_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("glowstone_brick_vertical_slab", () -> new VerticalSlabBlock(getProperties(GLOWSTONE_BRICKS.get())), "quark", IEBlockProviders.verticalSlab(GLOWSTONE_BRICKS));
    public static final RegistryObject<Block> GLOWSTONE_BRICK_STAIRS = registerBlockWithDefaultItem("glowstone_brick_stairs", () -> new StairBlock(() -> GLOWSTONE_BRICKS.get().defaultBlockState(), getProperties(GLOWSTONE_BRICKS.get())), IEBlockProviders.stairs(GLOWSTONE_BRICKS));
    public static final RegistryObject<Block> GLOWSTONE_BRICK_WALL = registerBlockWithDefaultItem("glowstone_brick_wall", () -> new WallBlock(getProperties(GLOWSTONE_BRICKS.get())), IEBlockProviders.wall(GLOWSTONE_BRICKS));

    public static final RegistryObject<Block> DIMSTONE_BRICK_SLAB = registerBlockWithDefaultItem("dimstone_brick_slab", () -> new SlabBlock(getProperties(DIMSTONE_BRICKS.get())), IEBlockProviders.slab(DIMSTONE_BRICKS));
    public static final RegistryObject<Block> DIMSTONE_BRICK_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("dimstone_brick_vertical_slab", () -> new VerticalSlabBlock(getProperties(DIMSTONE_BRICKS.get())), "quark", IEBlockProviders.verticalSlab(DIMSTONE_BRICKS));
    public static final RegistryObject<Block> DIMSTONE_BRICK_STAIRS = registerBlockWithDefaultItem("dimstone_brick_stairs", () -> new StairBlock(() -> DIMSTONE_BRICKS.get().defaultBlockState(), getProperties(DIMSTONE_BRICKS.get())), IEBlockProviders.stairs(DIMSTONE_BRICKS));
    public static final RegistryObject<Block> DIMSTONE_BRICK_WALL = registerBlockWithDefaultItem("dimstone_brick_wall", () -> new WallBlock(getProperties(DIMSTONE_BRICKS.get())), IEBlockProviders.wall(DIMSTONE_BRICKS));

    public static final RegistryObject<Block> DULLSTONE_BRICK_SLAB = registerBlockWithDefaultItem("dullstone_brick_slab", () -> new SlabBlock(getProperties(DULLSTONE_BRICKS.get())), IEBlockProviders.slab(DULLSTONE_BRICKS));
    public static final RegistryObject<Block> DULLSTONE_BRICK_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("dullstone_brick_vertical_slab", () -> new VerticalSlabBlock(getProperties(DULLSTONE_BRICKS.get())), "quark", IEBlockProviders.verticalSlab(DULLSTONE_BRICKS));
    public static final RegistryObject<Block> DULLSTONE_BRICK_STAIRS = registerBlockWithDefaultItem("dullstone_brick_stairs", () -> new StairBlock(() -> DULLSTONE_BRICKS.get().defaultBlockState(), getProperties(DULLSTONE_BRICKS.get())), IEBlockProviders.stairs(DULLSTONE_BRICKS));
    public static final RegistryObject<Block> DULLSTONE_BRICK_WALL = registerBlockWithDefaultItem("dullstone_brick_wall", () -> new WallBlock(getProperties(DULLSTONE_BRICKS.get())), IEBlockProviders.wall(DULLSTONE_BRICKS));

    public static final RegistryObject<Block> LUMINOUS_WART_BLOCK = registerBlockWithDefaultItem("luminous_wart_block", () -> new Block(getProperties(Blocks.NETHER_WART_BLOCK).lightLevel(value -> 8)), IEBlockProviders.simple());
    public static final RegistryObject<RotatedPillarBlock> LUMINOUS_STEM = registerBlockWithDefaultItem("luminous_stem", () -> new RotatedPillarBlock(getProperties(Blocks.CRIMSON_STEM)), IEBlockProviders.log());
    public static final RegistryObject<RotatedPillarBlock> LUMINOUS_HYPHAE = registerBlockWithDefaultItem("luminous_hyphae", () -> new RotatedPillarBlock(getProperties(Blocks.CRIMSON_HYPHAE)), IEBlockProviders.singleTexturePillar(LUMINOUS_STEM));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_LUMINOUS_STEM = registerBlockWithDefaultItem("stripped_luminous_stem", () -> new RotatedPillarBlock(getProperties(Blocks.STRIPPED_CRIMSON_STEM)), IEBlockProviders.log());
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_LUMINOUS_HYPHAE = registerBlockWithDefaultItem("stripped_luminous_hyphae", () -> new RotatedPillarBlock(getProperties(Blocks.STRIPPED_CRIMSON_HYPHAE)), IEBlockProviders.singleTexturePillar(STRIPPED_LUMINOUS_STEM));

    public static final RegistryObject<Block> GLOWDUST_SAND = registerBlockWithDefaultItem("glowdust_sand", () -> new GlowSandBlock(0xFFC267, BlockBehaviour.Properties.of(Material.SNOW, MaterialColor.SAND).requiresCorrectToolForDrops().strength(0.5F).sound(SoundType.SAND)), IEBlockProviders.randomizeRotations());
    public static final RegistryObject<Block> GLOWDUST = registerBlockWithDefaultItem("glowdust", () -> new GlowdustBlock(BlockBehaviour.Properties.of(Material.TOP_SNOW, MaterialColor.SAND).requiresCorrectToolForDrops().strength(0.2f).sound(SoundType.SAND)), IEBlockProviders.layer(GLOWDUST_SAND));
    public static final RegistryObject<Block> TRAPPED_GLOWDUST_SAND = registerBlockWithDefaultItem("trapped_glowdust_sand", () -> new TrappedGlowSandBlock(0xFFC267, getProperties(GLOWDUST_SAND.get()).strength(0.2F)), IEBlockProviders.randomizeRotations());

    public static final RegistryObject<Block> GLOWDUST_STONE = registerBlockWithDefaultItem("glowdust_stone", () -> new Block(getProperties(Blocks.SANDSTONE)), IEBlockProviders.simple());
    public static final RegistryObject<Block> GLOWDUST_STONE_SLAB = registerBlockWithDefaultItem("glowdust_stone_slab", () -> new SlabBlock(getProperties(GLOWDUST_STONE.get())), IEBlockProviders.slab(GLOWDUST_STONE));
    public static final RegistryObject<Block> GLOWDUST_STONE_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("glowdust_stone_vertical_slab", () -> new VerticalSlabBlock(getProperties(GLOWDUST_STONE.get())), "quark", IEBlockProviders.verticalSlab(GLOWDUST_STONE));
    public static final RegistryObject<Block> GLOWDUST_STONE_STAIRS = registerBlockWithDefaultItem("glowdust_stone_stairs", () -> new StairBlock(() -> GLOWDUST_STONE.get().defaultBlockState(), getProperties(GLOWDUST_STONE.get())), IEBlockProviders.stairs(GLOWDUST_STONE));
    public static final RegistryObject<Block> GLOWDUST_STONE_WALL = registerBlockWithDefaultItem("glowdust_stone_wall", () -> new WallBlock(getProperties(GLOWDUST_STONE.get())), IEBlockProviders.wall(GLOWDUST_STONE));

    public static final RegistryObject<Block> GLOWDUST_STONE_BRICKS = registerBlockWithDefaultItem("glowdust_stone_bricks", () -> new Block(getProperties(Blocks.SANDSTONE)), IEBlockProviders.simple());
    public static final RegistryObject<Block> GLOWDUST_STONE_BRICK_SLAB = registerBlockWithDefaultItem("glowdust_stone_brick_slab", () -> new SlabBlock(getProperties(GLOWDUST_STONE_BRICKS.get())), IEBlockProviders.slab(GLOWDUST_STONE_BRICKS));
    public static final RegistryObject<Block> GLOWDUST_STONE_BRICK_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("glowdust_stone_brick_vertical_slab", () -> new VerticalSlabBlock(getProperties(GLOWDUST_STONE_BRICKS.get())), "quark", IEBlockProviders.verticalSlab(GLOWDUST_STONE_BRICKS));
    public static final RegistryObject<Block> GLOWDUST_STONE_BRICK_STAIRS = registerBlockWithDefaultItem("glowdust_stone_brick_stairs", () -> new StairBlock(() -> GLOWDUST_STONE_BRICKS.get().defaultBlockState(), getProperties(GLOWDUST_STONE_BRICKS.get())), IEBlockProviders.stairs(GLOWDUST_STONE_BRICKS));
    public static final RegistryObject<Block> GLOWDUST_STONE_BRICK_WALL = registerBlockWithDefaultItem("glowdust_stone_brick_wall", () -> new WallBlock(getProperties(GLOWDUST_STONE_BRICKS.get())), IEBlockProviders.wall(GLOWDUST_STONE_BRICKS));
    public static final RegistryObject<Block> CRACKED_GLOWDUST_STONE_BRICKS = registerBlockWithDefaultItem("cracked_glowdust_stone_bricks", () -> new Block(getProperties(GLOWDUST_STONE_BRICKS.get())), IEBlockProviders.simple());
    public static final RegistryObject<Block> CHISELED_GLOWDUST_STONE_BRICKS = registerBlockWithDefaultItem("chiseled_glowdust_stone_bricks", () -> new Block(getProperties(GLOWDUST_STONE_BRICKS.get())), IEBlockProviders.simple());

    public static final RegistryObject<Block> CRUMBLING_BLACKSTONE = registerBlockWithDefaultItem("crumbling_blackstone", () -> new CrumblingBlackstoneBlock(BlockBehaviour.Properties.copy(Blocks.NETHERRACK)), IEBlockProviders.enumerateVariants(3, (provider, block, variant) -> {
        return new ConfiguredModel(provider.models()
            .withExistingParent(IEBlockProviders.BLOCK_FOLDER + IEBlockProviders.name(block) + "/" + variant, new ResourceLocation(IEBlockProviders.BLOCK_FOLDER + "cube_bottom_top"))
            .texture("top", IEBlockProviders.extend(IEBlockProviders.blockTexture(block), "/top" + variant))
            .texture("bottom", IEBlockProviders.extend(IEBlockProviders.blockTexture(block), "/top" + variant))
            .texture("side", IEBlockProviders.extend(IEBlockProviders.blockTexture(block), "/side" + variant))
        );
    }));
    public static final RegistryObject<Block> RUBBLE = registerBlockWithDefaultItem("rubble", () -> new Block(getProperties(Blocks.GRAVEL)), IEBlockProviders.randomizeRotations());
    public static final RegistryObject<Block> SILT = registerBlockWithDefaultItem("silt", () -> new Block(getProperties(Blocks.SAND)), IEBlockProviders.randomizeRotations());

    public static final RegistryObject<Block> BASALT_COBBLED = registerBlockWithDefaultItem("basalt_cobbled", () -> new RotatedPillarBlock(getProperties(Blocks.GRAVEL).sound(SoundType.BASALT)), IEBlockProviders.singleTexturePillar());
    public static final RegistryObject<Block> BASALT_COBBLED_SLAB = registerBlockWithDefaultItem("basalt_cobbled_slab", () -> new SlabBlock(getProperties(Blocks.GRAVEL).sound(SoundType.BASALT)), IEBlockProviders.slab(BASALT_COBBLED));
    public static final RegistryObject<Block> BASALT_COBBLED_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("basalt_cobbled_vertical_slab", () -> new VerticalSlabBlock(getProperties(Blocks.GRAVEL).sound(SoundType.BASALT)), "quark", IEBlockProviders.verticalSlab(BASALT_COBBLED));

    public static final RegistryObject<Block> BASALT_SLAB = registerBlockWithDefaultItem("basalt_slab", () -> new SlabBlock(getProperties(Blocks.BASALT)), IEBlockProviders.pillarSlab(() -> Blocks.BASALT));
    public static final RegistryObject<Block> BASALT_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("basalt_vertical_slab", () -> new VerticalSlabBlock(getProperties(Blocks.BASALT)), "quark", IEBlockProviders.pillarVerticalSlab(() -> Blocks.BASALT));
    public static final RegistryObject<Block> BASALT_STAIRS = registerBlockWithDefaultItem("basalt_stairs", () -> new StairBlock((Blocks.BASALT)::defaultBlockState, getProperties(Blocks.BASALT)), IEBlockProviders.pillarStairs(() -> Blocks.BASALT));
    public static final RegistryObject<Block> BASALT_WALL = registerBlockWithDefaultItem("basalt_wall", () -> new WallBlock(getProperties(Blocks.BASALT)), IEBlockProviders.pillarWall(() -> Blocks.BASALT));
    public static final RegistryObject<Block> BASALT_BUTTON = registerBlockWithDefaultItem("basalt_button", () -> new StoneButtonBlock(getProperties(Blocks.BASALT)), IEBlockProviders.pillarButton(() -> Blocks.BASALT));

    public static final RegistryObject<Block> POLISHED_BASALT_PRESSURE_PLATE = registerBlockWithDefaultItem("polished_basalt_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, getProperties(Blocks.POLISHED_BASALT)), IEBlockProviders.pillarPressurePlate(() -> Blocks.POLISHED_BASALT));
    public static final RegistryObject<Block> POLISHED_BASALT_SLAB = registerBlockWithDefaultItem("polished_basalt_slab", () -> new SlabBlock(getProperties(Blocks.POLISHED_BASALT)), IEBlockProviders.pillarSlab(() -> Blocks.POLISHED_BASALT));
    public static final RegistryObject<Block> POLISHED_BASALT_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("polished_basalt_vertical_slab", () -> new VerticalSlabBlock(getProperties(Blocks.POLISHED_BASALT)), "quark", IEBlockProviders.pillarVerticalSlab(() -> Blocks.POLISHED_BASALT));
    public static final RegistryObject<Block> POLISHED_BASALT_TILES = registerBlockWithDefaultItem("polished_basalt_tiles", () -> new RotatedPillarBlock(getProperties(Blocks.POLISHED_BASALT)), IEBlockProviders.singleTexturePillar());
    public static final RegistryObject<Block> POLISHED_BASALT_TILES_SLAB = registerBlockWithDefaultItem("polished_basalt_tiles_slab", () -> new SlabBlock(getProperties(Blocks.POLISHED_BASALT)), IEBlockProviders.slab(POLISHED_BASALT_TILES));
    public static final RegistryObject<Block> POLISHED_BASALT_TILES_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("polished_basalt_tiles_vertical_slab", () -> new VerticalSlabBlock(getProperties(Blocks.POLISHED_BASALT)), "quark", IEBlockProviders.verticalSlab(POLISHED_BASALT_TILES));

    public static final RegistryObject<Block> BASALT_BRICKS = registerBlockWithDefaultItem("basalt_bricks", () -> new RotatedPillarBlock(getProperties(Blocks.BASALT)), IEBlockProviders.singleTexturePillar());
    public static final RegistryObject<Block> BASALT_BRICK_SLAB = registerBlockWithDefaultItem("basalt_brick_slab", () -> new SlabBlock(getProperties(BASALT_BRICKS.get())), IEBlockProviders.slab(BASALT_BRICKS));
    public static final RegistryObject<Block> BASALT_BRICK_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("basalt_brick_vertical_slab", () -> new VerticalSlabBlock(getProperties(BASALT_BRICKS.get())), "quark", IEBlockProviders.verticalSlab(BASALT_BRICKS));
    public static final RegistryObject<Block> BASALT_BRICK_STAIRS = registerBlockWithDefaultItem("basalt_brick_stairs", () -> new StairBlock(() -> BASALT_BRICKS.get().defaultBlockState(), getProperties(BASALT_BRICKS.get())), IEBlockProviders.stairs(BASALT_BRICKS));
    public static final RegistryObject<Block> BASALT_BRICK_WALL = registerBlockWithDefaultItem("basalt_brick_wall", () -> new WallBlock(getProperties(BASALT_BRICKS.get())), IEBlockProviders.wall(BASALT_BRICKS));
    public static final RegistryObject<Block> CRACKED_BASALT_BRICKS = registerBlockWithDefaultItem("cracked_basalt_bricks", () -> new RotatedPillarBlock(getProperties(Blocks.BASALT)), IEBlockProviders.singleTexturePillar());
    public static final RegistryObject<Block> CHISELED_BASALT_BRICKS = registerBlockWithDefaultItem("chiseled_basalt_bricks", () -> new RotatedPillarBlock(getProperties(Blocks.BASALT)), IEBlockProviders.pillar());
    public static final RegistryObject<Block> MAGMATIC_CHISELED_BASALT_BRICKS = registerBlockWithDefaultItem("magmatic_chiseled_basalt_bricks", () -> new RotatedPillarBlock(getProperties(Blocks.BASALT)), IEBlockProviders.pillar());

    public static final RegistryObject<Block> BASALT_IRON_ORE = registerBlockWithDefaultItem("basalt_iron_ore", () -> new BasaltIronOreBlock(getProperties(Blocks.NETHER_GOLD_ORE)), IEBlockProviders.pillar());
    public static final RegistryObject<Block> BASALTIC_MAGMA = registerBlockWithDefaultItem("basaltic_magma", () -> new BasalticMagmaBlock(getProperties(Blocks.MAGMA_BLOCK).lightLevel(value -> 2)), IEBlockProviders.pillar());

    public static final RegistryObject<Block> SOUL_SAND_SLAB = registerBlockWithDefaultItem("soul_sand_slab", () -> new SlabBlock(getProperties(Blocks.SOUL_SAND)), IEBlockProviders.slab(() -> Blocks.SOUL_SAND));
    public static final RegistryObject<Block> SOUL_SAND_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("soul_sand_vertical_slab", () -> new VerticalSlabBlock(getProperties(Blocks.SOUL_SAND)), "quark", IEBlockProviders.verticalSlab(() -> Blocks.SOUL_SAND));
    public static final RegistryObject<Block> SOUL_SAND_STAIRS = registerBlockWithDefaultItem("soul_sand_stairs", () -> new StairBlock(Blocks.SOUL_SAND::defaultBlockState, getProperties((Blocks.SOUL_SAND))), IEBlockProviders.stairs(() -> Blocks.SOUL_SAND));

    public static final RegistryObject<Block> SOUL_SOIL_SLAB = registerBlockWithDefaultItem("soul_soil_slab", () -> new SlabBlock(getProperties(Blocks.SOUL_SOIL)), IEBlockProviders.slab(() -> Blocks.SOUL_SOIL));
    public static final RegistryObject<Block> SOUL_SOIL_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("soul_soil_vertical_slab", () -> new VerticalSlabBlock(getProperties(Blocks.SOUL_SOIL)), "quark", IEBlockProviders.verticalSlab(() -> Blocks.SOUL_SOIL));
    public static final RegistryObject<Block> SOUL_SOIL_STAIRS = registerBlockWithDefaultItem("soul_soil_stairs", () -> new StairBlock(Blocks.SOUL_SOIL::defaultBlockState, getProperties(Blocks.SOUL_SOIL)), IEBlockProviders.stairs(() -> Blocks.SOUL_SOIL));

    public static final RegistryObject<Block> SOUL_STONE = registerBlockWithDefaultItem("soul_stone", () -> new Block(getProperties(Blocks.COBBLESTONE).sound(IESoundEvents.SOUL_STONE_TYPE)), IEBlockProviders.simple());
    public static final RegistryObject<Block> SOUL_STONE_SLAB = registerBlockWithDefaultItem("soul_stone_slab", () -> new SlabBlock(getProperties(Blocks.COBBLESTONE).sound(SoundType.SOUL_SOIL)), IEBlockProviders.slab(SOUL_STONE));
    public static final RegistryObject<Block> SOUL_STONE_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("soul_stone_vertical_slab", () -> new VerticalSlabBlock((getProperties(Blocks.COBBLESTONE).sound(SoundType.SOUL_SOIL))), "quark", IEBlockProviders.verticalSlab(SOUL_STONE));
    public static final RegistryObject<Block> SOUL_STONE_STAIRS = registerBlockWithDefaultItem("soul_stone_stairs", () -> new StairBlock(Blocks.COBBLESTONE::defaultBlockState, getProperties(Blocks.SOUL_SOIL)), IEBlockProviders.stairs(SOUL_STONE));
    public static final RegistryObject<Block> SOUL_STONE_WALL = registerBlockWithDefaultItem("soul_stone_wall", () -> new WallBlock(getProperties(Blocks.COBBLESTONE_WALL)), IEBlockProviders.wall(SOUL_STONE));

    public static final RegistryObject<Block> SOUL_SLATE = registerBlockWithDefaultItem("soul_slate", () -> new Block(getProperties(Blocks.STONE_BRICKS).sound(IESoundEvents.SOUL_STONE_TYPE)), IEBlockProviders.simple());
    public static final RegistryObject<Block> SOUL_SLATE_SLAB = registerBlockWithDefaultItem("soul_slate_slab", () -> new SlabBlock(getProperties(SOUL_SLATE.get())), IEBlockProviders.slab(SOUL_SLATE));
    public static final RegistryObject<Block> SOUL_SLATE_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("soul_slate_vertical_slab", () -> new VerticalSlabBlock(getProperties(SOUL_SLATE.get())), "quark", IEBlockProviders.verticalSlab(SOUL_SLATE));
    public static final RegistryObject<Block> SOUL_SLATE_STAIRS = registerBlockWithDefaultItem("soul_slate_stairs", () -> new StairBlock(() -> SOUL_SLATE.get().defaultBlockState(), getProperties(SOUL_SLATE.get())), IEBlockProviders.stairs(SOUL_SLATE));
    public static final RegistryObject<Block> SOUL_SLATE_BUTTON = registerBlockWithDefaultItem("soul_slate_button", () -> new StoneButtonBlock(getProperties(SOUL_SLATE.get())), IEBlockProviders.button(SOUL_SLATE));
    public static final RegistryObject<Block> SOUL_SLATE_PRESSURE_PLATE = registerBlockWithDefaultItem("soul_slate_pressure_plate", () -> new LightUpPressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, getProperties(SOUL_SLATE.get()).lightLevel(getLightValueLit(15))), IEBlockProviders.pressurePlate(SOUL_SLATE));

    public static final RegistryObject<Block> SOUL_STONE_BRICKS = registerBlockWithDefaultItem("soul_stone_bricks", () -> new Block(getProperties(SOUL_STONE.get())), IEBlockProviders.simple());
    public static final RegistryObject<Block> SOUL_STONE_BRICK_SLAB = registerBlockWithDefaultItem("soul_stone_brick_slab", () -> new SlabBlock(getProperties(SOUL_STONE_BRICKS.get())), IEBlockProviders.slab(SOUL_STONE_BRICKS));
    public static final RegistryObject<Block> SOUL_STONE_BRICK_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("soul_stone_brick_vertical_slab", () -> new VerticalSlabBlock(getProperties(SOUL_STONE_BRICKS.get())), "quark", IEBlockProviders.verticalSlab(SOUL_STONE_BRICKS));
    public static final RegistryObject<Block> SOUL_STONE_BRICK_STAIRS = registerBlockWithDefaultItem("soul_stone_brick_stairs", () -> new StairBlock(() -> SOUL_STONE_BRICKS.get().defaultBlockState(), getProperties(SOUL_STONE_BRICKS.get())), IEBlockProviders.stairs(SOUL_STONE_BRICKS));
    public static final RegistryObject<Block> SOUL_STONE_BRICK_WALL = registerBlockWithDefaultItem("soul_stone_brick_wall", () -> new WallBlock(getProperties(SOUL_STONE_BRICKS.get())), IEBlockProviders.wall(SOUL_STONE_BRICKS));
    public static final RegistryObject<Block> CRACKED_SOUL_STONE_BRICKS = registerBlockWithDefaultItem("cracked_soul_stone_bricks", () -> new Block(getProperties(SOUL_STONE.get())), IEBlockProviders.simple());
    public static final RegistryObject<Block> CHISELED_SOUL_STONE_BRICKS = registerBlockWithDefaultItem("chiseled_soul_stone_bricks", () -> new RotatedPillarBlock(getProperties(SOUL_STONE.get())), IEBlockProviders.pillar());
    public static final RegistryObject<Block> CHARGED_CHISELED_SOUL_STONE_BRICKS = registerBlockWithDefaultItem("charged_chiseled_soul_stone_bricks", () -> new RotatedPillarBlock(getProperties(SOUL_SLATE.get())), IEBlockProviders.pillar());

    public static final RegistryObject<Block> SOUL_SLATE_BRICKS = registerBlockWithDefaultItem("soul_slate_bricks", () -> new Block(getProperties(SOUL_SLATE.get())), IEBlockProviders.simple());
    public static final RegistryObject<Block> SOUL_SLATE_BRICK_SLAB = registerBlockWithDefaultItem("soul_slate_brick_slab", () -> new SlabBlock(getProperties(SOUL_SLATE_BRICKS.get())), IEBlockProviders.slab(SOUL_SLATE_BRICKS));
    public static final RegistryObject<Block> SOUL_SLATE_BRICK_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("soul_slate_brick_vertical_slab", () -> new VerticalSlabBlock(getProperties(SOUL_SLATE_BRICKS.get())), "quark", IEBlockProviders.verticalSlab(SOUL_SLATE_BRICKS));
    public static final RegistryObject<Block> SOUL_SLATE_BRICK_STAIRS = registerBlockWithDefaultItem("soul_slate_brick_stairs", () -> new StairBlock(() -> SOUL_SLATE_BRICKS.get().defaultBlockState(), getProperties(SOUL_SLATE_BRICKS.get())), IEBlockProviders.stairs(SOUL_SLATE_BRICKS));
    public static final RegistryObject<Block> SOUL_SLATE_BRICK_WALL = registerBlockWithDefaultItem("soul_slate_brick_wall", () -> new WallBlock(getProperties(SOUL_SLATE_BRICKS.get())), IEBlockProviders.wall(SOUL_SLATE_BRICKS));
    public static final RegistryObject<Block> CRACKED_SOUL_SLATE_BRICKS = registerBlockWithDefaultItem("cracked_soul_slate_bricks", () -> new Block(getProperties(SOUL_SLATE.get())), IEBlockProviders.simple());
    public static final RegistryObject<Block> CHISELED_SOUL_SLATE_BRICKS = registerBlockWithDefaultItem("chiseled_soul_slate_bricks", () -> new RotatedPillarBlock(getProperties(SOUL_SLATE.get())), IEBlockProviders.pillar());
    public static final RegistryObject<Block> CHARGED_CHISELED_SOUL_SLATE_BRICKS = registerBlockWithDefaultItem("charged_chiseled_soul_slate_bricks", () -> new RotatedPillarBlock(getProperties(SOUL_SLATE.get())), IEBlockProviders.pillar());

    public static final RegistryObject<Block> CRIMSON_FUNGUS_CAP = registerBlockWithDefaultItem("crimson_fungus_cap", () -> new FungusCapBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_WART_BLOCK)), IEBlockProviders.staticPillar());
    public static final RegistryObject<Block> WARPED_FUNGUS_CAP = registerBlockWithDefaultItem("warped_fungus_cap", () -> new FungusCapBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_WART_BLOCK)), IEBlockProviders.staticPillar());
    public static final RegistryObject<Block> LUMINOUS_FUNGUS_CAP = registerBlockWithDefaultItem("luminous_fungus_cap", () -> new FungusCapBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_WART_BLOCK).lightLevel(value -> 14)), IEBlockProviders.staticPillar());

    public static final RegistryObject<Block> GLOW_LANTERN = registerBlockWithDefaultItem("glow_lantern", () -> new LanternBlock(getProperties(Blocks.LANTERN)), IEBlockProviders.lantern());
    public static final RegistryObject<Block> GLOW_TORCH = registerBlock("glow_torch", () -> new GlowTorchBlock(getProperties(Blocks.TORCH)), IEBlockProviders.torch());
    @SuppressWarnings("deprecation")
    public static final RegistryObject<Block> GLOW_TORCH_WALL = registerBlock("glow_torch_wall", () -> new GlowWallTorchBlock(getProperties(IEBlocks.GLOW_TORCH.get()).dropsLike(GLOW_TORCH.get())), IEBlockProviders.wallTorch());
    public static final RegistryObject<Block> GLOW_CAMPFIRE = registerBlockWithDefaultItem("glow_campfire", () -> new GlowCampfireBlock(2, getProperties(Blocks.CAMPFIRE)), IEBlockProviders.campfire());
    public static final RegistryObject<Block> GLOW_FIRE = registerBlock("glow_fire", () -> new GlowFireBlock(getProperties(Blocks.FIRE)), IEBlockProviders.fire());

    public static final RegistryObject<Block> GLOWSILK_COCOON = registerBlockWithDefaultItem("glowsilk_cocoon", () -> new RotatedPillarBlock(getProperties(Material.GRASS).sound(SoundType.WOOL).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).lightLevel(value -> 5)), IEBlockProviders.pillar());
    // Foliage
    public static final RegistryObject<Block> LUMINOUS_FUNGUS = registerBlockWithDefaultItem("luminous_fungus", () -> new LuminousFungusBlock(getProperties(Material.PLANT).lightLevel(getLightValueLit(15)).noCollission().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> DULLTHORNS = registerBlock("dullthorns", () -> new DullthornsBlock(BlockBehaviour.Properties.of(Material.PLANT).lightLevel(value -> 3).noCollission().randomTicks().strength(0.1F).sound(SoundType.GRASS)), (provider, block) -> {
        provider.getVariantBuilder(block.get())
            .partialState().with(DullthornsBlock.TIP, false)
            .modelForState().modelFile(provider.models().cross(IEBlockProviders.name(block.get()), IEBlockProviders.blockTexture(block.get()))).addModel()
            .partialState().with(DullthornsBlock.TIP, true)
            .modelForState().modelFile(provider.models().cross(IEBlockProviders.name(block.get()) + "_tip", IEBlockProviders.extend(IEBlockProviders.blockTexture(block.get()), "_tip"))).addModel();
    });

    public static final RegistryObject<Block> DULLTHORNS_BLOCK = registerBlockWithDefaultItem("dullthorns_block", () -> new DullthornsBlockBlock(BlockBehaviour.Properties.of(Material.CACTUS).strength(1.0F).sound(SoundType.WART_BLOCK)), IEBlockProviders.randomizeRotations());

    public static final RegistryObject<FlowerPotBlock> POTTED_LUMINOUS_FUNGUS = registerBlock("potted_luminous_fungus", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, LUMINOUS_FUNGUS, getProperties(Blocks.FLOWER_POT)), IEBlockProviders.pottedPlant(LUMINOUS_FUNGUS));
    public static final RegistryObject<FlowerPotBlock> POTTED_DULLTHORNS = registerBlock("potted_dullthorns", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, DULLTHORNS, getProperties(Blocks.FLOWER_POT)), IEBlockProviders.pottedPlant(DULLTHORNS));

    public static final RegistryObject<Block> SHROOMLIGHT_FUNGUS = registerBlockWithDefaultItem("shroomlight_fungus", () -> new ShroomlightFungusBlock(getProperties(Material.PLANT).lightLevel(value -> 13).noCollission().sound(SoundType.GRASS)));
    public static final RegistryObject<FlowerPotBlock> POTTED_SHROOMLIGHT_FUNGUS = registerBlock("potted_shroomlight_fungus", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SHROOMLIGHT_FUNGUS, getProperties(Blocks.FLOWER_POT)), IEBlockProviders.pottedPlant(SHROOMLIGHT_FUNGUS));

    public static final RegistryObject<BuriedBoneBlock> BURIED_BONE = registerBlock("buried_bone", () -> new BuriedBoneBlock(getProperties(Material.PLANT).noCollission().sound(SoundType.BONE_BLOCK)));
    public static final RegistryObject<FlowerPotBlock> POTTED_BURIED_BONE = registerBlock("potted_buried_bone", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BURIED_BONE, getProperties(Blocks.FLOWER_POT)));

    public static final RegistryObject<PlantedQuartzBlock> PLANTED_QUARTZ = registerBlock("planted_quartz", () -> new PlantedQuartzBlock(getProperties(Material.STONE).strength(1.5F).requiresCorrectToolForDrops().noCollission().sound(SoundType.NETHER_ORE)));

    public static final RegistryObject<Block> CRIMSON_NYLIUM_PATH = registerBlockWithDefaultItem("crimson_nylium_path", () -> new NetherrackPathBlock(getProperties(Blocks.NETHERRACK)), IEBlockProviders.nyliumPath(() -> Blocks.CRIMSON_NYLIUM));
    public static final RegistryObject<Block> WARPED_NYLIUM_PATH = registerBlockWithDefaultItem("warped_nylium_path", () -> new NetherrackPathBlock(getProperties(Blocks.NETHERRACK)), IEBlockProviders.nyliumPath(() -> Blocks.WARPED_NYLIUM));
    public static final RegistryObject<Block> CRIMSON_NYLIUM_CARPET = registerBlockWithDefaultItem("crimson_nylium_carpet", () -> new NetherCarpetBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.CRIMSON_NYLIUM).strength(0.1F).sound(SoundType.NYLIUM)), IEBlockProviders.carpet(() -> Blocks.CRIMSON_NYLIUM));
    public static final RegistryObject<Block> WARPED_NYLIUM_CARPET = registerBlockWithDefaultItem("warped_nylium_carpet", () -> new NetherCarpetBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.WARPED_NYLIUM).strength(0.1F).sound(SoundType.NYLIUM)), IEBlockProviders.carpet(() -> Blocks.WARPED_NYLIUM));
    public static final RegistryObject<Block> SOUL_SOIL_PATH = registerBlockWithDefaultItem("soul_soil_path", () -> new SoulSoilPathBlock(getProperties(Blocks.SOUL_SOIL)), IEBlockProviders.path(() -> Blocks.SOUL_SOIL));

    public static final RegistryObject<Block> QUARTZ_GLASS = registerBlockWithDefaultItem("quartz_glass", () -> new GlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(2.0F, 6.0F).sound(SoundType.GLASS).noOcclusion().isValidSpawn(IEBlocks::neverAllowSpawn).isRedstoneConductor(IEBlocks::isntSolid).isSuffocating(IEBlocks::isntSolid).isViewBlocking(IEBlocks::isntSolid)), IEBlockProviders.simple());
    public static final RegistryObject<Block> QUARTZ_GLASS_PANE = registerBlockWithDefaultItem("quartz_glass_pane", () -> new IronBarsBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(2.0F, 6.0F).sound(SoundType.GLASS).noOcclusion()), IEBlockProviders.pane(QUARTZ_GLASS));

    public static final RegistryObject<Block> GLOW_GLASS = registerBlockWithDefaultItem("glow_glass", () -> new GlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(0.3F).sound(SoundType.GLASS).noOcclusion().isValidSpawn(IEBlocks::neverAllowSpawn).isRedstoneConductor(IEBlocks::isntSolid).isSuffocating(IEBlocks::isntSolid).isViewBlocking(IEBlocks::isntSolid).lightLevel(value -> 10)), IEBlockProviders.simple());
    public static final RegistryObject<Block> GLOW_GLASS_PANE = registerBlockWithDefaultItem("glow_glass_pane", () -> new IronBarsBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(0.3F).sound(SoundType.GLASS).noOcclusion().lightLevel(value -> 10)), IEBlockProviders.pane(GLOW_GLASS));

    private static boolean isntSolid(BlockState state, BlockGetter reader, BlockPos pos) {
        return false;
    }

    private static Boolean neverAllowSpawn(BlockState state, BlockGetter reader, BlockPos pos, EntityType<?> entity) {
        return (boolean) false;
    }

    public static BlockBehaviour.Properties getProperties(Material materialIn, float hardnessAndResistanceIn) {
        return getProperties(materialIn, hardnessAndResistanceIn, hardnessAndResistanceIn);
    }

    public static BlockBehaviour.Properties getProperties(Material materialIn, float hardnessIn, float resistanceIn) {
        return BlockBehaviour.Properties.of(materialIn).strength(hardnessIn, resistanceIn);
    }

    public static BlockBehaviour.Properties getProperties(Material materialIn) {
        return BlockBehaviour.Properties.of(materialIn).instabreak();
    }

    public static BlockBehaviour.Properties getProperties(Block block) {
        return BlockBehaviour.Properties.copy(block);
    }

    private static ToIntFunction<BlockState> getLightValueLit(int lightValue) {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Blocks Registered!");
    }

    public static <T extends Block> RegistryObject<T> registerBlockWithDefaultItem(String name, Supplier<? extends T> blockSupplier) {
        return registerBlockWithDefaultItem(name, blockSupplier, null);
    }
    public static <T extends Block> RegistryObject<T> registerBlockWithDefaultItem(String name, Supplier<? extends T> blockSupplier, @Nullable IEBlockProviders.BlockProviderConsumer blockProvider) {
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier, blockProvider);
        IEItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(InfernalExpansion.TAB)));
        return block;
    }

    public static <T extends Block> RegistryObject<T> registerBlockWithDefaultItemConditioned(String name, Supplier<? extends T> blockSupplier, String modID) {
        return registerBlockWithDefaultItemConditioned(name, blockSupplier, modID, null);
    }

    public static <T extends Block> RegistryObject<T> registerBlockWithDefaultItemConditioned(String name, Supplier<? extends T> blockSupplier, String modID, @Nullable IEBlockProviders.BlockProviderConsumer blockProvider) {
        if (ModList.get().isLoaded(modID)) {
            RegistryObject<T> block = BLOCKS.register(name, blockSupplier, blockProvider);
            IEItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(InfernalExpansion.TAB)));
            return block;
        } else {
            RegistryObject<T> block = BLOCKS.register(name, blockSupplier, blockProvider);
            IEItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
            return block;
        }
    }

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<? extends T> blockSupplier) {
        return registerBlock(name, blockSupplier, null);
    }

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<? extends T> blockSupplier, @Nullable IEBlockProviders.BlockProviderConsumer blockProvider) {
        return BLOCKS.register(name, blockSupplier, blockProvider);
    }

}
