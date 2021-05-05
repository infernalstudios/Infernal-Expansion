package com.nekomaster1000.infernalexp.world.gen.features;

import com.mojang.datafixers.optics.profunctors.ReCartesian;
import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.init.IEBlocks;
import com.nekomaster1000.infernalexp.init.IETags;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class GlowLayerFeature extends Feature<NoFeatureConfig> {

    public GlowLayerFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
        BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable().setPos(pos);
        BlockPos.Mutable mutableBlockPosNeighbors = new BlockPos.Mutable().setPos(mutableBlockPos);
        boolean doExpandedPlacing = isMultipleBiomesInChunk(world, pos, mutableBlockPos);

        IChunk cachedChunk = world.getChunk(mutableBlockPos);
        int minimumRange = doExpandedPlacing ? -12 : 0;
        int maxRange = doExpandedPlacing ? 28 : 16;
        for (int x = minimumRange; x < maxRange; x++) {
            for (int z = minimumRange; z < maxRange; z++) {

                // Only check between top land and sealevel.
                // Prevents glowdust in caves below sealevel and better performance if player removes ceiling of Nether with datapack.
                int maxY = generator.getHeight(pos.getX() + x, pos.getZ() + z, Heightmap.Type.MOTION_BLOCKING);
                for (int y = maxY; y > generator.getSeaLevel(); y--) {
                    mutableBlockPos.setPos(pos).move(x, y, z);

                    // recache chunk if we need to. Faster performance this way when chunk scanning like we are.
                    if (cachedChunk.getPos().x != x >> 4 || cachedChunk.getPos().z != z >> 4) {
                        cachedChunk = world.getChunk(mutableBlockPos);
                    }

                    // Checks for if we are at glowdust sand and moves up to check for air space.
                    BlockState currentBlock = cachedChunk.getBlockState(mutableBlockPos);
                    if (currentBlock.matchesBlock(IEBlocks.GLOWDUST_SAND.get()) &&
                        cachedChunk.getBlockState(mutableBlockPos.move(Direction.UP)).isAir()) {
                        // we are now in the air space above Glowdust sand. Check if any of the 8 blocks around it is glowdust sand
                        boolean validNeighbor = isGlowdustSandNearby(world, mutableBlockPos, mutableBlockPosNeighbors);
                        if (validNeighbor) {
                            world.setBlockState(mutableBlockPos, IEBlocks.GLOWDUST.get().getDefaultState(), 3);
                        }
                    }
                }
            }
        }

        return true;
    }

    private boolean isMultipleBiomesInChunk(ISeedReader world, BlockPos pos, BlockPos.Mutable mutableBlockPos) {
        // check some edges of the chunk for invalid biomes to know when to
        // do better placement that follows biome borders better.
        Biome centerBiome = world.getBiome(mutableBlockPos.setPos(pos).move(8, 0, 8));
        for (int x = 0; x <= 16; x += 8) {
            for (int z = 0; z <= 16; z += 8) {
                if (x != 8 && z != 8) {
                    mutableBlockPos.setPos(pos);
                    mutableBlockPos.move(x, 0, z);

                    // move position back to edge of the chunk we are in instead of next chunk over
                    if (x == 16) mutableBlockPos.move(-1, 0, 0);
                    if (z == 16) mutableBlockPos.move(0, 0, -1);

                    if (centerBiome != world.getBiome(mutableBlockPos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isGlowdustSandNearby(ISeedReader world, BlockPos.Mutable mutableBlockPos, BlockPos.Mutable mutableBlockPosNeighbors) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                mutableBlockPosNeighbors.setPos(mutableBlockPos).move(x, 0, z);
                BlockState neighborBlock = world.getBlockState(mutableBlockPosNeighbors);
                // Do not use .isSolid check because Glowdust Sand is marked notSolid (cause it uses Glowstone properties)
                if (neighborBlock.matchesBlock(IEBlocks.GLOWDUST_SAND.get())) {
                    return true;
                }
            }
        }
        return false;
    }
}
