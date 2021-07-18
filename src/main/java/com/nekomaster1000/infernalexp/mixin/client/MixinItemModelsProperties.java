package com.nekomaster1000.infernalexp.mixin.client;

import com.nekomaster1000.infernalexp.init.IEBiomes;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

@Mixin(targets = "net.minecraft.item.ItemModelsProperties$1")
public class MixinItemModelsProperties {

	@ModifyVariable(method = "call", at = @At(value = "STORE", ordinal = 1 /* this ordinal is when its set to Math.random(), the second time d0 is set to something */), ordinal = 0 /* this ordinal means the first double variable */)
	private double IE_daytimeInGSC(double in, ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
	    ClientWorld clientWorld = world;
        if (entity == null) {
            return in;
        }
        if (world == null && entity.world instanceof ClientWorld) {
            clientWorld = (ClientWorld)entity.getEntityWorld();
        }

        Optional<RegistryKey<Biome>> biome = clientWorld.func_242406_i(entity.getPosition());

        if (biome.isPresent() && biome.get().getLocation().equals(IEBiomes.GLOWSTONE_CANYON.getId())) {
            return MathHelper.nextDouble(new Random(), 0.95, 1.05) % 1;
        }
        return in;
	}
}
