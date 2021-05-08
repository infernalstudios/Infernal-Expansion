package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import net.minecraft.entity.EntityClassification;

public class IEEntityClassifications {
    public static EntityClassification IECREATURE;

    public static void register() {
        IECREATURE = EntityClassification.create("IE_CREATURE", "IECREATURE", 70, true, false, 128);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Entity Classifications Registered!");
    }
}
