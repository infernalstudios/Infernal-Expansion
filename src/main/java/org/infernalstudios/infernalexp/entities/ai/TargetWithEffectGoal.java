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

package org.infernalstudios.infernalexp.entities.ai;

import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.effect.MobEffect;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Predicate;

/**
 * This goal allows a Mob to target a Player who has a potion effect. The constructor is similar to the basic
 * TargetNearestAttackable goal, but adds the input of an integer representing the ID number of the potion effect that
 * should be targeted.
 */
public class TargetWithEffectGoal extends NearestAttackableTargetGoal<LivingEntity> {
    private final MobEffect effect;
    private final Class<? extends LivingEntity> invalidTarget;

    public TargetWithEffectGoal(Mob goalOwnerIn, Class<LivingEntity> targetClassIn, boolean checkSight, MobEffect effect, @Nullable Class<? extends LivingEntity> invalidTargetClassIn) {
        super(goalOwnerIn, targetClassIn, checkSight);
        this.effect = effect;
        this.invalidTarget = invalidTargetClassIn;
    }

    public TargetWithEffectGoal(Mob goalOwnerIn, Class<LivingEntity> targetClassIn, boolean checkSight, boolean nearbyOnlyIn, MobEffect effect, @Nullable Class<? extends LivingEntity> invalidTargetClassIn) {
        super(goalOwnerIn, targetClassIn, checkSight, nearbyOnlyIn);
        this.effect = effect;
        this.invalidTarget = invalidTargetClassIn;
    }

    public TargetWithEffectGoal(Mob goalOwnerIn, Class<LivingEntity> targetClassIn, int targetChanceIn, boolean checkSight, boolean nearbyOnlyIn, Predicate<LivingEntity> targetPredicate, MobEffect effect, @Nullable Class<? extends LivingEntity> invalidTargetClassIn) {
        super(goalOwnerIn, targetClassIn, targetChanceIn, checkSight, nearbyOnlyIn, targetPredicate);
        this.effect = effect;
        this.invalidTarget = invalidTargetClassIn;
    }

    @Override
    protected boolean canAttack(@Nullable LivingEntity potentialTarget, @NotNull TargetingConditions targetPredicate) {
        if (this.invalidTarget != null && potentialTarget != null && potentialTarget.getClass() == this.invalidTarget) {
            return false;
        }
        return super.canAttack(potentialTarget, targetPredicate) && potentialTarget.hasEffect(this.effect);
    }

    @Override
    protected void findTarget() {
        super.findTarget();
        if (!canAttack(this.target, this.targetConditions)) {
            this.target = null;
        }
    }
}
