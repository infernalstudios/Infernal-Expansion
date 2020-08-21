package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, InfernalExpansion.MOD_ID);

    // Blocks
    public static final RegistryObject<Block> DIMSTONE = BLOCKS.register("dimstone", () -> new Block(getProperties(Blocks.GLOWSTONE).setLightLevel(value -> 12)));
    public static final RegistryObject<Block> DULLSTONE = BLOCKS.register("dullstone", () -> new Block(getProperties(Blocks.GLOWSTONE).setLightLevel(value -> 0)));
    public static final RegistryObject<Block> SMOOTH_GLOWSTONE = BLOCKS.register("smooth_glowstone", () -> new Block(getProperties(Material.GLASS, 1.5F, 6.0F).sound(SoundType.GLASS).setRequiresTool().setLightLevel(value -> 15)));
    public static final RegistryObject<Block> SMOOTH_DIMSTONE = BLOCKS.register("smooth_dimstone", () -> new Block(getProperties(SMOOTH_GLOWSTONE.get()).setLightLevel(value -> 12)));
    public static final RegistryObject<Block> SMOOTH_DULLSTONE = BLOCKS.register("smooth_dullstone", () -> new Block(getProperties(SMOOTH_GLOWSTONE.get()).setLightLevel(value -> 0)));
    public static final RegistryObject<Block> GLOWSTONE_BRICK = BLOCKS.register("glowstone_brick", () -> new Block(getProperties(SMOOTH_GLOWSTONE.get())));
    public static final RegistryObject<Block> DIMSTONE_BRICK = BLOCKS.register("dimstone_brick", () -> new Block(getProperties(SMOOTH_DIMSTONE.get())));
    public static final RegistryObject<Block> DULLSTONE_BRICK = BLOCKS.register("dullstone_brick", () -> new Block(getProperties(SMOOTH_DULLSTONE.get())));
    public static final RegistryObject<Block> CHISELED_GLOWSTONE_BRICK = BLOCKS.register("chiseled_glowstone_brick", () -> new Block(getProperties(SMOOTH_GLOWSTONE.get())));
    public static final RegistryObject<Block> CHISELED_DIMSTONE_BRICK = BLOCKS.register("chiseled_dimstone_brick", () -> new Block(getProperties(SMOOTH_DIMSTONE.get())));
    public static final RegistryObject<Block> CHISELED_DULLSTONE_BRICK = BLOCKS.register("chiseled_dullstone_brick", () -> new Block(getProperties(SMOOTH_DULLSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_GLOWSTONE_SLAB = BLOCKS.register("smooth_glowstone_slab", () -> new SlabBlock(getProperties(SMOOTH_GLOWSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_GLOWSTONE_STAIRS = BLOCKS.register("smooth_glowstone_stairs", () -> new StairsBlock(() -> SMOOTH_GLOWSTONE.get().getDefaultState(), getProperties(SMOOTH_GLOWSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_DIMSTONE_SLAB = BLOCKS.register("smooth_dimstone_slab", () -> new SlabBlock(getProperties(SMOOTH_DIMSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_DIMSTONE_STAIRS = BLOCKS.register("smooth_dimstone_stairs", () -> new StairsBlock(() -> SMOOTH_DIMSTONE.get().getDefaultState(), getProperties(SMOOTH_DIMSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_DULLSTONE_SLAB = BLOCKS.register("smooth_dullstone_slab", () -> new SlabBlock(getProperties(SMOOTH_DULLSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_DULLSTONE_STAIRS = BLOCKS.register("smooth_dullstone_stairs", () -> new StairsBlock(() -> SMOOTH_DULLSTONE.get().getDefaultState(), getProperties(SMOOTH_DULLSTONE.get())));
    public static final RegistryObject<Block> GLOWSTONE_BRICK_SLAB = BLOCKS.register("glowstone_brick_slab", () -> new SlabBlock(getProperties(GLOWSTONE_BRICK.get())));
    public static final RegistryObject<Block> GLOWSTONE_BRICK_STAIRS = BLOCKS.register("glowstone_brick_stairs", () -> new StairsBlock(() -> GLOWSTONE_BRICK.get().getDefaultState(), getProperties(GLOWSTONE_BRICK.get())));
    public static final RegistryObject<Block> DIMSTONE_BRICK_SLAB = BLOCKS.register("dimstone_brick_slab", () -> new SlabBlock(getProperties(DIMSTONE_BRICK.get())));
    public static final RegistryObject<Block> DIMSTONE_BRICK_STAIRS = BLOCKS.register("dimstone_brick_stairs", () -> new StairsBlock(() -> DIMSTONE_BRICK.get().getDefaultState(), getProperties(DIMSTONE_BRICK.get())));
    public static final RegistryObject<Block> DULLSTONE_BRICK_SLAB = BLOCKS.register("dullstone_brick_slab", () -> new SlabBlock(getProperties(DULLSTONE_BRICK.get())));
    public static final RegistryObject<Block> DULLSTONE_BRICK_STAIRS = BLOCKS.register("dullstone_brick_stairs", () -> new StairsBlock(() -> DULLSTONE_BRICK.get().getDefaultState(), getProperties(DULLSTONE_BRICK.get())));

    public static final RegistryObject<Block> GLOWDUST = BLOCKS.register("glowdust", () -> new SnowBlock(getProperties(Blocks.SAND).setLightLevel(value -> 12)));
    public static final RegistryObject<Block> GLOWDUST_SAND = BLOCKS.register("glowdust_sand", () -> new SandBlock(0xFFC267, getProperties(GLOWDUST.get())));
    public static final RegistryObject<Block> GLOWDUST_SANDSTONE = BLOCKS.register("glowdust_sandstone", () -> new Block(getProperties(Blocks.SANDSTONE).setLightLevel(value -> 12)));
    public static final RegistryObject<Block> CUT_GLOWDUST_SANDSTONE = BLOCKS.register("cut_glowdust_sandstone", () -> new Block(getProperties(GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> CHISELED_GLOWDUST_SANDSTONE = BLOCKS.register("chiseled_glowdust_sandstone", () -> new Block(getProperties(GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_GLOWDUST_SANDSTONE = BLOCKS.register("smooth_glowdust_sandstone", () -> new Block(getProperties(GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> GLOWDUST_SANDSTONE_SLAB = BLOCKS.register("glowdust_sandstone_slab", () -> new SlabBlock(getProperties(GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> GLOWDUST_SANDSTONE_STAIRS = BLOCKS.register("glowdust_sandstone_stairs", () -> new StairsBlock(() -> GLOWDUST_SANDSTONE.get().getDefaultState(), getProperties(GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> CUT_GLOWDUST_SANDSTONE_SLAB = BLOCKS.register("cut_glowdust_sandstone_slab", () -> new SlabBlock(getProperties(CUT_GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_GLOWDUST_SANDSTONE_SLAB = BLOCKS.register("smooth_glowdust_sandstone_slab", () -> new SlabBlock(getProperties(SMOOTH_GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_GLOWDUST_SANDSTONE_STAIRS = BLOCKS.register("smooth_glowdust_sandstone_stairs", () -> new StairsBlock(() -> SMOOTH_GLOWDUST_SANDSTONE.get().getDefaultState(), getProperties(SMOOTH_GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> GLOWDUST_SANDSTONE_WALL = BLOCKS.register("glowdust_sandstone_wall", () -> new WallBlock(getProperties(GLOWDUST_SANDSTONE.get())));

    public static final RegistryObject<Block> GLIMMERING_BLACKSTONE = BLOCKS.register("glimmering_blackstone", () -> new Block(getProperties(Blocks.BLACKSTONE).setLightLevel(value -> 12)));

    public static AbstractBlock.Properties getProperties(Material materialIn, float hardnessAndResistanceIn) {
        return getProperties(materialIn, hardnessAndResistanceIn, hardnessAndResistanceIn);
    }

    public static AbstractBlock.Properties getProperties(Material materialIn, float hardnessIn, float resistanceIn) {
        return AbstractBlock.Properties.create(materialIn).hardnessAndResistance(hardnessIn, resistanceIn);
    }

    public static AbstractBlock.Properties getProperties(Block block) {
        return AbstractBlock.Properties.from(block);
    }
}
