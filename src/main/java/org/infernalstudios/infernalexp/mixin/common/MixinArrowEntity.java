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

package org.infernalstudios.infernalexp.mixin.common;

import java.util.Set;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import org.infernalstudios.infernalexp.access.AbstractArrowEntityAccess;
import org.infernalstudios.infernalexp.init.IEEffects;
import org.infernalstudios.infernalexp.init.IEParticleTypes;
import org.infernalstudios.infernalexp.init.IEPotions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Arrow.class)
public abstract class MixinArrowEntity {
    @Final
    @Shadow
    private Set<MobEffectInstance> effects;

    @Shadow
    protected abstract void updateColor();

    @Shadow
    private Potion potion;

    @Inject(at = @At("RETURN"), method = "setEffectsFromItem")
    private void setPotionEffectInfernalExpansion(ItemStack stack, CallbackInfo ci) {
        if (this.potion == IEPotions.INFECTION.get() || this.potion == IEPotions.LONG_INFECTION.get() || this.potion == IEPotions.STRONG_INFECTION.get()) {
            ((AbstractArrowEntityAccess) this).setInfection(true);
        } else if (this.potion == IEPotions.LUMINOUS.get() || this.potion == IEPotions.LONG_LUMINOUS.get() || this.potion == IEPotions.STRONG_LUMINOUS.get()) {
            ((AbstractArrowEntityAccess) this).setLuminous(true);
        }
        this.updateColor();
    }

    @Inject(at = @At("RETURN"), method = "addEffect")
    private void addEffectInfernalExpansion(MobEffectInstance effect, CallbackInfo ci) {
        for (MobEffectInstance effectInstance : this.effects) {
            if (effectInstance.getEffect() == IEEffects.INFECTION.get()) {
                ((AbstractArrowEntityAccess) this).setInfection(true);
            } else if (effectInstance.getEffect() == IEEffects.LUMINOUS.get()) {
                ((AbstractArrowEntityAccess) this).setLuminous(true);
            }
        }
        this.updateColor();
    }

    @Inject(at = @At("HEAD"), method = "makeParticle")
    private void spawnCustomParticlesInfernalExpansion(int particleCount, CallbackInfo ci) {
        if (((AbstractArrowEntityAccess) this).getLuminous()) {
            for (int j = 0; j < particleCount; ++j) {
                ((Arrow) (Object) this).level.addParticle(IEParticleTypes.GLOWSTONE_SPARKLE.get(),
                    ((Arrow) (Object) this).getRandomX(0.5D), ((Arrow) (Object) this).getRandomY(),
                    ((Arrow) (Object) this).getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
            }
        }
        if (((AbstractArrowEntityAccess) this).getInfection()) {
            for (int j = 0; j < particleCount; ++j) {
                ((Arrow) (Object) this).level.addParticle(IEParticleTypes.INFECTION.get(),
                    ((Arrow) (Object) this).getRandomX(0.5D), ((Arrow) (Object) this).getRandomY(),
                    ((Arrow) (Object) this).getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "doPostHurtEffects")
    private void onArrowHitInfernalExpansion(LivingEntity living, CallbackInfo ci) {
        if (((AbstractArrowEntityAccess) this).getInfectedSource()) {
            living.addEffect(new MobEffectInstance(IEEffects.INFECTION.get(), 600));
        }
    }
}
