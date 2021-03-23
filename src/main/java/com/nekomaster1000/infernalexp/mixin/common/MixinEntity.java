package com.nekomaster1000.infernalexp.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.nekomaster1000.infernalexp.access.FireTypeAccess;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;

@Mixin(Entity.class)
public abstract class MixinEntity implements FireTypeAccess {

	@Unique
	private KnownFireTypes fireType = KnownFireTypes.FIRE;

	@Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setFlag(IZ)V", ordinal = 0, shift = Shift.AFTER))
	private void IE_removeCustomFires(CallbackInfo ci) {
		if (!isBurning()) {
			((FireTypeAccess) this).setFireType(KnownFireTypes.FIRE);
		}
	}

	@Shadow
	abstract boolean isBurning();

	@Inject(method = "writeWithoutTypeId", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundNBT;putShort(Ljava/lang/String;S)V", ordinal = 0, shift = Shift.AFTER))
	private void IE_writeCustomFires(CompoundNBT tag, CallbackInfoReturnable<CompoundNBT> ci) {
		tag.putString("fireType", fireType.getName());
	}

	@Inject(method = "read", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundNBT;getShort(Ljava/lang/String;)S", ordinal = 0, shift = Shift.AFTER))
	private void IE_readCustomFires(CompoundNBT tag, CallbackInfo ci) {
		fireType = KnownFireTypes.byName(tag.getString("fireType"));
	}

	@Override
	public KnownFireTypes getFireType() {
		return fireType;
	}

	@Override
	public void setFireType(KnownFireTypes type) {
		fireType = type;
	}

}
