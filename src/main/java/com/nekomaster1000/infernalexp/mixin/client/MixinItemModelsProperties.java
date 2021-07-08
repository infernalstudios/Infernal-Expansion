package com.nekomaster1000.infernalexp.mixin.client;

import java.util.Random;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.nekomaster1000.infernalexp.init.IEBiomes;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

@Mixin(targets = "net.minecraft.item.ItemModelsProperties$1")
public class MixinItemModelsProperties {

	@ModifyVariable(method = "call", at = @At(value = "STORE", ordinal = 1 /* this ordinal is when its set to Math.random(), the second time d0 is set to something */), ordinal = 0 /* this ordinal means the first double variable */)
	private double IE_daytimeInGSC(double in, ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
		if (world.getBiome(entity.getPosition()).getRegistryName().equals(IEBiomes.GLOWSTONE_CANYON.getId())) {
			return MathHelper.nextDouble(new Random(), 0.95, 1.05) % 1;
		}
		return in;
	}
}
