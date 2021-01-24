package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.world.gen.carvers.GlowstoneRavineCarver;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModCarvers {

    // Carvers
    public static final DeferredRegister<WorldCarver<?>> CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, InfernalExpansion.MOD_ID);
    public static final RegistryObject<WorldCarver<ProbabilityConfig>> GLOWSTONE_RAVINE = CARVERS.register("glowstone_ravine", () -> new GlowstoneRavineCarver(ProbabilityConfig.CODEC));

    public static void register(IEventBus eventBus) {
        CARVERS.register(eventBus);
        InfernalExpansion.LOGGER.info("Infernal Expansion: World Carvers Registered!");
    }

}
