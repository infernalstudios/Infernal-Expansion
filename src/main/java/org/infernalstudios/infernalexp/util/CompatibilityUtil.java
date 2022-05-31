package org.infernalstudios.infernalexp.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

public class CompatibilityUtil {

    public static Item getModItem(String namespace, String itemId) {
        Item modItem = ModList.get().isLoaded(namespace) ? ForgeRegistries.ITEMS.getValue(new ResourceLocation(namespace, itemId)) : null;
        return modItem != null ? modItem : Items.AIR;
    }

}
