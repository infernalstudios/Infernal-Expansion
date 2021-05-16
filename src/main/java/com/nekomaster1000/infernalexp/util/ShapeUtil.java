package com.nekomaster1000.infernalexp.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class ShapeUtil {

    /**
     * Generates an array of BlockPos that creates a circle
     * @param radius Radius of the generated circle
     * @return Returns an ArrayList of BlockPos
     */
    public static List<BlockPos> generateCircle(float radius) {
        List<BlockPos> posList = new ArrayList<>();
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

        Vector3d vec1 = new Vector3d(startPos.getX(), startPos.getY(), startPos.getZ());
        Vector3d vec2 = new Vector3d(endPos.getX(), endPos.getY(), endPos.getZ());

        Vector3d diffVec = vec2.subtract(vec1);
        Vector3d incVec = new Vector3d((int) diffVec.x / diffVec.length(), (int) diffVec.y / diffVec.length(), (int) diffVec.z / diffVec.length());

        for (int i = 0; i <= (int) diffVec.length(); i++) {
            posList.add(new BlockPos(vec1.x, vec1.y, vec1.z));
            vec1 = vec1.add(incVec);
        }

        return posList;
    }
}
