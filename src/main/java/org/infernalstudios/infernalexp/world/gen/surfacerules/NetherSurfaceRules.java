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

package org.infernalstudios.infernalexp.world.gen.surfacerules;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import org.infernalstudios.infernalexp.init.IEBiomes;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IESurfaceRules;

public class NetherSurfaceRules {

    private static final SurfaceRules.RuleSource GLOWDUST_SAND = makeStateRule(IEBlocks.GLOWDUST_SAND.get());
    private static final SurfaceRules.RuleSource GLOWDUST_STONE = makeStateRule(IEBlocks.GLOWDUST_STONE.get());
    private static final SurfaceRules.RuleSource TRAPPED_GLOWDUST_SAND = makeStateRule(IEBlocks.TRAPPED_GLOWDUST_SAND.get());
    private static final SurfaceRules.RuleSource DIMSTONE = makeStateRule(IEBlocks.DIMSTONE.get());
    private static final SurfaceRules.RuleSource DULLSTONE = makeStateRule(IEBlocks.DULLSTONE.get());

    private static final SurfaceRules.RuleSource GLOWSTONE_CANYON = SurfaceRules.ifTrue(SurfaceRules.isBiome(IEBiomes.GLOWSTONE_CANYON), SurfaceRules.sequence(
        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(
            SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, TRAPPED_GLOWDUST_SAND),
            GLOWDUST_SAND)),
        SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(3, false, CaveSurface.FLOOR), GLOWDUST_STONE),
        SurfaceRules.ifTrue(IESurfaceRules.chance("dimstone", 0.02F), DIMSTONE),
        DULLSTONE
    ));

    public static SurfaceRules.RuleSource addNetherSurfaceRules() {
        return SurfaceRules.sequence(GLOWSTONE_CANYON);
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

}
