package com.nekomaster1000.infernalexp.world.gen.features;

import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.blocks.PlantedQuartzBlock;
import com.nekomaster1000.infernalexp.init.IEBlocks;
import com.nekomaster1000.infernalexp.world.gen.features.config.PlantedQuartzFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class PlantedQuartzFeature extends Feature<PlantedQuartzFeatureConfig> {

    public PlantedQuartzFeature(Codec<PlantedQuartzFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader worldIn, ChunkGenerator generator, Random random, BlockPos pos, PlantedQuartzFeatureConfig config) {
        int i = 0;
        int amount = 15;
        AttachFace face;
        Direction direction = Direction.NORTH;

        int pickFacing = random.nextInt(4);

        if (pickFacing <= 1) {
            face = AttachFace.FLOOR;
        } else if (pickFacing == 2) {
            face = AttachFace.WALL;
            int pickDirection = random.nextInt(4);

            if (pickDirection == 0) {
                direction = Direction.NORTH;
            } else if (pickDirection == 1) {
                direction = Direction.SOUTH;
            } else if (pickDirection == 2) {
                direction = Direction.EAST;
            } else {
                direction = Direction.WEST;
            }
        } else {
            face = AttachFace.CEILING;
        }

        // Attempt to place quartz 128 times
        for (int j = 0; j < 256; j++) {
            // Randomize the location of the next quartz to be placed
            BlockState state;
            if (face == AttachFace.WALL) {
                state = IEBlocks.PLANTED_QUARTZ.get().getDefaultState().with(PlantedQuartzBlock.FACE, face).with(PlantedQuartzBlock.HORIZONTAL_FACING, direction);
            } else {
                state = IEBlocks.PLANTED_QUARTZ.get().getDefaultState().with(PlantedQuartzBlock.FACE, face);
            }
            BlockPos blockpos = pos.add(random.nextInt(10) - random.nextInt(20), random.nextInt(4) - random.nextInt(8), random.nextInt(10) - random.nextInt(20));

            // If it's a valid location, attempt a generation
            if (worldIn.isAirBlock(blockpos) && state.isValidPosition(worldIn, blockpos)) {
                // If there is quartz nearby or the chance to generate passes, generate it
                float chance = random.nextFloat();
                if (findOre(worldIn, blockpos) || chance > config.chanceToFail) {
                    worldIn.setBlockState(blockpos, state, 2);
                    i++;
                }
            }

            // If we have placed the max amount of quartz, then return
            if (i >= amount) {
                return true;
            }
        }

        return false;
    }

    public boolean findOre(ISeedReader world, BlockPos pos) {
        boolean foundOre = false;
        final int radius = 3;
        outerLoop:
        for (int x = pos.getX() - radius; x < pos.getX() + radius; x++) {
            for (int y = pos.getY() - radius; y < pos.getY() + radius; y++) {
                for (int z = pos.getZ() - radius; z < pos.getZ() + radius; z++) {
                    if (world.getBlockState(new BlockPos(x, y, z)).getBlock().equals(Blocks.NETHER_QUARTZ_ORE)) {
                        foundOre = true;
                        break outerLoop;
                    }
                }
            }
        }
        return foundOre;
    }
}
