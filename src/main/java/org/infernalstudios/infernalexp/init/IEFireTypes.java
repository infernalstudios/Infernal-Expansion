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

import org.infernalstudios.infernalexp.InfernalExpansion;

import crystalspider.soulfired.api.FireBuilder;
import crystalspider.soulfired.api.FireManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

public class IEFireTypes {
    public static final ResourceLocation GLOW_FIRE_TYPE = new ResourceLocation(InfernalExpansion.MOD_ID, "glow");
    public static final ResourceLocation ENDER_FIRE_TYPE = new ResourceLocation("endergetic", "ender");
    public static final ResourceLocation BORIC_FIRE_TYPE = new ResourceLocation("byg", "boric");
    public static final ResourceLocation CRYPTIC_FIRE_TYPE = new ResourceLocation("byg", "cryptic");

    public static void register() {
        FireBuilder fireBuilder = FireManager.fireBuilder(GLOW_FIRE_TYPE);
        FireManager.registerFire(
            fireBuilder
                .setDamage(2)
                .removeFireAspect()
                .removeFlame()
                .build()
        );
        if (ModList.get().isLoaded(ENDER_FIRE_TYPE.getNamespace())) {
            FireManager.registerFire(
                fireBuilder
                    .reset(ENDER_FIRE_TYPE)
                    .setDamage(3)
                    .removeFireAspect()
                    .removeFlame()
                    .build()
            );
        }
        if (ModList.get().isLoaded(BORIC_FIRE_TYPE.getNamespace())) {
            FireManager.registerFire(
                fireBuilder
                    .reset(BORIC_FIRE_TYPE)
                    .setDamage(3.5f)
                    .removeFireAspect()
                    .removeFlame()
                    .build()
            );
            FireManager.registerFire(
                fireBuilder
                    .reset(CRYPTIC_FIRE_TYPE)
                    .setDamage(3.5f)
                    .removeFireAspect()
                    .removeFlame()
                    .build()
            );
        }
    }
}
