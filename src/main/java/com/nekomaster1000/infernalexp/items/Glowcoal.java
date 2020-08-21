package com.nekomaster1000.infernalexp.items;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Glowcoal extends Item {
    public Glowcoal() {
        super(new Item.Properties().group(InfernalExpansion.TAB));
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 1600;
    }
}
