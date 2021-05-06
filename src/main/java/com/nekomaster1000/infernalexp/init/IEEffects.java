package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.effects.EffectBase;
import com.nekomaster1000.infernalexp.effects.InfectionEffect;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class IEEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, InfernalExpansion.MOD_ID);

    public static final RegistryObject<Effect> LUMINOUS = EFFECTS.register("luminous", () -> new EffectBase(EffectType.NEUTRAL, 0xDCBC82));
    public static final RegistryObject<Effect> INFECTION = EFFECTS.register("infection", () -> new InfectionEffect(EffectType.HARMFUL, 12856114));

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Effects Registered!");
    }
}
