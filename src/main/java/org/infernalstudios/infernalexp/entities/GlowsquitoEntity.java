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

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;
import org.infernalstudios.infernalexp.entities.ai.TargetWithEffectGoal;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IEEffects;
import org.infernalstudios.infernalexp.init.IEEntityTypes;
import org.infernalstudios.infernalexp.init.IESoundEvents;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class GlowsquitoEntity extends Animal implements FlyingAnimal {
    private static final EntityDataAccessor<Boolean> BRED = SynchedEntityData.defineId(GlowsquitoEntity.class, EntityDataSerializers.BOOLEAN);

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.of(IEBlocks.SHROOMLIGHT_FUNGUS.get().asItem());

    private EatBlockGoal eatGrassGoal;
    private int hogTimer;

    public GlowsquitoEntity(EntityType<? extends Animal> type, Level worldIn) {
        super(type, worldIn);
        this.moveControl = new FlyingMoveControl(this, 20, true); // Flying entity
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 32.0D)
            .add(Attributes.ATTACK_DAMAGE, 1.0D)
            .add(Attributes.FLYING_SPEED, 0.6D)
            .add(Attributes.MOVEMENT_SPEED, 0.5D);
        // Movement speed is required for flying entity, doesn't seem to affect actual movement speed
        // Turning this up makes them bounce on the ground like crazy, how do we fix that?
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.eatGrassGoal = new EatBlockGoal(this);

        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 0.8D, true));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MoveTowardsTargetGoal(this, 0.8D, 32.0F));
        this.goalSelector.addGoal(2, new BreedGoal(this, 0.8d));
        this.goalSelector.addGoal(3, new TemptGoal(this, 0.8d, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(8, new GlowsquitoEntity.WanderGoal());
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        if (InfernalExpansionConfig.MobInteractions.GLOWSQUITO_ATTACK_LUMINOUS.getBoolean()) {
            this.targetSelector.addGoal(1, new TargetWithEffectGoal(this, LivingEntity.class, true, false, IEEffects.LUMINOUS.get(), GlowsquitoEntity.class));
        }
        if (InfernalExpansionConfig.MobInteractions.GLOWSQUITO_ATTACK_DWARF.getBoolean()) {
            this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, BlackstoneDwarfEntity.class, true));
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level.isClientSide) {
            this.hogTimer = Math.max(0, this.hogTimer - 1);
        }
    }

    @Override
    protected void customServerAiStep() {
        this.hogTimer = this.eatGrassGoal.getEatAnimationTick();
        super.customServerAiStep();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleEntityEvent(byte id) {
        if (id == 10) {
            this.hogTimer = 40;
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BRED, false);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setBred(compound.getBoolean("Bred"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Bred", this.getBred());
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel world, @NotNull AgeableMob parent) {
        GlowsquitoEntity glowsquitoEntity = IEEntityTypes.GLOWSQUITO.get().create(world);
        glowsquitoEntity.setBred(true);

        return glowsquitoEntity;
    }

    @Override
    public boolean isFood(@NotNull ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }

    public boolean getBred() {
        return this.entityData.get(BRED);
    }

    public void setBred(boolean isBred) {
        this.entityData.set(BRED, isBred);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose poseIn, @NotNull EntityDimensions sizeIn) {
        return this.isBaby() ? sizeIn.height * 0.35F : sizeIn.height * 0.72F;
    }

    @Override
    protected void doPush(@NotNull Entity entityIn) {
        super.doPush(entityIn);
        if (!this.isBaby() && entityIn instanceof LivingEntity livingEntity && !(entityIn instanceof GlowsquitoEntity)) {
            livingEntity.addEffect(new MobEffectInstance(IEEffects.LUMINOUS.get(), 200));
        }
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level worldIn) {
        FlyingPathNavigation flyingPathNavigation = new FlyingPathNavigation(this, worldIn) {
            @Override
            public boolean isStableDestination(BlockPos pos) {
                return !this.level.getBlockState(pos.below()).isAir();
            }

            @Override
            public void tick() {
                super.tick();
            }
        };
        flyingPathNavigation.setCanOpenDoors(false);
        flyingPathNavigation.setCanFloat(true);
        flyingPathNavigation.setCanPassDoors(true);
        return flyingPathNavigation;
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, @NotNull DamageSource source) {
        return false;
    }

    @Override
    public boolean isFlying() {
        return !this.onGround;
    }


    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    protected int getExperienceReward(@NotNull Player player) {
        return 1 + this.level.random.nextInt(4);
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entityIn) {
        if (!super.doHurtTarget(entityIn)) {
            return false;
        } else {
            if (entityIn instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(IEEffects.LUMINOUS.get(), 600, 0, true, true)); // 30s
                livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 100)); // 5s
            }

            return true;
        }
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return IESoundEvents.GLOWSQUITO_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return IESoundEvents.GLOWSQUITO_DEATH.get();
    }

    /**
     * Simple goal for wandering around. Modified from Vanilla's BeeEntity WanderGoal subclass
     */
    class WanderGoal extends Goal {

        WanderGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return GlowsquitoEntity.this.navigation.isDone() && GlowsquitoEntity.this.random.nextInt(10) == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return GlowsquitoEntity.this.navigation.isInProgress();
        }

        @Override
        public void start() {
            Vec3 randomPos = this.getRandomLocation();
            if (randomPos != null) {
                GlowsquitoEntity.this.navigation.moveTo(GlowsquitoEntity.this.navigation.createPath(new BlockPos(randomPos), 1), 1.0D);
            }

        }

        @Nullable
        private Vec3 getRandomLocation() {
            Vec3 viewVec = GlowsquitoEntity.this.getViewVector(0.0F);

            // I think should work similarly to how it did in 1.16.5.
            // RandomPos got a major rework, and it's hard to tell what anything does without any parameter mappings
            Vec3 randomAirPos = AirRandomPos.getPosTowards(GlowsquitoEntity.this, 8, 7, 2, viewVec, ((float) Math.PI / 2F));
            return randomAirPos != null ? randomAirPos : LandRandomPos.getPosTowards(GlowsquitoEntity.this, 8, 4, viewVec);
        }

    }

}
