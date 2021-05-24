package com.nekomaster1000.infernalexp.entities;

import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;

import com.nekomaster1000.infernalexp.init.IESoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AmbientEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class GlowsilkMothEntity extends AmbientEntity {
    private BlockPos spawnPosition;

    public GlowsilkMothEntity(EntityType<? extends GlowsilkMothEntity> type, World worldIn) {
        super(type, worldIn);
    }

    //ATTRIBUTES
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 6.0D);
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume() {
        return 0.1F;
    }

    /**
     * Gets the pitch of living sounds in living entities.
     */
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.95F;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed() {
        return false;
    }

    protected void collideWithEntity(Entity entityIn) {
    }

    protected void collideWithNearbyEntities() {
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        this.setMotion(this.getMotion().mul(1.0D, 1.0D, 1.0D));
    }

    protected void updateAITasks() {
        super.updateAITasks();
        if (this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
                this.spawnPosition = null;
        }

        if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.withinDistance(this.getPositionVec(), 2.0D)) {
                this.spawnPosition = new BlockPos(this.getPosX() + (double)this.rand.nextInt(7) - (double)this.rand.nextInt(7), this.getPosY() + (double)this.rand.nextInt(6) - (double)this.rand.nextInt(2), this.getPosZ() + (double)this.rand.nextInt(7) - (double)this.rand.nextInt(7));
        }

        	double speed = InfernalExpansionConfig.MobInteractions.GLOWSILK_SPEED.getDouble() * 0.1;

            double d2 = (double)this.spawnPosition.getX() + 0.5D - this.getPosX();
            double d0 = (double)this.spawnPosition.getY() + 0.1D - this.getPosY();
            double d1 = (double)this.spawnPosition.getZ() + 0.5D - this.getPosZ();
            Vector3d vector3d = this.getMotion();
            Vector3d vector3d1 = vector3d.add((Math.signum(d2) * 0.5D - vector3d.x) * speed, (Math.signum(d0) * 0.7F - vector3d.y) * speed, (Math.signum(d1) * 0.5D - vector3d.z) * speed);
            this.setMotion(vector3d1);
            float f = (float)(MathHelper.atan2(vector3d1.z, vector3d1.x) * (double)(180F / (float)Math.PI)) - 90.0F;
            float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
            this.moveForward = 0.5F;
            this.rotationYaw += f1;
    }

    // SOUNDS
    @Override
    protected SoundEvent getAmbientSound() {
        return IESoundEvents.GLOWSILK_MOTH_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return IESoundEvents.GLOWSILK_MOTH_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return IESoundEvents.GLOWSILK_MOTH_HURT.get();
    }


    protected boolean canTriggerWalking() {
        return false;
    }

    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    /**
     * Return whether this entity should NOT trigger a pressure plate or a tripwire.
     */
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return this.isInvulnerableTo(source) ? false : super.attackEntityFrom(source, amount);
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height * 0.5F;
    }
}
