package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.blocks.CrumblingBlackstoneBlock;
import com.nekomaster1000.infernalexp.blocks.DullthornsBlock;
import com.nekomaster1000.infernalexp.blocks.DullthornsBlockBlock;
import com.nekomaster1000.infernalexp.blocks.GlowBlockBase;
import com.nekomaster1000.infernalexp.blocks.GlowCampfireBlock;
import com.nekomaster1000.infernalexp.blocks.GlowFireBlock;
import com.nekomaster1000.infernalexp.blocks.GlowSandBlock;
import com.nekomaster1000.infernalexp.blocks.GlowdustBlock;
import com.nekomaster1000.infernalexp.blocks.LuminousFungusBlock;
import com.nekomaster1000.infernalexp.blocks.ShroomlightFungusBlock;
import com.nekomaster1000.infernalexp.blocks.TrappedGlowSandBlock;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.material.Material;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, InfernalExpansion.MOD_ID);

    /*       A single file in /blockstates pulls from (potentially multiple files in)
             /model/block, which pulls from /textures/blocks all the different ways a block can look.
                (If a block is animated, pull the name of the animation texture.)
                /item pulls from the individual json file in /model/block.    */

    // Blocks
    public static final RegistryObject<Block> DIMSTONE = BLOCKS.register("dimstone",            () -> new Block(getProperties(Material.GLASS, 3.5F, 2.0F).sound(SoundType.GLASS).setRequiresTool().harvestTool(ToolType.PICKAXE).setLightLevel(value -> 12)));
    public static final RegistryObject<Block> DULLSTONE = BLOCKS.register("dullstone",          () -> new Block(getProperties(Material.GLASS, 10.0F, 6.0F).sound(SoundType.GLASS).setRequiresTool().harvestTool(ToolType.PICKAXE).setLightLevel(value -> 0)));
    public static final RegistryObject<Block> SMOOTH_GLOWSTONE = BLOCKS.register("smooth_glowstone",    () -> new Block(getProperties(Material.GLASS, 1.5F, 6.0F).sound(SoundType.GLASS).setRequiresTool().harvestTool(ToolType.PICKAXE).setLightLevel(value -> 15)));
    public static final RegistryObject<Block> SMOOTH_DIMSTONE = BLOCKS.register("smooth_dimstone",      () -> new Block(getProperties(SMOOTH_GLOWSTONE.get()).setLightLevel(value -> 12)));
    public static final RegistryObject<Block> SMOOTH_DULLSTONE = BLOCKS.register("smooth_dullstone",    () -> new Block(getProperties(SMOOTH_GLOWSTONE.get()).setLightLevel(value -> 0)));
    public static final RegistryObject<Block> GLOWSTONE_BRICK = BLOCKS.register("glowstone_brick",      () -> new Block(getProperties(SMOOTH_GLOWSTONE.get())));
    public static final RegistryObject<Block> DIMSTONE_BRICK = BLOCKS.register("dimstone_brick",        () -> new Block(getProperties(SMOOTH_DIMSTONE.get())));
    public static final RegistryObject<Block> DULLSTONE_BRICK = BLOCKS.register("dullstone_brick",      () -> new Block(getProperties(SMOOTH_DULLSTONE.get())));
    public static final RegistryObject<Block> CRACKED_GLOWSTONE_BRICK = BLOCKS.register("cracked_glowstone_brick",   () -> new Block(getProperties(SMOOTH_GLOWSTONE.get())));
    public static final RegistryObject<Block> CRACKED_DIMSTONE_BRICK = BLOCKS.register("cracked_dimstone_brick",     () -> new Block(getProperties(SMOOTH_DIMSTONE.get())));
    public static final RegistryObject<Block> CRACKED_DULLSTONE_BRICK = BLOCKS.register("cracked_dullstone_brick",   () -> new Block(getProperties(SMOOTH_DULLSTONE.get())));
    public static final RegistryObject<Block> CHISELED_GLOWSTONE_BRICK = BLOCKS.register("chiseled_glowstone_brick", () -> new Block(getProperties(SMOOTH_GLOWSTONE.get())));
    public static final RegistryObject<Block> CHISELED_DIMSTONE_BRICK = BLOCKS.register("chiseled_dimstone_brick",   () -> new Block(getProperties(SMOOTH_DIMSTONE.get())));
    public static final RegistryObject<Block> CHISELED_DULLSTONE_BRICK = BLOCKS.register("chiseled_dullstone_brick", () -> new Block(getProperties(SMOOTH_DULLSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_GLOWSTONE_SLAB = BLOCKS.register("smooth_glowstone_slab",       () -> new SlabBlock(getProperties(SMOOTH_GLOWSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_GLOWSTONE_STAIRS = BLOCKS.register("smooth_glowstone_stairs",   () -> new StairsBlock(() -> SMOOTH_GLOWSTONE.get().getDefaultState(), getProperties(SMOOTH_GLOWSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_DIMSTONE_SLAB = BLOCKS.register("smooth_dimstone_slab",         () -> new SlabBlock(getProperties(SMOOTH_DIMSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_DIMSTONE_STAIRS = BLOCKS.register("smooth_dimstone_stairs",     () -> new StairsBlock(() -> SMOOTH_DIMSTONE.get().getDefaultState(), getProperties(SMOOTH_DIMSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_DULLSTONE_SLAB = BLOCKS.register("smooth_dullstone_slab",       () -> new SlabBlock(getProperties(SMOOTH_DULLSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_DULLSTONE_STAIRS = BLOCKS.register("smooth_dullstone_stairs",   () -> new StairsBlock(() -> SMOOTH_DULLSTONE.get().getDefaultState(), getProperties(SMOOTH_DULLSTONE.get())));
    public static final RegistryObject<Block> GLOWSTONE_BRICK_SLAB = BLOCKS.register("glowstone_brick_slab",         () -> new SlabBlock(getProperties(GLOWSTONE_BRICK.get())));
    public static final RegistryObject<Block> GLOWSTONE_BRICK_STAIRS = BLOCKS.register("glowstone_brick_stairs",     () -> new StairsBlock(() -> GLOWSTONE_BRICK.get().getDefaultState(), getProperties(GLOWSTONE_BRICK.get())));
    public static final RegistryObject<Block> DIMSTONE_BRICK_SLAB = BLOCKS.register("dimstone_brick_slab",           () -> new SlabBlock(getProperties(DIMSTONE_BRICK.get())));
    public static final RegistryObject<Block> DIMSTONE_BRICK_STAIRS = BLOCKS.register("dimstone_brick_stairs",       () -> new StairsBlock(() -> DIMSTONE_BRICK.get().getDefaultState(), getProperties(DIMSTONE_BRICK.get())));
    public static final RegistryObject<Block> DULLSTONE_BRICK_SLAB = BLOCKS.register("dullstone_brick_slab",         () -> new SlabBlock(getProperties(DULLSTONE_BRICK.get())));
    public static final RegistryObject<Block> DULLSTONE_BRICK_STAIRS = BLOCKS.register("dullstone_brick_stairs",     () -> new StairsBlock(() -> DULLSTONE_BRICK.get().getDefaultState(), getProperties(DULLSTONE_BRICK.get())));

    public static final RegistryObject<Block> GLOWDUST = BLOCKS.register("glowdust", () -> new GlowdustBlock(getProperties(Blocks.SAND).setLightLevel(value -> 8).setRequiresTool().harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.2f)));
    public static final RegistryObject<Block> GLOWDUST_SAND = BLOCKS.register("glowdust_sand",          () -> new GlowSandBlock(0xFFC267, getProperties(GLOWDUST.get()).hardnessAndResistance(0.5F)));
    public static final RegistryObject<Block> LUMINOUS_WART_BLOCK = BLOCKS.register("luminous_wart_block",          () -> new Block(getProperties(Blocks.NETHER_WART_BLOCK)));
    public static final RegistryObject<Block> GLOWDUST_STONE = BLOCKS.register("glowdust_stone",                () -> new GlowBlockBase(getProperties(Blocks.SANDSTONE).setLightLevel(value -> 8)));
    public static final RegistryObject<Block> GLOWDUST_STONE_BRICKS = BLOCKS.register("glowdust_stone_bricks",  () -> new GlowBlockBase(getProperties(Blocks.SANDSTONE).setLightLevel(value -> 8)));
    public static final RegistryObject<Block> GLOWDUST_STONE_BRICK_SLAB = BLOCKS.register("glowdust_stone_brick_slab",      () -> new GlowBlockBase(getProperties(Blocks.SANDSTONE).setLightLevel(value -> 8)));
    public static final RegistryObject<Block> GLOWDUST_STONE_BRICK_STAIRS = BLOCKS.register("glowdust_stone_brick_stairs",  () -> new GlowBlockBase(getProperties(Blocks.SANDSTONE).setLightLevel(value -> 8)));
    public static final RegistryObject<Block> TRAPPED_GLOWDUST_SAND = BLOCKS.register("trapped_glowdust_sand",  () -> new TrappedGlowSandBlock(0xFFC267, getProperties(GLOWDUST.get()).hardnessAndResistance(0.2F).setLightLevel(value -> 4)));
    public static final RegistryObject<Block> GLOWDUST_SANDSTONE = BLOCKS.register("glowdust_sandstone",                    () -> new GlowBlockBase(getProperties(Blocks.SANDSTONE).setLightLevel(value -> 8)));
    public static final RegistryObject<Block> CUT_GLOWDUST_SANDSTONE = BLOCKS.register("cut_glowdust_sandstone",            () -> new Block(getProperties(GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> CHISELED_GLOWDUST_SANDSTONE = BLOCKS.register("chiseled_glowdust_sandstone",  () -> new Block(getProperties(GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_GLOWDUST_SANDSTONE = BLOCKS.register("smooth_glowdust_sandstone",      () -> new Block(getProperties(GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> GLOWDUST_SANDSTONE_SLAB = BLOCKS.register("glowdust_sandstone_slab",          () -> new SlabBlock(getProperties(GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> GLOWDUST_SANDSTONE_STAIRS = BLOCKS.register("glowdust_sandstone_stairs",      () -> new StairsBlock(() -> GLOWDUST_SANDSTONE.get().getDefaultState(), getProperties(GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> CUT_GLOWDUST_SANDSTONE_SLAB = BLOCKS.register("cut_glowdust_sandstone_slab",              () -> new SlabBlock(getProperties(CUT_GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_GLOWDUST_SANDSTONE_SLAB = BLOCKS.register("smooth_glowdust_sandstone_slab",        () -> new SlabBlock(getProperties(SMOOTH_GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_GLOWDUST_SANDSTONE_STAIRS = BLOCKS.register("smooth_glowdust_sandstone_stairs",    () -> new StairsBlock(() -> SMOOTH_GLOWDUST_SANDSTONE.get().getDefaultState(), getProperties(SMOOTH_GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> GLOWDUST_SANDSTONE_WALL = BLOCKS.register("glowdust_sandstone_wall",          () -> new WallBlock(getProperties(GLOWDUST_SANDSTONE.get())));

    public static final RegistryObject<Block> CRUMBLING_BLACKSTONE = BLOCKS.register("crumbling_blackstone",    () -> new CrumblingBlackstoneBlock(AbstractBlock.Properties.from(Blocks.NETHERRACK)));
    public static final RegistryObject<Block> GLIMMERING_BLACKSTONE = BLOCKS.register("glimmering_blackstone",  () -> new Block(getProperties(Blocks.BLACKSTONE).setLightLevel(value -> 6)));
    public static final RegistryObject<Block> SILT = BLOCKS.register("silt",        () -> new Block(getProperties(Blocks.SAND)));
    public static final RegistryObject<Block> RUBBLE = BLOCKS.register("rubble",    () -> new Block(getProperties(Blocks.GRAVEL)));

    public static final RegistryObject<Block> BASALT_IRON_ORE = BLOCKS.register("basalt_iron_ore",        () -> new Block(AbstractBlock.Properties.from(Blocks.NETHER_GOLD_ORE)));

    public static final RegistryObject<Block> CRIMSON_FUNGUS_CAP = BLOCKS.register("crimson_fungus_cap",  () -> new Block(AbstractBlock.Properties.from(Blocks.NETHER_WART_BLOCK)));
    public static final RegistryObject<Block> WARPED_FUNGUS_CAP = BLOCKS.register("warped_fungus_cap",    () -> new Block(AbstractBlock.Properties.from(Blocks.WARPED_WART_BLOCK)));
    public static final RegistryObject<Block> LUMINOUS_FUNGUS_CAP = BLOCKS.register("luminous_fungus_cap",  () -> new Block(AbstractBlock.Properties.from(LUMINOUS_WART_BLOCK.get())));

    public static final RegistryObject<Block> GLOW_LANTERN = BLOCKS.register("lantern_glow",        () -> new LanternBlock(getProperties(Blocks.LANTERN)));
    public static final RegistryObject<Block> GLOW_TORCH = BLOCKS.register("torch_glow",            () -> new TorchBlock(getProperties(Blocks.TORCH), ParticleTypes.CRIT));
    public static final RegistryObject<Block> GLOW_WALL_TORCH = BLOCKS.register("torch_glow_wall",  () -> new WallTorchBlock(getProperties(ModBlocks.GLOW_TORCH.get()).lootFrom(GLOW_TORCH.get()), ParticleTypes.CRIT));
    public static final RegistryObject<Block> GLOW_CAMPFIRE = BLOCKS.register("campfire_glow",      () -> new GlowCampfireBlock(true, 2, getProperties(Blocks.CAMPFIRE)));
    public static final RegistryObject<Block> GLOW_FIRE = BLOCKS.register("fire_glow",              () -> new GlowFireBlock(getProperties(Blocks.FIRE)));

    // Foliage
    public static final RegistryObject<Block> LUMINOUS_FUNGUS = BLOCKS.register("luminous_fungus",  () -> new LuminousFungusBlock(getProperties(Material.PLANTS).setLightLevel(value -> 13).doesNotBlockMovement().sound(SoundType.PLANT)));
    public static final RegistryObject<Block> DULLTHORNS = BLOCKS.register("dullthorns",            () -> new DullthornsBlock(AbstractBlock.Properties.create(Material.CACTUS).setLightLevel(value -> 3).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.1F).sound(SoundType.PLANT)));

    public static final RegistryObject<Block> DULLTHORNS_BLOCK = BLOCKS.register("dullthorns_block",        () -> new DullthornsBlockBlock(AbstractBlock.Properties.create(Material.CACTUS).hardnessAndResistance(0.2F).sound(SoundType.WART)));
    
    public static final RegistryObject<FlowerPotBlock> POTTED_LUMINOUS_FUNGUS = BLOCKS.register("potted_luminous_fungus",   () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, LUMINOUS_FUNGUS, getProperties(Blocks.FLOWER_POT)));
    public static final RegistryObject<FlowerPotBlock> POTTED_DULLTHORNS = BLOCKS.register("potted_dullthorns",             () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, DULLTHORNS, getProperties(Blocks.FLOWER_POT)));

    public static final RegistryObject<Block> SHROOMLIGHT_FUNGUS = BLOCKS.register("shroomlight_fungus",                        () -> new ShroomlightFungusBlock(getProperties(Material.PLANTS).setLightLevel(value -> 13).doesNotBlockMovement().sound(SoundType.PLANT)));
    public static final RegistryObject<FlowerPotBlock> POTTED_SHROOMLIGHT_FUNGUS = BLOCKS.register("potted_shroomlight_fungus", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SHROOMLIGHT_FUNGUS, getProperties(Blocks.FLOWER_POT)));
    
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

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Blocks Registered!");
    }
}
