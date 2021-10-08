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

package org.infernalstudios.infernalexp.effects;

import org.infernalstudios.infernalexp.init.IEEffects;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.damagesource.DamageSource;

public class InfectionEffect extends MobEffect {

    private int initialDuration;

    public InfectionEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn.getHealth() > 1.0F) {
            entityLivingBaseIn.hurt(DamageSource.MAGIC, 1.0F);
        }

        for (LivingEntity entity : entityLivingBaseIn.getCommandSenderWorld().getEntitiesOfClass(LivingEntity.class, entityLivingBaseIn.getBoundingBox().inflate(3))) {
            if (!entity.hasEffect(IEEffects.INFECTION.get()) && entity.isEffectiveAi()) {
                entity.addEffect(new MobEffectInstance(IEEffects.INFECTION.get(), entityLivingBaseIn.getEffect(IEEffects.INFECTION.get()).getDuration() / 2));
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
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
