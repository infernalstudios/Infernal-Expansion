package com.nekomaster1000.infernalexp.mixin.common;

import com.nekomaster1000.infernalexp.access.AbstractArrowEntityAccess;
import com.nekomaster1000.infernalexp.client.DynamicLightingHandler;

import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrowEntity.class)
public class MixinAbstractArrowEntity implements AbstractArrowEntityAccess {
	@Unique
	private static final DataParameter<Boolean> GLOW = EntityDataManager.createKey(AbstractArrowEntity.class, DataSerializers.BOOLEAN);

	@Unique
	private static final DataParameter<Boolean> LUMINOUS = EntityDataManager.createKey(AbstractArrowEntity.class, DataSerializers.BOOLEAN);

	@Unique
	private static final DataParameter<Boolean> INFECTION = EntityDataManager.createKey(AbstractArrowEntity.class, DataSerializers.BOOLEAN);


	@OnlyIn(Dist.CLIENT)
	@Inject(at = @At("RETURN"), method = "tick")
	private void arrowTickInfernalExpansion(CallbackInfo ci) {
		DynamicLightingHandler.tick(((AbstractArrowEntity) (Object) this));
	}

	@Inject(at = @At("RETURN"), method = "registerData")
	private void registerDataInfernalExpansion(CallbackInfo ci) {
		((AbstractArrowEntity) (Object) this).getDataManager().register(LUMINOUS, false);
		((AbstractArrowEntity) (Object) this).getDataManager().register(INFECTION, false);
		((AbstractArrowEntity) (Object) this).getDataManager().register(GLOW, false);
	}

	@Inject(at = @At("RETURN"), method = "writeAdditional")
	private void writeAdditionalInfernalExpansion(CompoundNBT compound, CallbackInfo ci){
		compound.putBoolean("Luminous", ((AbstractArrowEntity) (Object) this).getDataManager().get(LUMINOUS));
		compound.putBoolean("Infection", ((AbstractArrowEntity) (Object) this).getDataManager().get(INFECTION));
		compound.putBoolean("Glow", ((AbstractArrowEntity) (Object) this).getDataManager().get(GLOW));
	}

	@Inject(at = @At("RETURN"), method = "readAdditional")
	private void readAdditionalInfernalExpansion(CompoundNBT compound, CallbackInfo ci){
		setLuminous(compound.getBoolean("Luminous"));
		setInfection(compound.getBoolean("Infection"));
		setGlow(compound.getBoolean("Glow"));
	}

	@Override
	public boolean getLuminous() {
		return ((AbstractArrowEntity) (Object) this).getDataManager().get(LUMINOUS);
	}

	@Override
	public void setLuminous(boolean isLuminous) {
		((AbstractArrowEntity) (Object) this).getDataManager().set(LUMINOUS, isLuminous);
	}

	@Override
	public boolean getInfection() {
		return ((AbstractArrowEntity) (Object) this).getDataManager().get(INFECTION);
	}

	@Override
	public void setInfection(boolean isInfection) {
		((AbstractArrowEntity) (Object) this).getDataManager().set(INFECTION, isInfection);
	}

	@Override
	public boolean getGlow() {
		return ((AbstractArrowEntity) (Object) this).getDataManager().get(GLOW);
	}

	@Override
	public void setGlow(boolean shouldGlow) {
		((AbstractArrowEntity) (Object) this).getDataManager().set(GLOW, shouldGlow);
	}
}
