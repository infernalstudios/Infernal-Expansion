package com.nekomaster1000.infernalexp.util;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

public class NetherTeleportCommandUtil {

    //TODO: Refactor this to readable code
    
    /**
     * Finds a safe* position in given world around a coordinate.
     * * - Not in block and not in lava. Can be over lava though.
     * Taken from obfuscated Minecraft Forge code (net.minecraft.world.Teleporter), removed nether portal creation logic.
     * @param world World to search in
     * @param pos Position to spread the search from
     * @return Safe position to teleport to.
     */
    
   public static BlockPos getSafePosition(ServerWorld world, BlockPos pos) {
      Direction direction = Direction.getFacingFromAxis(Direction.AxisDirection.POSITIVE, Direction.Axis.X);
      double d0 = -1.0D;
      BlockPos blockpos = null;
      double d1 = -1.0D;
      BlockPos blockpos1 = null;
      WorldBorder worldborder = world.getWorldBorder();
      int i = world.func_234938_ad_() - 1;
      BlockPos.Mutable blockpos$mutable = pos.toMutable();

      for(BlockPos.Mutable blockpos$mutable1 : BlockPos.func_243514_a(pos, 16, Direction.EAST, Direction.SOUTH)) {
         int j = Math.min(i, world.getHeight(Heightmap.Type.MOTION_BLOCKING, blockpos$mutable1.getX(), blockpos$mutable1.getZ()));
         if (worldborder.contains(blockpos$mutable1) && worldborder.contains(blockpos$mutable1.move(direction, 1))) {
            blockpos$mutable1.move(direction.getOpposite(), 1);

            for(int l = j; l >= 0; --l) {
               blockpos$mutable1.setY(l);
               if (world.isAirBlock(blockpos$mutable1)) {
                  int i1;
                  for(i1 = l; l > 0 && world.isAirBlock(blockpos$mutable1.move(Direction.DOWN)); --l) {
                  }

                  if (l + 4 <= i) {
                     int j1 = i1 - l;
                     if (j1 <= 0 || j1 >= 3) {
                        blockpos$mutable1.setY(l);
                        if (NetherTeleportCommandUtil.checkRegionForPlacement(world, blockpos$mutable1, blockpos$mutable, direction, 0)) {
                           double d2 = pos.distanceSq(blockpos$mutable1);
                           if (NetherTeleportCommandUtil.checkRegionForPlacement(world, blockpos$mutable1, blockpos$mutable, direction, -1) && checkRegionForPlacement(world, blockpos$mutable1, blockpos$mutable, direction, 1) && (d0 == -1.0D || d0 > d2)) {
                              d0 = d2;
                              blockpos = blockpos$mutable1.toImmutable();
                           }

                           if (d0 == -1.0D && (d1 == -1.0D || d1 > d2)) {
                              d1 = d2;
                              blockpos1 = blockpos$mutable1.toImmutable();
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (d0 == -1.0D && d1 != -1.0D) {
         blockpos = blockpos1;
         d0 = d1;
      }

      if (d0 == -1.0D) {
         blockpos = (new BlockPos(pos.getX(), MathHelper.clamp(pos.getY(), 70, world.func_234938_ad_() - 10), pos.getZ())).toImmutable();
         if (!worldborder.contains(blockpos)) {
            return null;
         }
      }


      return blockpos;
   }

   private static boolean checkRegionForPlacement(ServerWorld world, BlockPos originalPos, BlockPos.Mutable offsetPos, Direction directionIn, int offsetScale) {
      Direction direction = directionIn.rotateY();

      for(int i = -1; i < 3; ++i) {
         for(int j = -1; j < 4; ++j) {
            offsetPos.setAndOffset(originalPos, directionIn.getXOffset() * i + direction.getXOffset() * offsetScale, j, directionIn.getZOffset() * i + direction.getZOffset() * offsetScale);
            if (j < 0 && !world.getBlockState(offsetPos).getMaterial().isSolid()) {
               return false;
            }

            if (j >= 0 && !world.isAirBlock(offsetPos)) {
               return false;
            }
         }
      }

      return true;
   }
}
