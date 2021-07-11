package com.nekomaster1000.infernalexp.world.gen.structures;

import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.InfernalExpansion;
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
		return new StructureSeparationSettings(4, 2, 720435943);
	}

	@Override
	public boolean shouldTransformLand() {
		return false;
	}

    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig config) {

        // Makes cheap check first, if it passes this check, it does a more in-depth check
        if (super.func_230363_a_(chunkGenerator, biomeProvider, seed, chunkRandom, chunkX, chunkZ, biome, chunkPos, config)) {

            for (int curChunkX = chunkX - 1; curChunkX <= chunkX + 1; curChunkX++) {
                for (int curChunkZ = chunkZ - 1; curChunkZ <= chunkZ + 1; curChunkZ++) {
                    BlockPos.Mutable mutable = new BlockPos.Mutable();
                    mutable.setPos(chunkX << 4, chunkGenerator.getSeaLevel() + 10, chunkZ << 4);
                    IBlockReader blockView = chunkGenerator.func_230348_a_(mutable.getX(), mutable.getZ());

                    // Makes sure there are at least 25 blocks of air to spawn the structure
                    int minValidSpace = 25;
                    int maxHeight = Math.min(chunkGenerator.getMaxBuildHeight(), chunkGenerator.getSeaLevel() + minValidSpace);

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

	public static class Start extends IEStart<NoFeatureConfig> {
		public Start(Structure<NoFeatureConfig> structure, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int reference, long seed) {
			super(structure, chunkX, chunkZ, mutableBoundingBox, reference, seed);
		}

		@Override
		public void func_230364_a_(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManager, int chunkX, int chunkZ, Biome biome, NoFeatureConfig config) {
			int x = (chunkX << 4) + this.rand.nextInt(16);
			int z = (chunkZ << 4) + this.rand.nextInt(16);

			BlockPos pos = new BlockPos(x, getYPos(chunkGenerator, x, z), z);

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
