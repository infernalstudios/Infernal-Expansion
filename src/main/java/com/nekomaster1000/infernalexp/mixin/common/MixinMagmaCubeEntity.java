package com.nekomaster1000.infernalexp.mixin.common;

import com.nekomaster1000.infernalexp.init.IEItems;
import com.nekomaster1000.infernalexp.util.IBucketable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.entity.monster.SlimeEntity;
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

import javax.annotation.Nullable;

@Mixin(MagmaCubeEntity.class)
public abstract class MixinMagmaCubeEntity extends SlimeEntity implements IBucketable {
    private static final DataParameter<Boolean> FROM_BUCKET = EntityDataManager.createKey(MagmaCubeEntity.class, DataSerializers.BOOLEAN);

    public MixinMagmaCubeEntity(EntityType<? extends SlimeEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerData() {
        super.registerData();
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

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("FromBucket", this.isFromBucket());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setFromBucket(compound.getBoolean("FromBucket"));
    }

    @Override
    protected ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        if (this.isSmallSlime()) {
            return IBucketable.tryBucketEntity(playerIn, hand, this).orElse(super.getEntityInteractionResult(playerIn, hand));
        } else {
            return super.getEntityInteractionResult(playerIn, hand);
        }
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (reason == SpawnReason.BUCKET) {
            return spawnDataIn;
        } else {
            this.setSlimeSize(0, false);
            return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        }
    }

    @Override
    public void copyToStack(ItemStack stack) {
        CompoundNBT compound = stack.getOrCreateTag();
        IBucketable.copyToStack(this, stack);

        compound.putInt("Size", this.getSlimeSize());
    }

    @Override
    public void copyFromAdditional(CompoundNBT compound) {
        IBucketable.copyFromAdditional(this, compound);
        if (compound.contains("Size", 99)) {
            this.setSlimeSize(compound.getInt("Size"), false);
        }
    }

    @Override
    public SoundEvent getBucketedSound() {
        return SoundEvents.ITEM_BUCKET_FILL_LAVA;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(IEItems.MAGMA_CUBE_BUCKET.get());
    }
}
