package com.nekomaster1000.Infernal.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import com.nekomaster1000.Infernal.InfernalExpansion;

public class ItemBase extends Item {

    public ItemBase() {
        super(new Item.Properties().group(InfernalExpansion.TAB));
    }
}
