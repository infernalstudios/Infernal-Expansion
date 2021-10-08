/*
 * Copyright 2021 Infernal Studios
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
import net.minecraft.util.Mth;
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
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;
import org.infernalstudios.infernalexp.entities.ai.TargetWithEffectGoal;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IEEffects;
import org.infernalstudios.infernalexp.init.IEEntityTypes;
import org.infernalstudios.infernalexp.init.IESoundEvents;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

// Extends AnimalEntity and implements IFlyingAnimal like BeeEntity class
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

    // createMobAttributes ---> registerAttributes()
    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 32.0D).add(Attributes.ATTACK_DAMAGE, 1.0D).add(Attributes.FLYING_SPEED, 0.6D)
            // Required for flying entity, doesn't seem to affect actual movement speed
            .add(Attributes.MOVEMENT_SPEED, 0.5D);
        // Turning this up makes them bounce on the ground like crazy, how do we fix
        // that?
    }

    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob parent) {
        GlowsquitoEntity glowsquitoEntity = IEEntityTypes.GLOWSQUITO.get().create(world);
        glowsquitoEntity.setBred(true);

        return glowsquitoEntity;
    }

    public boolean isFood(ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BRED, false);
    }

    public boolean getBred() {
        return this.entityData.get(BRED);
    }

    public void setBred(boolean isBred) {
        this.entityData.set(BRED, isBred);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Bred", this.getBred());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setBred(compound.getBoolean("Bred"));
    }

    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return this.isBaby() ? sizeIn.height * 0.35F : sizeIn.height * 0.72F;
    }

    @Override
    protected void doPush(Entity entityIn) {
        super.doPush(entityIn);
        if (!this.isBaby() && entityIn instanceof LivingEntity && !(entityIn instanceof GlowsquitoEntity)) {
            ((LivingEntity) entityIn).addEffect(new MobEffectInstance(IEEffects.LUMINOUS.get(), 200));
        }
    }

    protected PathNavigation createNavigation(Level worldIn) {
        FlyingPathNavigation flyingpathnavigator = new FlyingPathNavigation(this, worldIn) {
            public boolean isStableDestination(BlockPos pos) {
                return !this.level.getBlockState(pos.below()).isAir();
            }

            public void tick() {
                super.tick();
            }
        };
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(false);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
    }

    // Entity won't take fall damage
    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    public boolean isFlying() {
        return !this.onGround;
    }

    static class LookAroundGoal extends Goal {
        private final GlowsquitoEntity parentEntity;

        public LookAroundGoal(GlowsquitoEntity ghast) {
            this.parentEntity = ghast;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state
         * necessary for execution in this method as well.
         */
        public boolean canUse() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            Vec3 vector3d = this.parentEntity.getDeltaMovement();
            this.parentEntity.setYRot(-((float) Mth.atan2(vector3d.x, vector3d.z)) * (180F / (float) Math.PI));
            this.parentEntity.yBodyRot = this.parentEntity.getYRot();
        }
    }

    static class MoveHelperController extends MoveControl {
        private final GlowsquitoEntity parentEntity;
        private int courseChangeCooldown;

        public MoveHelperController(GlowsquitoEntity ghast) {
            super(ghast);
            this.parentEntity = ghast;
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                if (this.courseChangeCooldown-- <= 0) {
                    this.courseChangeCooldown += this.parentEntity.getRandom().nextInt(5) + 2;
                    Vec3 vector3d = new Vec3(this.wantedX - this.parentEntity.getX(), this.wantedY - this.parentEntity.getY(), this.wantedZ - this.parentEntity.getZ());
                    double d0 = vector3d.length();
                    vector3d = vector3d.normalize();
                    if (this.canReach(vector3d, Mth.ceil(d0))) {
                        this.parentEntity.setDeltaMovement(this.parentEntity.getDeltaMovement().add(vector3d.scale(0.1D)));
                    } else {
                        this.operation = MoveControl.Operation.WAIT;
                    }
                }

            }
        }

        private boolean canReach(Vec3 p_220673_1_, int p_220673_2_) {
            AABB axisalignedbb = this.parentEntity.getBoundingBox();

            for (int i = 1; i < p_220673_2_; ++i) {
                axisalignedbb = axisalignedbb.move(p_220673_1_);
                if (!this.parentEntity.level.noCollision(this.parentEntity, axisalignedbb)) {
                    return false;
                }
            }

            return true;
        }
    }

    static class RandomFlyGoal extends Goal {
        private final GlowsquitoEntity parentEntity;

        public RandomFlyGoal(GlowsquitoEntity glowsquito) {
            this.parentEntity = glowsquito;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state
         * necessary for execution in this method as well.
         */
        public boolean canUse() {
            MoveControl movementcontroller = this.parentEntity.getMoveControl();
            if (!movementcontroller.hasWanted()) {
                return true;
            } else {
                double d0 = movementcontroller.getWantedX() - this.parentEntity.getX();
                double d1 = movementcontroller.getWantedY() - this.parentEntity.getY();
                double d2 = movementcontroller.getWantedZ() - this.parentEntity.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return false;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            Random random = this.parentEntity.getRandom();
            double d0 = this.parentEntity.getX() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = this.parentEntity.getY() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d2 = this.parentEntity.getZ() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.parentEntity.getMoveControl().setWantedPosition(d0, d1, d2, 1.0D);
        }
    }

    // Simple goal for wandering around. Modified from Vanilla's BeeEntity
    // WanderGoal subclass
    class WanderGoal extends Goal {
        WanderGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state
         * necessary for execution in this method as well.
         */
        public boolean canUse() {
            return GlowsquitoEntity.this.navigation.isDone() && GlowsquitoEntity.this.random.nextInt(10) == 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return GlowsquitoEntity.this.navigation.isInProgress();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            Vec3 vector3d = this.getRandomLocation();
            if (vector3d != null) {
                GlowsquitoEntity.this.navigation.moveTo(GlowsquitoEntity.this.navigation.createPath(new BlockPos(vector3d), 1), 1.0D);
            }

        }

        @Nullable
        private Vec3 getRandomLocation() {
            Vec3 vector3d;
            vector3d = GlowsquitoEntity.this.getViewVector(0.0F);

            // I think should work similarly to how it did in 1.16.5.
            // RandomPos got a major rework, and it's hard to tell what anything does without any parameter mappings
            Vec3 vector3d2 = AirRandomPos.getPosTowards(GlowsquitoEntity.this, 8, 7, 2, vector3d, ((float) Math.PI / 2F));
            return vector3d2 != null ? vector3d2 : LandRandomPos.getPosTowards(GlowsquitoEntity.this, 8, 4, vector3d);
        }
    }

    // GOALS
    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.6D, true));

        this.eatGrassGoal = new EatBlockGoal(this);

        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 0.8D, true));
        this.goalSelector.addGoal(1, new MoveTowardsTargetGoal(this, 0.8D, 32.0F));
        // this.goalSelector.addGoal(5, new GlowsquitoEntity.RandomFlyGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 0.8d));
        this.goalSelector.addGoal(3, new TemptGoal(this, 0.8d, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(8, new GlowsquitoEntity.WanderGoal());
        // this.goalSelector.addGoal(7, new GlowsquitoEntity.LookAroundGoal(this));
        // this.goalSelector.addGoal(5, this.eatGrassGoal);
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        if (InfernalExpansionConfig.MobInteractions.GLOWSQUITO_ATTACK_LUMINOUS.getBoolean()) {
            this.targetSelector.addGoal(1, new TargetWithEffectGoal(this, LivingEntity.class, true, false, IEEffects.LUMINOUS.get(), GlowsquitoEntity.class));
        }
        if (InfernalExpansionConfig.MobInteractions.GLOWSQUITO_ATTACK_DWARF.getBoolean()) {
            this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, BlackstoneDwarfEntity.class, true));
        }
    }

    public boolean fireImmune() {
        return true;
    }

    @Override
    protected int getExperienceReward(Player player) {
        return 1 + this.level.random.nextInt(4);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return IESoundEvents.GLOWSQUITO_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return IESoundEvents.GLOWSQUITO_HURT.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
    }

    public boolean doHurtTarget(Entity entityIn) {
        if (!super.doHurtTarget(entityIn)) {
            return false;
        } else {
            if (entityIn instanceof LivingEntity) {
                ((LivingEntity) entityIn).addEffect(new MobEffectInstance(IEEffects.LUMINOUS.get(), 600, 0, true, true)); // 30s
                ((LivingEntity) entityIn).addEffect(new MobEffectInstance(MobEffects.POISON, 100)); // 5s
            }

            return true;
        }
    }

    @Override
    protected void customServerAiStep() {
        this.hogTimer = this.eatGrassGoal.getEatAnimationTick();
        super.customServerAiStep();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level.isClientSide) {
            this.hogTimer = Math.max(0, this.hogTimer - 1);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 10) {
            this.hogTimer = 40;
        } else {
            super.handleEntityEvent(id);
        }
    }

}
