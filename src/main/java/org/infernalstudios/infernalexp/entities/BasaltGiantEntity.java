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

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;
import org.infernalstudios.infernalexp.init.IESoundEvents;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.UUID;

public class BasaltGiantEntity extends PathfinderMob implements NeutralMob, IEntityAdditionalSpawnData, IResizable {

    //    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.BASALT, Items.POLISHED_BASALT);
    private static final UniformInt RANGED_INT = TimeUtil.rangeOfSeconds(20, 39);
    private int attackTimer;
    private int angerTime;
    private UUID angerTarget;

    // Constant values for entity scaling
    private static final float BASE_ENTITY_HEIGHT = 5.0F;
    private static final float MIN_ENTITY_HEIGHT = 4.0F;
    private static final float MAX_ENTITY_HEIGHT = 6.0F;

    private static final EntityDataAccessor<Float> GIANT_SIZE = SynchedEntityData.defineId(BasaltGiantEntity.class, EntityDataSerializers.FLOAT);

    public BasaltGiantEntity(EntityType<? extends BasaltGiantEntity> type, Level worldIn) {
        super(type, worldIn);
        this.maxUpStep = 2.0f;
    }

//    public BasaltGiantEntity(EntityType<? extends BasaltGiantEntity> type, World worldIn, float size) {
//        super(type, worldIn);
//        if (size != 1)
//            this.dataManager.set(GIANT_SIZE, size);
//    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {

        // Get a random size scale value resulting in a height between the MIN and MAX
        // values specified above
        float size = random.nextFloat();
        size /= BASE_ENTITY_HEIGHT / (MAX_ENTITY_HEIGHT - MIN_ENTITY_HEIGHT);
        size += MIN_ENTITY_HEIGHT / BASE_ENTITY_HEIGHT;
        this.setEntitySize(size);

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    // ATTRIBUTES
    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 56.0D).add(Attributes.ATTACK_DAMAGE, 12.0D).add(Attributes.ATTACK_KNOCKBACK, 2.0D).add(Attributes.KNOCKBACK_RESISTANCE, 30.0D).add(Attributes.MOVEMENT_SPEED, 0.45D);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(GIANT_SIZE, 1.0F);
    }

    @Override
    public float getEntitySize() {
        return this.entityData.get(GIANT_SIZE);
    }

    @Override
    public void setEntitySize(float size) {
        getEntityData().set(GIANT_SIZE, size);
        reapplyPosition();
        refreshDimensions();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("Size", getEntitySize());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        setEntitySize(compound.getFloat("Size"));
        super.readAdditionalSaveData(compound);
    }

    @Override
    public void refreshDimensions() {
        super.refreshDimensions();
        setPos(getX(), getY(), getZ());
    }

    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        return super.getDimensions(poseIn).scale(getEntitySize());
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (GIANT_SIZE.equals(key)) {
            refreshDimensions();
        }

        super.onSyncedDataUpdated(key);
    }

    // ---
    // Retaliating
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 4) {
            this.attackTimer = 10;
            this.playSound(IESoundEvents.BASALT_GIANT_DEATH.get(), 1.0F, 1.0F);
        } else {
            super.handleEntityEvent(id);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public int getAttackTimer() {
        return this.attackTimer;
    }

    public boolean doHurtTarget(Entity entityIn) {
        this.attackTimer = 10;
        this.level.broadcastEntityEvent(this, (byte) 4);
        float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1 = (int) f > 0 ? f / 2.0F + (float) this.random.nextInt((int) f) : f;
        float f2 = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);

        if (entityIn instanceof Player player && player.getUseItem().canPerformAction(ToolActions.SHIELD_BLOCK)) {
            attackFling(player, f2 * 3, 2.0);
            player.hurtMarked = true;
        }

        boolean flag = entityIn.hurt(DamageSource.mobAttack(this), f1);

        if (flag) {
            attackFling(entityIn, f2, 0.6);
        }

        this.playSound(IESoundEvents.BASALT_GIANT_HURT.get(), 1.0F, 1.0F);
        return flag;
    }

    private void attackFling(Entity entityIn, float f2, double height) {
        ((LivingEntity) entityIn).knockback(f2, Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180F)));
        entityIn.setDeltaMovement(entityIn.getDeltaMovement().add(0.0D, height, 0.0D));
        this.doEnchantDamageEffects(this, entityIn);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof AbstractArrow) {
            return false;
        }
        return super.hurt(source, amount);
    }

    // ---

    // BEHAVIOUR
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 0.6D, true));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.5d));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
//        this.goalSelector.addGoal(5, new TemptGoal(this, 0.6D, TEMPTATION_ITEMS, false));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        if (InfernalExpansionConfig.MobInteractions.SKELETON_ATTACK_GIANT.getBoolean()) {
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Skeleton.class, true, false));
        }
        if (InfernalExpansionConfig.MobInteractions.GIANT_ATTACK_MAGMA_CUBE.getBoolean()) {
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, MagmaCube.class, true, false));
        }
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeFloat(getEntitySize());
    }

    @Override
    public void readSpawnData(FriendlyByteBuf buffer) {
        this.entityData.set(GIANT_SIZE, buffer.readFloat());
    }

    // EXP POINTS
    @Override
    public int getExperienceReward() {
        return 73;
    }

    // SOUNDS
    @Override
    protected SoundEvent getAmbientSound() {
        return IESoundEvents.BASALT_GIANT_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return IESoundEvents.BASALT_GIANT_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return IESoundEvents.BASALT_GIANT_HURT.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
    }

    public boolean fireImmune() {
        return true;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.angerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int time) {
        this.angerTime = time;
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.angerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID target) {
        this.angerTarget = target;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(RANGED_INT.sample(this.random));
    }
}
