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
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.infernalstudios.infernalexp.init.IEStructureTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SizeCheckingNetherStructure extends Structure {

    public static final Codec<SizeCheckingNetherStructure> CODEC = RecordCodecBuilder.<SizeCheckingNetherStructure>mapCodec(instance -> instance.group(
        settingsCodec(instance),
        StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
        Codec.INT.fieldOf("size_to_check").forGetter(structure -> structure.sizeToCheck)
    ).apply(instance, SizeCheckingNetherStructure::new)).codec();

    private final Holder<StructureTemplatePool> startPool;
    private final int sizeToCheck;

    public SizeCheckingNetherStructure(StructureSettings settings, Holder<StructureTemplatePool> startPool, int sizeToCheck) {
        super(settings);
        this.startPool = startPool;
        this.sizeToCheck = sizeToCheck;
    }

    @Override
    public @NotNull Optional<GenerationStub> findGenerationPoint(@NotNull GenerationContext context) {
        Optional<Integer> yLevel = StructureUtil.getSuitableNetherYLevel(context, context.chunkPos().getMiddleBlockPosition(0));

        if (yLevel.isEmpty())
            return Optional.empty();


        BlockPos pos = context.chunkPos().getMiddleBlockPosition(yLevel.get());

        for (int x = pos.getX() - this.sizeToCheck; x <= pos.getX() + this.sizeToCheck; x += this.sizeToCheck) {
            for (int z = pos.getZ() - this.sizeToCheck; z <= pos.getZ() + this.sizeToCheck; z += this.sizeToCheck) {
                if (!StructureUtil.checkLandAtHeight(context, pos, 5))
                    return Optional.empty();
            }
        }

        return JigsawPlacement.addPieces(context, this.startPool, Optional.empty(), 1, pos, false, Optional.empty(), 1);
    }

    @Override
    public @NotNull StructureType<?> type() {
        return IEStructureTypes.SIZE_CHECKING_NETHER_STRUCTURE;
    }

}
