package com.nekomaster1000.infernalexp.util;

import net.minecraft.block.ComposterBlock;
import net.minecraft.util.IItemProvider;

public class DataUtil {

    public static void registerCompostable(IItemProvider item, float chance) {
        ComposterBlock.CHANCES.put(item.asItem(), chance);
    }
}
