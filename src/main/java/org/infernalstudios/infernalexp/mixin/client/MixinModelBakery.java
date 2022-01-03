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

package org.infernalstudios.infernalexp.mixin.client;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import org.infernalstudios.infernalexp.access.FireTypeAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;

@OnlyIn(Dist.CLIENT)
@Mixin(ModelBakery.class)
public class MixinModelBakery {

    @Shadow
    @Final
    protected static Set<Material> UNREFERENCED_TEXTURES;

    static {
        UNREFERENCED_TEXTURES.add(FireTypeAccess.LOCATION_SOUL_FIRE_0);
        UNREFERENCED_TEXTURES.add(FireTypeAccess.LOCATION_SOUL_FIRE_1);
        UNREFERENCED_TEXTURES.add(FireTypeAccess.LOCATION_GLOW_FIRE_0);
        UNREFERENCED_TEXTURES.add(FireTypeAccess.LOCATION_GLOW_FIRE_1);

        if (ModList.get().isLoaded("endergetic")) {
            UNREFERENCED_TEXTURES.add(FireTypeAccess.LOCATION_ENDER_FIRE_0);
            UNREFERENCED_TEXTURES.add(FireTypeAccess.LOCATION_ENDER_FIRE_1);
        }

        if (ModList.get().isLoaded("byg")) {
            UNREFERENCED_TEXTURES.add(FireTypeAccess.LOCATION_BORIC_FIRE_0);
            UNREFERENCED_TEXTURES.add(FireTypeAccess.LOCATION_BORIC_FIRE_1);
            UNREFERENCED_TEXTURES.add(FireTypeAccess.LOCATION_CRYPTIC_FIRE_0);
            UNREFERENCED_TEXTURES.add(FireTypeAccess.LOCATION_CRYPTIC_FIRE_1);
        }
    }

}
