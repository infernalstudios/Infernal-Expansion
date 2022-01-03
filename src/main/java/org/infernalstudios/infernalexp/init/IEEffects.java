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

import net.minecraftforge.fmllegacy.RegistryObject;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.effects.EffectBase;
import org.infernalstudios.infernalexp.effects.InfectionEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class IEEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, InfernalExpansion.MOD_ID);

    public static final RegistryObject<MobEffect> LUMINOUS = EFFECTS.register("luminous", () -> new EffectBase(MobEffectCategory.NEUTRAL, 0xDCBC82));
    public static final RegistryObject<MobEffect> INFECTION = EFFECTS.register("infection", () -> new InfectionEffect(MobEffectCategory.HARMFUL, 12856114));

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Effects Registered!");
    }
}
