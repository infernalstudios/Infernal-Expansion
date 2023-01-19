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

package org.infernalstudios.infernalexp.world.gen;

import org.infernalstudios.infernalexp.entities.BasaltGiantEntity;
import org.infernalstudios.infernalexp.entities.BlackstoneDwarfEntity;
import org.infernalstudios.infernalexp.entities.BlindsightEntity;
import org.infernalstudios.infernalexp.entities.EmbodyEntity;
import org.infernalstudios.infernalexp.entities.GlowsilkMothEntity;
import org.infernalstudios.infernalexp.entities.GlowsquitoEntity;
import org.infernalstudios.infernalexp.entities.ShroomloinEntity;
import org.infernalstudios.infernalexp.entities.VolineEntity;
import org.infernalstudios.infernalexp.entities.WarpbeetleEntity;
import org.infernalstudios.infernalexp.init.IEEntityTypes;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class ModEntityPlacement {

    public static void spawnPlacement() {
        SpawnPlacements.register(IEEntityTypes.VOLINE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, VolineEntity::checkMobSpawnRules);
        SpawnPlacements.register(IEEntityTypes.SHROOMLOIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ShroomloinEntity::checkMobSpawnRules);
        SpawnPlacements.register(IEEntityTypes.WARPBEETLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WarpbeetleEntity::checkMobSpawnRules);
        SpawnPlacements.register(IEEntityTypes.EMBODY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EmbodyEntity::checkMobSpawnRules);
        SpawnPlacements.register(IEEntityTypes.BASALT_GIANT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BasaltGiantEntity::checkMobSpawnRules);
        SpawnPlacements.register(IEEntityTypes.BLACKSTONE_DWARF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlackstoneDwarfEntity::checkMobSpawnRules);
        SpawnPlacements.register(IEEntityTypes.GLOWSQUITO.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GlowsquitoEntity::checkMobSpawnRules);
        SpawnPlacements.register(IEEntityTypes.BLINDSIGHT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlindsightEntity::checkMobSpawnRules);
        SpawnPlacements.register(IEEntityTypes.GLOWSILK_MOTH.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GlowsilkMothEntity::checkMobSpawnRules);
    }

}
