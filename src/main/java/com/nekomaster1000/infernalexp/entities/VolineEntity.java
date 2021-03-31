package com.nekomaster1000.infernalexp.entities;

import com.nekomaster1000.infernalexp.entities.ai.EatItemsGoal;
import com.nekomaster1000.infernalexp.entities.ai.TargetWithEffectGoal;
import com.nekomaster1000.infernalexp.events.MiscEvents;
import com.nekomaster1000.infernalexp.init.IESoundEvents;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;

public class VolineEntity extends MonsterEntity {

	private static final DataParameter<Float> VOLINE_SIZE = EntityDataManager.createKey(VolineEntity.class, DataSerializers.FLOAT);
	private boolean isEating;

	public VolineEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}

	// ATTRIBUTES
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 16.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D).createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5D);
	}

	@Nullable
	@Override
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		setVolineSize(1 + (world.getRandom().nextFloat() * 0.4F));
		return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	// BEHAVIOUR
	@Override
	protected void registerGoals() {
		super.registerGoals();
		// this.goalSelector.addGoal(0, new TemptGoal(this, 0.6D, TEMPTATION_ITEMS,
		// false));
		this.goalSelector.addGoal(0, new VolineEatItemsGoal(this, MiscEvents.getVolineEatTable(), 32.0D, getAttributeValue(Attributes.MOVEMENT_SPEED) * 2.0D));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, getAttributeValue(Attributes.MOVEMENT_SPEED) * 1.2D, true));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(1, new TargetWithEffectGoal(this, LivingEntity.class, true, false, Effects.FIRE_RESISTANCE, null));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, getAttributeValue(Attributes.MOVEMENT_SPEED)));
		this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, AbstractPiglinEntity.class, 16.0F, getAttributeValue(Attributes.MOVEMENT_SPEED) * 2.0D, getAttributeValue(Attributes.MOVEMENT_SPEED) * 1.5D));
		this.goalSelector.addGoal(5, new PanicGoal(this, getAttributeValue(Attributes.MOVEMENT_SPEED) * 2.0D));
	}

	@Override
	public void livingTick() {
		if (getAttributeValue(Attributes.MOVEMENT_SPEED) <= 0) {

			world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, getPosXRandom(0.5D), getPosY() + 1.6D, getPosZRandom(0.5D), 0, 0.07D, 0);
		}

		super.livingTick();
	}

	@Override
	protected void registerData() {
		super.registerData();
		dataManager.register(VOLINE_SIZE, 1.0F);
	}

	public void setVolineSize(float size) {
		size = Math.min(size, 2.0F);

		dataManager.set(VOLINE_SIZE, size);
		recenterBoundingBox();
		recalculateSize();
		getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5F - ((size - 1.0F) / 2.0F));
	}

	public float getVolineSize() {
		return dataManager.get(VOLINE_SIZE);
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putFloat("Size", getVolineSize());
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		float size = Math.max(compound.getFloat("Size"), 1.0F);

		setVolineSize(size);

		super.readAdditional(compound);
	}

	@Override
	public void recalculateSize() {
		super.recalculateSize();
		setPosition(getPosX(), getPosY(), getPosZ());
	}

	@Override
	public EntitySize getSize(Pose poseIn) {
		return super.getSize(poseIn).scale(0.85F * getVolineSize());
	}

	@Override
	public void notifyDataManagerChange(DataParameter<?> key) {
		if (VOLINE_SIZE.equals(key)) {
			recalculateSize();
		}

		super.notifyDataManagerChange(key);
	}

	// EXP POINTS
	@Override
	protected int getExperiencePoints(PlayerEntity player) {
		return 1 + this.world.rand.nextInt(4);
	}

	// SOUNDS
	@Override
	protected SoundEvent getAmbientSound() {
		return IESoundEvents.VOLINE_AMBIENT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return IESoundEvents.VOLINE_HURT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return IESoundEvents.VOLINE_HURT.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
	}

	public boolean isImmuneToFire() {
		return true;
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isEating() {
		return isEating;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleStatusUpdate(byte id) {
		if (id == 8) {
			isEating = false;
		} else if (id == 9) {
			isEating = true;
		} else {
			super.handleStatusUpdate(id);
		}
	}

	public static class VolineEatItemsGoal extends EatItemsGoal<VolineEntity> {

		private final Map<Item, Map<Item, Integer>> eatItemsMap;

		public VolineEatItemsGoal(VolineEntity entityIn, Map<Item, Map<Item, Integer>> itemsToEat, double range, double speedIn) {
			super(entityIn, itemsToEat.keySet(), range, speedIn);

			this.eatItemsMap = itemsToEat;
		}

		@Override
		public void consumeItem() {
			entityIn.setVolineSize(entityIn.getVolineSize() + 0.2F);

			Item itemReference = itemInstance.getItem().getItem();

			// Super call here so that we can get a reference of the item before the item
			// instance is deleted
			super.consumeItem();

			for (Map.Entry<Item, Integer> item : eatItemsMap.get(itemReference).entrySet()) {
				entityIn.entityDropItem(new ItemStack(item.getKey(), item.getValue()), 1);
			}
		}
	}
}
