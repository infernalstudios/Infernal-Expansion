package com.nekomaster1000.infernalexp.world.gen.structures;

import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.init.IEBlocks;
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

public class BastionOutpostStructure extends IEStructure<NoFeatureConfig> {

	public BastionOutpostStructure(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStartFactory() {
		return BastionOutpostStructure.Start::new;
	}

	@Override
	public GenerationStage.Decoration getDecorationStage() {
		return GenerationStage.Decoration.SURFACE_STRUCTURES;
	}

	@Override
	public StructureSeparationSettings getSeparationSettings() {
		return new StructureSeparationSettings(2, 1, 720435943);
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

            // Checks 9 points within about a chunk of the initial location
            for (int curX = posX - 8; curX <= posX + 8; curX += 8) {
                for (int curZ = posZ - 8; curZ <= posZ + 8; curZ += 8) {

                    // Starts 5 blocks below to check for solid land in each column
                    BlockPos.Mutable mutable = new BlockPos.Mutable();
                    mutable.setPos(curX, posY - 5, curZ);
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

                    // Checks if there are 15 blocks of air above the 5 checked for solid to spawn the structure
                    int minValidSpace = 15;
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
        }
        else {
            return false;
        }

        return true;
    }
    public int getYPos(ChunkGenerator chunkGenerator, int x, int z, SharedSeedRandom random) {
        int y = chunkGenerator.getSeaLevel() + random.nextInt(chunkGenerator.getMaxBuildHeight() - 2 - chunkGenerator.getSeaLevel());
        IBlockReader blockColumn = chunkGenerator.func_230348_a_(x, z);

        BlockPos pos = new BlockPos(x, y, z);

        while (y > chunkGenerator.getSeaLevel()) {
            BlockState checkAir = blockColumn.getBlockState(pos.down(y));
            BlockState checkBlock = blockColumn.getBlockState(pos.down(y + 1));

            if (checkAir.isAir() && (checkBlock.matchesBlock(IEBlocks.GLOWDUST_SAND.get()) || checkBlock.isSolidSide(blockColumn, pos.down(y), Direction.UP))) {
                return y;
            }

            y--;
        }

        return 0;
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
						new VillageConfig(() -> dynamicRegistryManager.getRegistry(Registry.JIGSAW_POOL_KEY).getOrDefault(new ResourceLocation(InfernalExpansion.MOD_ID, "bastion_outpost/start_pool")), 1),
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
