package org.infernalstudios.infernalexp.util;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

public class CompatibilityUtil {

    public static Item getModItem(String namespace, String itemId) {
        Item modItem = ModList.get().isLoaded(namespace) ? ForgeRegistries.ITEMS.getValue(new ResourceLocation(namespace, itemId)) : null;
        return modItem != null ? modItem : Items.AIR;
    }

}
