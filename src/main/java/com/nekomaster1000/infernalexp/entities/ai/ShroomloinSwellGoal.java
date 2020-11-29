package com.nekomaster1000.infernalexp.entities.ai;

import com.nekomaster1000.infernalexp.entities.ShroomloinEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class ShroomloinSwellGoal extends Goal{
    private final ShroomloinEntity swellingShroomloin;
    private LivingEntity shroomloinAttackTarget;

    public ShroomloinSwellGoal(ShroomloinEntity entityshroomloinIn) {
        this.swellingShroomloin = entityshroomloinIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        LivingEntity livingentity = this.swellingShroomloin.getAttackTarget();
        return this.swellingShroomloin.getShroomloinState() > 0 || livingentity != null && this.swellingShroomloin.getDistanceSq(livingentity) < 9.0D;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.swellingShroomloin.getNavigator().clearPath();
        this.shroomloinAttackTarget = this.swellingShroomloin.getAttackTarget();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.shroomloinAttackTarget = null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (this.shroomloinAttackTarget == null) {
            this.swellingShroomloin.setShroomloinState(-1);
        } else if (this.swellingShroomloin.getDistanceSq(this.shroomloinAttackTarget) > 49.0D) {
            this.swellingShroomloin.setShroomloinState(-1);
        } else if (!this.swellingShroomloin.getEntitySenses().canSee(this.shroomloinAttackTarget)) {
            this.swellingShroomloin.setShroomloinState(-1);
        } else {
            this.swellingShroomloin.setShroomloinState(1);
        }
    }
}
