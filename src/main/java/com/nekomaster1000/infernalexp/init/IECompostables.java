package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.util.DataUtil;

public class IECompostables {

    public static void registerCompostables() {
        DataUtil.registerCompostable(1.0F, IEBlocks.CRIMSON_FUNGUS_CAP.get().asItem());
        DataUtil.registerCompostable(0.45F, IEBlocks.LUMINOUS_FUNGUS.get().asItem());
        DataUtil.registerCompostable(1.0F, IEBlocks.LUMINOUS_FUNGUS_CAP.get().asItem());
        DataUtil.registerCompostable(0.65F, IEBlocks.SHROOMLIGHT_FUNGUS.get().asItem());
        DataUtil.registerCompostable(1.0F, IEBlocks.WARPED_FUNGUS_CAP.get().asItem());
    }
}
