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

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SimpleNetherStructure extends StructureFeature<JigsawConfiguration> {

    public SimpleNetherStructure() {
        super(JigsawConfiguration.CODEC, SimpleNetherStructure::createPiecesGenerator, PostPlacementProcessor.NONE);
    }

    @NotNull
    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @NotNull
    private static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        Optional<Integer> yLevel = StructureUtil.getSuitableNetherYLevel(context, context.chunkPos().getMiddleBlockPosition(0));

        if (yLevel.isEmpty())
            return Optional.empty();

        BlockPos pos = context.chunkPos().getMiddleBlockPosition(yLevel.get());

        return JigsawPlacement.addPieces(context, PoolElementStructurePiece::new, pos, false, false);
    }
}
