package org.infernalstudios.infernalexp.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import org.infernalstudios.infernalexp.init.IEEffects;
import org.infernalstudios.infernalexp.init.IEItems;
import org.infernalstudios.infernalexp.init.IEShroomloinTypes;
import org.infernalstudios.infernalexp.init.IESoundEvents;
import org.infernalstudios.infernalexp.util.ShroomloinType;

import javax.annotation.Nullable;
import java.util.EnumSet;

/**
 * Looking for ShroomloinType?
 * @see org.infernalstudios.infernalexp.util.ShroomloinType
 */
public class ShroomloinEntity extends CreatureEntity implements IRangedAttackMob {
    private static final DataParameter<String> TYPE = EntityDataManager.createKey(ShroomloinEntity.class, DataSerializers.STRING);
	private static final DataParameter<Integer> STATE = EntityDataManager.createKey(ShroomloinEntity.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> IGNITED = EntityDataManager.createKey(ShroomloinEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> CONVERTING = EntityDataManager.createKey(ShroomloinEntity.class, DataSerializers.BOOLEAN);
	private int lastActiveTime;
	private int timeSinceIgnited;
	private final int fuseTime = 59;
    private int conversionTicks;
    private ShroomloinType predictedType;
    // public static final Ingredient TEMPTATION_ITEMS =
	// Ingredient.fromItems(IEItems.DULLROCKS.get(), Items.MAGMA_CREAM);

	public ShroomloinEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
		super(type, worldIn);
	}

	// ATTRIBUTES
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 24.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.5D)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1.5D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.6D);
	}

	protected void registerData() {
		super.registerData();
		this.dataManager.register(STATE, -1);
		this.dataManager.register(IGNITED, false);
		this.dataManager.register(CONVERTING, false);
		this.dataManager.register(TYPE, IEShroomloinTypes.CRIMSON.getId().toString());
	}

    public boolean isConverting() {
	    return this.dataManager.get(CONVERTING);
    }

    public void setConverting(boolean converting) {
	    this.dataManager.set(CONVERTING, converting);
    }

    public boolean isShaking() {
	    return this.isConverting();
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putString("ShroomloinType", this.getShroomloinType().getId().toString());
        compound.putInt("ShroomloinConversionTime", this.isConverting() ? this.conversionTicks : -1);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
		ShroomloinType type = ShroomloinType.getById(compound.getString("ShroomloinType"));
		if (type == null) {
			type = IEShroomloinTypes.CRIMSON;
		}
        this.setShroomloinType(type);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
	    ResourceLocation biome = worldIn.getBiome(this.getPosition()).getRegistryName();
	    if (reason == SpawnReason.NATURAL) {
            if (ModList.get().isLoaded("byg")) {
                if (biome.equals(new ResourceLocation("byg", "glowstone_gardens"))) {
                    if (rand.nextBoolean()) {
                        this.setShroomloinType(IEShroomloinTypes.SOUL_SHROOM);
                    } else {
                        this.setShroomloinType(IEShroomloinTypes.DEATH_CAP);
                    }
                } else if (biome.equals(new ResourceLocation("byg", "embur_bog"))) {
                    this.setShroomloinType(IEShroomloinTypes.EMBUR);
                } else if (biome.equals(new ResourceLocation("byg", "sythian_torrids"))) {
                    this.setShroomloinType(IEShroomloinTypes.SYTHIAN_FUNGUS);
                }
            }
        }

	    return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public void setShroomloinType(ShroomloinType shroomloinType) {
	    this.dataManager.set(TYPE, shroomloinType.getId().toString());
    }

    public ShroomloinType getShroomloinType() {
	    return ShroomloinType.getById(this.dataManager.get(TYPE));
    }

    public Item getConversionItem() {
	    return this.getShroomloinType().getConversionItem();
    }

    @Override
    protected ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        if (this.isConverting()) {
            return ActionResultType.FAIL;
        }

        ItemStack stack = playerIn.getHeldItem(hand);
        ShroomloinType shroomloinType = ShroomloinType.getByItem(stack.getItem());

        if (shroomloinType == null || shroomloinType.getConversionItem() == null) {
            return super.getEntityInteractionResult(playerIn, hand);
        }

        if (shroomloinType.getId().equals(this.getShroomloinType().getId()) || shroomloinType == IEShroomloinTypes.PIZZA) {
            return ActionResultType.FAIL;
        }

        this.predictedType = shroomloinType;
        this.conversionTicks = 40;
        this.setConverting(true);

        if (!playerIn.isCreative()) {
            stack.shrink(1);
        }

        return ActionResultType.SUCCESS;
    }

    /**
	 * Called to update the entity's position/logic.
	 */
	public void tick() {
		if (this.isAlive()) {
		    if (this.getName().getUnformattedComponentText().equals("pizza")) {
		        if (getShroomloinType() != IEShroomloinTypes.PIZZA && this.predictedType != IEShroomloinTypes.PIZZA) {
                    this.predictedType = IEShroomloinTypes.PIZZA;
                    this.conversionTicks = 40;
                    this.setConverting(true);
                }
            }
		    if (this.isConverting() && this.conversionTicks > 0) {
		        this.conversionTicks--;
		        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2D);
		        if (this.conversionTicks == 0) {
                    this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.6D);
                    this.setShroomloinType(this.predictedType);
		            this.setConverting(false);
                }
            }
			if (this.isPotionActive(IEEffects.INFECTION.get())) {
				this.removeActivePotionEffect(IEEffects.INFECTION.get());
			}
			if (this.isPotionActive(Effects.POISON)) {
				this.removeActivePotionEffect(Effects.POISON);
            }
		}

		this.lastActiveTime = this.timeSinceIgnited;

		int i = this.getShroomloinState();
		if (i > 0 && this.timeSinceIgnited == 0) {
			this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
		}

		this.timeSinceIgnited += i;
		if (this.timeSinceIgnited < 0 || this.timeSinceIgnited >= this.fuseTime || i < 0) {
			this.timeSinceIgnited = 0;
		}

		super.tick();
	}

    // BEHAVIOUR
	@Override
	protected void registerGoals() {
		super.registerGoals();
		// this.goalSelector.addGoal(0, new TemptGoal(this, 0.6D, TEMPTATION_ITEMS,
		// false));
		// this.goalSelector.addGoal(0, new ShroomloinSwellGoal(this));
		this.goalSelector.addGoal(1, new RangedAttackUnInfectedGoal(this, 1, 60, 10));
		this.goalSelector.addGoal(1, new MeleeAttackInfectedGoal(this, 0.6d, true));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 0.5d));
		this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8.0f));
		this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new ShroomloinTargetGoal(this));

