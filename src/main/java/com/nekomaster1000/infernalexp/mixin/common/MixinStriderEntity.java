package com.nekomaster1000.infernalexp.mixin.common;

import com.nekomaster1000.infernalexp.init.IEItems;
import com.nekomaster1000.infernalexp.util.IBucketable;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRideable;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StriderEntity.class)
public abstract class MixinStriderEntity extends AnimalEntity implements IRideable, IEquipable, IBucketable {
    @Shadow
    protected abstract void registerData();

    @Shadow
    public abstract void readAdditional(CompoundNBT compound);

    private static final DataParameter<Boolean> FROM_BUCKET = EntityDataManager.createKey(StriderEntity.class, DataSerializers.BOOLEAN);

    protected MixinStriderEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Inject(method = "registerData", at = @At("HEAD"))
    private void IE_registerData(CallbackInfo info) {
        this.dataManager.register(FROM_BUCKET, false);
    }

    @Override
    public boolean isFromBucket() {
        return this.dataManager.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean isFromBucket) {
        this.dataManager.set(FROM_BUCKET, isFromBucket);
    }

    @Inject(method = "writeAdditional", at = @At("HEAD"))
    private void IE_writeAdditional(CompoundNBT compound, CallbackInfo ci) {
        compound.putBoolean("FromBucket", this.isFromBucket());
    }

    @Inject(method = "readAdditional", at = @At("HEAD"))
    private void IE_readAdditional(CompoundNBT compound, CallbackInfo ci) {
        this.setFromBucket(compound.getBoolean("FromBucket"));
    }

    @Inject(method = "getEntityInteractionResult", at = @At("RETURN"), cancellable = true)
    public void IE_getEntityInteractionResult(PlayerEntity playerIn, Hand hand, CallbackInfoReturnable<ActionResultType> cir) {
        ActionResultType resultType = cir.getReturnValue();
        if (this.isChild()) {
            cir.setReturnValue(IBucketable.tryBucketEntity(playerIn, hand, this).orElse(super.getEntityInteractionResult(playerIn, hand)));
        } else {
            cir.setReturnValue(resultType);
        }
    }

    @Inject(method = "onInitialSpawn", at = @At("HEAD"), cancellable = true)
    private void IE_onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, ILivingEntityData spawnDataIn, CompoundNBT dataTag, CallbackInfoReturnable<ILivingEntityData> cir) {
        if (reason == SpawnReason.BUCKET) {
            spawnDataIn = new AgeableEntity.AgeableData(true);
            this.setGrowingAge(-24000);
            cir.setReturnValue(spawnDataIn);
        }
    }

    @Override
    public void copyToStack(ItemStack stack) {
        CompoundNBT compound = stack.getOrCreateTag();
        IBucketable.copyToStack(this, stack);

        compound.putInt("Age", this.getGrowingAge());
    }

    @Override
    public void copyFromAdditional(CompoundNBT compound) {
        IBucketable.copyFromAdditional(this, compound);
        if (compound.contains("Age", 99)) {
            this.setGrowingAge(compound.getInt("Age"));
        }
    }

    @Override
    public SoundEvent getBucketedSound() {
        return SoundEvents.ITEM_BUCKET_FILL_LAVA;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(IEItems.STRIDER_BUCKET.get());
    }
}
