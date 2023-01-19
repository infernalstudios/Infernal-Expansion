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

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.infernalstudios.infernalexp.InfernalExpansion;
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

@Mod.EventBusSubscriber(modid = InfernalExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntitySpawns {

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(IEEntityTypes.VOLINE.get(), VolineEntity.setCustomAttributes().build());
        event.put(IEEntityTypes.SHROOMLOIN.get(), ShroomloinEntity.setCustomAttributes().build());
        event.put(IEEntityTypes.WARPBEETLE.get(), WarpbeetleEntity.setCustomAttributes().build());
        event.put(IEEntityTypes.EMBODY.get(), EmbodyEntity.setCustomAttributes().build());
        event.put(IEEntityTypes.BASALT_GIANT.get(), BasaltGiantEntity.setCustomAttributes().build());
        event.put(IEEntityTypes.BLACKSTONE_DWARF.get(), BlackstoneDwarfEntity.setCustomAttributes().build());
        event.put(IEEntityTypes.GLOWSQUITO.get(), GlowsquitoEntity.setCustomAttributes().build());
        event.put(IEEntityTypes.BLINDSIGHT.get(), BlindsightEntity.setCustomAttributes().build());
        event.put(IEEntityTypes.GLOWSILK_MOTH.get(), GlowsilkMothEntity.setCustomAttributes().build());
    }
}
