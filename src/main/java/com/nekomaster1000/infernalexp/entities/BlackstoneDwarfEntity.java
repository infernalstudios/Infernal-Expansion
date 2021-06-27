package com.nekomaster1000.infernalexp.entities;

import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;
import com.nekomaster1000.infernalexp.init.IESoundEvents;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.UUID;

//import net.minecraft.entity.projectile.ArrowEntity;

public class BlackstoneDwarfEntity extends CreatureEntity implements IAngerable {
	private static final RangedInteger RANGED_INT = TickRangeConverter.convertRange(20, 39);
	private int attackTimer;
	private int angerTime;
	private UUID angerTarget;

	public BlackstoneDwarfEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
		super(type, worldIn);
	}

	// ATTRIBUTES
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.func_233666_p_()
            .createMutableAttribute(Attributes.MAX_HEALTH, 40.0D)
            .createMutableAttribute(Attributes.ATTACK_DAMAGE, 10.0D)
            .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 2.0D)
            .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 2.0D)
            .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.40D);
		// .createMutableAttribute(Attributes.FOLLOW_RANGE, 20.0D);
	}

	// ---
	// Retaliating
	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 4) {
			this.attackTimer = 10;
			this.playSound(IESoundEvents.BASALT_GIANT_DEATH.get(), 1.0F, 1.0F);
		} else {
			super.handleStatusUpdate(id);
		}

	}

	public void livingTick() {
		super.livingTick();
		if (this.attackTimer > 0) {
			--this.attackTimer;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public int getAttackTimer() {
		return this.attackTimer;
	}

	public boolean attackEntityAsMob(Entity entityIn) {
		this.attackTimer = 10;
		this.world.setEntityState(this, (byte) 4);
        boolean disableShield = false;
		float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
		float f1 = (int) f > 0 ? f / 2.0F + (float) this.rand.nextInt((int) f) : f;
		float f2 = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);

		if (entityIn instanceof PlayerEntity && ((PlayerEntity) entityIn).getActiveItemStack().isShield((PlayerEntity) entityIn)) {
			attackFling(entityIn, f2 * 3, 2.0);
			entityIn.velocityChanged = true;
			disableShield = true;
		}

		boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f1);

		if (flag) {
			attackFling(entityIn, f2, 0.6);
		}
		if (disableShield) {
		    ((PlayerEntity) entityIn).disableShield(true);
        }

		this.playSound(IESoundEvents.BASALT_GIANT_HURT.get(), 1.0F, 1.0F);
		return flag;
	}

	private void attackFling(Entity entityIn, float f2, double height) {
		((LivingEntity) entityIn).applyKnockback(f2, MathHelper.sin(this.rotationYaw * ((float) Math.PI / 180F)), -MathHelper.cos(this.rotationYaw * ((float) Math.PI / 180F)));
		entityIn.setMotion(entityIn.getMotion().add(0.0D, height, 0.0D));
		this.applyEnchantments(this, entityIn);
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
		this.goalSelector.addGoal(1, new WaterAvoidingRandomWalkingGoal(this, 0.5d));
		this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8.0f));
		this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        if (InfernalExpansionConfig.MobInteractions.DWARF_ATTACK_PIGLIN.getBoolean()) {
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractPiglinEntity.class, true, false));
        }
        if (InfernalExpansionConfig.MobInteractions.DWARF_ATTACK_ZOMBIE_PIGLIN.getBoolean()) {
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, ZombifiedPiglinEntity.class, true, false));
        }
        if (InfernalExpansionConfig.MobInteractions.DWARF_ATTACK_PLAYER.getBoolean()) {
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        }
        if (InfernalExpansionConfig.MobInteractions.GLOWSQUITO_ATTACK_DWARF.getBoolean()) {
            this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, GlowsquitoEntity.class, true));
        }
	}

	// EXP POINTS
	@Override
	protected int getExperiencePoints(PlayerEntity player) {
		return 2 + this.world.rand.nextInt(2);
	}

	public boolean isImmuneToFire() {
		return true;
	}

	@Override
	public int getAngerTime() {
		return this.angerTime;
	}

	@Override
	public void setAngerTime(int time) {
		this.angerTime = time;
	}

	@Nullable
	@Override
	public UUID getAngerTarget() {
		return this.angerTarget;
	}

	@Override
	public void setAngerTarget(@Nullable UUID target) {
		this.angerTarget = target;
	}

	@Override
	public void func_230258_H__() {
		this.setAngerTime(RANGED_INT.getRandomWithinRange(this.rand));
	}
}
