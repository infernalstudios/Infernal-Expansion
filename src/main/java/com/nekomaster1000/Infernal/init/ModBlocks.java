package com.nekomaster1000.Infernal.init;

import com.nekomaster1000.Infernal.InfernalExpansion;
import com.nekomaster1000.Infernal.blocks.*;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, InfernalExpansion.MOD_ID);

    //blocks
    public static final RegistryObject<Block> DIMSTONE = BLOCKS.register("dimstone", Dimstone::new);
    public static final RegistryObject<Block> DULLSTONE = BLOCKS.register("dullstone", Dullstone::new);
    public static final RegistryObject<Block> glowstone_smooth = BLOCKS.register("glowstone_smooth", GlowstoneSmooth::new);
    public static final RegistryObject<Block> dimstone_smooth = BLOCKS.register("dimstone_smooth", DimstoneSmooth::new);
    public static final RegistryObject<Block> dullstone_smooth = BLOCKS.register("dullstone_smooth", DullstoneSmooth::new);
    public static final RegistryObject<Block> glowstone_brick = BLOCKS.register("glowstone_brick", GlowstoneBrick::new);
    public static final RegistryObject<Block> dimstone_brick = BLOCKS.register("dimstone_brick", DimstoneBrick::new);
    public static final RegistryObject<Block> dullstone_brick = BLOCKS.register("dullstone_brick", DullstoneBrick::new);
    public static final RegistryObject<Block> chiselled_glowstone_brick1 = BLOCKS.register("chiselled_glowstone_brick1", chiselledGlowstoneBrick1::new);
    public static final RegistryObject<Block> chiselled_glowstone_brick2 = BLOCKS.register("chiselled_glowstone_brick2", chiselledGlowstoneBrick2::new);
    public static final RegistryObject<Block> chiselled_dimstone_brick1 = BLOCKS.register("chiselled_dimstone_brick1", chiselledDimstoneBrick1::new);
    public static final RegistryObject<Block> chiselled_dimstone_brick2 = BLOCKS.register("chiselled_dimstone_brick2", chiselledDimstoneBrick2::new);
    public static final RegistryObject<Block> chiselled_dullstone_brick1 = BLOCKS.register("chiselled_dullstone_brick1", chiselledDullstoneBrick1::new);
    public static final RegistryObject<Block> chiselled_dullstone_brick2 = BLOCKS.register("chiselled_dullstone_brick2", chiselledDullstoneBrick2::new);

}
