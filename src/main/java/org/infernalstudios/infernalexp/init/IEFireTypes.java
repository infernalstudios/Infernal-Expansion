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

package org.infernalstudios.infernalexp.init;

import net.minecraft.util.ResourceLocation;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.api.FireType;

public class IEFireTypes {

    public static final FireType FIRE = FireType.register(new ResourceLocation("fire"));
    public static final FireType SOUL_FIRE = FireType.register(new ResourceLocation("soul_fire"));
    public static final FireType GLOW_FIRE = FireType.register(new ResourceLocation(InfernalExpansion.MOD_ID, "glow_fire"));
    public static final FireType ENDER_FIRE = FireType.register(new ResourceLocation("endergetic", "ender_fire"));
    public static final FireType BORIC_FIRE = FireType.register(new ResourceLocation("byg", "boric_fire"));
    public static final FireType CRYPTIC_FIRE = FireType.register(new ResourceLocation("byg", "cryptic_fire"));

    public static void register() {}

    ;

}
