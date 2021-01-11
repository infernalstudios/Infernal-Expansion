package com.nekomaster1000.infernalexp.items;

import net.minecraft.item.SoupItem;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class SlurpSoupItem extends SoupItem {
    public SlurpSoupItem(Properties builder) {
        super(builder);
    }

    @Override
    public SoundEvent getDrinkSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    @Override
    public SoundEvent getEatSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }
}