//        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, PlayerEntity.class, 10, true, false, this::func_233680_b_));
//?        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
	}

	//

	/**
	 * Returns whether execution should begin. You can also read and cache any state
	 * necessary for execution in this method as well.
	 */

	/*
	 * public boolean shouldExecute() { int i = this.goalOwner.getRevengeTimer();
	 * LivingEntity livingentity = this.goalOwner.getRevengeTarget(); if (i !=
	 * this.revengeTimerOld && livingentity != null) { if (livingentity.getType() ==
	 * EntityType.PLAYER &&
	 * this.goalOwner.world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER)) {
	 * return false; } else { for(Class<?> oclass : this.excludedReinforcementTypes)
	 * { if (oclass.isAssignableFrom(livingentity.getClass())) { return false; } }
	 * 
	 * return this.isSuitableTarget(livingentity, field_220795_a); } } else { return
	 * false; } }
	 * 
	 * public HurtByTargetGoal setCallsForHelp(Class<?>... reinforcementTypes) {
	 * this.entityCallsForHelp = true; this.reinforcementTypes = reinforcementTypes;
	 * return this; }
	 * 
	 */

	@OnlyIn(Dist.CLIENT)
	public float getShroomloinFlashIntensity(float partialTicks) {
		return MathHelper.lerp(partialTicks, (float) this.lastActiveTime, (float) this.timeSinceIgnited) / (float) (this.fuseTime - 2);
	}

	/**
	 * Returns the current state of shroomloin, -1 is idle, 1 is 'in fuse'
	 */
	public int getShroomloinState() {
		return this.dataManager.get(STATE);
	}

	/**
	 * Sets the state of shroomloin, -1 to idle and 1 to be 'in fuse'
	 */
	public void setShroomloinState(int state) {
		this.dataManager.set(STATE, state);
	}

	public void becomeAngryAt(LivingEntity entity) {
		this.setAttackTarget(entity);
	}

