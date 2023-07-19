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

package org.infernalstudios.infernalexp.client.entity.render;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.client.entity.model.BlackstoneDwarfModel;
import org.infernalstudios.infernalexp.entities.BlackstoneDwarfEntity;
import org.jetbrains.annotations.NotNull;

public class BlackstoneDwarfRenderer extends MobRenderer<BlackstoneDwarfEntity, BlackstoneDwarfModel<BlackstoneDwarfEntity>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/blackstone_dwarf.png");
    public static final ResourceLocation CREATED_TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/blackstone_dwarf_created.png");

    public BlackstoneDwarfRenderer(EntityRendererProvider.Context context) {
        super(context, new BlackstoneDwarfModel<>(context.bakeLayer(BlackstoneDwarfModel.LAYER_LOCATION)), 0.7f);
        this.addLayer(new BlackstoneDwarfGlowLayer<>(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BlackstoneDwarfEntity entity) {
        return entity.isPlayerCreated() ? CREATED_TEXTURE : TEXTURE;
    }
}
