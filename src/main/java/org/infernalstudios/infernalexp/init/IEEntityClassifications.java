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

package org.infernalstudios.infernalexp.init;

import org.infernalstudios.infernalexp.InfernalExpansion;
import net.minecraft.world.entity.MobCategory;

public class IEEntityClassifications {
    public static MobCategory IE_CREATURE;

    public static void register() {
        IE_CREATURE = MobCategory.create("IE_CREATURE", "IECREATURE", 70, true, false, 128);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Entity Classifications Registered!");
    }
}
