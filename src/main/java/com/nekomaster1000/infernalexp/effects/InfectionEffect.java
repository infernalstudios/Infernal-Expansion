/*
 * Copyright 2021 Infernal Studios
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

package com.nekomaster1000.infernalexp.effects;

import com.nekomaster1000.infernalexp.init.IEEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class InfectionEffect extends Effect {

    private int initialDuration;

    public InfectionEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn.getHealth() > 1.0F) {
            entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC, 1.0F);
        }

        for (LivingEntity entity : entityLivingBaseIn.getEntityWorld().getEntitiesWithinAABB(LivingEntity.class, entityLivingBaseIn.getBoundingBox().grow(3))) {
            if (!entity.isPotionActive(IEEffects.INFECTION.get()) && entity.isServerWorld()) {
                entity.addPotionEffect(new EffectInstance(IEEffects.INFECTION.get(), entityLivingBaseIn.getActivePotionEffect(IEEffects.INFECTION.get()).getDuration() / 2));
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        if (this == IEEffects.INFECTION.get()) {
            // This number (50 right now) determines how often the performEffect will be called. The lower the number the more often performEffect will be called
            int j = 50 >> amplifier;
            if (j > 0) {
                return duration % j == 0;
            } else {
                return true;
            }
        }

        return false;
    }
}
