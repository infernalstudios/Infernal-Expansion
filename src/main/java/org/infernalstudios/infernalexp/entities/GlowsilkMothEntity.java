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

import net.minecraft.world.entity.MobType;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;

import org.infernalstudios.infernalexp.init.IESoundEvents;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

public class GlowsilkMothEntity extends AmbientCreature {
    private BlockPos spawnPosition;

    public GlowsilkMothEntity(EntityType<? extends GlowsilkMothEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    //ATTRIBUTES
    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 6.0D);
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    @Override
    protected float getSoundVolume() {
        return 0.1F;
    }

    /**
     * Gets the pitch of living sounds in living entities.
     */
    @Override
    public float getVoicePitch() {
        return super.getVoicePitch() * 0.95F;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(Entity entityIn) {
    }

    @Override
    protected void pushEntities() {
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        super.tick();
        this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 1.0D, 1.0D));
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.spawnPosition != null && (!this.level.isEmptyBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
            this.spawnPosition = null;
        }

        if (this.spawnPosition == null || this.random.nextInt(30) == 0 || this.spawnPosition.closerThan(this.position(), 2.0D)) {
            this.spawnPosition = new BlockPos(this.getX() + (double) this.random.nextInt(7) - (double) this.random.nextInt(7), this.getY() + (double) this.random.nextInt(6) - (double) this.random.nextInt(2), this.getZ() + (double) this.random.nextInt(7) - (double) this.random.nextInt(7));
        }

        double speed = InfernalExpansionConfig.MobInteractions.GLOWSILK_SPEED.getDouble() * 0.1;

        double d2 = (double) this.spawnPosition.getX() + 0.5D - this.getX();
        double d0 = (double) this.spawnPosition.getY() + 0.1D - this.getY();
        double d1 = (double) this.spawnPosition.getZ() + 0.5D - this.getZ();
        Vec3 vector3d = this.getDeltaMovement();
        Vec3 vector3d1 = vector3d.add((Math.signum(d2) * 0.5D - vector3d.x) * speed, (Math.signum(d0) * 0.7F - vector3d.y) * speed, (Math.signum(d1) * 0.5D - vector3d.z) * speed);
        this.setDeltaMovement(vector3d1);
        float f = (float) (Mth.atan2(vector3d1.z, vector3d1.x) * (double) (180F / (float) Math.PI)) - 90.0F;
        float f1 = Mth.wrapDegrees(f - getYRot());
        this.zza = 0.5F;
        setYRot(getYRot() + f1);
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

    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    protected boolean isMovementNoisy() {
        return false;
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    /**
     * Return whether this entity should NOT trigger a pressure plate or a tripwire.
     */
    @Override
    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        return this.isInvulnerableTo(source) ? false : super.hurt(source, amount);
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return sizeIn.height * 0.5F;
    }
}
