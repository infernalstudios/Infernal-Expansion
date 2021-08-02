package com.nekomaster1000.infernalexp.world.gen.structures;

import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.init.IEStructures;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

public class GlowstoneCanyonRuinStructure extends IEStructure<NoFeatureConfig> {
	public GlowstoneCanyonRuinStructure(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStartFactory() {
		return GlowstoneCanyonRuinStructure.Start::new;
	}

	@Override
	public GenerationStage.Decoration getDecorationStage() {
		return GenerationStage.Decoration.SURFACE_STRUCTURES;
	}

	@Override
	public StructureSeparationSettings getSeparationSettings() {
		return new StructureSeparationSettings(1, 0, 20394857);
	}

    @Override
    public boolean shouldTransformLand() {
        return true;
    }

	@Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig config) {
        SharedSeedRandom random = new SharedSeedRandom(seed + (chunkX * (chunkZ * 17)));

        // Makes cheap check first, if it passes this check, it does a more in-depth check
        if (super.func_230363_a_(chunkGenerator, biomeProvider, seed, chunkRandom, chunkX, chunkZ, biome, chunkPos, config)) {

            int posX = chunkX << 4;
            int posZ = chunkZ << 4;
            int posY = getYPos(chunkGenerator, posX, posZ, random);

            // Checks 9 points within a small area of the spawn location
            for (int curX = posX - 3; curX <= posX + 3; curX += 3) {
                for (int curZ = posZ - 3; curZ <= posZ + 3; curZ += 3) {

                    // Starts 5 blocks below to check for solid land in each column
                    BlockPos.Mutable mutable = new BlockPos.Mutable(curX, posY - 5, curZ);
                    IBlockReader blockView = chunkGenerator.func_230348_a_(mutable.getX(), mutable.getZ());

                    // Flag represents a block with air above it
                    boolean flag = false;

                    while (mutable.getY() <= posY + 5) {
                        BlockState state = blockView.getBlockState(mutable);
                        if (state.isSolid()) {
                            mutable.move(Direction.UP);
                            state = blockView.getBlockState(mutable);

                            if (state.isAir()) {
                                flag = true;
                                break;
                            }

                        } else {
                            mutable.move(Direction.UP);
                        }
                    }

                    // If there is no block with air above it in this range, the structure can't spawn
                    if (!flag) {
                        return false;
                    }

                    // Checks if there are 5 blocks of air above the 5 checked for solid to spawn the structure
                    int minValidSpace = 5;
                    int maxHeight = Math.min(chunkGenerator.getMaxBuildHeight(), posY + minValidSpace);

                    while (mutable.getY() < maxHeight) {
                        BlockState state = blockView.getBlockState(mutable);
                        if (!state.isAir()) {
                            return false;
                        }
                        mutable.move(Direction.UP);
                    }
                }
            }
        } else {
            return false;
        }

        //cannot be near other specified structure
        for (int curChunkX = chunkX - 1; curChunkX <= chunkX + 1; curChunkX++) {
            for (int curChunkZ = chunkZ - 1; curChunkZ <= chunkZ + 1; curChunkZ++) {

                StructureSeparationSettings structureConfig = chunkGenerator.func_235957_b_().func_236197_a_(IEStructures.BASTION_OUTPOST);
                if (structureConfig != null) {
                    ChunkPos chunkPos2 = IEStructures.BASTION_OUTPOST.getChunkPosForStructure(structureConfig, seed, chunkRandom, curChunkX, curChunkZ);
                    if (curChunkX == chunkPos2.x && curChunkZ == chunkPos2.z) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

	public static class Start extends IEStart<NoFeatureConfig> {
        private final long seed;

		public Start(Structure<NoFeatureConfig> structure, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int reference, long seed) {
			super(structure, chunkX, chunkZ, mutableBoundingBox, reference, seed);
			this.seed = seed;
		}

		@Override
		public void func_230364_a_(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManager, int chunkX, int chunkZ, Biome biome, NoFeatureConfig config) {
            SharedSeedRandom random = new SharedSeedRandom(seed + (chunkX * (chunkZ * 17)));
		    int x = chunkX << 4;
			int z = chunkZ << 4;

			BlockPos pos = new BlockPos(x, getYPos(chunkGenerator, x, z, random), z);

			if (pos.getY() != 0) {
				JigsawManager.func_242837_a(
						dynamicRegistryManager,
						new VillageConfig(() -> dynamicRegistryManager.getRegistry(Registry.JIGSAW_POOL_KEY).getOrDefault(new ResourceLocation(InfernalExpansion.MOD_ID, "glowstone_canyon_ruin/start_pool")), 1),
						AbstractVillagePiece::new,
						chunkGenerator,
						templateManager,
						pos,
						this.components,
						this.rand,
						false,
						false
				);

				this.recalculateStructureSize();
			}
		}
	}
}
