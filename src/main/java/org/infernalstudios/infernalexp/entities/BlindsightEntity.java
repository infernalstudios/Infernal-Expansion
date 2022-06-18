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
import org.infernalstudios.infernalexp.init.IEEffects;
import org.infernalstudios.infernalexp.init.IESoundEvents;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class BlindsightEntity extends MonsterEntity {

    private int jumpDuration;
    private int jumpTicks;
    private Random rand = new Random();

    public BlindsightEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveController = new BlindsightEntity.MoveHelperController(this);
    }

    //ATTRIBUTES
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
            .createMutableAttribute(Attributes.MAX_HEALTH, 24.0D)
            .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D)
            .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1.5D)
            .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.9D)
            .createMutableAttribute(Attributes.FOLLOW_RANGE, 18.0D);
    }

    //BEHAVIOUR
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new BlindsightEntity.FloatGoal(this));
        this.goalSelector.addGoal(1, new BlindsightEntity.AttackGoal(this));
        this.goalSelector.addGoal(2, new BlindsightEntity.LeapGoal(this, 6.0D));
        this.goalSelector.addGoal(3, new BlindsightEntity.FaceRandomGoal(this));
        this.goalSelector.addGoal(5, new BlindsightEntity.HopGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        if (InfernalExpansionConfig.MobInteractions.BLINDSIGHT_ATTACK_GLOWSQUITO.getBoolean()) {
            this.targetSelector.addGoal(1, new BlindsightEntity.TargetGlowsquitoGoal(this, true, false));
        }
        if (InfernalExpansionConfig.MobInteractions.BLINDSIGHT_ATTACK_PLAYER.getBoolean()) {
            this.targetSelector.addGoal(2, new BlindsightEntity.TargetGoal<>(this, PlayerEntity.class, true, false));
        }
    }

    //EXP POINTS
    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 1 + this.world.rand.nextInt(4);
    }

    //SOUNDS
    @Override
    protected SoundEvent getAmbientSound() {
        return IESoundEvents.BLINDSIGHT_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return IESoundEvents.BLINDSIGHT_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return IESoundEvents.BLINDSIGHT_HURT.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    public float getJumpCompletion(float adjustTicks) {
        return jumpDuration == 0 ? 0.0F : ((float) jumpTicks + adjustTicks) / (float) jumpDuration;
    }

    @Override
    public void livingTick() {
        if (jumpTicks != jumpDuration) {
            jumpTicks++;
        } else if (jumpDuration != 0) {
            jumpTicks = 0;
            jumpDuration = 0;
        }

        super.livingTick();
    }

    @Override
    protected void jump() {
        jumpDuration = 10;
        jumpTicks = 0;

        //Randomizes jump height to vary how high the Blindsight jumps
        float f = this.getJumpUpwardsMotion() + (rand.nextFloat() * 0.7F);

        //Copied from super.jump(), gives the Blindsight upwards motion
        Vector3d vector3d = this.getMotion();
        this.setMotion(vector3d.x, f, vector3d.z);
        if (this.isSprinting()) {
            float f1 = this.rotationYaw * ((float) Math.PI / 180F);
            this.setMotion(this.getMotion().add((-MathHelper.sin(f1) * 0.2F), 0.0D, (MathHelper.cos(f1) * 0.2F)));
        }

        this.isAirBorne = true;
        net.minecraftforge.common.ForgeHooks.onLivingJump(this);

        if (!world.isRemote) {
            world.setEntityState(this, (byte) 1);
        }
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 1) {
            jumpDuration = 10;
            jumpTicks = 0;
        } else {
            super.handleStatusUpdate(id);
        }
    }

    /*
    public boolean isImmuneToFire() {
        return true;
    }
*/

    protected int getJumpDelay() {
        return (this.rand.nextInt(20) + 10) * 2;
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.ENTITY_SLIME_JUMP;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.FALL) {
            return false;
        }
        return super.attackEntityFrom(source, amount);
    }

    public void applyEntityCollision(Entity entityIn) {
        super.applyEntityCollision(entityIn);
        if (entityIn instanceof GlowsquitoEntity || entityIn instanceof PlayerEntity) {
            this.dealDamage((LivingEntity) entityIn);
        }
    }

    protected void dealDamage(LivingEntity entityIn) {
        if (this.isAlive()) {
            if (this.canEntityBeSeen(entityIn) && entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE))) {
                entityIn.addPotionEffect(new EffectInstance(IEEffects.LUMINOUS.get(), 100, 0, true, true));
                entityIn.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 100, 1, true, true));
                this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                this.applyEnchantments(this, entityIn);
            }
        }

    }

    static class LeapGoal extends Goal {
        private final MobEntity leaper;
        private LivingEntity leapTarget;
        private final double maxJumpHeight;

        public LeapGoal(MobEntity leapingEntity, double maxJumpHeightIn) {
            this.leaper = leapingEntity;
            this.leapTarget = leaper.getAttackTarget();
            this.maxJumpHeight = maxJumpHeightIn;
        }

        @Override
        public boolean shouldExecute() {
            if (this.leaper.isBeingRidden()) {
                return false;
            } else {
                this.leapTarget = this.leaper.getAttackTarget();
                if (this.leapTarget == null || !(this.leapTarget instanceof GlowsquitoEntity)) {
                    return false;
                } else {
                    double d0 = this.leapTarget.getPosY() - this.leaper.getPosY();
                    double d1 = this.leapTarget.getPosX() - this.leaper.getPosX();
                    double d2 = this.leapTarget.getPosZ() - this.leaper.getPosZ();
                    double d3 = MathHelper.sqrt(d1 * d1 + d2 * d2);
                    if (!(d0 < 1.0D) && !(d0 > this.maxJumpHeight) && !(d3 > 3.0D)) {
                        if (!this.leaper.isOnGround()) {
                            return false;
                        } else {
                            return this.leaper.getRNG().nextInt(5) == 0;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }

        @Override
        public void startExecuting() {
            Vector3d vector3d = this.leaper.getMotion();
            Vector3d vector3d1 = new Vector3d(this.leapTarget.getPosX() - this.leaper.getPosX(), this.leapTarget.getPosY() - this.leaper.getPosY(), this.leapTarget.getPosZ() - this.leaper.getPosZ());
            if (vector3d1.lengthSquared() > 1.0E-7D) {
                vector3d1 = vector3d1.normalize().scale(0.9D).add(vector3d.scale(0.2D));
            }

            this.leaper.setMotion(vector3d1.x, vector3d1.y, vector3d1.z);
        }
    }

    static class TargetGlowsquitoGoal extends NearestAttackableTargetGoal<GlowsquitoEntity> {

        public TargetGlowsquitoGoal(MobEntity goalOwnerIn, boolean checkSight, boolean nearbyOnlyIn) {
            super(goalOwnerIn, GlowsquitoEntity.class, checkSight, nearbyOnlyIn);
        }

        @Override
        protected AxisAlignedBB getTargetableArea(double targetDistance) {
            return this.goalOwner.getBoundingBox().grow(targetDistance, 4.5D, targetDistance);
        }
    }

    static class TargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

        public TargetGoal(MobEntity goalOwnerIn, Class<T> targetClassIn, boolean checkSight, boolean nearbyOnlyIn) {
            super(goalOwnerIn, targetClassIn, checkSight, nearbyOnlyIn);
        }

        @Override
        protected boolean isSuitableTarget(@Nullable LivingEntity potentialTarget, EntityPredicate targetPredicate) {
            return super.isSuitableTarget(potentialTarget, targetPredicate) &&
                (this.goalOwner.getDistanceSq(this.nearestTarget) <= 10.0F ||
                    (this.goalOwner.getDistanceSq(this.nearestTarget) > 10.0F &&
                        this.nearestTarget.isPotionActive(IEEffects.LUMINOUS.get())));
        }
    }

    static class HopGoal extends Goal {
        private final BlindsightEntity blindsight;

        public HopGoal(BlindsightEntity blindsightIn) {
            this.blindsight = blindsightIn;
            this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            return !this.blindsight.isPassenger() && this.blindsight.getAttackTarget() != null && this.blindsight.getMoveHelper() instanceof BlindsightEntity.MoveHelperController;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            ((BlindsightEntity.MoveHelperController) this.blindsight.getMoveHelper()).setSpeed(1.0D);
        }
    }

    public static class MoveHelperController extends MovementController {
        private float yRot;
        private int jumpDelay;
        private final BlindsightEntity blindsight;

        public MoveHelperController(BlindsightEntity blindsightIn) {
            super(blindsightIn);
            this.blindsight = blindsightIn;
            this.yRot = 180.0F * blindsightIn.rotationYaw / (float) Math.PI;
        }

        public void setDirection(float yRotIn) {
            this.yRot = yRotIn;
        }

        public void setSpeed(double speedIn) {
            this.speed = speedIn;
            this.action = MovementController.Action.MOVE_TO;
        }

        public void tick() {
            this.mob.rotationYaw = this.limitAngle(this.mob.rotationYaw, this.yRot, 90.0F);
            this.mob.rotationYawHead = this.mob.rotationYaw;
            this.mob.renderYawOffset = this.mob.rotationYaw;
            if (this.action != MovementController.Action.MOVE_TO) {
                this.mob.setMoveForward(0.0F);
            } else {
                this.action = MovementController.Action.WAIT;
                if (this.mob.isOnGround()) {
                    this.mob.setAIMoveSpeed((float) (this.speed * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.blindsight.getJumpDelay() / 3;

                        this.blindsight.getJumpController().setJumping();
                        this.blindsight.playSound(this.blindsight.getJumpSound(), this.blindsight.getSoundVolume(), 1.5F);
                    } else {
                        this.blindsight.moveStrafing = 0.0F;
                        this.blindsight.moveForward = 0.0F;
                        this.mob.setAIMoveSpeed(0.0F);
                    }
                } else {
                    this.mob.setAIMoveSpeed((float) (this.speed * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                }

            }
        }
    }

    static class AttackGoal extends Goal {
        private final BlindsightEntity blindsight;
        private int growTieredTimer;

        public AttackGoal(BlindsightEntity blindsightIn) {
            this.blindsight = blindsightIn;
            this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            LivingEntity livingentity = this.blindsight.getAttackTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                return this.blindsight.getMoveHelper() instanceof BlindsightEntity.MoveHelperController;
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.growTieredTimer = 300;
            super.startExecuting();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return --this.growTieredTimer > 0 && this.shouldExecute();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            this.blindsight.faceEntity(this.blindsight.getAttackTarget(), 10.0F, 10.0F);
            ((BlindsightEntity.MoveHelperController) this.blindsight.getMoveHelper()).setDirection(this.blindsight.rotationYaw);
        }
    }

    static class FaceRandomGoal extends Goal {
        private final BlindsightEntity blindsight;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public FaceRandomGoal(BlindsightEntity blindsightIn) {
            this.blindsight = blindsightIn;
            this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            return this.blindsight.getAttackTarget() == null && (this.blindsight.onGround || this.blindsight.isInWater() || this.blindsight.isInLava() || this.blindsight.isPotionActive(Effects.LEVITATION)) && this.blindsight.getMoveHelper() instanceof BlindsightEntity.MoveHelperController;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = 40 + this.blindsight.getRNG().nextInt(60);
                this.chosenDegrees = (float) this.blindsight.getRNG().nextInt(360);
            }

            ((BlindsightEntity.MoveHelperController) this.blindsight.getMoveHelper()).setDirection(this.chosenDegrees);
        }
    }

    static class FloatGoal extends Goal {
        private final BlindsightEntity blindsight;

        public FloatGoal(BlindsightEntity blindsightIn) {
            this.blindsight = blindsightIn;
            this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
            blindsightIn.getNavigator().setCanSwim(true);
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            return (this.blindsight.isInWater() || this.blindsight.isInLava()) && this.blindsight.getMoveHelper() instanceof BlindsightEntity.MoveHelperController;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (this.blindsight.getRNG().nextFloat() < 0.8F) {
                this.blindsight.getJumpController().setJumping();
            }

            ((BlindsightEntity.MoveHelperController) this.blindsight.getMoveHelper()).setSpeed(1.2D);
        }
    }
}
