package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.blocks.BuriedBoneBlock;
import com.nekomaster1000.infernalexp.blocks.CrumblingBlackstoneBlock;
import com.nekomaster1000.infernalexp.blocks.DullthornsBlock;
import com.nekomaster1000.infernalexp.blocks.DullthornsBlockBlock;
import com.nekomaster1000.infernalexp.blocks.FungusCapBlock;
import com.nekomaster1000.infernalexp.blocks.GlowCampfireBlock;
import com.nekomaster1000.infernalexp.blocks.GlowFireBlock;
import com.nekomaster1000.infernalexp.blocks.GlowSandBlock;
import com.nekomaster1000.infernalexp.blocks.GlowTorchBlock;
import com.nekomaster1000.infernalexp.blocks.GlowWallTorchBlock;
import com.nekomaster1000.infernalexp.blocks.GlowdustBlock;
import com.nekomaster1000.infernalexp.blocks.LuminousFungusBlock;
import com.nekomaster1000.infernalexp.blocks.ShroomlightFungusBlock;
import com.nekomaster1000.infernalexp.blocks.TrappedGlowSandBlock;
import com.nekomaster1000.infernalexp.util.RegistryHandler;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

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
    			For RECIPIES, make sure the block is craftable in some form if not a biome-building block.
    			If it's a type of stone or has bricks, give it a STONE-CUTTER recipe.
    			Furnace Recipes are included in the recipes folder. */

    // Blocks
    public static final RegistryObject<Block> DIMSTONE = BLOCKS.register("dimstone",            () -> new Block(getProperties(Material.GLASS, 3.5F, 2.0F).sound(RegistryHandler.DIMSTONE_TYPE).setRequiresTool().harvestTool(ToolType.PICKAXE).setLightLevel(value -> 12)));
    public static final RegistryObject<Block> DULLSTONE = BLOCKS.register("dullstone",          () -> new Block(getProperties(Material.GLASS, 1.5F, 6.0F).sound(RegistryHandler.DULLSTONE_TYPE).setRequiresTool().harvestTool(ToolType.PICKAXE).setLightLevel(value -> 0)));
    public static final RegistryObject<Block> SMOOTH_GLOWSTONE = BLOCKS.register("smooth_glowstone",    () -> new Block(getProperties(Material.GLASS, 1.5F, 6.0F).sound(SoundType.GLASS).setRequiresTool().harvestTool(ToolType.PICKAXE).setLightLevel(value -> 15)));
    public static final RegistryObject<Block> SMOOTH_DIMSTONE = BLOCKS.register("smooth_dimstone",      () -> new Block(getProperties(SMOOTH_GLOWSTONE.get()).setLightLevel(value -> 12)));
    public static final RegistryObject<Block> SMOOTH_DULLSTONE = BLOCKS.register("smooth_dullstone",    () -> new Block(getProperties(SMOOTH_GLOWSTONE.get()).setLightLevel(value -> 0)));
    public static final RegistryObject<Block> GLOWSTONE_BRICKS = BLOCKS.register("glowstone_bricks",      () -> new Block(getProperties(SMOOTH_GLOWSTONE.get())));
    public static final RegistryObject<Block> DIMSTONE_BRICKS = BLOCKS.register("dimstone_bricks",        () -> new Block(getProperties(SMOOTH_DIMSTONE.get())));
    public static final RegistryObject<Block> DULLSTONE_BRICKS = BLOCKS.register("dullstone_bricks",      () -> new Block(getProperties(SMOOTH_DULLSTONE.get())));
    public static final RegistryObject<Block> CRACKED_GLOWSTONE_BRICKS = BLOCKS.register("cracked_glowstone_bricks",   () -> new Block(getProperties(SMOOTH_GLOWSTONE.get())));
    public static final RegistryObject<Block> CRACKED_DIMSTONE_BRICKS = BLOCKS.register("cracked_dimstone_bricks",     () -> new Block(getProperties(SMOOTH_DIMSTONE.get())));
    public static final RegistryObject<Block> CRACKED_DULLSTONE_BRICKS = BLOCKS.register("cracked_dullstone_bricks",   () -> new Block(getProperties(SMOOTH_DULLSTONE.get())));
    public static final RegistryObject<Block> CHISELED_GLOWSTONE_BRICKS = BLOCKS.register("chiseled_glowstone_bricks", () -> new Block(getProperties(SMOOTH_GLOWSTONE.get())));
    public static final RegistryObject<Block> CHISELED_DIMSTONE_BRICKS = BLOCKS.register("chiseled_dimstone_bricks",   () -> new Block(getProperties(SMOOTH_DIMSTONE.get())));
    public static final RegistryObject<Block> CHISELED_DULLSTONE_BRICKS = BLOCKS.register("chiseled_dullstone_bricks", () -> new Block(getProperties(SMOOTH_DULLSTONE.get())));

    public static final RegistryObject<Block> SMOOTH_GLOWSTONE_SLAB = BLOCKS.register("smooth_glowstone_slab",       () -> new SlabBlock(getProperties(SMOOTH_GLOWSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_GLOWSTONE_STAIRS = BLOCKS.register("smooth_glowstone_stairs",   () -> new StairsBlock(() -> SMOOTH_GLOWSTONE.get().getDefaultState(), getProperties(SMOOTH_GLOWSTONE.get())));
	public static final RegistryObject<Block> SMOOTH_GLOWSTONE_BUTTON = BLOCKS.register("smooth_glowstone_button",   () -> new StoneButtonBlock(getProperties(SMOOTH_GLOWSTONE.get())));
	public static final RegistryObject<Block> SMOOTH_GLOWSTONE_PRESSURE_PLATE = BLOCKS.register("smooth_glowstone_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, getProperties(SMOOTH_GLOWSTONE.get())));

	public static final RegistryObject<Block> SMOOTH_DIMSTONE_SLAB = BLOCKS.register("smooth_dimstone_slab",         () -> new SlabBlock(getProperties(SMOOTH_DIMSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_DIMSTONE_STAIRS = BLOCKS.register("smooth_dimstone_stairs",     () -> new StairsBlock(() -> SMOOTH_DIMSTONE.get().getDefaultState(), getProperties(SMOOTH_DIMSTONE.get())));
	public static final RegistryObject<Block> SMOOTH_DIMSTONE_BUTTON = BLOCKS.register("smooth_dimstone_button",     () -> new StoneButtonBlock(getProperties(SMOOTH_DIMSTONE.get())));

	public static final RegistryObject<Block> SMOOTH_DULLSTONE_SLAB = BLOCKS.register("smooth_dullstone_slab",       () -> new SlabBlock(getProperties(SMOOTH_DULLSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_DULLSTONE_STAIRS = BLOCKS.register("smooth_dullstone_stairs",   () -> new StairsBlock(() -> SMOOTH_DULLSTONE.get().getDefaultState(), getProperties(SMOOTH_DULLSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_DULLSTONE_BUTTON = BLOCKS.register("smooth_dullstone_button",   () -> new StoneButtonBlock(getProperties(SMOOTH_DULLSTONE.get())));

    public static final RegistryObject<Block> GLOWSTONE_BRICK_SLAB = BLOCKS.register("glowstone_brick_slab",         () -> new SlabBlock(getProperties(GLOWSTONE_BRICKS.get())));
    public static final RegistryObject<Block> GLOWSTONE_BRICK_STAIRS = BLOCKS.register("glowstone_brick_stairs",     () -> new StairsBlock(() -> GLOWSTONE_BRICKS.get().getDefaultState(), getProperties(GLOWSTONE_BRICKS.get())));
	public static final RegistryObject<Block> GLOWSTONE_BRICK_WALL = BLOCKS.register("glowstone_brick_wall",		   () -> new WallBlock(getProperties(GLOWSTONE_BRICKS.get())));

	public static final RegistryObject<Block> DIMSTONE_BRICK_SLAB = BLOCKS.register("dimstone_brick_slab",           () -> new SlabBlock(getProperties(DIMSTONE_BRICKS.get())));
    public static final RegistryObject<Block> DIMSTONE_BRICK_STAIRS = BLOCKS.register("dimstone_brick_stairs",       () -> new StairsBlock(() -> DIMSTONE_BRICKS.get().getDefaultState(), getProperties(DIMSTONE_BRICKS.get())));
	public static final RegistryObject<Block> DIMSTONE_BRICK_WALL = BLOCKS.register("dimstone_brick_wall",		   () -> new WallBlock(getProperties(DIMSTONE_BRICKS.get())));

	public static final RegistryObject<Block> DULLSTONE_BRICK_SLAB = BLOCKS.register("dullstone_brick_slab",         () -> new SlabBlock(getProperties(DULLSTONE_BRICKS.get())));
    public static final RegistryObject<Block> DULLSTONE_BRICK_STAIRS = BLOCKS.register("dullstone_brick_stairs",     () -> new StairsBlock(() -> DULLSTONE_BRICKS.get().getDefaultState(), getProperties(DULLSTONE_BRICKS.get())));
	public static final RegistryObject<Block> DULLSTONE_BRICK_WALL = BLOCKS.register("dullstone_brick_wall",		   () -> new WallBlock(getProperties(DULLSTONE_BRICKS.get())));

    public static final RegistryObject<Block> GLOWDUST = BLOCKS.register("glowdust", () -> new GlowdustBlock(getProperties(Blocks.SAND).setLightLevel(value -> 8).setRequiresTool().harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.2f)));
    public static final RegistryObject<Block> GLOWDUST_SAND = BLOCKS.register("glowdust_sand",          () -> new GlowSandBlock(0xFFC267, getProperties(GLOWDUST.get()).hardnessAndResistance(0.5F)));
    public static final RegistryObject<Block> LUMINOUS_WART_BLOCK = BLOCKS.register("luminous_wart_block",          () -> new Block(getProperties(Blocks.NETHER_WART_BLOCK).setLightLevel(value -> 14)));
    public static final RegistryObject<Block> GLOWDUST_STONE = BLOCKS.register("glowdust_stone",                () -> new Block(getProperties(Blocks.SANDSTONE).setLightLevel(value -> 8)));
    public static final RegistryObject<Block> GLOWDUST_STONE_BRICKS = BLOCKS.register("glowdust_stone_bricks",  () -> new Block(getProperties(Blocks.SANDSTONE).setLightLevel(value -> 8)));
    public static final RegistryObject<Block> GLOWDUST_STONE_BRICK_SLAB = BLOCKS.register("glowdust_stone_brick_slab",      () -> new SlabBlock(getProperties(Blocks.SANDSTONE).setLightLevel(value -> 8)));
    public static final RegistryObject<Block> GLOWDUST_STONE_BRICK_STAIRS = BLOCKS.register("glowdust_stone_brick_stairs",  () -> new StairsBlock(() -> GLOWDUST_STONE.get().getDefaultState(), getProperties(Blocks.SANDSTONE).setLightLevel(value -> 8)));
    public static final RegistryObject<Block> GLOWDUST_STONE_BRICK_WALL = BLOCKS.register("glowdust_stone_brick_wall", 	  () -> new WallBlock(getProperties(GLOWDUST_STONE_BRICKS.get())));
//	public static final RegistryObject<Block> CRACKED_GLOWDUST_STONE_BRICKS = BLOCKS.register("cracked_glowdust_stone_bricks",	() -> new Block(getProperties(Blocks.SANDSTONE).setLightLevel(value -> 8)));
//	public static final RegistryObject<Block> CHISELED_GLOWDUST_STONE_BRICKS = BLOCKS.register("chiseled_glowdust_stone_brick",	() -> new Block(getProperties(GLOWDUST_STONE_BRICKS.get())));

	public static final RegistryObject<Block> TRAPPED_GLOWDUST_SAND = BLOCKS.register("trapped_glowdust_sand",  () -> new TrappedGlowSandBlock(0xFFC267, getProperties(GLOWDUST.get()).hardnessAndResistance(0.2F).setLightLevel(value -> 4)));
    public static final RegistryObject<Block> GLOWDUST_SANDSTONE = BLOCKS.register("glowdust_sandstone",                    () -> new Block(getProperties(Blocks.SANDSTONE).setLightLevel(value -> 8)));
    public static final RegistryObject<Block> CUT_GLOWDUST_SANDSTONE = BLOCKS.register("cut_glowdust_sandstone",            () -> new Block(getProperties(GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> CHISELED_GLOWDUST_SANDSTONE = BLOCKS.register("chiseled_glowdust_sandstone",  () -> new Block(getProperties(GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_GLOWDUST_SANDSTONE = BLOCKS.register("smooth_glowdust_sandstone",      () -> new Block(getProperties(GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> GLOWDUST_SANDSTONE_SLAB = BLOCKS.register("glowdust_sandstone_slab",          () -> new SlabBlock(getProperties(GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> GLOWDUST_SANDSTONE_STAIRS = BLOCKS.register("glowdust_sandstone_stairs",      () -> new StairsBlock(() -> GLOWDUST_SANDSTONE.get().getDefaultState(), getProperties(GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> CUT_GLOWDUST_SANDSTONE_SLAB = BLOCKS.register("cut_glowdust_sandstone_slab",              () -> new SlabBlock(getProperties(CUT_GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_GLOWDUST_SANDSTONE_SLAB = BLOCKS.register("smooth_glowdust_sandstone_slab",        () -> new SlabBlock(getProperties(SMOOTH_GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_GLOWDUST_SANDSTONE_STAIRS = BLOCKS.register("smooth_glowdust_sandstone_stairs",    () -> new StairsBlock(() -> SMOOTH_GLOWDUST_SANDSTONE.get().getDefaultState(), getProperties(SMOOTH_GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> GLOWDUST_SANDSTONE_WALL = BLOCKS.register("glowdust_sandstone_wall",          () -> new WallBlock(getProperties(GLOWDUST_SANDSTONE.get())));

	public static final RegistryObject<Block> CRUMBLING_BLACKSTONE = BLOCKS.register("crumbling_blackstone", () -> new CrumblingBlackstoneBlock(AbstractBlock.Properties.from(Blocks.NETHERRACK)));
	public static final RegistryObject<Block> RUBBLE = BLOCKS.register("rubble", () -> new Block(getProperties(Blocks.GRAVEL)));
	public static final RegistryObject<Block> SILT = BLOCKS.register("silt", () -> new Block(getProperties(Blocks.SAND)));

//	public static final RegistryObject<Block> BASALT_COBBLED = BLOCKS.register("basalt_cobbled", () -> new Block(getProperties(Blocks.BASALT)));

	public static final RegistryObject<Block> BASALT_SLAB = BLOCKS.register("basalt_slab",	() -> new SlabBlock(getProperties(Blocks.BASALT)));
 	public static final RegistryObject<Block> BASALT_STAIRS = BLOCKS.register("basalt_stairs",	() -> new StairsBlock(() -> (Blocks.BASALT).getDefaultState(), getProperties(Blocks.BASALT)));
//	public static final RegistryObject<Block> BASALT_WALL = BLOCKS.register("basalt_wall",		() -> new WallBlock(getProperties(Blocks.BASALT)));
//	public static final RegistryObject<Block> BASALT_BUTTON = BLOCKS.register("basalt_button",	() -> new StoneButtonBlock(getProperties(Blocks.BASALT)));
//
//	public static final RegistryObject<Block> POLISHED_BASALT_PRESSURE_PLATE = BLOCKS.register ("polished_basalt_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, getProperties(Blocks.POLISHED_BASALT)));
// 	public static final RegistryObject<Block> POLISHED_BASALT_SLAB = BLOCKS.register("polished_basalt_slab",	() -> new SlabBlock(getProperties(Blocks.POLISHED_BASALT)));
//	public static final RegistryObject<Block> POLISHED_BASALT_TILES = BLOCKS.register("polished_basalt_tiles", () -> new Block(getProperties(Blocks.POLISHED_BASALT)));
//	public static final RegistryObject<Block> POLISHED_BASALT_TILES_SLAB = BLOCKS.register("polished_basalt_tiles_slab", () -> new SlabBlock(getProperties(Blocks.POLISHED_BASALT)));

//  public static final RegistryObject<Block> BASALT_BRICKS = BLOCKS.register("basalt_bricks",    			() -> new Block(getProperties(Blocks.BASALT)));
// 	public static final RegistryObject<Block> BASALT_BRICK_SLAB = BLOCKS.register("basalt_bricks_slab", 	() -> new SlabBlock(getProperties(BASALT_BRICKS.get())));
//  public static final RegistryObject<Block> BASALT_BRICK_STAIRS = BLOCKS.register("basalt_bricks_stairs",	() -> new StairsBlock(() -> BASALT_BRICKS.get().getDefaultState(), getProperties(BASALT_BRICKS.get())));
//	public static final RegistryObject<Block> BASALT_BRICK_WALL = BLOCKS.register("basalt_bricks_wall",		() -> new WallBlock(getProperties(BASALT_BRICKS.get())));
//  public static final RegistryObject<Block> CRACKED_BASALT_BRICKS = BLOCKS.register("cracked_basalt_bricks",      () -> new Block(getProperties(Blocks.BASALT)));
//  public static final RegistryObject<Block> CHISELED_BASALT_BRICK = BLOCKS.register("chiseled_basalt_bricks",		() -> new Block(getProperties(Blocks.BASALT)));

    public static final RegistryObject<Block> BASALT_IRON_ORE = BLOCKS.register("basalt_iron_ore", () -> new Block(AbstractBlock.Properties.from(Blocks.NETHER_GOLD_ORE)));

//  public static final RegistryObject<Block> BASALTIC_MAGMA = BLOCKS.register("basaltic_magma",			() -> new Block(getProperties(Blocks.MAGMA_BLOCK)));


//  public static final RegistryObject<Block> SOUL_SAND_SLAB = BLOCKS.register("soul_sand_slab",		() -> new SlabBlock(getProperties(Blocks.SOUL_SAND)));
//	public static final RegistryObject<Block> SOUL_SAND_STAIRS = BLOCKS.register("soul_sand_stairs",	() -> new StairsBlock(() -> Blocks.SOUL_SAND.getDefaultState(), getProperties((Blocks.SOUL_SAND))));
//
//	public static final RegistryObject<Block> SOUL_SOIL_SLAB = BLOCKS.register("soul_soil_slab", 		() -> new SlabBlock(getProperties(Blocks.SOUL_SOIL)));
//	public static final RegistryObject<Block> SOUL_SOIL_STAIRS = BLOCKS.register("soul_soil_stairs",	() -> new StairsBlock(() -> Blocks.SOUL_SOIL.getDefaultState(), getProperties(Blocks.SOUL_SOIL)));
//
//	public static final RegistryObject<Block> SOUL_STONE = BLOCKS.register("soul_stone",    			() -> new Block(getProperties(Blocks.SOUL_SOIL)));
//
//	public static final RegistryObject<Block> SOUL_SLATE = BLOCKS.register("soul_bricks",    			() -> new Block(getProperties(Blocks.SOUL_SOIL)));
//	public static final RegistryObject<Block> SOUL_SLATE_SLAB = BLOCKS.register("soul_bricks_slab", 	() -> new SlabBlock(getProperties(SOUL_SLATE.get())));
//	public static final RegistryObject<Block> SOUL_SLATE_STAIRS = BLOCKS.register("soul_bricks_stairs",	() -> new StairsBlock(() -> SOUL_SLATE.get().getDefaultState(), getProperties(SOUL_SLATE.get())));
//	public static final RegistryObject<Block> SOUL_SLATE_BUTTON = BLOCKS.register("soul_slate_button",			() -> new StoneButtonBlock(getProperties(SOUL_SLATE.get())));
//	public static final RegistryObject<Block> SOUL_SLATE_PRESSURE_PLATE = BLOCKS.register ("soul_slate_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, (getProperties(SOUL_SLATE.get()))));
//
//	public static final RegistryObject<Block> SOUL_SLATE_BRICKS = BLOCKS.register("soul_bricks",    			() -> new Block(getProperties(SOUL_SLATE.get())));
//	public static final RegistryObject<Block> SOUL_SLATE_BRICK_SLAB = BLOCKS.register("soul_bricks_slab", 	() -> new SlabBlock(getProperties(SOUL_SLATE_BRICKS.get())));
//	public static final RegistryObject<Block> SOUL_SLATE_BRICK_STAIRS = BLOCKS.register("soul_bricks_stairs",	() -> new StairsBlock(() -> SOUL_SLATE_BRICKS.get().getDefaultState(), getProperties(SOUL_SLATE_BRICKS.get())));
//	public static final RegistryObject<Block> SOUL_SLATE_BRICK_WALL = BLOCKS.register("soul_bricks_wall",		() -> new WallBlock(getProperties(SOUL_SLATE_BRICKS.get())));
//	public static final RegistryObject<Block> CRACKED_SOUL_SLATE_BRICKS = BLOCKS.register("cracked_soul_bricks",      () -> new Block(getProperties(SOUL_SLATE.get())));
//	public static final RegistryObject<Block> CHISELED_SOUL_SLATE_BRICK = BLOCKS.register("chiseled_soul_bricks",		() -> new Block(getProperties(SOUL_SLATE.get())));

	public static final RegistryObject<Block> CRIMSON_FUNGUS_CAP = BLOCKS.register("crimson_fungus_cap", () -> new FungusCapBlock(AbstractBlock.Properties.from(Blocks.NETHER_WART_BLOCK)));
	public static final RegistryObject<Block> WARPED_FUNGUS_CAP = BLOCKS.register("warped_fungus_cap", () -> new FungusCapBlock(AbstractBlock.Properties.from(Blocks.WARPED_WART_BLOCK)));
	public static final RegistryObject<Block> LUMINOUS_FUNGUS_CAP = BLOCKS.register("luminous_fungus_cap", () -> new FungusCapBlock(AbstractBlock.Properties.from(Blocks.NETHER_WART_BLOCK).setLightLevel(value -> 14)));

	public static final RegistryObject<Block> GLOW_LANTERN = BLOCKS.register("lantern_glow", () -> new LanternBlock(getProperties(Blocks.LANTERN)));
	public static final RegistryObject<Block> GLOW_TORCH = BLOCKS.register("torch_glow", () -> new GlowTorchBlock(getProperties(Blocks.TORCH)));
	public static final RegistryObject<Block> GLOW_WALL_TORCH = BLOCKS.register("torch_glow_wall", () -> new GlowWallTorchBlock(getProperties(IEBlocks.GLOW_TORCH.get()).lootFrom(GLOW_TORCH.get())));
	public static final RegistryObject<Block> GLOW_CAMPFIRE = BLOCKS.register("campfire_glow", () -> new GlowCampfireBlock(true, 2, getProperties(Blocks.CAMPFIRE)));
	public static final RegistryObject<Block> GLOW_FIRE = BLOCKS.register("fire_glow", () -> new GlowFireBlock(getProperties(Blocks.FIRE)));

	// Foliage
	public static final RegistryObject<Block> LUMINOUS_FUNGUS = BLOCKS.register("luminous_fungus", () -> new LuminousFungusBlock(getProperties(Material.PLANTS).setLightLevel(getLightValueLit(15)).doesNotBlockMovement().sound(SoundType.PLANT)));
	public static final RegistryObject<Block> DULLTHORNS = BLOCKS.register("dullthorns", () -> new DullthornsBlock(AbstractBlock.Properties.create(Material.CACTUS).setLightLevel(value -> 3).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.1F).sound(SoundType.PLANT)));

	public static final RegistryObject<Block> DULLTHORNS_BLOCK = BLOCKS.register("dullthorns_block", () -> new DullthornsBlockBlock(AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(0.2F).sound(SoundType.WART)));

	public static final RegistryObject<FlowerPotBlock> POTTED_LUMINOUS_FUNGUS = BLOCKS.register("potted_luminous_fungus", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, LUMINOUS_FUNGUS, getProperties(Blocks.FLOWER_POT)));
	public static final RegistryObject<FlowerPotBlock> POTTED_DULLTHORNS = BLOCKS.register("potted_dullthorns", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, DULLTHORNS, getProperties(Blocks.FLOWER_POT)));

	public static final RegistryObject<Block> SHROOMLIGHT_FUNGUS = BLOCKS.register("shroomlight_fungus", () -> new ShroomlightFungusBlock(getProperties(Material.PLANTS).setLightLevel(value -> 13).doesNotBlockMovement().sound(SoundType.PLANT)));
	public static final RegistryObject<FlowerPotBlock> POTTED_SHROOMLIGHT_FUNGUS = BLOCKS.register("potted_shroomlight_fungus", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SHROOMLIGHT_FUNGUS, getProperties(Blocks.FLOWER_POT)));

	public static final RegistryObject<BuriedBoneBlock> BURIED_BONE = BLOCKS.register("buried_bone", () -> new BuriedBoneBlock(getProperties(Material.PLANTS).doesNotBlockMovement().sound(SoundType.BONE)));
	public static final RegistryObject<FlowerPotBlock> POTTED_BURIED_BONE = BLOCKS.register("potted_buried_bone", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BURIED_BONE, getProperties(Blocks.FLOWER_POT)));

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
}
