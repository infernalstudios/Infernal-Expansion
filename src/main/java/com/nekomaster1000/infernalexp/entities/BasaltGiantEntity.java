package com.nekomaster1000.infernalexp.entities;

import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;
import com.nekomaster1000.infernalexp.init.IESoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import javax.annotation.Nullable;
import java.util.UUID;

public class BasaltGiantEntity extends CreatureEntity implements IEntityAdditionalSpawnData, IAngerable {

//    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.BASALT, Items.POLISHED_BASALT);
	private static final RangedInteger RANGED_INT = TickRangeConverter.convertRange(20, 39);
	private int attackTimer;
	private int angerTime;
	private UUID angerTarget;

	// Constant values for entity scaling
	private static final float BASE_ENTITY_HEIGHT = 5.0F;
	private static final float MIN_ENTITY_HEIGHT = 4.0F;
	private static final float MAX_ENTITY_HEIGHT = 6.0F;

	private static final DataParameter<Float> GIANT_SIZE = EntityDataManager.createKey(BasaltGiantEntity.class, DataSerializers.FLOAT);

	public BasaltGiantEntity(EntityType<? extends BasaltGiantEntity> type, World worldIn) {
		super(type, worldIn);
		this.stepHeight = 2.0f;
	}

//    public BasaltGiantEntity(EntityType<? extends BasaltGiantEntity> type, World worldIn, float size) {
//        super(type, worldIn);
//        if (size != 1)
//            this.dataManager.set(GIANT_SIZE, size);
//    }

	@Nullable
	@Override
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {

		// Get a random size scale value resulting in a height between the MIN and MAX
		// values specified above
		float size = rand.nextFloat();
		size /= BASE_ENTITY_HEIGHT / (MAX_ENTITY_HEIGHT - MIN_ENTITY_HEIGHT);
		size += MIN_ENTITY_HEIGHT / BASE_ENTITY_HEIGHT;
		this.setGiantSize(size);

		return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	// ATTRIBUTES
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 56.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 12.0D).createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 2.0D).createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 30.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.45D);
	}

	@Override
	public void livingTick() {
		super.livingTick();
		if (this.attackTimer > 0) {
			--this.attackTimer;
		}
	}

	@Override
	protected void registerData() {
		super.registerData();
		dataManager.register(GIANT_SIZE, 1.0F);
	}

	public void setGiantSize(float size) {
		getDataManager().set(GIANT_SIZE, size);
		recenterBoundingBox();
		recalculateSize();
	}

	public float getGiantSize() {
		return this.dataManager.get(GIANT_SIZE);
	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putFloat("Size", getGiantSize());
	}

	public void readAdditional(CompoundNBT compound) {
		setGiantSize(compound.getFloat("Size"));
		super.readAdditional(compound);
	}

	@Override
	public void recalculateSize() {
		super.recalculateSize();
		setPosition(getPosX(), getPosY(), getPosZ());
	}

	@Override
	public EntitySize getSize(Pose poseIn) {
		return super.getSize(poseIn).scale(getGiantSize());
	}

	@Override
	public void notifyDataManagerChange(DataParameter<?> key) {
		if (GIANT_SIZE.equals(key)) {
			recalculateSize();
		}

		super.notifyDataManagerChange(key);
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

	@OnlyIn(Dist.CLIENT)
	public int getAttackTimer() {
		return this.attackTimer;
	}

	public boolean attackEntityAsMob(Entity entityIn) {
		this.attackTimer = 10;
		this.world.setEntityState(this, (byte) 4);
		float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
		float f1 = (int) f > 0 ? f / 2.0F + (float) this.rand.nextInt((int) f) : f;
		float f2 = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);

		if (entityIn instanceof PlayerEntity && ((PlayerEntity) entityIn).getActiveItemStack().isShield((PlayerEntity) entityIn)) {
			attackFling(entityIn, f2 * 3, 2.0);
			entityIn.velocityChanged = true;
		}

		boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f1);

		if (flag) {
			attackFling(entityIn, f2, 0.6);
		}

		this.playSound(IESoundEvents.BASALT_GIANT_HURT.get(), 1.0F, 1.0F);
		return flag;
	}

	private void attackFling(Entity entityIn, float f2, double height) {
		((LivingEntity) entityIn).applyKnockback(f2, MathHelper.sin(this.rotationYaw * ((float) Math.PI / 180F)), -MathHelper.cos(this.rotationYaw * ((float) Math.PI / 180F)));
		entityIn.setMotion(entityIn.getMotion().add(0.0D, height, 0.0D));
		this.applyEnchantments(this, entityIn);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (source.getImmediateSource() instanceof AbstractArrowEntity) {
			return false;
		}
		return super.attackEntityFrom(source, amount);
	}

	// ---

	// BEHAVIOUR
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 0.6D, true));
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 0.5d));
		this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8.0f));
		this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
//        this.goalSelector.addGoal(5, new TemptGoal(this, 0.6D, TEMPTATION_ITEMS, false));

		this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
		if (InfernalExpansionConfig.MobInteractions.SKELETON_ATTACK_GIANT.getBoolean()) {
			this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, SkeletonEntity.class, true, false));
		}
		if (InfernalExpansionConfig.MobInteractions.GIANT_ATTACK_MAGMA_CUBE.getBoolean()) {
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, MagmaCubeEntity.class, true, false));
        }
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeFloat(getGiantSize());
	}

	@Override
	public void readSpawnData(PacketBuffer buffer) {
		this.dataManager.set(GIANT_SIZE, buffer.readFloat());
	}

	// EXP POINTS
	@Override
	protected int getExperiencePoints(PlayerEntity player) {
		return 73;
	}

	// SOUNDS
	@Override
	protected SoundEvent getAmbientSound() {
		return IESoundEvents.BASALT_GIANT_AMBIENT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return IESoundEvents.BASALT_GIANT_DEATH.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return IESoundEvents.BASALT_GIANT_HURT.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
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
