package com.nekomaster1000.infernalexp.util;

import net.minecraft.block.ComposterBlock;
import net.minecraft.util.IItemProvider;

import net.minecraftforge.fml.ModList;

public class DataUtil {

    public static void registerCompostable(float chance, IItemProvider item) {
        ComposterBlock.CHANCES.put(item.asItem(), chance);
    }

    public static boolean isLoaded(String modID) {
        return ModList.get().isLoaded(modID);
    }
}
