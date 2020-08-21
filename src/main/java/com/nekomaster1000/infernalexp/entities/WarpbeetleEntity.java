package com.nekomaster1000.infernalexp.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WarpbeetleEntity extends CreatureEntity {

        public WarpbeetleEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
                super(type, worldIn);
        }

        public static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.WARPED_FUNGUS, Items.CRIMSON_FUNGUS);


        //ATTRIBUTES
public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
        .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
        .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
        }

//BEHAVIOUR
@Override
protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new TemptGoal(this, 0.6D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 0.5d));
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        }

//EXP POINTS
@Override
protected int getExperiencePoints(PlayerEntity player) {
        return 1 + this.world.rand.nextInt(4);
        }

//SOUNDS
@Override
protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_PIG_AMBIENT; }
@Override
protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_PIG_DEATH; }
@Override
protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PIG_HURT;
        }
@Override
protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
        }

        }
