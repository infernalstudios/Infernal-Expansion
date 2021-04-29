package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.util.DataUtil;

public class IECompostables {

    public static void registerCompostables() {
        DataUtil.registerCompostable(IEItems.CRIMSON_FUNGUS_CAP.get(), 1.0F);
        DataUtil.registerCompostable(IEItems.LUMINOUS_FUNGUS.get(), 0.45F);
        DataUtil.registerCompostable(IEItems.LUMINOUS_FUNGUS_CAP.get(), 1.0F);
        DataUtil.registerCompostable(IEItems.SHROOMLIGHT_FUNGUS.get(), 0.65F);
        DataUtil.registerCompostable(IEItems.WARPED_FUNGUS_CAP.get(), 1.0F);
    }
}
