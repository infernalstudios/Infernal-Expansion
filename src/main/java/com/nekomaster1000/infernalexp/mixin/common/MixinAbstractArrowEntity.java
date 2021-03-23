package com.nekomaster1000.infernalexp.mixin.common;

import com.nekomaster1000.infernalexp.client.DynamicLightingHandler;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrowEntity.class)
public class MixinAbstractArrowEntity {

	@Unique


	@Inject(at = @At("RETURN"), method = "tick")
	private void arrowTickInfernalExpansion(CallbackInfo ci) {
		DynamicLightingHandler.tick(((ArrowEntity) (Object) this));
	}
}
