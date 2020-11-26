package com.nekomaster1000.infernalexp.entities.ai;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.potion.Effect;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class TargetWithEffectGoal extends NearestAttackableTargetGoal {
    private int potionId;

    public TargetWithEffectGoal(MobEntity goalOwnerIn, Class targetClassIn, boolean checkSight, int potionId) {
        super(goalOwnerIn, targetClassIn, checkSight);
        this.potionId = potionId;
    }

    public TargetWithEffectGoal(MobEntity goalOwnerIn, Class targetClassIn, boolean checkSight, boolean nearbyOnlyIn, int potionId) {
        super(goalOwnerIn, targetClassIn, checkSight, nearbyOnlyIn);
        this.potionId = potionId;
    }

    public TargetWithEffectGoal(MobEntity goalOwnerIn, Class targetClassIn, int targetChanceIn, boolean checkSight, boolean nearbyOnlyIn, Predicate targetPredicate, int potionId) {
        super(goalOwnerIn, targetClassIn, targetChanceIn, checkSight, nearbyOnlyIn, targetPredicate);
        this.potionId = potionId;
    }

    @Override
    protected boolean isSuitableTarget(@Nullable LivingEntity potentialTarget, EntityPredicate targetPredicate) {
        if(super.isSuitableTarget(potentialTarget, targetPredicate) && potentialTarget.isPotionActive(Effect.get(this.potionId))){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    protected void findNearestTarget() {
        super.findNearestTarget();
        if(isSuitableTarget(this.nearestTarget, this.targetEntitySelector)) {
            super.findNearestTarget();
        }
        else{
            this.nearestTarget = null;
        }
    }
}
