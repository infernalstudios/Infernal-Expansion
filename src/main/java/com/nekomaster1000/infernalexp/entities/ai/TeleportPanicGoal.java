package com.nekomaster1000.infernalexp.entities.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.util.math.vector.Vector3d;

public class TeleportPanicGoal extends PanicGoal {

    public TeleportPanicGoal(CreatureEntity creature, double speedIn) {
        super(creature, speedIn);
    }

    @Override
    public void startExecuting() {
        this.creature.attemptTeleport(this.randPosX, this.randPosY, this.randPosZ, true); //Final parameter determines whether or not purple enderman particles appear on teleport
        this.creature.setMotion(0.0D, 0.0D, 0.0D);
        this.creature.setRevengeTarget(null);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return false;
    }

    @Override
    protected boolean findRandomPosition() {
        Vector3d vector3d = RandomPositionGenerator.findRandomTarget(this.creature, 16, 6);
        if (vector3d == null) {
            return false;
        } else {
            this.randPosX = vector3d.x;
            this.randPosY = vector3d.y;
            this.randPosZ = vector3d.z;

            return true;
        }
    }
}
