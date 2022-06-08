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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;
import org.infernalstudios.infernalexp.init.IEEffects;
import org.infernalstudios.infernalexp.init.IESoundEvents;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class BlindsightEntity extends Monster {

    private int jumpDuration;
    private int jumpTicks;
    private Random rand = new Random();

    public BlindsightEntity(EntityType<? extends Monster> type, Level worldIn) {
        super(type, worldIn);
        this.moveControl = new BlindsightEntity.MoveHelperController(this);
    }

    //ATTRIBUTES
    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 24.0D)
            .add(Attributes.ATTACK_DAMAGE, 2.0D)
            .add(Attributes.ATTACK_KNOCKBACK, 1.5D)
            .add(Attributes.MOVEMENT_SPEED, 0.9D)
            .add(Attributes.FOLLOW_RANGE, 18.0D);
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
            this.targetSelector.addGoal(2, new BlindsightEntity.TargetGoal<>(this, Player.class, true, false));
        }
    }

    //EXP POINTS
    @Override
    public int getExperienceReward() {
        return 1 + this.level.random.nextInt(4);
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
        this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    public float getJumpCompletion(float adjustTicks) {
        return jumpDuration == 0 ? 0.0F : ((float) jumpTicks + adjustTicks) / (float) jumpDuration;
    }

    @Override
    public void aiStep() {
        if (jumpTicks != jumpDuration) {
            jumpTicks++;
        } else if (jumpDuration != 0) {
            jumpTicks = 0;
            jumpDuration = 0;
        }

        super.aiStep();
    }

    @Override
    protected void jumpFromGround() {
        jumpDuration = 10;
        jumpTicks = 0;

        //Randomizes jump height to vary how high the Blindsight jumps
        float f = this.getJumpPower() + (rand.nextFloat() * 0.7F);

        //Copied from super.jump(), gives the Blindsight upwards motion
        Vec3 vector3d = this.getDeltaMovement();
        this.setDeltaMovement(vector3d.x, f, vector3d.z);
        if (this.isSprinting()) {
            float f1 = this.getYRot() * ((float) Math.PI / 180F);
            this.setDeltaMovement(this.getDeltaMovement().add((-Mth.sin(f1) * 0.2F), 0.0D, (Mth.cos(f1) * 0.2F)));
        }

        this.hasImpulse = true;
        net.minecraftforge.common.ForgeHooks.onLivingJump(this);

        if (!level.isClientSide) {
            level.broadcastEntityEvent(this, (byte) 1);
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 1) {
            jumpDuration = 10;
            jumpTicks = 0;
        } else {
            super.handleEntityEvent(id);
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
        return SoundEvents.SLIME_JUMP;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source == DamageSource.FALL) {
            return false;
        }
        return super.hurt(source, amount);
    }

    public void push(Entity entityIn) {
        super.push(entityIn);
        if (entityIn instanceof GlowsquitoEntity || entityIn instanceof Player) {
            this.dealDamage((LivingEntity) entityIn);
        }
    }

    protected void dealDamage(LivingEntity entityIn) {
        if (this.isAlive()) {
            if (this.hasLineOfSight(entityIn) && entityIn.hurt(DamageSource.mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE))) {
                entityIn.addEffect(new MobEffectInstance(IEEffects.LUMINOUS.get(), 100, 0, true, true));
                entityIn.addEffect(new MobEffectInstance(MobEffects.JUMP, 100, 1, true, true));
                this.playSound(SoundEvents.SLIME_ATTACK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                this.doEnchantDamageEffects(this, entityIn);
            }
        }

    }

    static class LeapGoal extends Goal {
        private final Mob leaper;
        private LivingEntity leapTarget;
        private final double maxJumpHeight;

        public LeapGoal(Mob leapingEntity, double maxJumpHeightIn) {
            this.leaper = leapingEntity;
            this.leapTarget = leaper.getTarget();
            this.maxJumpHeight = maxJumpHeightIn;
        }

        @Override
        public boolean canUse() {
            if (this.leaper.isVehicle()) {
                return false;
            } else {
                this.leapTarget = this.leaper.getTarget();
                if (this.leapTarget == null || !(this.leapTarget instanceof GlowsquitoEntity)) {
                    return false;
                } else {
                    double d0 = this.leapTarget.getY() - this.leaper.getY();
                    double d1 = this.leapTarget.getX() - this.leaper.getX();
                    double d2 = this.leapTarget.getZ() - this.leaper.getZ();
                    double d3 = Mth.sqrt((float) (d1 * d1 + d2 * d2));
                    if (!(d0 < 1.0D) && !(d0 > this.maxJumpHeight) && !(d3 > 3.0D)) {
                        if (!this.leaper.isOnGround()) {
                            return false;
                        } else {
                            return this.leaper.getRandom().nextInt(5) == 0;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }

        @Override
        public void start() {
            Vec3 vector3d = this.leaper.getDeltaMovement();
            Vec3 vector3d1 = new Vec3(this.leapTarget.getX() - this.leaper.getX(), this.leapTarget.getY() - this.leaper.getY(), this.leapTarget.getZ() - this.leaper.getZ());
            if (vector3d1.lengthSqr() > 1.0E-7D) {
                vector3d1 = vector3d1.normalize().scale(0.9D).add(vector3d.scale(0.2D));
            }

            this.leaper.setDeltaMovement(vector3d1.x, vector3d1.y, vector3d1.z);
        }
    }

    static class TargetGlowsquitoGoal extends NearestAttackableTargetGoal<GlowsquitoEntity> {

        public TargetGlowsquitoGoal(Mob goalOwnerIn, boolean checkSight, boolean nearbyOnlyIn) {
            super(goalOwnerIn, GlowsquitoEntity.class, checkSight, nearbyOnlyIn);
        }

        @Override
        protected AABB getTargetSearchArea(double targetDistance) {
            return this.mob.getBoundingBox().inflate(targetDistance, 4.5D, targetDistance);
        }
    }

    static class TargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

        public TargetGoal(Mob goalOwnerIn, Class<T> targetClassIn, boolean checkSight, boolean nearbyOnlyIn) {
            super(goalOwnerIn, targetClassIn, checkSight, nearbyOnlyIn);
        }

        @Override
        protected boolean canAttack(@Nullable LivingEntity potentialTarget, TargetingConditions targetPredicate) {
            return super.canAttack(potentialTarget, targetPredicate) &&
                (this.mob.distanceToSqr(this.target) <= 10.0F ||
                    (this.mob.distanceToSqr(this.target) > 10.0F &&
                        this.target.hasEffect(IEEffects.LUMINOUS.get())));
        }
    }

    static class HopGoal extends Goal {
        private final BlindsightEntity blindsight;

        public HopGoal(BlindsightEntity blindsightIn) {
            this.blindsight = blindsightIn;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return !this.blindsight.isPassenger() && this.blindsight.getTarget() != null;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            ((BlindsightEntity.MoveHelperController) this.blindsight.getMoveControl()).setSpeed(1.0D);
        }
    }

    public static class MoveHelperController extends MoveControl {
        private float yRot;
        private int jumpDelay;
        private final BlindsightEntity blindsight;

        public MoveHelperController(BlindsightEntity blindsightIn) {
            super(blindsightIn);
            this.blindsight = blindsightIn;
            this.yRot = 180.0F * blindsightIn.getYRot() / (float) Math.PI;
        }

        public void setDirection(float yRotIn) {
            this.yRot = yRotIn;
        }

        public void setSpeed(double speedIn) {
            this.speedModifier = speedIn;
            this.operation = MoveControl.Operation.MOVE_TO;
        }

        public void tick() {
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();
            if (this.operation != MoveControl.Operation.MOVE_TO) {
                this.mob.setZza(0.0F);
            } else {
                this.operation = MoveControl.Operation.WAIT;
                if (this.mob.isOnGround()) {
                    this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.blindsight.getJumpDelay() / 3;

                        this.blindsight.getJumpControl().jump();
                        this.blindsight.playSound(this.blindsight.getJumpSound(), this.blindsight.getSoundVolume(), 1.5F);
                    } else {
                        this.blindsight.xxa = 0.0F;
                        this.blindsight.zza = 0.0F;
                        this.mob.setSpeed(0.0F);
                    }
                } else {
                    this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                }

            }
        }
    }

    static class AttackGoal extends Goal {
        private final BlindsightEntity blindsight;
        private int growTieredTimer;

        public AttackGoal(BlindsightEntity blindsightIn) {
            this.blindsight = blindsightIn;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            LivingEntity livingentity = this.blindsight.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                return this.blindsight.getMoveControl() instanceof BlindsightEntity.MoveHelperController;
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.growTieredTimer = 300;
            super.start();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            LivingEntity livingentity = this.blindsight.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                return --this.growTieredTimer > 0;
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            this.blindsight.lookAt(this.blindsight.getTarget(), 10.0F, 10.0F);
            ((BlindsightEntity.MoveHelperController) this.blindsight.getMoveControl()).setDirection(this.blindsight.getYRot());
        }
    }

    static class FaceRandomGoal extends Goal {
        private final BlindsightEntity blindsight;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public FaceRandomGoal(BlindsightEntity blindsightIn) {
            this.blindsight = blindsightIn;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return this.blindsight.getTarget() == null && (this.blindsight.onGround || this.blindsight.isInWater() || this.blindsight.isInLava() || this.blindsight.hasEffect(MobEffects.LEVITATION)) && this.blindsight.getMoveControl() instanceof BlindsightEntity.MoveHelperController;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = 40 + this.blindsight.getRandom().nextInt(60);
                this.chosenDegrees = (float) this.blindsight.getRandom().nextInt(360);
            }

            ((BlindsightEntity.MoveHelperController) this.blindsight.getMoveControl()).setDirection(this.chosenDegrees);
        }
    }

    static class FloatGoal extends Goal {
        private final BlindsightEntity blindsight;

        public FloatGoal(BlindsightEntity blindsightIn) {
            this.blindsight = blindsightIn;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
            blindsightIn.getNavigation().setCanFloat(true);
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return (this.blindsight.isInWater() || this.blindsight.isInLava()) && this.blindsight.getMoveControl() instanceof BlindsightEntity.MoveHelperController;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (this.blindsight.getRandom().nextFloat() < 0.8F) {
                this.blindsight.getJumpControl().jump();
            }

            ((BlindsightEntity.MoveHelperController) this.blindsight.getMoveControl()).setSpeed(1.2D);
        }
    }
}
