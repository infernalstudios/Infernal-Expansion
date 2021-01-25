package com.nekomaster1000.infernalexp.entities;

import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;
import com.nekomaster1000.infernalexp.util.RegistryHandler;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
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

    //ATTRIBUTES
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 5.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 2.0D);
    }

    protected void func_230273_eI_() {
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getModifiedMovementSpeed());
    }

    //BEHAVIOUR
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.6D, true));
//        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, SkeletonEntity.class, true, false));
        if (InfernalExpansionConfig.skeletonAttackEmbody) {
            this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, AbstractSkeletonEntity.class, true, false));
        }
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractPiglinEntity.class, true, false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 0.5d));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
            }

    protected double getModifiedMovementSpeed() {
        //Change the last value in the next line in order to adjust the range of speed they can vary between
        return ((double)0.45F + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D) * 0.50D;
    }

    public boolean isImmuneToFire() {
        return true;
    }

    public boolean isBurning() {
        return false;
    }

    public boolean isWaterSensitive() { return true;}

    //EXP POINTS
    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 1 + this.world.rand.nextInt(2);
    }

    //SOUNDS
    @Override
    protected SoundEvent getAmbientSound() { return RegistryHandler.embody_ambient; }
    @Override
    protected SoundEvent getDeathSound() { return RegistryHandler.embody_death; }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return RegistryHandler.embody_hurt; }

}