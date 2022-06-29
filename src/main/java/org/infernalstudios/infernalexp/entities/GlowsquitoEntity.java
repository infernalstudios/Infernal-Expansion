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

import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;
import org.infernalstudios.infernalexp.entities.ai.TargetWithEffectGoal;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IEEffects;
import org.infernalstudios.infernalexp.init.IEEntityTypes;
import org.infernalstudios.infernalexp.init.IESoundEvents;

import javax.annotation.Nullable;
import java.util.EnumSet;

// Extends AnimalEntity and implements IFlyingAnimal like BeeEntity class
public class GlowsquitoEntity extends AnimalEntity implements IFlyingAnimal {
    private static final DataParameter<Boolean> BRED = EntityDataManager.createKey(GlowsquitoEntity.class, DataSerializers.BOOLEAN);

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(IEBlocks.SHROOMLIGHT_FUNGUS.get().asItem());

    private EatGrassGoal eatGrassGoal;
    private int hogTimer;

    public GlowsquitoEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveController = new FlyingMovementController(this, 20, true); // Flying entity
        this.setPathPriority(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.WATER_BORDER, 16.0F);
    }

    // func_233666_p_ ---> registerAttributes()
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 32.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D).createMutableAttribute(Attributes.FLYING_SPEED, 0.6D)
            // Required for flying entity, doesn't seem to affect actual movement speed
            .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5D);
        // Turning this up makes them bounce on the ground like crazy, how do we fix
        // that?
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }

    @Override
    public GlowsquitoEntity createChild(ServerWorld world, AgeableEntity parent) {
        GlowsquitoEntity glowsquitoEntity = IEEntityTypes.GLOWSQUITO.get().create(world);
        glowsquitoEntity.setBred(true);

        return glowsquitoEntity;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(BRED, false);
    }

    public boolean getBred() {
        return this.dataManager.get(BRED);
    }

    public void setBred(boolean isBred) {
        this.dataManager.set(BRED, isBred);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Bred", this.getBred());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setBred(compound.getBoolean("Bred"));
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.isChild() ? sizeIn.height * 0.35F : sizeIn.height * 0.72F;
    }

    @Override
    protected void collideWithEntity(Entity entityIn) {
        super.collideWithEntity(entityIn);
        if (!this.isChild() && entityIn instanceof LivingEntity && !(entityIn instanceof GlowsquitoEntity)) {
            ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(IEEffects.LUMINOUS.get(), 200));
        }
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, worldIn) {
            public boolean canEntityStandOnPos(BlockPos pos) {
                return !this.world.getBlockState(pos.down()).isAir();
            }

            public void tick() {
                super.tick();
            }
        };
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanSwim(true);
        flyingpathnavigator.setCanEnterDoors(true);
        return flyingpathnavigator;
    }

    // Entity won't take fall damage
    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    // Simple goal for wandering around. Modified from Vanilla's BeeEntity
    // WanderGoal subclass
    class WanderGoal extends Goal {
        WanderGoal() {
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state
         * necessary for execution in this method as well.
         */
        @Override
        public boolean shouldExecute() {
            return GlowsquitoEntity.this.navigator.noPath() && GlowsquitoEntity.this.rand.nextInt(10) == 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        @Override
        public boolean shouldContinueExecuting() {
            return GlowsquitoEntity.this.navigator.hasPath();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        @Override
        public void startExecuting() {
            Vector3d vector3d = this.getRandomLocation();
            if (vector3d != null) {
                GlowsquitoEntity.this.navigator.setPath(GlowsquitoEntity.this.navigator.getPathToPos(new BlockPos(vector3d), 1), 1.0D);
            }

        }

        @Nullable
        private Vector3d getRandomLocation() {
            Vector3d vector3d;
            vector3d = GlowsquitoEntity.this.getLook(0.0F);

            Vector3d vector3d2 = RandomPositionGenerator.findAirTarget(GlowsquitoEntity.this, 8, 7, vector3d, ((float) Math.PI / 2F), 2, 1);
            return vector3d2 != null ? vector3d2 : RandomPositionGenerator.findGroundTarget(GlowsquitoEntity.this, 8, 4, -2, vector3d, (float) Math.PI / 2F);
        }
    }

    // GOALS
    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.eatGrassGoal = new EatGrassGoal(this);

        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 0.8D, true));
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MoveTowardsTargetGoal(this, 0.8D, 32.0F));
        this.goalSelector.addGoal(2, new BreedGoal(this, 0.8d));
        this.goalSelector.addGoal(3, new TemptGoal(this, 0.8d, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(8, new GlowsquitoEntity.WanderGoal());
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        if (InfernalExpansionConfig.MobInteractions.GLOWSQUITO_ATTACK_LUMINOUS.getBoolean()) {
            this.targetSelector.addGoal(1, new TargetWithEffectGoal(this, LivingEntity.class, true, false, IEEffects.LUMINOUS.get(), GlowsquitoEntity.class));
        }
        if (InfernalExpansionConfig.MobInteractions.GLOWSQUITO_ATTACK_DWARF.getBoolean()) {
            this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, BlackstoneDwarfEntity.class, true));
        }
    }

    @Override
    public boolean isImmuneToFire() {
        return true;
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 1 + this.world.rand.nextInt(4);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return IESoundEvents.GLOWSQUITO_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return IESoundEvents.GLOWSQUITO_HURT.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (!super.attackEntityAsMob(entityIn)) {
            return false;
        } else {
            if (entityIn instanceof LivingEntity) {
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(IEEffects.LUMINOUS.get(), 600, 0, true, true)); // 30s
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.POISON, 100)); // 5s
            }

            return true;
        }
    }

    @Override
    protected void updateAITasks() {
        this.hogTimer = this.eatGrassGoal.getEatingGrassTimer();
        super.updateAITasks();
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.world.isRemote) {
            this.hogTimer = Math.max(0, this.hogTimer - 1);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 10) {
            this.hogTimer = 40;
        } else {
            super.handleStatusUpdate(id);
        }
    }

}
