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
import net.minecraft.core.Vec3i;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;
import org.infernalstudios.infernalexp.init.IESoundEvents;
import org.jetbrains.annotations.NotNull;

public class GlowsilkMothEntity extends AmbientCreature {

    private BlockPos spawnPosition;

    public GlowsilkMothEntity(EntityType<? extends GlowsilkMothEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 6.0D);
    }

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

        if (this.spawnPosition == null || this.random.nextInt(30) == 0 || this.spawnPosition.closerThan(new Vec3i(this.position().x(), this.position().y(), this.position().z()), 2.0D)) {
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

    @Override
    protected float getSoundVolume() {
        return 0.1F;
    }

    @Override
    public float getVoicePitch() {
        return super.getVoicePitch() * 0.95F;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(@NotNull Entity entityIn) {
    }

    @Override
    protected void pushEntities() {
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return !this.isInvulnerableTo(source) && super.hurt(source, amount);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose poseIn, EntityDimensions sizeIn) {
        return sizeIn.height * 0.5F;
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return IESoundEvents.GLOWSILK_MOTH_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return IESoundEvents.GLOWSILK_MOTH_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return IESoundEvents.GLOWSILK_MOTH_DEATH.get();
    }

}


