package com.nekomaster1000.infernalexp.world.gen.structures;

import com.mojang.serialization.Codec;

import com.nekomaster1000.infernalexp.InfernalExpansion;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
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

public class StriderAltarStructure extends IEStructure<NoFeatureConfig> {
    public StriderAltarStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return StriderAltarStructure.Start::new;
    }

    @Override
    public GenerationStage.Decoration getDecorationStage() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public StructureSeparationSettings getSeparationSettings() {
        return new StructureSeparationSettings(10, 5, 80837592);
    }

    @Override
    public boolean shouldTransformLand() {
        return true;
    }

    public static class Start extends IEStart<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structure, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int reference, long seed) {
            super(structure, chunkX, chunkZ, mutableBoundingBox, reference, seed);
        }

        public int getLavaY(ChunkGenerator chunkGenerator, int x, int z) {
            int y = chunkGenerator.getSeaLevel();
            IBlockReader blockColumn = chunkGenerator.func_230348_a_(x, z);

            BlockPos pos = new BlockPos(x, y, z);

            int topDown = 1;

            if (blockColumn.getBlockState(pos).isSolid()) {
                return 0;
            }

            while (topDown <= y) {
                BlockState checkLava = blockColumn.getBlockState(pos.down(topDown));
                BlockState checkBlock = blockColumn.getBlockState(pos.down(topDown + 1));

                if (checkLava.matchesBlock(Blocks.LAVA) && checkBlock.isSolidSide(blockColumn, pos.down(y), Direction.UP)) {
                    return y-topDown;
                }

                topDown++;
            }

            return 0;
        }



        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManager, int chunkX, int chunkZ, Biome biome, NoFeatureConfig config) {
            int x = (chunkX << 4) + this.rand.nextInt(16);
            int z = (chunkZ << 4) + this.rand.nextInt(16);

            BlockPos pos = new BlockPos(x, getLavaY(chunkGenerator, x, z), z);

            if (pos.getY() != 0) {
                JigsawManager.func_242837_a(
                    dynamicRegistryManager,
                    new VillageConfig(() -> dynamicRegistryManager.getRegistry(Registry.JIGSAW_POOL_KEY).getOrDefault(new ResourceLocation(InfernalExpansion.MOD_ID, "strider_altar/start_pool")), 1),
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