//    public boolean isImmuneToFire() {
//    return true;
//    }

	// EXP POINTS
	@Override
	protected int getExperiencePoints(PlayerEntity player) {
		return 1 + this.world.rand.nextInt(4);
	}

    // SOUNDS
	@Override
	protected SoundEvent getAmbientSound() {
		return IESoundEvents.SHROOMLOIN_AMBIENT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return IESoundEvents.SHROOMLOIN_DEATH.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return IESoundEvents.SHROOMLOIN_HURT.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
	}

	@Override
	public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
		Vector3d vector3d = target.getMotion();
		double x = target.getPosX() + vector3d.x - this.getPosX();
		double y = target.getPosYEye() - 1.1 - this.getPosY();
		double z = target.getPosZ() + vector3d.z - this.getPosZ();

		float distance = MathHelper.sqrt(x * x + z * z);

        AscusBombEntity ascusBombEntity = new AscusBombEntity(this.world, this);

        ascusBombEntity.setItem(new ItemStack(IEItems.ASCUS_BOMB.get()));
        ascusBombEntity.rotationPitch -= -20;
        ascusBombEntity.shoot(x, y + (distance * 0.2), z, 0.75f, 8);

        this.setShroomloinState(-1);
        this.world.addEntity(ascusBombEntity);
    }

    static class ShroomloinTargetGoal extends HurtByTargetGoal {

        private final ShroomloinEntity shroomloin;

        public ShroomloinTargetGoal(ShroomloinEntity shroomloin, Class<?>... excludeReinforcementTypes) {
            super(shroomloin, excludeReinforcementTypes);

            this.shroomloin = shroomloin;
        }

        @Override
        public boolean shouldExecute() {
            if (this.shroomloin.isConverting())
                return false;

            return super.shouldExecute();
        }

        @Override
        public boolean shouldContinueExecuting() {
            if (this.shroomloin.isConverting())
                return false;

            return super.shouldContinueExecuting();
        }

    }

    static class MeleeAttackInfectedGoal extends MeleeAttackGoal {

        public MeleeAttackInfectedGoal(CreatureEntity creature, double speedIn, boolean useLongMemory) {
            super(creature, speedIn, useLongMemory);
        }

        @Override
        public boolean shouldExecute() {
            LivingEntity attackTarget = this.attacker.getAttackTarget();
			if (attackTarget != null && !attackTarget.isPotionActive(IEEffects.INFECTION.get())) {
				return false;
			}
			return super.shouldExecute();
		}

		@Override
		public boolean shouldContinueExecuting() {
			LivingEntity attackTarget = this.attacker.getAttackTarget();
			if (attackTarget != null && !attackTarget.isPotionActive(IEEffects.INFECTION.get())) {
				return false;
			}
			return super.shouldContinueExecuting();
		}
	}

	static class RangedAttackUnInfectedGoal extends Goal {
		private final ShroomloinEntity entityHost;
		private final IRangedAttackMob rangedAttackEntityHost;
		private LivingEntity attackTarget;
		private int rangedAttackTime = -1;
		private final double entityMoveSpeed;
		private int seeTime;
		private final int attackIntervalMin;
		private final int maxRangedAttackTime;
		private final float attackRadius;
		private final float maxAttackDistance;

		public RangedAttackUnInfectedGoal(IRangedAttackMob attacker, double movespeed, int maxAttackTime, float maxAttackDistanceIn) {
			this(attacker, movespeed, maxAttackTime, maxAttackTime, maxAttackDistanceIn);
		}

		public RangedAttackUnInfectedGoal(IRangedAttackMob attacker, double movespeed, int p_i1650_4_, int maxAttackTime, float maxAttackDistanceIn) {
			if (!(attacker instanceof LivingEntity)) {
				throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
			} else {
				this.rangedAttackEntityHost = attacker;
				this.entityHost = (ShroomloinEntity) attacker;
				this.entityMoveSpeed = movespeed;
				this.attackIntervalMin = p_i1650_4_;
				this.maxRangedAttackTime = maxAttackTime;
				this.attackRadius = maxAttackDistanceIn;
				this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
				this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
			}
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state
		 * necessary for execution in this method as well.
		 */
		public boolean shouldExecute() {
			LivingEntity attackTarget = this.entityHost.getAttackTarget();
			if (attackTarget != null && attackTarget.isAlive() && !attackTarget.isPotionActive(IEEffects.INFECTION.get())) {
				this.attackTarget = attackTarget;
				return true;
			} else {
				return false;
			}
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean shouldContinueExecuting() {
			return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
		}

		/**
		 * Reset the task's internal state. Called when this task is interrupted by
		 * another one
		 */
		public void resetTask() {
			this.attackTarget = null;
			this.seeTime = 0;
			this.rangedAttackTime = -1;
			this.entityHost.setShroomloinState(-1);
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void tick() {
			double d0 = this.entityHost.getDistanceSq(this.attackTarget.getPosX(), this.attackTarget.getPosY(), this.attackTarget.getPosZ());
			boolean flag = this.entityHost.getEntitySenses().canSee(this.attackTarget);

			if (flag) {
				++this.seeTime;
			} else {
				this.seeTime = 0;
			}

			if (!(d0 > (double) this.maxAttackDistance) && this.seeTime >= 5) {
				this.entityHost.getNavigator().clearPath();
			} else {
				this.entityHost.setShroomloinState(-1);
				this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
			}

			this.entityHost.getLookController().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);
			if (--this.rangedAttackTime == 0) {
				if (!flag) {
					return;
				}

				float f = MathHelper.sqrt(d0) / this.attackRadius;
				float lvt_5_1_ = MathHelper.clamp(f, 0.1F, 1.0F);
				this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.attackTarget, lvt_5_1_);
				this.rangedAttackTime = MathHelper.floor(f * (float) (this.maxRangedAttackTime - this.attackIntervalMin) + (float) this.attackIntervalMin);
			} else if (this.rangedAttackTime < 0) {
				float f2 = MathHelper.sqrt(d0) / this.attackRadius;
				this.rangedAttackTime = MathHelper.floor(f2 * (float) (this.maxRangedAttackTime - this.attackIntervalMin) + (float) this.attackIntervalMin);
			} else {
				this.entityHost.setShroomloinState(1);
			}
		}
	}
}
