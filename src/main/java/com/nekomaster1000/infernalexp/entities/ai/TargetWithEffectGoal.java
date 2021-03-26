package com.nekomaster1000.infernalexp.entities.ai;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.potion.Effect;

import javax.annotation.Nullable;
import java.util.function.Predicate;

/**
 * This goal allows a Mob to target a Player who has a potion effect. The constructor is similar to the basic
 * TargetNearestAttackable goal, but adds the input of an integer representing the ID number of the potion effect that
 * should be targeted.
 */
public class TargetWithEffectGoal extends NearestAttackableTargetGoal {
    private final Effect effect;
    private final Class invalidTarget;

    public TargetWithEffectGoal(MobEntity goalOwnerIn, Class targetClassIn, boolean checkSight, Effect effect, @Nullable  Class invalidTargetClassIn) {
        super(goalOwnerIn, targetClassIn, checkSight);
        this.effect = effect;
        this.invalidTarget = invalidTargetClassIn;
    }

    public TargetWithEffectGoal(MobEntity goalOwnerIn, Class targetClassIn, boolean checkSight, boolean nearbyOnlyIn, Effect effect, @Nullable  Class invalidTargetClassIn) {
        super(goalOwnerIn, targetClassIn, checkSight, nearbyOnlyIn);
        this.effect = effect;
		this.invalidTarget = invalidTargetClassIn;
    }

    public TargetWithEffectGoal(MobEntity goalOwnerIn, Class targetClassIn, int targetChanceIn, boolean checkSight, boolean nearbyOnlyIn, Predicate targetPredicate, Effect effect, @Nullable  Class invalidTargetClassIn) {
        super(goalOwnerIn, targetClassIn, targetChanceIn, checkSight, nearbyOnlyIn, targetPredicate);
        this.effect = effect;
		this.invalidTarget = invalidTargetClassIn;
    }

    @Override
    protected boolean isSuitableTarget(@Nullable LivingEntity potentialTarget, EntityPredicate targetPredicate) {
    	if (this.invalidTarget != null && potentialTarget != null && potentialTarget.getClass() == this.invalidTarget) {
    		return false;
		}
        return super.isSuitableTarget(potentialTarget, targetPredicate) && potentialTarget.isPotionActive(this.effect);
    }

    @Override
    protected void findNearestTarget() {
        super.findNearestTarget();
        if(!isSuitableTarget(this.nearestTarget, this.targetEntitySelector)) {
            this.nearestTarget = null;
        }
    }
}
