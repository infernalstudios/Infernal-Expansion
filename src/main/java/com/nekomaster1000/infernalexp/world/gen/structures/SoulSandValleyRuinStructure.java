package com.nekomaster1000.infernalexp.world.gen.structures;

import com.mojang.serialization.Codec;

import com.nekomaster1000.infernalexp.InfernalExpansion;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

public class SoulSandValleyRuinStructure extends IEStructure<NoFeatureConfig> {
	public SoulSandValleyRuinStructure(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStartFactory() {
		return SoulSandValleyRuinStructure.Start::new;
	}

	@Override
	public GenerationStage.Decoration getDecorationStage() {
		return GenerationStage.Decoration.SURFACE_STRUCTURES;
	}

	@Override
	public StructureSeparationSettings getSeparationSettings() {
		return new StructureSeparationSettings(4, 2, 29456392);
	}

//	@Override
//	protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig config) {
//		return super.func_230363_a_(chunkGenerator, biomeProvider, seed, chunkRandom, chunkX, chunkZ, biome, chunkPos, config);
//	}

	@Override
	public boolean shouldTransformLand() {
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
						new VillageConfig(() -> dynamicRegistryManager.getRegistry(Registry.JIGSAW_POOL_KEY).getOrDefault(new ResourceLocation(InfernalExpansion.MOD_ID, "soul_sand_valley_ruin/start_pool")), 1),
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
