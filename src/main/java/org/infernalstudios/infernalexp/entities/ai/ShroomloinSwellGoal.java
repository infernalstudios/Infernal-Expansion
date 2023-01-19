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

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import org.infernalstudios.infernalexp.entities.ShroomloinEntity;

import java.util.EnumSet;

public class ShroomloinSwellGoal extends Goal {

    private final ShroomloinEntity swellingShroomloin;
    private LivingEntity shroomloinAttackTarget;

    public ShroomloinSwellGoal(ShroomloinEntity shroomloinEntity) {
        this.swellingShroomloin = shroomloinEntity;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    @Override
    public boolean canUse() {
        LivingEntity livingentity = this.swellingShroomloin.getTarget();
        return this.swellingShroomloin.getShroomloinState() > 0 || livingentity != null && this.swellingShroomloin.distanceToSqr(livingentity) < 9.0D;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.swellingShroomloin.getNavigation().stop();
        this.shroomloinAttackTarget = this.swellingShroomloin.getTarget();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    @Override
    public void stop() {
        this.shroomloinAttackTarget = null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void tick() {
        if (this.shroomloinAttackTarget == null) {
            this.swellingShroomloin.setShroomloinState(-1);
        } else if (this.swellingShroomloin.distanceToSqr(this.shroomloinAttackTarget) > 49.0D) {
            this.swellingShroomloin.setShroomloinState(-1);
        } else if (!this.swellingShroomloin.getSensing().hasLineOfSight(this.shroomloinAttackTarget)) {
            this.swellingShroomloin.setShroomloinState(-1);
        } else {
            this.swellingShroomloin.setShroomloinState(1);
        }
    }
}
