package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.blocks.*;
import com.nekomaster1000.infernalexp.util.DataUtil;

import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class IEBlocks {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, InfernalExpansion.MOD_ID);

  /*			single file in /blockstates pulls from (potentially multiple files in)
    			/model/block, which pulls from /textures/blocks all the different ways a block can look.
    			(If a block is animated, pull the name of the animation texture.)
    			/item pulls from the individual json file in /model/block.

    			If the block is a BUTTON, PRESSURE PLATE, WALL, or WART BLOCK, add it to the corresponding tag file
    			in data/minecraft.tags.

    			When that's done, make sure they have a LOOT TABLE, found in data/infernalexp/loot_tables.
    			For RECIPES, make sure the block is craftable in some form if not a biome-building block.
    			If it's a type of stone or has bricks, give it a STONE-CUTTER recipe.
    			Furnace Recipes are included in the recipes folder. */

    // Blocks
    public static final RegistryObject<Block> DIMSTONE = registerBlockWithDefaultItem("dimstone",            () -> new Block(getProperties(Material.GLASS, 3.5F, 2.0F).sound(IESoundEvents.DIMSTONE_TYPE).setRequiresTool().harvestTool(ToolType.PICKAXE).setLightLevel(value -> 12)));
    public static final RegistryObject<Block> DULLSTONE = registerBlockWithDefaultItem("dullstone",          () -> new Block(getProperties(Material.GLASS, 1.5F, 6.0F).sound(IESoundEvents.DULLSTONE_TYPE).setRequiresTool().harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> SMOOTH_GLOWSTONE = registerBlockWithDefaultItem("smooth_glowstone",    () -> new Block(getProperties(Material.GLASS, 1.5F, 6.0F).sound(SoundType.GLASS).setRequiresTool().harvestTool(ToolType.PICKAXE).setLightLevel(value -> 15)));
    public static final RegistryObject<Block> SMOOTH_DIMSTONE = registerBlockWithDefaultItem("smooth_dimstone",      () -> new Block(getProperties(DIMSTONE.get()).hardnessAndResistance(1.5F, 6.0F).setLightLevel(value -> 12)));
    public static final RegistryObject<Block> SMOOTH_DULLSTONE = registerBlockWithDefaultItem("smooth_dullstone",    () -> new Block(getProperties(DULLSTONE.get()).hardnessAndResistance(1.5F, 6.0F)));
    public static final RegistryObject<Block> GLOWSTONE_BRICKS = registerBlockWithDefaultItem("glowstone_bricks",      () -> new Block(getProperties(SMOOTH_GLOWSTONE.get())));
    public static final RegistryObject<Block> DIMSTONE_BRICKS = registerBlockWithDefaultItem("dimstone_bricks",        () -> new Block(getProperties(SMOOTH_DIMSTONE.get())));
    public static final RegistryObject<Block> DULLSTONE_BRICKS = registerBlockWithDefaultItem("dullstone_bricks",      () -> new Block(getProperties(SMOOTH_DULLSTONE.get())));

    public static final RegistryObject<Block> CRACKED_GLOWSTONE_BRICKS = registerBlockWithDefaultItem("cracked_glowstone_bricks",   () -> new Block(getProperties(SMOOTH_GLOWSTONE.get())));
    public static final RegistryObject<Block> CRACKED_DIMSTONE_BRICKS = registerBlockWithDefaultItem("cracked_dimstone_bricks",     () -> new Block(getProperties(SMOOTH_DIMSTONE.get())));
    public static final RegistryObject<Block> CRACKED_DULLSTONE_BRICKS = registerBlockWithDefaultItem("cracked_dullstone_bricks",   () -> new Block(getProperties(SMOOTH_DULLSTONE.get())));

    public static final RegistryObject<Block> CHISELED_GLOWSTONE_BRICKS = registerBlockWithDefaultItem("chiseled_glowstone_bricks", () -> new Block(getProperties(SMOOTH_GLOWSTONE.get())));
    public static final RegistryObject<Block> CHISELED_DIMSTONE_BRICKS = registerBlockWithDefaultItem("chiseled_dimstone_bricks",   () -> new Block(getProperties(SMOOTH_DIMSTONE.get())));
    public static final RegistryObject<Block> CHISELED_DULLSTONE_BRICKS = registerBlockWithDefaultItem("chiseled_dullstone_bricks", () -> new Block(getProperties(SMOOTH_DULLSTONE.get())));

    public static final RegistryObject<Block> SMOOTH_GLOWSTONE_SLAB = registerBlockWithDefaultItem("smooth_glowstone_slab",       () -> new SlabBlock(getProperties(SMOOTH_GLOWSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_GLOWSTONE_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("smooth_glowstone_vertical_slab", () -> new VerticalSlabBlock(getProperties(SMOOTH_GLOWSTONE.get())), "quark");
    public static final RegistryObject<Block> SMOOTH_GLOWSTONE_STAIRS = registerBlockWithDefaultItem("smooth_glowstone_stairs",   () -> new StairsBlock(() -> SMOOTH_GLOWSTONE.get().getDefaultState(), getProperties(SMOOTH_GLOWSTONE.get())));
	public static final RegistryObject<Block> SMOOTH_GLOWSTONE_BUTTON = registerBlockWithDefaultItem("smooth_glowstone_button",   () -> new StoneButtonBlock(getProperties(Material.MISCELLANEOUS, 0.5F).sound(SoundType.GLASS).setLightLevel(value -> 15).doesNotBlockMovement()));
	public static final RegistryObject<Block> SMOOTH_GLOWSTONE_PRESSURE_PLATE = registerBlockWithDefaultItem("smooth_glowstone_pressure_plate", () -> new LightUpPressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, getProperties(SMOOTH_GLOWSTONE.get()).setLightLevel(getLightValueLit(15))));

	public static final RegistryObject<Block> SMOOTH_DIMSTONE_SLAB = registerBlockWithDefaultItem("smooth_dimstone_slab",         () -> new SlabBlock(getProperties(SMOOTH_DIMSTONE.get())));
	public static final RegistryObject<Block> SMOOTH_DIMSTONE_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("smooth_dimstone_vertical_slab", () -> new VerticalSlabBlock(getProperties(SMOOTH_DIMSTONE.get())), "quark");
    public static final RegistryObject<Block> SMOOTH_DIMSTONE_STAIRS = registerBlockWithDefaultItem("smooth_dimstone_stairs",     () -> new StairsBlock(() -> SMOOTH_DIMSTONE.get().getDefaultState(), getProperties(SMOOTH_DIMSTONE.get())));
	public static final RegistryObject<Block> SMOOTH_DIMSTONE_BUTTON = registerBlockWithDefaultItem("smooth_dimstone_button",     () -> new StoneButtonBlock(getProperties(Material.MISCELLANEOUS, 0.5F).sound(SoundType.GLASS).setLightLevel(value -> 12).doesNotBlockMovement()));

	public static final RegistryObject<Block> SMOOTH_DULLSTONE_SLAB = registerBlockWithDefaultItem("smooth_dullstone_slab",       () -> new SlabBlock(getProperties(SMOOTH_DULLSTONE.get())));
	public static final RegistryObject<Block> SMOOTH_DULLSTONE_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("smooth_dullstone_vertical_slab", () -> new VerticalSlabBlock(getProperties(SMOOTH_DULLSTONE.get())), "quark");
    public static final RegistryObject<Block> SMOOTH_DULLSTONE_STAIRS = registerBlockWithDefaultItem("smooth_dullstone_stairs",   () -> new StairsBlock(() -> SMOOTH_DULLSTONE.get().getDefaultState(), getProperties(SMOOTH_DULLSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_DULLSTONE_BUTTON = registerBlockWithDefaultItem("smooth_dullstone_button",   () -> new StoneButtonBlock(getProperties(Material.MISCELLANEOUS, 0.5F).sound(IESoundEvents.DULLSTONE_TYPE).doesNotBlockMovement()));

    public static final RegistryObject<Block> GLOWSTONE_BRICK_SLAB = registerBlockWithDefaultItem("glowstone_brick_slab",         () -> new SlabBlock(getProperties(GLOWSTONE_BRICKS.get())));
    public static final RegistryObject<Block> GLOWSTONE_BRICK_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("glowstone_brick_vertical_slab", () -> new VerticalSlabBlock(getProperties(GLOWSTONE_BRICKS.get())), "quark");
    public static final RegistryObject<Block> GLOWSTONE_BRICK_STAIRS = registerBlockWithDefaultItem("glowstone_brick_stairs",     () -> new StairsBlock(() -> GLOWSTONE_BRICKS.get().getDefaultState(), getProperties(GLOWSTONE_BRICKS.get())));
	public static final RegistryObject<Block> GLOWSTONE_BRICK_WALL = registerBlockWithDefaultItem("glowstone_brick_wall",		   () -> new WallBlock(getProperties(GLOWSTONE_BRICKS.get())));

	public static final RegistryObject<Block> DIMSTONE_BRICK_SLAB = registerBlockWithDefaultItem("dimstone_brick_slab",           () -> new SlabBlock(getProperties(DIMSTONE_BRICKS.get())));
	public static final RegistryObject<Block> DIMSTONE_BRICK_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("dimstone_brick_vertical_slab", () -> new VerticalSlabBlock(getProperties(DIMSTONE_BRICKS.get())), "quark");
    public static final RegistryObject<Block> DIMSTONE_BRICK_STAIRS = registerBlockWithDefaultItem("dimstone_brick_stairs", () -> new StairsBlock(() -> DIMSTONE_BRICKS.get().getDefaultState(), getProperties(DIMSTONE_BRICKS.get())));
    public static final RegistryObject<Block> DIMSTONE_BRICK_WALL = registerBlockWithDefaultItem("dimstone_brick_wall", () -> new WallBlock(getProperties(DIMSTONE_BRICKS.get())));

    public static final RegistryObject<Block> DULLSTONE_BRICK_SLAB = registerBlockWithDefaultItem("dullstone_brick_slab", () -> new SlabBlock(getProperties(DULLSTONE_BRICKS.get())));
    public static final RegistryObject<Block> DULLSTONE_BRICK_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("dullstone_brick_vertical_slab", () -> new VerticalSlabBlock(getProperties(DULLSTONE_BRICKS.get())), "quark");
    public static final RegistryObject<Block> DULLSTONE_BRICK_STAIRS = registerBlockWithDefaultItem("dullstone_brick_stairs", () -> new StairsBlock(() -> DULLSTONE_BRICKS.get().getDefaultState(), getProperties(DULLSTONE_BRICKS.get())));
    public static final RegistryObject<Block> DULLSTONE_BRICK_WALL = registerBlockWithDefaultItem("dullstone_brick_wall", () -> new WallBlock(getProperties(DULLSTONE_BRICKS.get())));

    public static final RegistryObject<Block> LUMINOUS_WART_BLOCK = registerBlockWithDefaultItem("luminous_wart_block", () -> new Block(getProperties(Blocks.NETHER_WART_BLOCK).setLightLevel(value -> 8)));

    public static final RegistryObject<Block> GLOWDUST_SAND = registerBlockWithDefaultItem("glowdust_sand", () -> new GlowSandBlock(0xFFC267, AbstractBlock.Properties.create(Material.SNOW_BLOCK, MaterialColor.SAND).setRequiresTool().harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<Block> GLOWDUST = registerBlockWithDefaultItem("glowdust", () -> new GlowdustBlock(AbstractBlock.Properties.create(Material.SNOW, MaterialColor.SAND).setRequiresTool().harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.2f).sound(SoundType.SAND)));
    public static final RegistryObject<Block> TRAPPED_GLOWDUST_SAND = registerBlockWithDefaultItem("trapped_glowdust_sand", () -> new TrappedGlowSandBlock(0xFFC267, getProperties(GLOWDUST.get()).hardnessAndResistance(0.2F).setLightLevel(value -> 5)));

    public static final RegistryObject<Block> GLOWDUST_STONE = registerBlockWithDefaultItem("glowdust_stone", () -> new Block(getProperties(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> GLOWDUST_STONE_SLAB = registerBlockWithDefaultItem("glowdust_stone_slab", () -> new SlabBlock(getProperties(GLOWDUST_STONE.get())));
    public static final RegistryObject<Block> GLOWDUST_STONE_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("glowdust_stone_vertical_slab", () -> new VerticalSlabBlock(getProperties(GLOWDUST_STONE.get())), "quark");
    public static final RegistryObject<Block> GLOWDUST_STONE_STAIRS = registerBlockWithDefaultItem("glowdust_stone_stairs", () -> new StairsBlock(() -> GLOWDUST_STONE.get().getDefaultState(), getProperties(GLOWDUST_STONE.get())));
    public static final RegistryObject<Block> GLOWDUST_STONE_WALL = registerBlockWithDefaultItem("glowdust_stone_wall", () -> new WallBlock(getProperties(GLOWDUST_STONE.get())));

    public static final RegistryObject<Block> GLOWDUST_STONE_BRICKS = registerBlockWithDefaultItem("glowdust_stone_bricks", () -> new Block(getProperties(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> GLOWDUST_STONE_BRICK_SLAB = registerBlockWithDefaultItem("glowdust_stone_brick_slab",      () -> new SlabBlock(getProperties(GLOWDUST_STONE_BRICKS.get())));
    public static final RegistryObject<Block> GLOWDUST_STONE_BRICK_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("glowdust_stone_brick_vertical_slab", () -> new VerticalSlabBlock(getProperties(GLOWDUST_STONE_BRICKS.get())), "quark");
    public static final RegistryObject<Block> GLOWDUST_STONE_BRICK_STAIRS = registerBlockWithDefaultItem("glowdust_stone_brick_stairs",  () -> new StairsBlock(() -> GLOWDUST_STONE_BRICKS.get().getDefaultState(), getProperties(GLOWDUST_STONE_BRICKS.get())));
    public static final RegistryObject<Block> GLOWDUST_STONE_BRICK_WALL = registerBlockWithDefaultItem("glowdust_stone_brick_wall", 	  () -> new WallBlock(getProperties(GLOWDUST_STONE_BRICKS.get())));
	public static final RegistryObject<Block> CRACKED_GLOWDUST_STONE_BRICKS = registerBlockWithDefaultItem("cracked_glowdust_stone_bricks",	() -> new Block(getProperties(GLOWDUST_STONE_BRICKS.get())));
	public static final RegistryObject<Block> CHISELED_GLOWDUST_STONE_BRICKS = registerBlockWithDefaultItem("chiseled_glowdust_stone_bricks",	() -> new Block(getProperties(GLOWDUST_STONE_BRICKS.get())));

	public static final RegistryObject<Block> CRUMBLING_BLACKSTONE = registerBlockWithDefaultItem("crumbling_blackstone", () -> new CrumblingBlackstoneBlock(AbstractBlock.Properties.from(Blocks.NETHERRACK)));
	public static final RegistryObject<Block> RUBBLE = registerBlockWithDefaultItem("rubble", () -> new Block(getProperties(Blocks.GRAVEL)));
	public static final RegistryObject<Block> SILT = registerBlockWithDefaultItem("silt", () -> new Block(getProperties(Blocks.SAND)));

	public static final RegistryObject<Block> BASALT_COBBLED = registerBlockWithDefaultItem("basalt_cobbled", () -> new RotatedPillarBlock(getProperties(Blocks.GRAVEL).sound(SoundType.BASALT)));
	public static final RegistryObject<Block> BASALT_COBBLED_SLAB = registerBlockWithDefaultItem("basalt_cobbled_slab", () -> new SlabBlock(getProperties(Blocks.GRAVEL).sound(SoundType.BASALT)));
    public static final RegistryObject<Block> BASALT_COBBLED_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("basalt_cobbled_vertical_slab", () -> new VerticalSlabBlock(getProperties(Blocks.GRAVEL).sound(SoundType.BASALT)), "quark");

	public static final RegistryObject<Block> BASALT_SLAB = registerBlockWithDefaultItem("basalt_slab",	() -> new SlabBlock(getProperties(Blocks.BASALT)));
    public static final RegistryObject<Block> BASALT_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("basalt_vertical_slab",	() -> new VerticalSlabBlock(getProperties(Blocks.BASALT)), "quark");
	public static final RegistryObject<Block> BASALT_STAIRS = registerBlockWithDefaultItem("basalt_stairs",	() -> new StairsBlock((Blocks.BASALT)::getDefaultState, getProperties(Blocks.BASALT)));
	public static final RegistryObject<Block> BASALT_WALL = registerBlockWithDefaultItem("basalt_wall",		() -> new WallBlock(getProperties(Blocks.BASALT)));
	public static final RegistryObject<Block> BASALT_BUTTON = registerBlockWithDefaultItem("basalt_button",	() -> new StoneButtonBlock(getProperties(Blocks.BASALT)));

	public static final RegistryObject<Block> POLISHED_BASALT_PRESSURE_PLATE = registerBlockWithDefaultItem("polished_basalt_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, getProperties(Blocks.POLISHED_BASALT)));
 	public static final RegistryObject<Block> POLISHED_BASALT_SLAB = registerBlockWithDefaultItem("polished_basalt_slab",	() -> new SlabBlock(getProperties(Blocks.POLISHED_BASALT)));
 	public static final RegistryObject<Block> POLISHED_BASALT_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("polished_basalt_vertical_slab", () -> new VerticalSlabBlock(getProperties(Blocks.POLISHED_BASALT)), "quark");
	public static final RegistryObject<Block> POLISHED_BASALT_TILES = registerBlockWithDefaultItem("polished_basalt_tiles", () -> new RotatedPillarBlock(getProperties(Blocks.POLISHED_BASALT)));
	public static final RegistryObject<Block> POLISHED_BASALT_TILES_SLAB = registerBlockWithDefaultItem("polished_basalt_tiles_slab", () -> new SlabBlock(getProperties(Blocks.POLISHED_BASALT)));
    public static final RegistryObject<Block> POLISHED_BASALT_TILES_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("polished_basalt_tiles_vertical_slab", () -> new VerticalSlabBlock(getProperties(Blocks.POLISHED_BASALT)), "quark");

    public static final RegistryObject<Block> BASALT_BRICKS = registerBlockWithDefaultItem("basalt_bricks", () -> new RotatedPillarBlock(getProperties(Blocks.BASALT)));
    public static final RegistryObject<Block> BASALT_BRICK_SLAB = registerBlockWithDefaultItem("basalt_brick_slab", () -> new SlabBlock(getProperties(BASALT_BRICKS.get())));
    public static final RegistryObject<Block> BASALT_BRICK_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("basalt_brick_vertical_slab", () -> new VerticalSlabBlock(getProperties(BASALT_BRICKS.get())), "quark");
    public static final RegistryObject<Block> BASALT_BRICK_STAIRS = registerBlockWithDefaultItem("basalt_brick_stairs", () -> new StairsBlock(() -> BASALT_BRICKS.get().getDefaultState(), getProperties(BASALT_BRICKS.get())));
	public static final RegistryObject<Block> BASALT_BRICK_WALL = registerBlockWithDefaultItem("basalt_brick_wall", () -> new WallBlock(getProperties(BASALT_BRICKS.get())));
    public static final RegistryObject<Block> CRACKED_BASALT_BRICKS = registerBlockWithDefaultItem("cracked_basalt_bricks", () -> new RotatedPillarBlock(getProperties(Blocks.BASALT)));
    public static final RegistryObject<Block> CHISELED_BASALT_BRICKS = registerBlockWithDefaultItem("chiseled_basalt_bricks",	() -> new RotatedPillarBlock(getProperties(Blocks.BASALT)));
    public static final RegistryObject<Block> MAGMATIC_CHISELED_BASALT_BRICKS = registerBlockWithDefaultItem("magmatic_chiseled_basalt_bricks", () -> new RotatedPillarBlock(getProperties(Blocks.BASALT)));

    public static final RegistryObject<Block> BASALT_IRON_ORE = registerBlockWithDefaultItem("basalt_iron_ore", () -> new BasaltIronOreBlock(getProperties(Blocks.NETHER_GOLD_ORE)));
    public static final RegistryObject<Block> BASALTIC_MAGMA = registerBlockWithDefaultItem("basaltic_magma",		() -> new BasalticMagmaBlock(getProperties(Blocks.MAGMA_BLOCK).setLightLevel(value -> 2)));

    public static final RegistryObject<Block> SOUL_SAND_SLAB = registerBlockWithDefaultItem("soul_sand_slab",		() -> new SlabBlock(getProperties(Blocks.SOUL_SAND)));
    public static final RegistryObject<Block> SOUL_SAND_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("soul_sand_vertical_slab", () -> new VerticalSlabBlock(getProperties(Blocks.SOUL_SAND)), "quark");
	public static final RegistryObject<Block> SOUL_SAND_STAIRS = registerBlockWithDefaultItem("soul_sand_stairs",	() -> new StairsBlock(Blocks.SOUL_SAND::getDefaultState, getProperties((Blocks.SOUL_SAND))));

	public static final RegistryObject<Block> SOUL_SOIL_SLAB = registerBlockWithDefaultItem("soul_soil_slab", 		() -> new SlabBlock(getProperties(Blocks.SOUL_SOIL)));
	public static final RegistryObject<Block> SOUL_SOIL_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("soul_soil_vertical_slab", () -> new VerticalSlabBlock(getProperties(Blocks.SOUL_SOIL)), "quark");
	public static final RegistryObject<Block> SOUL_SOIL_STAIRS = registerBlockWithDefaultItem("soul_soil_stairs",	() -> new StairsBlock(Blocks.SOUL_SOIL::getDefaultState, getProperties(Blocks.SOUL_SOIL)));

	public static final RegistryObject<Block> SOUL_STONE = registerBlockWithDefaultItem("soul_stone",    			() -> new Block(getProperties(Blocks.COBBLESTONE).sound(IESoundEvents.SOUL_STONE_TYPE)));
	public static final RegistryObject<Block> SOUL_STONE_SLAB = registerBlockWithDefaultItem("soul_stone_slab",    () -> new SlabBlock(getProperties(Blocks.COBBLESTONE).sound(SoundType.SOUL_SOIL)));
	public static final RegistryObject<Block> SOUL_STONE_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("soul_stone_vertical_slab", () -> new VerticalSlabBlock((getProperties(Blocks.COBBLESTONE).sound(SoundType.SOUL_SOIL))), "quark");
    public static final RegistryObject<Block> SOUL_STONE_STAIRS = registerBlockWithDefaultItem("soul_stone_stairs",() -> new StairsBlock(Blocks.COBBLESTONE::getDefaultState, getProperties(Blocks.SOUL_SOIL)));
    public static final RegistryObject<Block> SOUL_STONE_WALL = registerBlockWithDefaultItem("soul_stone_wall",	() -> new WallBlock(getProperties(Blocks.COBBLESTONE_WALL)));

    public static final RegistryObject<Block> SOUL_SLATE = registerBlockWithDefaultItem("soul_slate",    			() -> new Block(getProperties(Blocks.STONE_BRICKS).sound(IESoundEvents.SOUL_STONE_TYPE)));
	public static final RegistryObject<Block> SOUL_SLATE_SLAB = registerBlockWithDefaultItem("soul_slate_slab", 	() -> new SlabBlock(getProperties(SOUL_SLATE.get())));
	public static final RegistryObject<Block> SOUL_SLATE_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("soul_slate_vertical_slab", () -> new VerticalSlabBlock(getProperties(SOUL_SLATE.get())), "quark");
	public static final RegistryObject<Block> SOUL_SLATE_STAIRS = registerBlockWithDefaultItem("soul_slate_stairs",	() -> new StairsBlock(() -> SOUL_SLATE.get().getDefaultState(), getProperties(SOUL_SLATE.get())));
	public static final RegistryObject<Block> SOUL_SLATE_BUTTON = registerBlockWithDefaultItem("soul_slate_button",			() -> new StoneButtonBlock(getProperties(SOUL_SLATE.get())));
	public static final RegistryObject<Block> SOUL_SLATE_PRESSURE_PLATE = registerBlockWithDefaultItem("soul_slate_pressure_plate", () -> new LightUpPressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, getProperties(SOUL_SLATE.get()).setLightLevel(getLightValueLit(15))));

    public static final RegistryObject<Block> SOUL_STONE_BRICKS = registerBlockWithDefaultItem("soul_stone_bricks",    		() -> new Block(getProperties(SOUL_STONE.get())));
    public static final RegistryObject<Block> SOUL_STONE_BRICK_SLAB = registerBlockWithDefaultItem("soul_stone_brick_slab", 	() -> new SlabBlock(getProperties(SOUL_STONE_BRICKS.get())));
    public static final RegistryObject<Block> SOUL_STONE_BRICK_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("soul_stone_brick_vertical_slab", () -> new VerticalSlabBlock(getProperties(SOUL_STONE_BRICKS.get())), "quark");
    public static final RegistryObject<Block> SOUL_STONE_BRICK_STAIRS = registerBlockWithDefaultItem("soul_stone_brick_stairs",	() -> new StairsBlock(() -> SOUL_STONE_BRICKS.get().getDefaultState(), getProperties(SOUL_STONE_BRICKS.get())));
    public static final RegistryObject<Block> SOUL_STONE_BRICK_WALL = registerBlockWithDefaultItem("soul_stone_brick_wall",		() -> new WallBlock(getProperties(SOUL_STONE_BRICKS.get())));
    public static final RegistryObject<Block> CRACKED_SOUL_STONE_BRICKS = registerBlockWithDefaultItem("cracked_soul_stone_bricks",    () -> new Block(getProperties(SOUL_STONE.get())));
    public static final RegistryObject<Block> CHISELED_SOUL_STONE_BRICKS = registerBlockWithDefaultItem("chiseled_soul_stone_bricks",	() -> new RotatedPillarBlock(getProperties(SOUL_STONE.get())));
    public static final RegistryObject<Block> CHARGED_CHISELED_SOUL_STONE_BRICKS = registerBlockWithDefaultItem("charged_chiseled_soul_stone_bricks",	() -> new RotatedPillarBlock(getProperties(SOUL_SLATE.get())));

    public static final RegistryObject<Block> SOUL_SLATE_BRICKS = registerBlockWithDefaultItem("soul_slate_bricks",    		() -> new Block(getProperties(SOUL_SLATE.get())));
	public static final RegistryObject<Block> SOUL_SLATE_BRICK_SLAB = registerBlockWithDefaultItem("soul_slate_brick_slab", 	() -> new SlabBlock(getProperties(SOUL_SLATE_BRICKS.get())));
	public static final RegistryObject<Block> SOUL_SLATE_BRICK_VERTICAL_SLAB = registerBlockWithDefaultItemConditioned("soul_slate_brick_vertical_slab", () -> new VerticalSlabBlock(getProperties(SOUL_SLATE_BRICKS.get())), "quark");
	public static final RegistryObject<Block> SOUL_SLATE_BRICK_STAIRS = registerBlockWithDefaultItem("soul_slate_brick_stairs",	() -> new StairsBlock(() -> SOUL_SLATE_BRICKS.get().getDefaultState(), getProperties(SOUL_SLATE_BRICKS.get())));
	public static final RegistryObject<Block> SOUL_SLATE_BRICK_WALL = registerBlockWithDefaultItem("soul_slate_brick_wall",		() -> new WallBlock(getProperties(SOUL_SLATE_BRICKS.get())));
	public static final RegistryObject<Block> CRACKED_SOUL_SLATE_BRICKS = registerBlockWithDefaultItem("cracked_soul_slate_bricks",    () -> new Block(getProperties(SOUL_SLATE.get())));
	public static final RegistryObject<Block> CHISELED_SOUL_SLATE_BRICKS = registerBlockWithDefaultItem("chiseled_soul_slate_bricks",	() -> new RotatedPillarBlock(getProperties(SOUL_SLATE.get())));
    public static final RegistryObject<Block> CHARGED_CHISELED_SOUL_SLATE_BRICKS = registerBlockWithDefaultItem("charged_chiseled_soul_slate_bricks",	() -> new RotatedPillarBlock(getProperties(SOUL_SLATE.get())));

	public static final RegistryObject<Block> CRIMSON_FUNGUS_CAP = registerBlockWithDefaultItem("crimson_fungus_cap", () -> new FungusCapBlock(AbstractBlock.Properties.from(Blocks.NETHER_WART_BLOCK)));
	public static final RegistryObject<Block> WARPED_FUNGUS_CAP = registerBlockWithDefaultItem("warped_fungus_cap", () -> new FungusCapBlock(AbstractBlock.Properties.from(Blocks.WARPED_WART_BLOCK)));
	public static final RegistryObject<Block> LUMINOUS_FUNGUS_CAP = registerBlockWithDefaultItem("luminous_fungus_cap", () -> new FungusCapBlock(AbstractBlock.Properties.from(Blocks.NETHER_WART_BLOCK).setLightLevel(value -> 14)));

	public static final RegistryObject<Block> GLOW_LANTERN = registerBlockWithDefaultItem("glow_lantern", () -> new LanternBlock(getProperties(Blocks.LANTERN)));
	public static final RegistryObject<Block> GLOW_TORCH = registerBlock("glow_torch", () -> new GlowTorchBlock(getProperties(Blocks.TORCH)));
	public static final RegistryObject<Block> GLOW_TORCH_WALL = registerBlock("glow_torch_wall", () -> new GlowWallTorchBlock(getProperties(IEBlocks.GLOW_TORCH.get()).lootFrom(GLOW_TORCH.get())));
	public static final RegistryObject<Block> GLOW_CAMPFIRE = registerBlockWithDefaultItem("glow_campfire", () -> new GlowCampfireBlock(true, 2, getProperties(Blocks.CAMPFIRE)));
	public static final RegistryObject<Block> GLOW_FIRE = registerBlock("glow_fire", () -> new GlowFireBlock(getProperties(Blocks.FIRE)));

	public static final RegistryObject<Block> GLOWSILK_COCOON = registerBlockWithDefaultItem("glowsilk_cocoon", () -> new RotatedPillarBlock(getProperties(Material.LEAVES).sound(SoundType.CLOTH).setRequiresTool().harvestTool(ToolType.HOE).harvestLevel(3).hardnessAndResistance(5.0F, 1200.0F).setLightLevel(value -> 5)));
	// Foliage
	public static final RegistryObject<Block> LUMINOUS_FUNGUS = registerBlockWithDefaultItem("luminous_fungus", () -> new LuminousFungusBlock(getProperties(Material.PLANTS).setLightLevel(getLightValueLit(15)).doesNotBlockMovement().sound(SoundType.PLANT)));
	public static final RegistryObject<Block> DULLTHORNS = registerBlock("dullthorns", () -> new DullthornsBlock(AbstractBlock.Properties.create(Material.PLANTS).setLightLevel(value -> 3).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.1F).sound(SoundType.PLANT)));

	public static final RegistryObject<Block> DULLTHORNS_BLOCK = registerBlockWithDefaultItem("dullthorns_block", () -> new DullthornsBlockBlock(AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(0.2F).sound(SoundType.WART)));

	public static final RegistryObject<FlowerPotBlock> POTTED_LUMINOUS_FUNGUS = registerBlock("potted_luminous_fungus", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, LUMINOUS_FUNGUS, getProperties(Blocks.FLOWER_POT)));
	public static final RegistryObject<FlowerPotBlock> POTTED_DULLTHORNS = registerBlock("potted_dullthorns", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, DULLTHORNS, getProperties(Blocks.FLOWER_POT)));

	public static final RegistryObject<Block> SHROOMLIGHT_FUNGUS = registerBlockWithDefaultItem("shroomlight_fungus", () -> new ShroomlightFungusBlock(getProperties(Material.PLANTS).setLightLevel(value -> 13).doesNotBlockMovement().sound(SoundType.PLANT)));
	public static final RegistryObject<FlowerPotBlock> POTTED_SHROOMLIGHT_FUNGUS = registerBlock("potted_shroomlight_fungus", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SHROOMLIGHT_FUNGUS, getProperties(Blocks.FLOWER_POT)));

	public static final RegistryObject<BuriedBoneBlock> BURIED_BONE = registerBlock("buried_bone", () -> new BuriedBoneBlock(getProperties(Material.PLANTS).doesNotBlockMovement().sound(SoundType.BONE)));
	public static final RegistryObject<FlowerPotBlock> POTTED_BURIED_BONE = registerBlock("potted_buried_bone", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BURIED_BONE, getProperties(Blocks.FLOWER_POT)));

    public static final RegistryObject<Block> CRIMSON_NYLIUM_PATH = registerBlockWithDefaultItem("crimson_nylium_path", () -> new NetherrackPathBlock(getProperties(Blocks.NETHERRACK)));
    public static final RegistryObject<Block> WARPED_NYLIUM_PATH = registerBlockWithDefaultItem("warped_nylium_path", () -> new NetherrackPathBlock(getProperties(Blocks.NETHERRACK)));
    public static final RegistryObject<Block> CRIMSON_NYLIUM_CARPET = registerBlockWithDefaultItem("crimson_nylium_carpet", () -> new NetherCarpetBlock(AbstractBlock.Properties.create(Material.CARPET, MaterialColor.CRIMSON_NYLIUM).hardnessAndResistance(0.1F).sound(SoundType.NYLIUM)));
    public static final RegistryObject<Block> WARPED_NYLIUM_CARPET = registerBlockWithDefaultItem("warped_nylium_carpet", () -> new NetherCarpetBlock(AbstractBlock.Properties.create(Material.CARPET, MaterialColor.WARPED_NYLIUM).hardnessAndResistance(0.1F).sound(SoundType.NYLIUM)));
    public static final RegistryObject<Block> SOUL_SOIL_PATH = registerBlockWithDefaultItem("soul_soil_path", () -> new SoulSoilPathBlock(getProperties(Blocks.SOUL_SOIL)));

    public static final RegistryObject<Block> QUARTZ_GLASS = registerBlockWithDefaultItem("quartz_glass", () -> new GlassBlock(AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.GLASS).notSolid().setAllowsSpawn(IEBlocks::neverAllowSpawn).setOpaque(IEBlocks::isntSolid).setSuffocates(IEBlocks::isntSolid).setBlocksVision(IEBlocks::isntSolid)));
    public static final RegistryObject<Block> QUARTZ_GLASS_PANE = registerBlockWithDefaultItem("quartz_glass_pane", () -> new PaneBlock(AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.GLASS).notSolid()));

    public static final RegistryObject<Block> GLOW_GLASS = registerBlockWithDefaultItem("glow_glass", () -> new GlassBlock(AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid().setAllowsSpawn(IEBlocks::neverAllowSpawn).setOpaque(IEBlocks::isntSolid).setSuffocates(IEBlocks::isntSolid).setBlocksVision(IEBlocks::isntSolid).setLightLevel(value -> 10)));
    public static final RegistryObject<Block> GLOW_GLASS_PANE = registerBlockWithDefaultItem("glow_glass_pane", () -> new PaneBlock(AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid().setLightLevel(value -> 10)));

    private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }

    private static Boolean neverAllowSpawn(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
        return (boolean)false;
    }

    public static AbstractBlock.Properties getProperties(Material materialIn, float hardnessAndResistanceIn) {
		return getProperties(materialIn, hardnessAndResistanceIn, hardnessAndResistanceIn);
    }

	public static AbstractBlock.Properties getProperties(Material materialIn, float hardnessIn, float resistanceIn) {
		return AbstractBlock.Properties.create(materialIn).hardnessAndResistance(hardnessIn, resistanceIn);
	}

	public static AbstractBlock.Properties getProperties(Material materialIn) {
		return AbstractBlock.Properties.create(materialIn).zeroHardnessAndResistance();
	}

	public static AbstractBlock.Properties getProperties(Block block) {
		return AbstractBlock.Properties.from(block);
	}

	private static ToIntFunction<BlockState> getLightValueLit(int lightValue) {
		return (state) -> state.get(BlockStateProperties.LIT) ? lightValue : 0;
	}

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
		InfernalExpansion.LOGGER.info("Infernal Expansion: Blocks Registered!");
	}

	public static <T extends Block> RegistryObject<T> registerBlockWithDefaultItem(String name, Supplier<? extends T> blockSupplier) {
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        IEItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(InfernalExpansion.TAB)));
        return block;
    }

    public static <T extends Block> RegistryObject<T> registerBlockWithDefaultItemConditioned(String name, Supplier<? extends T> blockSupplier, String modID) {
        if (DataUtil.isLoaded(modID)) {
            RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
            IEItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(InfernalExpansion.TAB)));
            return block;
        } else {
            RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
            IEItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
            return block;
        }
    }

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<? extends T> blockSupplier) {
        return BLOCKS.register(name, blockSupplier);
    }

}
