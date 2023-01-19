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
import org.infernalstudios.infernalexp.client.entity.model.BlindsightModel;
import org.infernalstudios.infernalexp.entities.BlindsightEntity;
import org.jetbrains.annotations.NotNull;

public class BlindsightRenderer extends MobRenderer<BlindsightEntity, BlindsightModel<BlindsightEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/blindsight.png");

    public BlindsightRenderer(EntityRendererProvider.Context context) {
        super(context, new BlindsightModel<>(context.bakeLayer(BlindsightModel.LAYER_LOCATION)), 0.7f);
        this.addLayer(new BlindsightGlowLayer<>(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BlindsightEntity entity) {
        return TEXTURE;
    }
}
