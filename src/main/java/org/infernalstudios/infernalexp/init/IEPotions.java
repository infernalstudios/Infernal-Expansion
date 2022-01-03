/*
 * Copyright 2022 Infernal Studios
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.infernalstudios.infernalexp.init;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.infernalstudios.infernalexp.InfernalExpansion;

public class IEPotions {
    public static final DeferredRegister<Potion> POTION_TYPES = DeferredRegister.create(ForgeRegistries.POTIONS, InfernalExpansion.MOD_ID);

    public static final RegistryObject<Potion> INFECTION = POTION_TYPES.register("infection", () -> new Potion(new MobEffectInstance(IEEffects.INFECTION.get(), 3600)));
    public static final RegistryObject<Potion> LONG_INFECTION = POTION_TYPES.register("long_infection", () -> new Potion("infection", new MobEffectInstance(IEEffects.INFECTION.get(), 9600)));
    public static final RegistryObject<Potion> STRONG_INFECTION = POTION_TYPES.register("strong_infection", () -> new Potion("infection", new MobEffectInstance(IEEffects.INFECTION.get(), 1800, 1)));

    public static final RegistryObject<Potion> LUMINOUS = POTION_TYPES.register("luminous", () -> new Potion(new MobEffectInstance(IEEffects.LUMINOUS.get(), 3600)));
    public static final RegistryObject<Potion> LONG_LUMINOUS = POTION_TYPES.register("long_luminous", () -> new Potion("luminous", new MobEffectInstance(IEEffects.LUMINOUS.get(), 9600)));
    public static final RegistryObject<Potion> STRONG_LUMINOUS = POTION_TYPES.register("strong_luminous", () -> new Potion("luminous", new MobEffectInstance(IEEffects.LUMINOUS.get(), 1800, 1)));

    public static void register(IEventBus eventBus) {
        POTION_TYPES.register(eventBus);

        InfernalExpansion.LOGGER.info("Infernal Expansion: Potions Registered!");
    }
}
