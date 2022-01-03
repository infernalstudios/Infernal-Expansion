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

package org.infernalstudios.infernalexp.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class ShapeUtil {

    /**
     * Generates an array of BlockPos that creates a circle
     *
     * @param radius Radius of the generated circle
     * @return Returns an ArrayList of BlockPos
     */
    public static List<BlockPos> generateCircle(float radius) {
        List<BlockPos> posList = new ArrayList<>();
        float thetaIncrement = 1 / radius;

        // Uses trigonometry to calculate x, y positions for each point
        for (float theta = 0; theta < 2 * Math.PI; theta += thetaIncrement) {
            posList.add(new BlockPos(Mth.cos(theta) * radius, 0, Mth.sin(theta) * radius));
        }

        return posList;
    }

    /**
     * Generates an array of BlockPos that creates a filled circle
     *
     * @param radius Radius of the generated solid circle
     * @return Returns an ArrayList of BlockPos
     */
    public static List<BlockPos> generateSolidCircle(float radius) {
        List<BlockPos> posList = new ArrayList<>();

        // Checks distance away from the center to see if the point is within the circle
        for (int x = (int) -radius; x < radius; x++) {
            for (int z = (int) -radius; z < radius; z++) {
                if ((x * x) + (z * z) <= (radius * radius)) {
                    posList.add(new BlockPos(x, 0, z));
                }
            }
        }

        return posList;
    }

    /**
     * Generates an array of BlockPos that creates a solid sphere
     *
     * @param radius Radius of the generated solid sphere
     * @return Returns an ArrayList of BlockPos
     */
    public static List<BlockPos> generateSolidSphere(float radius) {
        List<BlockPos> posList = new ArrayList<>();

        // Checks distance away from the center to see if the point is within the circle
        for (int x = (int) -radius; x < radius; x++) {
            for (int y = (int) -radius; y < radius; y++) {
                for (int z = (int) -radius; z < radius; z++) {
                    if ((x * x) + (y * y) + (z * z) <= (radius * radius)) {
                        posList.add(new BlockPos(x, y, z));
                    }
                }
            }
        }

        return posList;
    }

    /**
     * Returns a line of blocks from the startPos to the endPos.
     *
     * @param startPos Start position
     * @param endPos   End position
     */
    public static List<BlockPos> generateLine(BlockPos startPos, BlockPos endPos) {
        List<BlockPos> posList = new ArrayList<>();

        Vec3 vec1 = new Vec3(startPos.getX(), startPos.getY(), startPos.getZ());
        Vec3 vec2 = new Vec3(endPos.getX(), endPos.getY(), endPos.getZ());

        Vec3 diffVec = vec2.subtract(vec1);
        Vec3 incVec = new Vec3((int) diffVec.x / diffVec.length(), (int) diffVec.y / diffVec.length(), (int) diffVec.z / diffVec.length());

        for (int i = 0; i <= (int) diffVec.length(); i++) {
            posList.add(new BlockPos(vec1.x, vec1.y, vec1.z));
            vec1 = vec1.add(incVec);
        }

        return posList;
    }
}
