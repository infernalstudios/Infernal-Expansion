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

package org.infernalstudios.infernalexp.mixin.common;

import org.infernalstudios.infernalexp.access.AbstractArrowEntityAccess;
import org.infernalstudios.infernalexp.init.IEEffects;
import org.infernalstudios.infernalexp.init.IEParticleTypes;
import org.infernalstudios.infernalexp.init.IEPotions;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(ArrowEntity.class)
public abstract class MixinArrowEntity {
    @Final
    @Shadow
    private Set<EffectInstance> customPotionEffects;

    @Shadow
    protected abstract void refreshColor();

    @Shadow
    private Potion potion;

    @Inject(at = @At("RETURN"), method = "setPotionEffect")
    private void setPotionEffectInfernalExpansion(ItemStack stack, CallbackInfo ci) {
        if (this.potion == IEPotions.INFECTION.get() || this.potion == IEPotions.LONG_INFECTION.get() || this.potion == IEPotions.STRONG_INFECTION.get()) {
            ((AbstractArrowEntityAccess) this).setInfection(true);
        } else if (this.potion == IEPotions.LUMINOUS.get() || this.potion == IEPotions.LONG_LUMINOUS.get() || this.potion == IEPotions.STRONG_LUMINOUS.get()) {
            ((AbstractArrowEntityAccess) this).setLuminous(true);
        }
        this.refreshColor();
    }

    @Inject(at = @At("RETURN"), method = "addEffect")
    private void addEffectInfernalExpansion(EffectInstance effect, CallbackInfo ci) {
        for (EffectInstance effectInstance : this.customPotionEffects) {
            if (effectInstance.getPotion() == IEEffects.INFECTION.get()) {
                ((AbstractArrowEntityAccess) this).setInfection(true);
            } else if (effectInstance.getPotion() == IEEffects.LUMINOUS.get()) {
                ((AbstractArrowEntityAccess) this).setLuminous(true);
            }
        }
        this.refreshColor();
    }

    @Inject(at = @At("HEAD"), method = "spawnPotionParticles")
    private void spawnCustomParticlesInfernalExpansion(int particleCount, CallbackInfo ci) {
        if (((AbstractArrowEntityAccess) this).getLuminous() || ((AbstractArrowEntityAccess) this).getGlow()) {
            for (int j = 0; j < particleCount; ++j) {
                ((ArrowEntity) (Object) this).world.addParticle(IEParticleTypes.GLOWSTONE_SPARKLE.get(),
                    ((ArrowEntity) (Object) this).getPosXRandom(0.5D), ((ArrowEntity) (Object) this).getPosYRandom(),
                    ((ArrowEntity) (Object) this).getPosZRandom(0.5D), 0.0D, 0.0D, 0.0D);
            }
        }
        if (((AbstractArrowEntityAccess) this).getInfection()) {
            for (int j = 0; j < particleCount; ++j) {
                ((ArrowEntity) (Object) this).world.addParticle(IEParticleTypes.INFECTION.get(),
                    ((ArrowEntity) (Object) this).getPosXRandom(0.5D), ((ArrowEntity) (Object) this).getPosYRandom(),
                    ((ArrowEntity) (Object) this).getPosZRandom(0.5D), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "arrowHit")
    private void onArrowHitInfernalExpansion(LivingEntity living, CallbackInfo ci) {
        if (((AbstractArrowEntityAccess) this).getGlow()) {
            living.addPotionEffect(new EffectInstance(IEEffects.LUMINOUS.get(), 3600));
        }

        if (((AbstractArrowEntityAccess) this).getInfectedSource()) {
            living.addPotionEffect(new EffectInstance(IEEffects.INFECTION.get(), 600));
        }
    }
}
