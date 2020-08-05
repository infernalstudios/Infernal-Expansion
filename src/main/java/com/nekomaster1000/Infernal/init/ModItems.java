package com.nekomaster1000.Infernal.init;

import com.nekomaster1000.Infernal.InfernalExpansion;
import com.nekomaster1000.Infernal.blocks.BlockItemBase;
import com.nekomaster1000.Infernal.items.ItemBase;
import com.nekomaster1000.Infernal.util.ModItemTier;
import com.nekomaster1000.Infernal.util.ModSpawnEggItem;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, InfernalExpansion.MOD_ID);

    // Items
    public static final RegistryObject<Item> GLOWCOAL = ITEMS.register("glowcoal", ItemBase::new);
    public static final RegistryObject<Item> DIMROCKS = ITEMS.register("glownuggets", ItemBase::new);

    // Spawn Eggs
    public static final RegistryObject<ModSpawnEggItem> GLOWSQUITO_SPAWN_EGG = ITEMS.register("glowsquito_spawn_egg", () -> new ModSpawnEggItem(ModEntityType.GLOWSQUITO, 0xF0A5A2, 0xA9672B, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<ModSpawnEggItem> VOLINE_SPAWN_EGG = ITEMS.register("voline_spawn_egg", () -> new ModSpawnEggItem(ModEntityType.VOLINE, 0xF0A5A2, 0xA9672B, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<ModSpawnEggItem> PYRNO_SPAWN_EGG = ITEMS.register("pyrno_spawn_egg", () -> new ModSpawnEggItem(ModEntityType.PYRNO, 0xF0A5A2, 0xA9672B, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<ModSpawnEggItem> WARPBEETLE_SPAWN_EGG = ITEMS.register("warpbeetle_spawn_egg", () -> new ModSpawnEggItem(ModEntityType.WARPBEETLE, 0xF0A5A2, 0xA9672B, new Item.Properties().group(InfernalExpansion.TAB)));

    // Block items
    public static final RegistryObject<Item> DIMSTONE = ITEMS.register("dimstone", () -> new BlockItemBase(ModBlocks.DIMSTONE.get()));
    public static final RegistryObject<Item> DULLSTONE = ITEMS.register("dullstone", () -> new BlockItemBase(ModBlocks.DULLSTONE.get()));
    public static final RegistryObject<Item> SMOOTH_GLOWSTONE = ITEMS.register("smooth_glowstone", () -> new BlockItemBase(ModBlocks.SMOOTH_GLOWSTONE.get()));
    public static final RegistryObject<Item> SMOOTH_DIMSTONE = ITEMS.register("smooth_dimstone", () -> new BlockItemBase(ModBlocks.SMOOTH_DIMSTONE.get()));
    public static final RegistryObject<Item> SMOOTH_DULLSTONE_ = ITEMS.register("smooth_dullstone", () -> new BlockItemBase(ModBlocks.SMOOTH_DULLSTONE.get()));
    public static final RegistryObject<Item> GLOWSTONE_BRICK = ITEMS.register("glowstone_brick", () -> new BlockItemBase(ModBlocks.GLOWSTONE_BRICK.get()));
    public static final RegistryObject<Item> DIMSTONE_BRICK = ITEMS.register("dimstone_brick", () -> new BlockItemBase(ModBlocks.DIMSTONE_BRICK.get()));
    public static final RegistryObject<Item> DULLSTONE_BRICK = ITEMS.register("dullstone_brick", () -> new BlockItemBase(ModBlocks.DULLSTONE_BRICK.get()));
    public static final RegistryObject<Item> CHISELED_GLOWSTONE_BRICK = ITEMS.register("chiseled_glowstone_brick", () -> new BlockItemBase(ModBlocks.CHISELED_GLOWSTONE_BRICK.get()));
    public static final RegistryObject<Item> CHISELED_DIMSTONE_BRICK = ITEMS.register("chiseled_dimstone_brick", () -> new BlockItemBase(ModBlocks.CHISELED_DIMSTONE_BRICK.get()));
    public static final RegistryObject<Item> CHISELED_DULLSTONE_BRICK = ITEMS.register("chiseled_dullstone_brick", () -> new BlockItemBase(ModBlocks.CHISELED_DULLSTONE_BRICK.get()));

    public static final RegistryObject<Item> GLOWDUST = ITEMS.register("glowdust", () -> new BlockItemBase(ModBlocks.GLOWDUST.get()));
    public static final RegistryObject<Item> GLOWDUST_SAND = ITEMS.register("glowdust_sand", () -> new BlockItemBase(ModBlocks.GLOWDUST_SAND.get()));
    public static final RegistryObject<Item> GLOWDUST_SANDSTONE = ITEMS.register("glowdust_sandstone", () -> new BlockItemBase(ModBlocks.GLOWDUST_SANDSTONE.get()));
    public static final RegistryObject<Item> CUT_GLOWDUST_SANDSTONE = ITEMS.register("cut_glowdust_sandstone", () -> new BlockItemBase(ModBlocks.CUT_GLOWDUST_SANDSTONE.get()));
    public static final RegistryObject<Item> CHISELED_GLOWDUST_SANDSTONE = ITEMS.register("chiseled_glowdust_sandstone", () -> new BlockItemBase(ModBlocks.CHISELED_GLOWDUST_SANDSTONE.get()));
    public static final RegistryObject<Item> SMOOTH_GLOWDUST_SANDSTONE = ITEMS.register("smooth_glowdust_sandstone", () -> new BlockItemBase(ModBlocks.SMOOTH_GLOWDUST_SANDSTONE.get()));
    public static final RegistryObject<Item> GLOWDUST_SANDSTONE_SLAB = ITEMS.register("glowdust_sandstone_slab", () -> new BlockItemBase(ModBlocks.GLOWDUST_SANDSTONE_SLAB.get()));
    public static final RegistryObject<Item> GLOWDUST_SANDSTONE_STAIRS = ITEMS.register("glowdust_sandstone_stairs", () -> new BlockItemBase(ModBlocks.GLOWDUST_SANDSTONE_STAIRS.get()));

    public static final RegistryObject<Item> GLIMMERING_BLACKSTONE = ITEMS.register("glimmering_blackstone", () -> new BlockItemBase(ModBlocks.GLIMMERING_BLACKSTONE.get()));

    // Tools
    public static final RegistryObject<SwordItem> FROSTBITTEN_SWORD = ITEMS.register("frostbitten_sword", () -> new SwordItem(ModItemTier.FROSTBITTEN_NETHERITE, 2, -2.4F, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<PickaxeItem> FROSTBITTEN_PICKAXE = ITEMS.register("frostbitten_pickaxe", () -> new PickaxeItem(ModItemTier.FROSTBITTEN_NETHERITE, 1, -2.4F, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<AxeItem> FROSTBITTEN_AXE = ITEMS.register("frostbitten_axe", () -> new AxeItem(ModItemTier.FROSTBITTEN_NETHERITE, 4, -3.1F, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<ShovelItem> FROSTBITTEN_SHOVEL = ITEMS.register("frostbitten_shovel", () -> new ShovelItem(ModItemTier.FROSTBITTEN_NETHERITE, -1, -2.4F, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<HoeItem> FROSTBITTEN_HOE = ITEMS.register("frostbitten_hoe", () -> new HoeItem(ModItemTier.FROSTBITTEN_NETHERITE, -2, -1.0F, new Item.Properties().group(InfernalExpansion.TAB)));
}
