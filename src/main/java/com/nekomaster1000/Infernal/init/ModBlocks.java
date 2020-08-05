package com.nekomaster1000.Infernal.init;

import com.nekomaster1000.Infernal.InfernalExpansion;
import com.nekomaster1000.Infernal.blocks.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, InfernalExpansion.MOD_ID);

    // Blocks
    public static final RegistryObject<Block> DIMSTONE = BLOCKS.register("dimstone", () -> new Block(getBasicProperties(Material.GLASS, 4.0F, 5.0F).sound(SoundType.GLASS).setRequiresTool().setLightLevel(value -> 10)));
    public static final RegistryObject<Block> DULLSTONE = BLOCKS.register("dullstone", () -> new Block(getBasicProperties(Material.GLASS, 4.0F, 5.0F).sound(SoundType.GLASS).setRequiresTool()));
    public static final RegistryObject<Block> SMOOTH_GLOWSTONE = BLOCKS.register("smooth_glowstone", () -> new Block(getBasicProperties(Material.GLASS, 4.0F, 5.0F).sound(SoundType.GLASS).setRequiresTool().setLightLevel(value -> 15)));
    public static final RegistryObject<Block> SMOOTH_DIMSTONE = BLOCKS.register("smooth_dimstone", () -> new Block(getBasicProperties(Material.GLASS, 4.0F, 5.0F).sound(SoundType.GLASS).setRequiresTool().setLightLevel(value -> 10)));
    public static final RegistryObject<Block> SMOOTH_DULLSTONE = BLOCKS.register("smooth_dullstone", () -> new Block(getBasicProperties(Material.GLASS, 4.0F, 5.0F).sound(SoundType.GLASS).setRequiresTool()));
    public static final RegistryObject<Block> GLOWSTONE_BRICK = BLOCKS.register("glowstone_brick", () -> new Block(getBasicProperties(Material.GLASS, 4.0F, 5.0F).sound(SoundType.GLASS).setRequiresTool().setLightLevel(value -> 15)));
    public static final RegistryObject<Block> DIMSTONE_BRICK = BLOCKS.register("dimstone_brick", () -> new Block(getBasicProperties(Material.GLASS, 4.0F, 5.0F).sound(SoundType.GLASS).setRequiresTool().setLightLevel(value -> 10)));
    public static final RegistryObject<Block> DULLSTONE_BRICK = BLOCKS.register("dullstone_brick", () -> new Block(getBasicProperties(Material.GLASS, 4.0F, 5.0F).sound(SoundType.GLASS).setRequiresTool()));
    public static final RegistryObject<Block> CHISELED_GLOWSTONE_BRICK = BLOCKS.register("chiseled_glowstone_brick", () -> new Block(getBasicProperties(Material.GLASS, 4.0F, 5.0F).sound(SoundType.GLASS).setRequiresTool().setLightLevel(value -> 15)));
    public static final RegistryObject<Block> CHISELED_DIMSTONE_BRICK = BLOCKS.register("chiseled_dimstone_brick", () -> new Block(getBasicProperties(Material.GLASS, 4.0F, 5.0F).sound(SoundType.GLASS).setRequiresTool().setLightLevel(value -> 10)));
    public static final RegistryObject<Block> CHISELED_DULLSTONE_BRICK = BLOCKS.register("chiseled_dullstone_brick", () -> new Block(getBasicProperties(Material.GLASS, 4.0F, 5.0F).sound(SoundType.GLASS).setRequiresTool()));
    public static final RegistryObject<Block> GLOWDUST_SAND = BLOCKS.register("glowdust_sand", () -> new SandBlock(0xFFC267, getBasicProperties(Material.SAND, 0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<Block> GLOWDUST_SANDSTONE = BLOCKS.register("glowdust_sandstone", () -> new Block(getBasicProperties(Material.ROCK, 0.8F).setRequiresTool()));
    public static final RegistryObject<Block> CUT_GLOWDUST_SANDSTONE = BLOCKS.register("cut_glowdust_sandstone", () -> new Block(getBasicProperties(Material.ROCK, 0.8F).setRequiresTool()));
    public static final RegistryObject<Block> CHISELED_GLOWDUST_SANDSTONE = BLOCKS.register("chiseled_glowdust_sandstone", () -> new Block(getBasicProperties(Material.ROCK, 0.8F).setRequiresTool()));
    public static final RegistryObject<Block> SMOOTH_GLOWDUST_SANDSTONE = BLOCKS.register("smooth_glowdust_sandstone", () -> new Block(getBasicProperties(Material.ROCK, 2.0F, 6.0F).setRequiresTool()));
    public static final RegistryObject<Block> GLOWDUST = BLOCKS.register("glowdust", () ->  new SnowBlock(getBasicProperties(Material.SAND, 0.1F).tickRandomly().setRequiresTool().sound(SoundType.SAND)));
    public static final RegistryObject<Block> GLOWDUST_SANDSTONE_SLAB = BLOCKS.register("glowdust_sandstone_slab", () -> new SlabBlock(getBasicProperties(Material.ROCK, 2.0F, 6.0F).setRequiresTool()));
    public static final RegistryObject<Block> GLOWDUST_SANDSTONE_STAIRS = BLOCKS.register("glowdust_sandstone_stairs", () -> new StairsBlock(() -> GLOWDUST_SANDSTONE.get().getDefaultState(),getBasicProperties(Material.ROCK, 2.0F, 6.0F).setRequiresTool()));

    public static AbstractBlock.Properties getBasicProperties(Material materialIn, float hardnessAndResistanceIn) {
        return getBasicProperties(materialIn, hardnessAndResistanceIn, hardnessAndResistanceIn);
    }

    public static AbstractBlock.Properties getBasicProperties(Material materialIn, float hardnessIn, float resistanceIn) {
        return AbstractBlock.Properties.create(materialIn).hardnessAndResistance(hardnessIn, resistanceIn);
    }
}
