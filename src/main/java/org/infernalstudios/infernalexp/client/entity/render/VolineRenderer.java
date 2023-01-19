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

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.client.entity.model.VolineModel;
import org.infernalstudios.infernalexp.entities.VolineEntity;
import org.jetbrains.annotations.NotNull;

public class VolineRenderer extends MobRenderer<VolineEntity, VolineModel<VolineEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/voline.png");
    protected static final ResourceLocation TIRED_TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/voline_tired.png");

    public VolineRenderer(EntityRendererProvider.Context context) {
        super(context, new VolineModel<>(context.bakeLayer(VolineModel.LAYER_LOCATION)), 0.7F);
        this.addLayer(new VolineGlowLayer<>(this));
    }

    @Override
    public void render(VolineEntity entityIn, float entityYaw, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        shadowRadius = 0.25F * entityIn.getEntitySize();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected void scale(VolineEntity volineEntity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(volineEntity.getEntitySize(), volineEntity.getEntitySize(), volineEntity.getEntitySize());
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(VolineEntity entity) {
        return entity.getAttributeValue(Attributes.MOVEMENT_SPEED) <= 0 ? TIRED_TEXTURE : TEXTURE;
    }
}
