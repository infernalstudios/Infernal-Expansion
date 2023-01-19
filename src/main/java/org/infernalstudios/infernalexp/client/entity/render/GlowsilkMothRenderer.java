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
import org.infernalstudios.infernalexp.client.entity.model.GlowsilkMothModel;
import org.infernalstudios.infernalexp.entities.GlowsilkMothEntity;
import org.jetbrains.annotations.NotNull;

public class GlowsilkMothRenderer extends MobRenderer<GlowsilkMothEntity, GlowsilkMothModel<GlowsilkMothEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/glowsilk_moth.png");

    public GlowsilkMothRenderer(EntityRendererProvider.Context context) {
        super(context, new GlowsilkMothModel<>(context.bakeLayer(GlowsilkMothModel.LAYER_LOCATION)), 0.7f);
        this.addLayer(new GlowsilkMothGlowLayer<>(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GlowsilkMothEntity entity) {
        return TEXTURE;
    }

}
