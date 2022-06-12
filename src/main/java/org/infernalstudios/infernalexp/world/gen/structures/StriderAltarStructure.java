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

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.infernalstudios.infernalexp.init.IEStructureTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class StriderAltarStructure extends Structure {

    // Minimum space allowed above lava ocean for structure to spawn
    private static final int MIN_VALID_SPACE = 20;

    public static final Codec<StriderAltarStructure> CODEC = RecordCodecBuilder.<StriderAltarStructure>mapCodec(instance -> instance.group(settingsCodec(instance), StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool)).apply(instance, StriderAltarStructure::new)).codec();

    private final Holder<StructureTemplatePool> startPool;

    public StriderAltarStructure(StructureSettings settings, Holder<StructureTemplatePool> startPool) {
        super(settings);
        this.startPool = startPool;
    }

    @Override
    public @NotNull Optional<GenerationStub> findGenerationPoint(@NotNull GenerationContext context) {
        BlockPos pos = context.chunkPos().getMiddleBlockPosition(0);
        NoiseColumn column = context.chunkGenerator().getBaseColumn(pos.getX(), pos.getZ(), context.heightAccessor(), context.randomState());

        int maxHeight = Math.min(context.chunkGenerator().getGenDepth(), context.chunkGenerator().getSeaLevel() + MIN_VALID_SPACE);

        for (int y = context.chunkGenerator().getSeaLevel(); y < maxHeight; y++) {
            if (!column.getBlock(y).isAir()) return Optional.empty();
        }

        Optional<Integer> yLevel = StructureUtil.getNetherLavaFloorY(context, pos);

        if (yLevel.isEmpty()) return Optional.empty();

        pos = pos.above(yLevel.get());

        return JigsawPlacement.addPieces(context, this.startPool, Optional.empty(), 1, pos, false, Optional.empty(), 1);
    }

    @Override
    public @NotNull StructureType<?> type() {
        return IEStructureTypes.STRIDER_ALTAR;
    }

}
