package com.nekomaster1000.infernalexp.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class BlindsightEntity extends MonsterEntity {

    public BlindsightEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    //ATTRIBUTES
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1.5D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5D);
    }

    //BEHAVIOUR
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 0.6D, true));
        this.goalSelector.addGoal(1, new BlindsightEntity.LeapGoal(this, 6.0D));
        this.goalSelector.addGoal(2, new RandomWalkingGoal(this, 0.5d));
        this.goalSelector.addGoal(2, new SwimGoal(this));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new BlindsightEntity.TargetGlowsquitoGoal(this, true, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true, false));
    }

    //EXP POINTS
    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 1 + this.world.rand.nextInt(4);
    }

    //SOUNDS
    @Override
    protected SoundEvent getAmbientSound() { return null; }
    @Override
    protected SoundEvent getDeathSound() { return null; }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return null;
    }
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);;
    }

    public boolean isImmuneToFire() {
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(source == DamageSource.FALL){
            return false;
        }
        return super.attackEntityFrom(source, amount);
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
        public boolean shouldExecute(){
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
                    double d3 = MathHelper.sqrt(d1*d1 + d2*d2);
                    if (!(d0 < 1.0D) && !(d0 > this.maxJumpHeight) && !(d3 > 3.0D)){
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

    static class TargetGlowsquitoGoal extends NearestAttackableTargetGoal<GlowsquitoEntity>{

        public TargetGlowsquitoGoal(MobEntity goalOwnerIn, boolean checkSight, boolean nearbyOnlyIn) {
            super(goalOwnerIn, GlowsquitoEntity.class, checkSight, nearbyOnlyIn);
        }

        @Override
        protected AxisAlignedBB getTargetableArea(double targetDistance) {
            return this.goalOwner.getBoundingBox().grow(targetDistance, 4.5D, targetDistance);
        }
    }

}

