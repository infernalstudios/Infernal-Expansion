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
