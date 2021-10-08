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

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.entities.VolineEntity;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VolineGlowLayer<T extends VolineEntity, M extends EntityModel<T>> extends EyesLayer<T, M> {
    private static final RenderType RENDER_TYPE = RenderType.eyes(new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/voline_glow.png"));
    private static final RenderType TIRED_RENDER_TYPE = RenderType.eyes(new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/voline_tired_glow.png"));

    public VolineGlowLayer(RenderLayerParent<T, M> rendererIn) {
        super(rendererIn);
    }

    public RenderType getVolineRenderType(VolineEntity entity) {
        return entity.getAttributeValue(Attributes.MOVEMENT_SPEED) <= 0 ? TIRED_RENDER_TYPE : RENDER_TYPE;
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.getVolineRenderType(entitylivingbaseIn));
        this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    // I've created a custom getRenderType method above because I need to be able to change the glow layer texture
    // It returns something just in case something breaks and it gets called (it shouldn't though), thanks Swan for the reminder
    @Override
    public RenderType renderType() {
        return RENDER_TYPE;
    }
}
