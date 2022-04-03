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
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class StructureUtil {
    /**
     * Looks for suitable y levels to place a structure then randomly picks one if multiple are found.
     *
     * @return Will return an empty {@link java.util.Optional} if no suitable y level has been found
     */
    public static Optional<Integer> getSuitableNetherYLevel(PieceGeneratorSupplier.Context<?> context, BlockPos pos) {
        NoiseColumn column = context.chunkGenerator().getBaseColumn(pos.getX(), pos.getZ(), context.heightAccessor());
        List<Integer> suitableYLevels = new ArrayList<>();

        for (int y = context.heightAccessor().getMinBuildHeight(); y < 128; y++) {
            if (column.getBlock(y).canOcclude() && column.getBlock(y + 1).isAir() && column.getBlock(y + 5).isAir()) {
                suitableYLevels.add(y);
            }
        }

        if (suitableYLevels.isEmpty())
            return Optional.empty();

        return Optional.of(suitableYLevels.get(new Random(context.seed()).nextInt(suitableYLevels.size())));
    }

    /**
     * Looks for y level of lava floor
     *
     * @return Will return an empty {@link java.util.Optional} if lava floor is not found
     */
    public static Optional<Integer> getNetherLavaFloorY(PieceGeneratorSupplier.Context<?> context, BlockPos pos) {
        int y = context.chunkGenerator().getSeaLevel();
        NoiseColumn column = context.chunkGenerator().getBaseColumn(pos.getX(), pos.getZ(), context.heightAccessor());

        BlockPos lavaPos = new BlockPos(pos.getX(), y, pos.getZ());

        int topDown = 1;

        if (column.getBlock(pos.getY()).canOcclude()) {
            return Optional.empty();
        }

        while (topDown <= y) {
            BlockState checkLava = column.getBlock(lavaPos.getY() - topDown);
            BlockState checkBlock = column.getBlock(lavaPos.getY() - topDown - 1);

            if (checkLava.is(Blocks.LAVA) && checkBlock.isFaceSturdy((BlockGetter) context.heightAccessor(), pos.below(y), Direction.UP)) {
                return Optional.of(y - topDown);
            }

            topDown++;
        }

        return Optional.empty();
    }
}
