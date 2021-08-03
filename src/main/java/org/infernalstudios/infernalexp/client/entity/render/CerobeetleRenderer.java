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

package org.infernalstudios.infernalexp.client.entity.render;

import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.client.entity.model.CerobeetleModel;
import org.infernalstudios.infernalexp.entities.CerobeetleEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class CerobeetleRenderer extends MobRenderer<CerobeetleEntity, CerobeetleModel<CerobeetleEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/cerobeetle.png");

    public CerobeetleRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CerobeetleModel<>(), 0.7f);
    }

    @Override
    public ResourceLocation getEntityTexture(CerobeetleEntity entity) {
        return TEXTURE;
    }
}
