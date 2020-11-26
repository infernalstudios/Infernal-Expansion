package com.nekomaster1000.infernalexp.entities;

import com.nekomaster1000.infernalexp.init.ModItems;
import com.nekomaster1000.infernalexp.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class ShroomloinEntity extends MonsterEntity {

   // public static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(ModItems.DULLROCKS.get(), Items.MAGMA_CREAM);

    public ShroomloinEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    //ATTRIBUTES
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 2.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.6D);
    }

    //BEHAVIOUR
    @Override
    protected void registerGoals() {
        super.registerGoals();
        //this.goalSelector.addGoal(0, new TemptGoal(this, 0.6D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.6D, true));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 0.5d));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));

//        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, PlayerEntity.class, 10, true, false, this::func_233680_b_));
//?        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }



    //

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */

    /*
    public boolean shouldExecute() {
        int i = this.goalOwner.getRevengeTimer();
        LivingEntity livingentity = this.goalOwner.getRevengeTarget();
        if (i != this.revengeTimerOld && livingentity != null) {
            if (livingentity.getType() == EntityType.PLAYER && this.goalOwner.world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER)) {
                return false;
            } else {
                for(Class<?> oclass : this.excludedReinforcementTypes) {
                    if (oclass.isAssignableFrom(livingentity.getClass())) {
                        return false;
                    }
                }

                return this.isSuitableTarget(livingentity, field_220795_a);
            }
        } else {
            return false;
        }
    }

    public HurtByTargetGoal setCallsForHelp(Class<?>... reinforcementTypes) {
        this.entityCallsForHelp = true;
        this.reinforcementTypes = reinforcementTypes;
        return this;
    }

    */

    //EXP POINTS
    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 1 + this.world.rand.nextInt(4);
    }

    //SOUNDS
    @Override
    protected SoundEvent getAmbientSound() { return RegistryHandler.voline_ambient; }
    @Override
    protected SoundEvent getDeathSound() { return RegistryHandler.voline_hurt; }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return RegistryHandler.voline_hurt;
    }
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }

//    public boolean isImmuneToFire() {
//    return true;
//    }

}

