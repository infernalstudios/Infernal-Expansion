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

package com.nekomaster1000.infernalexp.client.entity.render;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.client.entity.model.GlowsquitoModel;
import com.nekomaster1000.infernalexp.entities.GlowsquitoEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class GlowsquitoRenderer extends MobRenderer<GlowsquitoEntity, GlowsquitoModel<GlowsquitoEntity>> {
    protected static final ResourceLocation UNBRED_TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/glowsquitoid.png");

    protected static final ResourceLocation BRED_TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/glowsquitoid_shroomlight.png");

    public GlowsquitoRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new GlowsquitoModel<>(), 0.7f);
        this.addLayer(new GlowsquitoGlowLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(GlowsquitoEntity entity) {
        if (entity.getBred()) {
            return BRED_TEXTURE;
        } else {
            return UNBRED_TEXTURE;
        }
    }
}
