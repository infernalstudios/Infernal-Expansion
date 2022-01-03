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

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class TeleportPanicGoal extends PanicGoal {

    public TeleportPanicGoal(PathfinderMob creature, double speedIn) {
        super(creature, speedIn);
    }

    @Override
    public void start() {
        this.mob.randomTeleport(this.posX, this.posY, this.posZ, true); //Final parameter determines whether or not purple enderman particles appear on teleport
        this.mob.setDeltaMovement(0.0D, 0.0D, 0.0D);
        this.mob.setLastHurtByMob(null);
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    protected boolean findRandomPosition() {
        Vec3 vector3d = DefaultRandomPos.getPos(this.mob, 16, 6);
        if (vector3d == null) {
            return false;
        } else {
            this.posX = vector3d.x;
            this.posY = vector3d.y;
            this.posZ = vector3d.z;

            return true;
        }
    }
}
