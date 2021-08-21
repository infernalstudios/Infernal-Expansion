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

import com.mojang.blaze3d.matrix.MatrixStack;

import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.client.entity.model.VolineModel;
import org.infernalstudios.infernalexp.entities.VolineEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.ResourceLocation;

public class VolineRenderer extends MobRenderer<VolineEntity, VolineModel<VolineEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/voline.png");
    protected static final ResourceLocation TIRED_TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/voline_tired.png");

    public VolineRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new VolineModel<>(), 0.7F);
        this.addLayer(new VolineGlowLayer<>(this));
    }

    @Override
    public void render(VolineEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        shadowSize = 0.25F * entityIn.getEntitySize();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected void preRenderCallback(VolineEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(entitylivingbaseIn.getEntitySize(), entitylivingbaseIn.getEntitySize(), entitylivingbaseIn.getEntitySize());
    }

    @Override
    public ResourceLocation getEntityTexture(VolineEntity entity) {
        return entity.getAttributeValue(Attributes.MOVEMENT_SPEED) <= 0 ? TIRED_TEXTURE : TEXTURE;
    }
}
