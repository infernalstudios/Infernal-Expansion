package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class IEPotions {
    public static final DeferredRegister<Potion> POTION_TYPES = DeferredRegister.create(ForgeRegistries.POTION_TYPES, InfernalExpansion.MOD_ID);

    public static final RegistryObject<Potion> INFECTION = POTION_TYPES.register("infection", () -> new Potion(new EffectInstance(IEEffects.INFECTION.get(), 3600)));
    public static final RegistryObject<Potion> LUMINOUS = POTION_TYPES.register("luminous", () -> new Potion(new EffectInstance(IEEffects.LUMINOUS.get(), 3600)));
    public static final RegistryObject<Potion> LONG_LUMINOUS = POTION_TYPES.register("long_luminous", () -> new Potion("luminous", new EffectInstance(IEEffects.LUMINOUS.get(), 9600)));
    public static final RegistryObject<Potion> STRONG_LUMINOUS = POTION_TYPES.register("strong_luminous", () -> new Potion("luminous", new EffectInstance(IEEffects.LUMINOUS.get(), 1800, 1)));

    public static void register(IEventBus eventBus) {
        POTION_TYPES.register(eventBus);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Potions Registered!");
    }
}
