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

import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolActions;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;
import org.infernalstudios.infernalexp.init.IESoundEvents;

import javax.annotation.Nullable;
import java.util.UUID;

//import net.minecraft.entity.projectile.ArrowEntity;

public class BlackstoneDwarfEntity extends PathfinderMob implements NeutralMob {
    private static final UniformInt RANGED_INT = TimeUtil.rangeOfSeconds(20, 39);
    private int attackTimer;
    private int angerTime;
    private UUID angerTarget;

    public BlackstoneDwarfEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
    }

    // ATTRIBUTES
    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 40.0D)
            .add(Attributes.ATTACK_DAMAGE, 10.0D)
            .add(Attributes.ATTACK_KNOCKBACK, 2.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 2.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.40D);
        // .createMutableAttribute(Attributes.FOLLOW_RANGE, 20.0D);
    }

    // ---
    // Retaliating
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 4) {
            this.attackTimer = 10;
            this.playSound(IESoundEvents.BASALT_GIANT_DEATH.get(), 1.0F, 1.0F);
        } else {
            super.handleEntityEvent(id);
        }

    }

    public void aiStep() {
        super.aiStep();
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getAttackTimer() {
        return this.attackTimer;
    }

    public boolean doHurtTarget(Entity entityIn) {
        this.attackTimer = 10;
        this.level.broadcastEntityEvent(this, (byte) 4);
        boolean disableShield = false;
        float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1 = (int) f > 0 ? f / 2.0F + (float) this.random.nextInt((int) f) : f;
        float f2 = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);

        if (entityIn instanceof Player && ((Player) entityIn).getUseItem().canPerformAction(ToolActions.SHIELD_BLOCK)) {
            attackFling(entityIn, f2 * 3, 2.0);
            entityIn.hurtMarked = true;
            disableShield = true;
        }

        boolean flag = entityIn.hurt(DamageSource.mobAttack(this), f1);

        if (flag) {
            attackFling(entityIn, f2, 0.6);
        }
        if (disableShield) {
            ((Player) entityIn).disableShield(true);
        }

        this.playSound(IESoundEvents.BASALT_GIANT_HURT.get(), 1.0F, 1.0F);
        return flag;
    }

    private void attackFling(Entity entityIn, float f2, double height) {
        ((LivingEntity) entityIn).knockback(f2, Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180F)));
        entityIn.setDeltaMovement(entityIn.getDeltaMovement().add(0.0D, height, 0.0D));
        this.doEnchantDamageEffects(this, entityIn);
    }

    /*
     * @Override public boolean attackEntityFrom(DamageSource source, float amount)
     * { if(source.getImmediateSource() instanceof ArrowEntity){ return false; }
     * return super.attackEntityFrom(source, amount); }
     */

    // ---

    // BEHAVIOUR
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 0.6D, true));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 0.5d));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        if (InfernalExpansionConfig.MobInteractions.DWARF_ATTACK_PIGLIN.getBoolean()) {
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractPiglin.class, true, false));
        }
        if (InfernalExpansionConfig.MobInteractions.DWARF_ATTACK_ZOMBIE_PIGLIN.getBoolean()) {
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, ZombifiedPiglin.class, true, false));
        }
        if (InfernalExpansionConfig.MobInteractions.DWARF_ATTACK_PLAYER.getBoolean()) {
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        }
        if (InfernalExpansionConfig.MobInteractions.GLOWSQUITO_ATTACK_DWARF.getBoolean()) {
            this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, GlowsquitoEntity.class, true));
        }
    }

    // EXP POINTS
    @Override
    protected int getExperienceReward(Player player) {
        return 2 + this.level.random.nextInt(2);
    }

    public boolean fireImmune() {
        return true;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.angerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int time) {
        this.angerTime = time;
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.angerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID target) {
        this.angerTarget = target;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(RANGED_INT.sample(this.random));
    }
}
