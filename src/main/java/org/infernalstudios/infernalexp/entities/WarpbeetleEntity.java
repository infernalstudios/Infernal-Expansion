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
import org.infernalstudios.infernalexp.entities.ai.TeleportPanicGoal;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IESoundEvents;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

public class WarpbeetleEntity extends PathfinderMob {
    private int attackTimer;
    private int conversionTicks;
    private static final EntityDataAccessor<Boolean> CONVERTING = SynchedEntityData.defineId(ShroomloinEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> CHORUS = SynchedEntityData.defineId(WarpbeetleEntity.class, EntityDataSerializers.BOOLEAN);

    public float shellRotationMultiplier = 0.0F;

    public WarpbeetleEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
    }

    public static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.CRIMSON_FUNGUS, IEBlocks.CRIMSON_FUNGUS_CAP.get().asItem());

    // ATTRIBUTES
    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.4D)
            .add(Attributes.ATTACK_DAMAGE, 2.0F).add(Attributes.ATTACK_KNOCKBACK, 2.0F);
    }

    // BEHAVIOUR
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 0.6D, true));
        this.goalSelector.addGoal(1, new TeleportPanicGoal(this, 1.4D));
        this.goalSelector.addGoal(2, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(3, new FloatGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 0.6D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.5d));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        if (InfernalExpansionConfig.MobInteractions.SPIDER_ATTACK_WARPBEETLE.getBoolean()) {
            this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Spider.class, true, false));
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CONVERTING, false);
        this.entityData.define(CHORUS, false);
    }

    public boolean isConverting() {
        return this.entityData.get(CONVERTING);
    }

    public void setConverting(boolean converting) {
        this.entityData.set(CONVERTING, converting);
    }

    public boolean isShaking() {
        return this.isConverting();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("chorus", this.isChorus());
        compound.putInt("WarpbeetleConversionTime", this.isConverting() ? this.conversionTicks : -1);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setChorus(compound.getBoolean("chorus"));
    }

    public boolean isChorus() {
        return this.entityData.get(CHORUS);
    }

    public void setChorus(boolean chorus) {
        this.entityData.set(CHORUS, chorus);
    }

    @Override
    protected InteractionResult mobInteract(Player playerIn, InteractionHand hand) {
        ItemStack stack = playerIn.getItemInHand(hand);
        if (this.isConverting()) {
            return InteractionResult.FAIL;
        }
        if (!this.isChorus() && stack.getItem() == Items.CHORUS_FRUIT) {
            this.conversionTicks = 40;
            this.setConverting(true);
            if (!playerIn.isCreative()) {
                stack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        } else if (this.isChorus() && stack.getItem() == Items.WARPED_FUNGUS) {
            this.conversionTicks = 40;
            this.setConverting(true);
            if (!playerIn.isCreative()) {
                stack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        } else {
            return super.mobInteract(playerIn, hand);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.attackTimer > 0) {
            --this.attackTimer;
        }

        if (this.isAlive()) {
            if (this.isConverting() && this.conversionTicks > 0) {
                this.conversionTicks--;
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2D);
                if (this.conversionTicks == 0) {
                    this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
                    this.setChorus(!this.isChorus());
                    this.setConverting(false);
                }
            }
        }

        // This slows the falling speed
        Vec3 vector3d = this.getDeltaMovement();
        if (!this.onGround && vector3d.y < 0.0D) {
            this.setDeltaMovement(vector3d.multiply(1.0D, 0.6D, 1.0D));
        }
    }

    public boolean doHurtTarget(Entity entityIn) {
        this.attackTimer = 10;
        this.level.broadcastEntityEvent(this, (byte) 4);
        float f = this.getAttackDamage();
        float f1 = (int) f > 0 ? f / 2.0F + (float) this.random.nextInt((int) f) : f;
        float f2 = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        boolean flag = entityIn.hurt(DamageSource.mobAttack(this), f1);
        if (flag) {
            ((LivingEntity) entityIn).knockback(f2 * 0.5F, Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180F)));
            entityIn.setDeltaMovement(entityIn.getDeltaMovement().multiply(1.0D, 2.5D, 1.0D));
            this.doEnchantDamageEffects(this, entityIn);
        }

        this.playSound(IESoundEvents.WARPBEETLE_HURT.get(), 1.0F, 1.0F);
        return flag;
    }

    private float getAttackDamage() {
        return (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    // EXP POINTS
    @Override
    protected int getExperienceReward(Player player) {
        return 1 + this.level.random.nextInt(4);
    }

    // SOUNDS
    @Override
    protected SoundEvent getAmbientSound() {
        return IESoundEvents.WARPBEETLE_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return IESoundEvents.WARPBEETLE_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return IESoundEvents.WARPBEETLE_HURT.get();
    }

    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }
}
