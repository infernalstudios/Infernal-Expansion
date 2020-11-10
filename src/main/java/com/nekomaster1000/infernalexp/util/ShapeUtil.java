package com.nekomaster1000.infernalexp.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;

public class ShapeUtil {

    /**
     * Generates an array of BlockPos that creates a circle
     * @param radius Radius of the generated circle
     * @return Returns an ArrayList of BlockPos
     */
    public static ArrayList<BlockPos> generateCircle(float radius) {
        ArrayList<BlockPos> posList = new ArrayList<>();
        float thetaIncrement = 1 / radius;

        // Uses trigonometry to calculate x, y positions for each point
        for (float theta = 0; theta < 2 * Math.PI; theta += thetaIncrement) {
            posList.add(new BlockPos(MathHelper.cos(theta) * radius, 0, MathHelper.sin(theta) * radius));
        }

        return posList;
    }

    /**
     * Generates an array of BlockPos that creates a filled circle
     * @param radius Radius of the generated solid circle
     * @return Returns an ArrayList of BlockPos
     */
    public static ArrayList<BlockPos> generateSolidCircle(float radius) {
        ArrayList<BlockPos> posList = new ArrayList<>();

        // Checks distance away from the center to see if the point is within the circle
        for (int x = (int) -radius; x < radius; x++) {
            for (int z = (int) -radius; z < radius; z++) {
                if (Math.pow(x, 2) + Math.pow(z, 2) <= Math.pow(radius, 2)) {
                    posList.add(new BlockPos(x, 0, z));
                }
            }
        }

        return posList;
    }
}
