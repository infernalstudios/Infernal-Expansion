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
import com.nekomaster1000.infernalexp.client.entity.model.WarpbeetleModel;
import com.nekomaster1000.infernalexp.entities.WarpbeetleEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class WarpbeetleRenderer extends MobRenderer<WarpbeetleEntity, WarpbeetleModel<WarpbeetleEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/warpbeetle/warpbeetle.png");
    protected static final ResourceLocation CHORUS_TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/warpbeetle/chorus_warpbeetle.png");

    public WarpbeetleRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new WarpbeetleModel<>(), 0.7f);
    }

    @Override
    public ResourceLocation getEntityTexture(WarpbeetleEntity entity) {
        return entity.isChorus() ? CHORUS_TEXTURE : TEXTURE;
    }

    @Override
    protected boolean func_230495_a_(WarpbeetleEntity entity) {
        return entity.isShaking();
    }
}
