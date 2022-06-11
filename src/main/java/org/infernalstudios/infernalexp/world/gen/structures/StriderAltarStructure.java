/*
 * Copyright 2022 Infernal Studios
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.infernalstudios.infernalexp.world.gen.structures;

public class StriderAltarStructure {

    // TODO
    //    // Minimum space allowed above lava ocean for structure to spawn
    //    private static final int MIN_VALID_SPACE = 20;
    //
    //    public StriderAltarStructure() {
    //        super(JigsawConfiguration.CODEC, StriderAltarStructure::createPiecesGenerator, PostPlacementProcessor.NONE);
    //    }
    //
    //    @NotNull
    //    @Override
    //    public GenerationStep.Decoration step() {
    //        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    //    }
    //
    //    @NotNull
    //    private static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
    //        BlockPos pos = context.chunkPos().getMiddleBlockPosition(0);
    //        NoiseColumn column = context.chunkGenerator().getBaseColumn(pos.getX(), pos.getZ(), context.heightAccessor());
    //
    //        int maxHeight = Math.min(context.chunkGenerator().getGenDepth(), context.chunkGenerator().getSeaLevel() + MIN_VALID_SPACE);
    //
    //        for (int y = context.chunkGenerator().getSeaLevel(); y < maxHeight; y++) {
    //            if (!column.getBlock(y).isAir())
    //                return Optional.empty();
    //        }
    //
    //        Optional<Integer> yLevel = StructureUtil.getNetherLavaFloorY(context, pos);
    //
    //        if (yLevel.isEmpty())
    //            return Optional.empty();
    //
    //        pos = pos.above(yLevel.get());
    //
    //        return JigsawPlacement.addPieces(context, PoolElementStructurePiece::new, pos, false, false);
    //    }
}
