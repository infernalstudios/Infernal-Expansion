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

package org.infernalstudios.infernalexp.entities;

import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;
import org.infernalstudios.infernalexp.entities.ai.EatItemsGoal;
import org.infernalstudios.infernalexp.entities.ai.TargetWithEffectGoal;
import org.infernalstudios.infernalexp.events.MiscEvents;
import org.infernalstudios.infernalexp.init.IEItems;
import org.infernalstudios.infernalexp.init.IESoundEvents;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Predicate;

public class VolineEntity extends Monster implements IBucketable, IResizable {
    private static final Predicate<LivingEntity> TARGETABLE_MAGMA_CUBES = (livingEntity) -> {
        MagmaCube magmaCubeEntity = (MagmaCube) livingEntity;
        return magmaCubeEntity.isTiny() && !magmaCubeEntity.hasCustomName();
    };

    private static final EntityDataAccessor<Float> VOLINE_SIZE = SynchedEntityData.defineId(VolineEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(VolineEntity.class, EntityDataSerializers.BOOLEAN);
    private boolean isEating;

    public VolineEntity(EntityType<? extends Monster> type, Level worldIn) {
        super(type, worldIn);
    }

    // ATTRIBUTES
    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 16.0D).add(Attributes.ATTACK_DAMAGE, 1.0D).add(Attributes.ATTACK_KNOCKBACK, 1.0D).add(Attributes.MOVEMENT_SPEED, 0.5D);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        setEntitySize(1 + (level.getRandom().nextFloat() * 0.4F));
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    // BEHAVIOUR
    @Override
    protected void registerGoals() {
        super.registerGoals();
        // this.goalSelector.addGoal(0, new TemptGoal(this, 0.6D, TEMPTATION_ITEMS,
        // false));
        this.goalSelector.addGoal(0, new VolineEatItemsGoal(this, MiscEvents.getVolineEatTable(), 32.0D, getAttributeValue(Attributes.MOVEMENT_SPEED) * 2.0D));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, getAttributeValue(Attributes.MOVEMENT_SPEED) * 1.2D, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, getAttributeValue(Attributes.MOVEMENT_SPEED)));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, AbstractPiglin.class, 16.0F, getAttributeValue(Attributes.MOVEMENT_SPEED) * 2.0D, getAttributeValue(Attributes.MOVEMENT_SPEED) * 1.5D));
        this.goalSelector.addGoal(5, new PanicGoal(this, getAttributeValue(Attributes.MOVEMENT_SPEED) * 2.0D));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        if (InfernalExpansionConfig.MobInteractions.VOLINE_ATTACK_FIRE_RESISTANCE.getBoolean()) {
            this.targetSelector.addGoal(1, new TargetWithEffectGoal(this, LivingEntity.class, true, false, MobEffects.FIRE_RESISTANCE, null));
        }
        if (InfernalExpansionConfig.MobInteractions.VOLINE_ATTACK_PLAYER.getBoolean()) {
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        }
        if (InfernalExpansionConfig.MobInteractions.VOLINE_ATTACK_MAGMA_CUBE.getBoolean()) {
            this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MagmaCube.class, 10, true, true, TARGETABLE_MAGMA_CUBES));
        }
    }

    @Override
    public void aiStep() {
        if (getAttributeValue(Attributes.MOVEMENT_SPEED) <= 0) {

            level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, getRandomX(0.5D), getY() + 1.6D, getRandomZ(0.5D), 0, 0.07D, 0);
        }

        super.aiStep();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(VOLINE_SIZE, 1.0F);
        entityData.define(FROM_BUCKET, false);
    }

    @Override
    public float getEntitySize() {
        return entityData.get(VOLINE_SIZE);
    }

    @Override
    public void setEntitySize(float size) {
        size = Math.min(size, 2.0F);

        entityData.set(VOLINE_SIZE, size);
        reapplyPosition();
        refreshDimensions();
        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5F - ((size - 1.0F) / 2.0F));
    }

    @Override
    public boolean isFromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("Size", getEntitySize());
        compound.putBoolean("FromBucket", this.isFromBucket());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        float size = Math.max(compound.getFloat("Size"), 1.0F);

        setEntitySize(size);

        this.setFromBucket(compound.getBoolean("FromBucket"));
        super.readAdditionalSaveData(compound);
    }

    @Override
    public void refreshDimensions() {
        super.refreshDimensions();
        setPos(getX(), getY(), getZ());
    }

    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        return super.getDimensions(poseIn).scale(0.85F * getEntitySize());
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (VOLINE_SIZE.equals(key)) {
            refreshDimensions();
        }

        super.onSyncedDataUpdated(key);
    }

    // EXP POINTS
    @Override
    protected int getExperienceReward(Player player) {
        return 1 + this.level.random.nextInt(4);
    }

    // SOUNDS
    @Override
    protected SoundEvent getAmbientSound() {
        return IESoundEvents.VOLINE_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return IESoundEvents.VOLINE_HURT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return IESoundEvents.VOLINE_HURT.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isEating() {
        return isEating;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleEntityEvent(byte id) {
        if (id == 8) {
            isEating = false;
        } else if (id == 9) {
            isEating = true;
        } else {
            super.handleEntityEvent(id);
        }
    }

    //BUCKETABLE
    @Override
    protected InteractionResult mobInteract(Player playerIn, InteractionHand hand) {
        return IBucketable.tryBucketEntity(playerIn, hand, this).orElse(super.mobInteract(playerIn, hand));
    }

    @Override
    public void copyToStack(ItemStack stack) {
        CompoundTag compoundNBT = stack.getOrCreateTag();
        IBucketable.copyToStack(this, stack);

        compoundNBT.putFloat("Size", this.getEntitySize());
    }

    @Override
    public void copyFromAdditional(CompoundTag compound) {
        IBucketable.copyFromAdditional(this, compound);
        if (compound.contains("Size", 99)) {
            this.setEntitySize(compound.getFloat("Size"));
        }
    }

    @Override
    public SoundEvent getBucketedSound() {
        return SoundEvents.BUCKET_FILL_LAVA;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(IEItems.VOLINE_BUCKET.get());
    }

    public static class VolineEatItemsGoal extends EatItemsGoal<VolineEntity> {

        private final Map<Item, Map<Item, Integer>> eatItemsMap;

        public VolineEatItemsGoal(VolineEntity entityIn, Map<Item, Map<Item, Integer>> itemsToEat, double range, double speedIn) {
            super(entityIn, itemsToEat.keySet(), range, speedIn);

            this.eatItemsMap = itemsToEat;
        }

        @Override
        public void consumeItem() {
            entityIn.setEntitySize(entityIn.getEntitySize() + 0.2F);

            Item itemReference = itemInstance.getItem().getItem();

            // Super call here so that we can get a reference of the item before the item
            // instance is deleted
            super.consumeItem();

            if (itemReference == Items.GOLDEN_APPLE) {
                entityIn.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1));
                entityIn.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0));
            }

            Map<Item, Integer> eatItem = eatItemsMap.get(itemReference);

            if (eatItem != null) {
                for (Map.Entry<Item, Integer> item : eatItem.entrySet()) {
                    entityIn.spawnAtLocation(new ItemStack(item.getKey(), item.getValue()), 1);
                }
            }
        }
    }
}
