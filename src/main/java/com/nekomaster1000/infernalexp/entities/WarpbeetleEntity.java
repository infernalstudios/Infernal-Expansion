package com.nekomaster1000.infernalexp.entities;

import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;
import com.nekomaster1000.infernalexp.entities.ai.TeleportPanicGoal;
import com.nekomaster1000.infernalexp.init.IEBlocks;
import com.nekomaster1000.infernalexp.init.IEItems;
import com.nekomaster1000.infernalexp.init.IESoundEvents;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class WarpbeetleEntity extends CreatureEntity {
    private int attackTimer;

	public float shellRotationMultiplier = 0.0F;

	public WarpbeetleEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.CRIMSON_FUNGUS, IEBlocks.CRIMSON_FUNGUS_CAP.get().asItem());

	// ATTRIBUTES
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 8.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.4D)
            .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0F).createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 2.0F);
	}

	// BEHAVIOUR
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 0.6D, true));
		this.goalSelector.addGoal(1, new TeleportPanicGoal(this, 1.4D));
		this.goalSelector.addGoal(2, new PanicGoal(this, 2.0D));
		this.goalSelector.addGoal(3, new SwimGoal(this));
		this.goalSelector.addGoal(4, new TemptGoal(this, 0.6D, TEMPTATION_ITEMS, false));
		this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 8.0f));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.5d));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		if (InfernalExpansionConfig.MobInteractions.SPIDER_ATTACK_WARPBEETLE.getBoolean()) {
            this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, SpiderEntity.class, true, false));
        }
	}

	@Override
	public void livingTick() {
		super.livingTick();

        if (this.attackTimer > 0) {
            --this.attackTimer;
        }

		// This slows the falling speed
		Vector3d vector3d = this.getMotion();
		if (!this.onGround && vector3d.y < 0.0D) {
			this.setMotion(vector3d.mul(1.0D, 0.6D, 1.0D));
		}
	}

    public boolean attackEntityAsMob(Entity entityIn) {
        this.attackTimer = 10;
        this.world.setEntityState(this, (byte) 4);
        float f = this.getAttackDamage();
        float f1 = (int) f > 0 ? f / 2.0F + (float) this.rand.nextInt((int) f) : f;
        float f2 = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f1);
        if (flag) {
            ((LivingEntity) entityIn).applyKnockback(f2 * 0.5F, MathHelper.sin(this.rotationYaw * ((float) Math.PI / 180F)), -MathHelper.cos(this.rotationYaw * ((float) Math.PI / 180F)));
            entityIn.setMotion(entityIn.getMotion().mul(1.0D, 2.5D, 1.0D));
            this.applyEnchantments(this, entityIn);
        }

        this.playSound(IESoundEvents.WARPBEETLE_HURT.get(), 1.0F, 1.0F);
        return flag;
    }

    private float getAttackDamage() {
        return (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

	// EXP POINTS
	@Override
	protected int getExperiencePoints(PlayerEntity player) {
		return 1 + this.world.rand.nextInt(4);
	}

	// SOUNDS
	@Override
	protected SoundEvent getAmbientSound() {
		return IESoundEvents.WARPBEETLE_AMBIENT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return IESoundEvents.WARPBEETLE_DEATH.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return IESoundEvents.WARPBEETLE_HURT.get();
	}

	@Override
	public CreatureAttribute getCreatureAttribute() {
		return CreatureAttribute.ARTHROPOD;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
	}

	@Override
	public boolean onLivingFall(float distance, float damageMultiplier) {
		return false;
	}
}
