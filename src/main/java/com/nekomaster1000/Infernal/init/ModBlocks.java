package com.nekomaster1000.Infernal.init;

import com.nekomaster1000.Infernal.InfernalExpansion;
import com.nekomaster1000.Infernal.blocks.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SandBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, InfernalExpansion.MOD_ID);

    // Blocks
    public static final RegistryObject<Block> DIMSTONE = BLOCKS.register("dimstone", Dimstone::new);
    public static final RegistryObject<Block> DULLSTONE = BLOCKS.register("dullstone", Dullstone::new);
    public static final RegistryObject<Block> GLOWSTONE_SMOOTH = BLOCKS.register("glowstone_smooth", GlowstoneSmooth::new);
    public static final RegistryObject<Block> DIMSTONE_SMOOTH = BLOCKS.register("dimstone_smooth", DimstoneSmooth::new);
    public static final RegistryObject<Block> DULLSTONE_SMOOTH = BLOCKS.register("dullstone_smooth", DullstoneSmooth::new);
    public static final RegistryObject<Block> GLOWSTONE_BRICK = BLOCKS.register("glowstone_brick", GlowstoneBrick::new);
    public static final RegistryObject<Block> DIMSTONE_BRICK = BLOCKS.register("dimstone_brick", DimstoneBrick::new);
    public static final RegistryObject<Block> DULLSTONE_BRICK = BLOCKS.register("dullstone_brick", DullstoneBrick::new);
    public static final RegistryObject<Block> GLOWSQUITO_GLOWSTONE_BRICK = BLOCKS.register("glowsquito_glowstone_brick", GlowsquitoGlowstoneBrick::new);
    public static final RegistryObject<Block> CHISELED_GLOWSTONE_BRICK = BLOCKS.register("chiseled_glowstone_brick", ChiselledGlowstoneBrick::new);
    public static final RegistryObject<Block> GLOWSQUITO_DIMSTONE_BRICK = BLOCKS.register("glowsquito_dimstone_brick", GlowsquitoDimstoneBrick::new);
    public static final RegistryObject<Block> CHISELED_DIMSTONE_BRICK = BLOCKS.register("chiseled_dimstone_brick", ChiselledDimstoneBrick::new);
    public static final RegistryObject<Block> GLOWSQUITO_DULLSTONE_BRICK = BLOCKS.register("glowsquito_dullstone_brick", GlowsquitoDullstoneBrick::new);
    public static final RegistryObject<Block> CHISELED_DULLSTONE_BRICK = BLOCKS.register("chiseled_dullstone_brick", ChiselledDullstoneBrick::new);
    public static final RegistryObject<Block> GLOWDUST_SAND = BLOCKS.register("glowdust_sand", () -> new SandBlock(0xFFC267, AbstractBlock.Properties.create(Material.SAND).hardnessAndResistance(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<Block> GLOWDUST_SANDSTONE = BLOCKS.register("glowdust_sandstone", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(0.8F)));
    public static final RegistryObject<Block> CUT_GLOWDUST_SANDSTONE = BLOCKS.register("cut_glowdust_sandstone", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(0.8F)));
    public static final RegistryObject<Block> CHISELED_GLOWDUST_SANDSTONE = BLOCKS.register("chiseled_glowdust_sandstone", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(0.8F)));
    public static final RegistryObject<Block> SMOOTH_GLOWDUST_SANDSTONE = BLOCKS.register("smooth_glowdust_sandstone", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(2.0F, 6.0F)));

}
