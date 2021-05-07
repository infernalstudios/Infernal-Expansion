package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import net.minecraft.entity.EntityClassification;

public class IEEntityClassifications {
    public static EntityClassification IEMOB;

    public static void register() {
        IEMOB = EntityClassification.create("IE_mob", "IEMOB", 70, true, false, 128);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Items Registered!");
    }
}
