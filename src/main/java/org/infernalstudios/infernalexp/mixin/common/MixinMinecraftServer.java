/*
 * Copyright 2021 Infernal Studios
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

package org.infernalstudios.infernalexp.mixin.common;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.storage.WorldData;
import org.infernalstudios.infernalexp.world.gen.surfacerules.NetherSurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {

    @Shadow
    public abstract WorldData getWorldData();

    /**
     * Thanks to CorgiTaco from BYG for showing me and letting me use this approach to adding surface rules
     *
     * @see <a href="https://github.com/AOCAWOL/BYG/blob/1.18.X/Common/src/main/java/potionstudios/byg/util/AddSurfaceRulesUtil.java">AddSurfaceRulesUtil.java</a>
     */
    @Inject(method = "createLevels", at = @At("TAIL"))
    private void IE_appendSurfaceRules(ChunkProgressListener holder, CallbackInfo ci) {
        if (this.getWorldData() == null)
            throw new NullPointerException("What! The server's world data is null.");

        LevelStem levelStem = this.getWorldData().worldGenSettings().dimensions().get(LevelStem.NETHER);

        if (levelStem == null)
            throw new NullPointerException(LevelStem.NETHER.location() + " is not a valid level stem key. This is likely the result of a broken level.dat, likely caused by moving this world between MC versions.");

        if (levelStem.generator() instanceof NoiseBasedChunkGenerator) {
            NoiseGeneratorSettings settings = ((NoiseBasedChunkGeneratorAccessor) levelStem.generator()).getSettings().value();

            // This won't add surface rules to Terrablender worlds because Terrablender uses their own "NamespacedSurfaceRuleSource" instead of "SequenceRuleSource"
            if (settings.surfaceRule() instanceof SurfaceRules.SequenceRuleSource sequenceRuleSource) {
                List<SurfaceRules.RuleSource> rules = new ArrayList<>(sequenceRuleSource.sequence());
                rules.add(rules.size() - 1, NetherSurfaceRules.addNetherSurfaceRules()); // Add our surface rules to the second last position in the sequence, right before NETHERRACK

                ((NoiseGeneratorSettingsAccessor) (Object) settings).setSurfaceRule(new SurfaceRules.SequenceRuleSource(rules));
            }
        }
    }
}
