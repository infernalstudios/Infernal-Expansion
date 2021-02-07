package com.nekomaster1000.infernalexp.effects;

import com.nekomaster1000.infernalexp.init.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class InfectionEffect extends Effect {
    public InfectionEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn.getHealth() > 1.0F) {
            entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC, 1.0F);
        }

        for (LivingEntity entity : entityLivingBaseIn.getEntityWorld().getEntitiesWithinAABB(LivingEntity.class, entityLivingBaseIn.getBoundingBox().grow(3))) {
            if (!entity.isPotionActive(ModEffects.INFECTION.get())) {
                entity.addPotionEffect(new EffectInstance(ModEffects.INFECTION.get(), 300));
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        if (this == ModEffects.INFECTION.get()) {
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
