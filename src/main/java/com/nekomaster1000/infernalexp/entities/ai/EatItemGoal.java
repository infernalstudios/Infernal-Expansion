package com.nekomaster1000.infernalexp.entities.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.SoundEvents;

import java.util.List;

public class EatItemGoal<T extends ItemEntity> extends Goal {
    protected Path path;
    protected ItemEntity itemInstance;
    protected double eatTime;
    protected boolean eating;

    private final double speed;
    private final CreatureEntity entityIn;
    private final Item eatItem;
    private final double range;
    private final PathNavigator navigation;

    public EatItemGoal(CreatureEntity entityIn, Item itemToEat, double range, double speedIn){
        this.entityIn = entityIn;
        this.eatItem = itemToEat;
        this.range = range;
        this.speed = speedIn;
        this.navigation = entityIn.getNavigator();
    }

    @Override
    public boolean shouldExecute() {
        if (this.itemInstance == null) {
            List<ItemEntity> list = this.entityIn.world.getEntitiesWithinAABB(ItemEntity.class, this.entityIn.getBoundingBox().grow(this.range, 3.0d, this.range));
            for (ItemEntity item : list) {
                if (item.getItem().getItem() == this.eatItem) {
                    this.path = this.navigation.getPathToEntity(item, 0);
                    this.itemInstance = item;
                    return path != null;
                }
            }
        }

        return false;
    }

    @Override
    public void resetTask() {
        this.itemInstance = null;
        this.path = null;
        this.eating = false;
    }

    @Override
    public void startExecuting() {
        this.navigation.setPath(this.path, this.speed);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return (!this.navigation.noPath() || this.eatTime > 0) && this.itemInstance.isAlive();
    }

    @Override
    public void tick() {
        if(this.entityIn.getDistanceSq(itemInstance) < 2.0d) {
            this.navigation.setSpeed(0.0d);
            if (!this.eating) {
                this.eating = true;
                this.eatTime = 30;
            } else {
                if (--this.eatTime <= 0) {
                    this.eating = false;
                    this.itemInstance.remove();
                }
                else if (eatTime % 3 == 0) {
                    this.entityIn.playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.9f, 1.0f);
                }
            }
        }
    }
}
