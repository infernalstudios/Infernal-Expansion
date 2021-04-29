package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.util.DataUtil;

public class IECompostables {

    public static void registerCompostables() {
        DataUtil.registerCompostable(1.0F, IEBlocks.CRIMSON_FUNGUS_CAP.get().asItem());
        DataUtil.registerCompostable(0.45F, IEItems.LUMINOUS_FUNGUS.get());
        DataUtil.registerCompostable(1.0F, IEItems.LUMINOUS_FUNGUS_CAP.get());
        DataUtil.registerCompostable(0.65F, IEItems.SHROOMLIGHT_FUNGUS.get());
        DataUtil.registerCompostable(1.0F, IEItems.WARPED_FUNGUS_CAP.get());
    }
}
