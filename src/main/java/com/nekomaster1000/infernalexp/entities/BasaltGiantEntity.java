package com.nekomaster1000.infernalexp.entities;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public class BasaltGiantEntity extends CreatureEntity implements IAngerable {
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.BASALT, Items.POLISHED_BASALT);
    private static final RangedInteger RANGED_INT = TickRangeConverter.convertRange(20, 39);
    private int angerTime;
    private UUID angerTarget;


    public BasaltGiantEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
    }

    //ATTRIBUTES
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 50.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 30.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.15D);
    }

    //BEHAVIOUR
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new TemptGoal(this, 0.6D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 0.5d));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
    }

    //EXP POINTS
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
        this.setAngerTime(RANGED_INT.func_233018_a_(this.rand));
    }
}
