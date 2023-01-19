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
import org.infernalstudios.infernalexp.init.IESoundEvents;

import org.infernalstudios.infernalexp.init.IETags;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class EmbodyEntity extends Monster {

    public EmbodyEntity(EntityType<? extends Monster> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        this.randomizeAttributes();
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    // ATTRIBUTES
    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 15.0D).add(Attributes.ATTACK_DAMAGE, 4.0D).add(Attributes.ATTACK_KNOCKBACK, 1.2D);
    }

    protected void randomizeAttributes() {
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getModifiedMovementSpeed());
    }

    // BEHAVIOUR
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.6D, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.5d));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        if (InfernalExpansionConfig.MobInteractions.SKELETON_ATTACK_EMBODY.getBoolean()) {
            this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, AbstractSkeleton.class, true, false));
        }
        if (InfernalExpansionConfig.MobInteractions.EMBODY_ATTACK_PLAYER.getBoolean()) {
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        }
        if (InfernalExpansionConfig.MobInteractions.EMBODY_ATTACK_PIGLIN.getBoolean()) {
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractPiglin.class, true, false));
        }
    }

    protected double getModifiedMovementSpeed() {
        // Change the last value in the next line in order to adjust the range of speed
        // they can vary between
        return ((double) 0.45F + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D) * 0.50D;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getMsgId().equals("fall") && getBlockStateOn().is(IETags.Blocks.EMBODY_FALL_BLOCKS)) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean isSensitiveToWater() {
        return true;
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    // EXP POINTS
    @Override
    protected int getExperienceReward(Player player) {
        return 1 + this.level.random.nextInt(2);
    }

    // SOUNDS
    @Override
    protected SoundEvent getAmbientSound() {
        return IESoundEvents.EMBODY_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return IESoundEvents.EMBODY_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return IESoundEvents.EMBODY_HURT.get();
    }

}
