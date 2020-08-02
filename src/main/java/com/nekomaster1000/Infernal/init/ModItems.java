package com.nekomaster1000.Infernal.init;

import com.nekomaster1000.Infernal.InfernalExpansion;
import com.nekomaster1000.Infernal.blocks.BlockItemBase;
import com.nekomaster1000.Infernal.items.ItemBase;
import com.nekomaster1000.Infernal.tools.ModItemTier;
import com.nekomaster1000.Infernal.util.ModdedSpawnEggItem;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, InfernalExpansion.MOD_ID);

    //items
    public static final RegistryObject<Item> GLOWCOAL = ITEMS.register("glowcoal", ItemBase::new);
    public static final RegistryObject<Item> DIMROCKS = ITEMS.register("glownuggets", ItemBase::new);
    public static final RegistryObject<ModdedSpawnEggItem> GLOWSQUITO_SPAWN_EGG = ITEMS.register("glowsquito_spawn_egg", () -> new ModdedSpawnEggItem(ModEntityType.GLOWSQUITO, 0x4A4A4A, 0xF4CD55, new Item.Properties().group(InfernalExpansion.TAB)));

    //block items
    public static final RegistryObject<Item> DIMSTONE_ITEM = ITEMS.register("dimstone", () -> new BlockItemBase(ModBlocks.DIMSTONE.get()));
    public static final RegistryObject<Item> DULLSTONE_ITEM = ITEMS.register("dullstone", () -> new BlockItemBase(ModBlocks.DULLSTONE.get()));
    public static final RegistryObject<Item> glowstone_brick_item = ITEMS.register("glowstone_brick", () -> new BlockItemBase(ModBlocks.glowstone_brick.get()));
    public static final RegistryObject<Item> dimstone_brick_item = ITEMS.register("dimstone_brick", () -> new BlockItemBase(ModBlocks.dimstone_brick.get()));
    public static final RegistryObject<Item> dullstone_brick_item = ITEMS.register("dullstone_brick", () -> new BlockItemBase(ModBlocks.dullstone_brick.get()));
    public static final RegistryObject<Item> glowstone_smooth_item = ITEMS.register("glowstone_smooth", () -> new BlockItemBase(ModBlocks.glowstone_smooth.get()));
    public static final RegistryObject<Item> dimstone_smooth_item = ITEMS.register("dimstone_smooth", () -> new BlockItemBase(ModBlocks.dimstone_smooth.get()));
    public static final RegistryObject<Item> dullstone_smooth_item = ITEMS.register("dullstone_smooth", () -> new BlockItemBase(ModBlocks.dullstone_smooth.get()));
    public static final RegistryObject<Item> chiselled_glowstone_brick1_Item = ITEMS.register("chiselled_glowstone_brick1", () -> new BlockItemBase(ModBlocks.chiselled_glowstone_brick1.get()));
    public static final RegistryObject<Item> chiselled_glowstone_brick2_Item = ITEMS.register("chiselled_glowstone_brick2", () -> new BlockItemBase(ModBlocks.chiselled_glowstone_brick2.get()));
    public static final RegistryObject<Item> chiselled_dimstone_brick1_Item = ITEMS.register("chiselled_dimstone_brick1", () -> new BlockItemBase(ModBlocks.chiselled_dimstone_brick1.get()));
    public static final RegistryObject<Item> chiselled_dimstone_brick2_Item = ITEMS.register("chiselled_dimstone_brick2", () -> new BlockItemBase(ModBlocks.chiselled_dimstone_brick2.get()));
    public static final RegistryObject<Item> chiselled_dullstone_brick1_Item = ITEMS.register("chiselled_dullstone_brick1", () -> new BlockItemBase(ModBlocks.chiselled_dullstone_brick1.get()));
    public static final RegistryObject<Item> chiselled_dullstone_brick2_Item = ITEMS.register("chiselled_dullstone_brick2", () -> new BlockItemBase(ModBlocks.chiselled_dullstone_brick2.get()));


    //tools
    public static final RegistryObject<SwordItem> FROSTBITTEN_SWORD = ITEMS.register("frostbitten_sword", () ->
            new SwordItem(ModItemTier.FROSTBITTEN_NETHERITE, 2, -2.4F, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<PickaxeItem> FROSTBITTEN_PICKAXE = ITEMS.register("frostbitten_pickaxe", () ->
            new PickaxeItem(ModItemTier.FROSTBITTEN_NETHERITE, 1, -2.4F, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<AxeItem> FROSTBITTEN_AXE = ITEMS.register("frostbitten_axe", () ->
            new AxeItem(ModItemTier.FROSTBITTEN_NETHERITE, 4, -3.1F, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<ShovelItem> FROSTBITTEN_SHOVEL = ITEMS.register("frostbitten_shovel", () ->
            new ShovelItem(ModItemTier.FROSTBITTEN_NETHERITE, -1, -2.4F, new Item.Properties().group(InfernalExpansion.TAB)));
    public static final RegistryObject<HoeItem> FROSTBITTEN_HOE = ITEMS.register("frostbitten_hoe", () ->
            new HoeItem(ModItemTier.FROSTBITTEN_NETHERITE, -2, -1.0F, new Item.Properties().group(InfernalExpansion.TAB)));


}
