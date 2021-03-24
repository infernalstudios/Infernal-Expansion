package com.nekomaster1000.infernalexp.mixin.common;

import com.nekomaster1000.infernalexp.access.AbstractArrowEntityAccess;
import com.nekomaster1000.infernalexp.client.DynamicLightingHandler;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrowEntity.class)
public class MixinAbstractArrowEntity implements AbstractArrowEntityAccess {

	@Unique
	private static final DataParameter<Boolean> LUMINOUS = EntityDataManager.createKey(AbstractArrowEntity.class, DataSerializers.BOOLEAN);

	@Inject(at = @At("RETURN"), method = "tick")
	private void arrowTickInfernalExpansion(CallbackInfo ci) {
		DynamicLightingHandler.tick(((AbstractArrowEntity) (Object) this));
	}

	@Inject(at = @At("RETURN"), method = "registerData")
	private void registerDataInfernalExpansion(CallbackInfo ci){
		((AbstractArrowEntity) (Object) this).getDataManager().register(LUMINOUS, false);
	}

	@Inject(at = @At("RETURN"), method = "writeAdditional")
	private void writeAdditionalInfernalExpansion(CompoundNBT compound, CallbackInfo ci){
		compound.putBoolean("Luminous", ((AbstractArrowEntity) (Object) this).getDataManager().get(LUMINOUS));
	}

	@Inject(at = @At("RETURN"), method = "readAdditional")
	private void readAdditionalInfernalExpansion(CompoundNBT compound, CallbackInfo ci){
		setLuminous(compound.getBoolean("Luminous"));
	}

	@Override
	public DataParameter<Boolean> getLuminous() {
		return LUMINOUS;
	}

	@Override
	public void setLuminous(boolean isLuminous) {
		((AbstractArrowEntity) (Object) this).getDataManager().set(LUMINOUS, isLuminous);
	}
}
