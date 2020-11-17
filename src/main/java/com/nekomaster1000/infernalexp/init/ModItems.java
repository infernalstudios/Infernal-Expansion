package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.blocks.BlockItemBase;
import com.nekomaster1000.infernalexp.items.ItemBase;
import com.nekomaster1000.infernalexp.util.ModItemTier;
import com.nekomaster1000.infernalexp.util.ModSpawnEggItem;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, InfernalExpansion.MOD_ID);

    // Items
    public static final RegistryObject<Item> GLOWCOAL = ITEMS.register("glowcoal", ItemBase::new);
    public static final RegistryObject<Item> DULLROCKS = ITEMS.register("glownuggets", ItemBase::new);

    // Spawn Eggs
    public static final RegistryObject<ModSpawnEggItem> GLOWSQUITO_SPAWN_EGG = ITEMS.register("glowsquito_spawn_egg", () -> new ModSpawnEggItem(ModEntityType.GLOWSQUITO, 0xF0A5A2, 0xA9672B, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<ModSpawnEggItem> VOLINE_SPAWN_EGG = ITEMS.register("voline_spawn_egg", () -> new ModSpawnEggItem(ModEntityType.VOLINE, 0xF0A5A2, 0xA9672B, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<ModSpawnEggItem> PYRNO_SPAWN_EGG = ITEMS.register("pyrno_spawn_egg", () -> new ModSpawnEggItem(ModEntityType.PYRNO, 0xF0A5A2, 0xA9672B, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<ModSpawnEggItem> WARPBEETLE_SPAWN_EGG = ITEMS.register("warpbeetle_spawn_egg", () -> new ModSpawnEggItem(ModEntityType.WARPBEETLE, 0xF0A5A2, 0xA9672B, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<ModSpawnEggItem> CEROBEETLE_SPAWN_EGG = ITEMS.register("cerobeetle_spawn_egg", () -> new ModSpawnEggItem(ModEntityType.CEROBEETLE, 0xF0A5A2, 0xA9672B, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<ModSpawnEggItem> EMBODY_SPAWN_EGG = ITEMS.register("embody_spawn_egg", () -> new ModSpawnEggItem(ModEntityType.EMBODY, 0xF0A5A2, 0xA9672B, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<ModSpawnEggItem> BASALT_GIANT_SPAWN_EGG = ITEMS.register("basalt_giant_spawn_egg", () -> new ModSpawnEggItem(ModEntityType.BASALT_GIANT, 0xF0A5A2, 0xA9672B, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<ModSpawnEggItem> BASALT_TITAN_SPAWN_EGG = ITEMS.register("basalt_titan_spawn_egg", () -> new ModSpawnEggItem(ModEntityType.BASALT_TITAN, 0xF0A5A2, 0xA9672B, new Item.Properties().group(InfernalExpansion.TAB)));


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
    public static final RegistryObject<Item> SMOOTH_GLOWSTONE_SLAB = ITEMS.register("smooth_glowstone_slab", () -> new BlockItemBase(ModBlocks.SMOOTH_GLOWSTONE_SLAB.get()));
    public static final RegistryObject<Item> SMOOTH_GLOWSTONE_STAIRS = ITEMS.register("smooth_glowstone_stairs", () -> new BlockItemBase(ModBlocks.SMOOTH_GLOWSTONE_STAIRS.get()));
    public static final RegistryObject<Item> SMOOTH_DIMSTONE_SLAB = ITEMS.register("smooth_dimstone_slab", () -> new BlockItemBase(ModBlocks.SMOOTH_DIMSTONE_SLAB.get()));
    public static final RegistryObject<Item> SMOOTH_DIMSTONE_STAIRS = ITEMS.register("smooth_dimstone_stairs", () -> new BlockItemBase(ModBlocks.SMOOTH_DIMSTONE_STAIRS.get()));
    public static final RegistryObject<Item> SMOOTH_DULLSTONE_SLAB = ITEMS.register("smooth_dullstone_slab", () -> new BlockItemBase(ModBlocks.SMOOTH_DULLSTONE_SLAB.get()));
    public static final RegistryObject<Item> SMOOTH_DULLSTONE_STAIRS = ITEMS.register("smooth_dullstone_stairs", () -> new BlockItemBase(ModBlocks.SMOOTH_DULLSTONE_STAIRS.get()));
    public static final RegistryObject<Item> GLOWSTONE_BRICK_SLAB = ITEMS.register("glowstone_brick_slab", () -> new BlockItemBase(ModBlocks.GLOWSTONE_BRICK_SLAB.get()));
    public static final RegistryObject<Item> GLOWSTONE_BRICK_STAIRS = ITEMS.register("glowstone_brick_stairs", () -> new BlockItemBase(ModBlocks.GLOWSTONE_BRICK_STAIRS.get()));
    public static final RegistryObject<Item> DIMSTONE_BRICK_SLAB = ITEMS.register("dimstone_brick_slab", () -> new BlockItemBase(ModBlocks.DIMSTONE_BRICK_SLAB.get()));
    public static final RegistryObject<Item> DIMSTONE_BRICK_STAIRS = ITEMS.register("dimstone_brick_stairs", () -> new BlockItemBase(ModBlocks.DIMSTONE_BRICK_STAIRS.get()));
    public static final RegistryObject<Item> DULLSTONE_BRICK_SLAB = ITEMS.register("dullstone_brick_slab", () -> new BlockItemBase(ModBlocks.DULLSTONE_BRICK_SLAB.get()));
    public static final RegistryObject<Item> DULLSTONE_BRICK_STAIRS = ITEMS.register("dullstone_brick_stairs", () -> new BlockItemBase(ModBlocks.DULLSTONE_BRICK_STAIRS.get()));

    public static final RegistryObject<Item> GLOWDUST = ITEMS.register("glowdust", () -> new BlockItemBase(ModBlocks.GLOWDUST.get()));
    public static final RegistryObject<Item> GLOWDUST_SAND = ITEMS.register("glowdust_sand", () -> new BlockItemBase(ModBlocks.GLOWDUST_SAND.get()));
    public static final RegistryObject<Item> GLOWDUST_SANDSTONE = ITEMS.register("glowdust_sandstone", () -> new BlockItemBase(ModBlocks.GLOWDUST_SANDSTONE.get()));
    public static final RegistryObject<Item> GLOWDUST_STONE = ITEMS.register("glowdust_stone", () -> new BlockItemBase(ModBlocks.GLOWDUST_STONE.get()));
    public static final RegistryObject<Item> CUT_GLOWDUST_SANDSTONE = ITEMS.register("cut_glowdust_sandstone", () -> new BlockItemBase(ModBlocks.CUT_GLOWDUST_SANDSTONE.get()));
    public static final RegistryObject<Item> CHISELED_GLOWDUST_SANDSTONE = ITEMS.register("chiseled_glowdust_sandstone", () -> new BlockItemBase(ModBlocks.CHISELED_GLOWDUST_SANDSTONE.get()));
    public static final RegistryObject<Item> SMOOTH_GLOWDUST_SANDSTONE = ITEMS.register("smooth_glowdust_sandstone", () -> new BlockItemBase(ModBlocks.SMOOTH_GLOWDUST_SANDSTONE.get()));
    public static final RegistryObject<Item> GLOWDUST_SANDSTONE_SLAB = ITEMS.register("glowdust_sandstone_slab", () -> new BlockItemBase(ModBlocks.GLOWDUST_SANDSTONE_SLAB.get()));
    public static final RegistryObject<Item> GLOWDUST_SANDSTONE_STAIRS = ITEMS.register("glowdust_sandstone_stairs", () -> new BlockItemBase(ModBlocks.GLOWDUST_SANDSTONE_STAIRS.get()));
    public static final RegistryObject<Item> CUT_GLOWDUST_SANDSTONE_SLAB = ITEMS.register("cut_glowdust_sandstone_slab", () -> new BlockItemBase(ModBlocks.CUT_GLOWDUST_SANDSTONE_SLAB.get()));
    public static final RegistryObject<Item> SMOOTH_GLOWDUST_SANDSTONE_SLAB = ITEMS.register("smooth_glowdust_sandstone_slab", () -> new BlockItemBase(ModBlocks.SMOOTH_GLOWDUST_SANDSTONE_SLAB.get()));
    public static final RegistryObject<Item> SMOOTH_GLOWDUST_SANDSTONE_STAIRS = ITEMS.register("smooth_glowdust_sandstone_stairs", () -> new BlockItemBase(ModBlocks.SMOOTH_GLOWDUST_SANDSTONE_STAIRS.get()));
    public static final RegistryObject<Item> GLOWDUST_SANDSTONE_WALL = ITEMS.register("glowdust_sandstone_wall", () -> new BlockItemBase(ModBlocks.GLOWDUST_SANDSTONE_WALL.get()));

    public static final RegistryObject<Item> GLIMMERING_BLACKSTONE = ITEMS.register("glimmering_blackstone", () -> new BlockItemBase(ModBlocks.GLIMMERING_BLACKSTONE.get()));
    public static final RegistryObject<Item> SILT = ITEMS.register("silt", () -> new BlockItemBase(ModBlocks.SILT.get()));
    public static final RegistryObject<Item> RUBBLE = ITEMS.register("rubble", () -> new BlockItemBase(ModBlocks.RUBBLE.get()));

    public static final RegistryObject<Item> GLOW_LANTERN = ITEMS.register("lantern_glow", () -> new BlockItemBase(ModBlocks.GLOW_LANTERN.get()));
    public static final RegistryObject<Item> GLOW_TORCH = ITEMS.register("torch_glow", () -> new WallOrFloorItem(ModBlocks.GLOW_TORCH.get(), ModBlocks.GLOW_WALL_TORCH.get(), (new Item.Properties()).group(InfernalExpansion.TAB)));
    public static final RegistryObject<Item> GLOW_CAMPFIRE = ITEMS.register("campfire_glow", () -> new BlockItemBase(ModBlocks.GLOW_CAMPFIRE.get()));

    public static final RegistryObject<Item> LUMINOUS_FUNGUS = ITEMS.register("luminous_fungus", () -> new BlockItemBase(ModBlocks.LUMINOUS_FUNGUS.get()));


    // Tools
    public static final RegistryObject<SwordItem> FROSTBITTEN_SWORD = ITEMS.register("frostbitten_sword", () -> new SwordItem(ModItemTier.FROSTBITTEN_NETHERITE, 2, -2.4F, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<PickaxeItem> FROSTBITTEN_PICKAXE = ITEMS.register("frostbitten_pickaxe", () -> new PickaxeItem(ModItemTier.FROSTBITTEN_NETHERITE, 1, -2.4F, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<AxeItem> FROSTBITTEN_AXE = ITEMS.register("frostbitten_axe", () -> new AxeItem(ModItemTier.FROSTBITTEN_NETHERITE, 4, -3.1F, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<ShovelItem> FROSTBITTEN_SHOVEL = ITEMS.register("frostbitten_shovel", () -> new ShovelItem(ModItemTier.FROSTBITTEN_NETHERITE, -1, -2.4F, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<HoeItem> FROSTBITTEN_HOE = ITEMS.register("frostbitten_hoe", () -> new HoeItem(ModItemTier.FROSTBITTEN_NETHERITE, -2, -1.0F, new Item.Properties().group(InfernalExpansion.TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Items Registered!");
    }

}
