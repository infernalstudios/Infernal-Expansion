package com.nekomaster1000.infernalexp.mixin.common;

import com.nekomaster1000.infernalexp.init.IEEffects;
import com.nekomaster1000.infernalexp.init.IEParticleTypes;
import net.minecraft.entity.projectile.ArrowEntity;
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

	@Inject(at = @At("HEAD"), method = "spawnPotionParticles", cancellable = true)
	private void spawnCustomParticlesInfernalExpansion(int particleCount, CallbackInfo ci) {
		for (EffectInstance effectInstance : this.customPotionEffects) {
			if (effectInstance.getPotion() == IEEffects.INFECTION.get()) {
				for (int j = 0; j < particleCount; ++j) {
					((ArrowEntity) (Object) this).world.addParticle(IEParticleTypes.INFECTION.get(),
						((ArrowEntity) (Object) this).getPosXRandom(0.5D), ((ArrowEntity) (Object) this).getPosYRandom(),
						((ArrowEntity) (Object) this).getPosZRandom(0.5D), 0.0D, 0.0D, 0.0D);
				}

				this.customPotionEffects.remove(effectInstance);

			} else if (effectInstance.getPotion() == IEEffects.LUMINOUS.get()) {
				for (int j = 0; j < particleCount; ++j) {
					((ArrowEntity) (Object) this).world.addParticle(IEParticleTypes.GLOWSTONE_SPARKLE.get(),
						((ArrowEntity) (Object) this).getPosXRandom(0.5D), ((ArrowEntity) (Object) this).getPosYRandom(),
						((ArrowEntity) (Object) this).getPosZRandom(0.5D), 0.0D, 0.0D, 0.0D);
				}

				this.customPotionEffects.remove(effectInstance);
			}
		}

		//this.refreshColor();
	}
}
