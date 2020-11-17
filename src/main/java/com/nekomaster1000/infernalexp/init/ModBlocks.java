package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.blocks.GlowCampfireBlock;
import com.nekomaster1000.infernalexp.blocks.GlowdustBlock;
import com.nekomaster1000.infernalexp.blocks.LuminousFungusBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, InfernalExpansion.MOD_ID);

    // Blocks
    public static final RegistryObject<Block> DIMSTONE = BLOCKS.register("dimstone",            () -> new Block(getProperties(Material.GLASS, 3.5F, 2.0F).sound(SoundType.GLASS).setRequiresTool().harvestTool(ToolType.PICKAXE).setLightLevel(value -> 12)));
    public static final RegistryObject<Block> DULLSTONE = BLOCKS.register("dullstone",          () -> new Block(getProperties(Material.GLASS, 10.0F, 6.0F).sound(SoundType.GLASS).setRequiresTool().harvestTool(ToolType.PICKAXE).setLightLevel(value -> 0)));
    public static final RegistryObject<Block> SMOOTH_GLOWSTONE = BLOCKS.register("smooth_glowstone", () -> new Block(getProperties(Material.GLASS, 1.5F, 6.0F).sound(SoundType.GLASS).setRequiresTool().harvestTool(ToolType.PICKAXE).setLightLevel(value -> 15)));
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

    public static final RegistryObject<Block> GLOWDUST = BLOCKS.register("glowdust",            () -> new GlowdustBlock(getProperties(Blocks.SAND).setLightLevel(value -> 8)));
    public static final RegistryObject<Block> GLOWDUST_SAND = BLOCKS.register("glowdust_sand", () -> new SandBlock(0xFFC267, getProperties(GLOWDUST.get())));
    public static final RegistryObject<Block> GLOWDUST_STONE = BLOCKS.register("glowdust_stone", () -> new SandBlock(0xFFC267, getProperties(GLOWDUST.get())));
    public static final RegistryObject<Block> GLOWDUST_SANDSTONE = BLOCKS.register("glowdust_sandstone",            () -> new Block(getProperties(Blocks.SANDSTONE).setLightLevel(value -> 8)));
    public static final RegistryObject<Block> CUT_GLOWDUST_SANDSTONE = BLOCKS.register("cut_glowdust_sandstone", () -> new Block(getProperties(GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> CHISELED_GLOWDUST_SANDSTONE = BLOCKS.register("chiseled_glowdust_sandstone", () -> new Block(getProperties(GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_GLOWDUST_SANDSTONE = BLOCKS.register("smooth_glowdust_sandstone", () -> new Block(getProperties(GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> GLOWDUST_SANDSTONE_SLAB = BLOCKS.register("glowdust_sandstone_slab", () -> new SlabBlock(getProperties(GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> GLOWDUST_SANDSTONE_STAIRS = BLOCKS.register("glowdust_sandstone_stairs", () -> new StairsBlock(() -> GLOWDUST_SANDSTONE.get().getDefaultState(), getProperties(GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> CUT_GLOWDUST_SANDSTONE_SLAB = BLOCKS.register("cut_glowdust_sandstone_slab", () -> new SlabBlock(getProperties(CUT_GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_GLOWDUST_SANDSTONE_SLAB = BLOCKS.register("smooth_glowdust_sandstone_slab", () -> new SlabBlock(getProperties(SMOOTH_GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_GLOWDUST_SANDSTONE_STAIRS = BLOCKS.register("smooth_glowdust_sandstone_stairs", () -> new StairsBlock(() -> SMOOTH_GLOWDUST_SANDSTONE.get().getDefaultState(), getProperties(SMOOTH_GLOWDUST_SANDSTONE.get())));
    public static final RegistryObject<Block> GLOWDUST_SANDSTONE_WALL = BLOCKS.register("glowdust_sandstone_wall", () -> new WallBlock(getProperties(GLOWDUST_SANDSTONE.get())));

    public static final RegistryObject<Block> GLIMMERING_BLACKSTONE = BLOCKS.register("glimmering_blackstone",            () -> new Block(getProperties(Blocks.BLACKSTONE).setLightLevel(value -> 6)));
    public static final RegistryObject<Block> SILT = BLOCKS.register("silt", () -> new Block(getProperties(Blocks.GRAVEL)));
    public static final RegistryObject<Block> RUBBLE = BLOCKS.register("rubble",  () -> new Block(getProperties(Blocks.GRAVEL)));


    public static final RegistryObject<Block> GLOW_LANTERN = BLOCKS.register("lantern_glow", () -> new LanternBlock(getProperties(Blocks.LANTERN)));
    public static final RegistryObject<Block> GLOW_TORCH = BLOCKS.register("torch_glow",  () -> new TorchBlock(getProperties(Blocks.TORCH), ParticleTypes.CRIT));
    public static final RegistryObject<Block> GLOW_WALL_TORCH = BLOCKS.register("torch_glow_wall", () -> new WallTorchBlock(getProperties(ModBlocks.GLOW_TORCH.get()).lootFrom(GLOW_TORCH.get()), ParticleTypes.CRIT));
    public static final RegistryObject<Block> GLOW_CAMPFIRE = BLOCKS.register("campfire_glow", () -> new GlowCampfireBlock(true, 2, getProperties(Blocks.CAMPFIRE)));

    // Foliage
    public static final RegistryObject<Block> LUMINOUS_FUNGUS = BLOCKS.register("luminous_fungus", () -> new LuminousFungusBlock(getProperties(Material.PLANTS).doesNotBlockMovement().sound(SoundType.PLANT)));

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
