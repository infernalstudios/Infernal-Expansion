package com.nekomaster1000.infernalexp.entities;

import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;
import com.nekomaster1000.infernalexp.init.IESoundEvents;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EmbodyEntity extends MonsterEntity {

	public EmbodyEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Nullable
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		this.func_230273_eI_();
		return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	// ATTRIBUTES
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 15.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0D).createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1.2D);
	}

	protected void func_230273_eI_() {
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getModifiedMovementSpeed());
	}

	// BEHAVIOUR
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.6D, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 0.5d));
		this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8.0f));
		this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        if (InfernalExpansionConfig.MobInteractions.SKELETON_ATTACK_EMBODY.getBoolean()) {
            this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, AbstractSkeletonEntity.class, true, false));
        }
        if (InfernalExpansionConfig.MobInteractions.EMBODY_ATTACK_PLAYER.getBoolean()) {
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        }
        if (InfernalExpansionConfig.MobInteractions.EMBODY_ATTACK_PIGLIN.getBoolean()) {
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractPiglinEntity.class, true, false));
        }
    }

	protected double getModifiedMovementSpeed() {
		// Change the last value in the next line in order to adjust the range of speed
		// they can vary between
		return ((double) 0.45F + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D) * 0.50D;
	}

	public boolean isImmuneToFire() {
		return true;
	}

	public boolean isBurning() {
		return false;
	}

	public boolean isWaterSensitive() {
		return true;
	}

	@Override
	public CreatureAttribute getCreatureAttribute() {
		return CreatureAttribute.UNDEAD;
	}

	// EXP POINTS
	@Override
	protected int getExperiencePoints(PlayerEntity player) {
		return 1 + this.world.rand.nextInt(2);
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
