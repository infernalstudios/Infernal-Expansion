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
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.client.entity.model.BasaltGiantModel;
import org.infernalstudios.infernalexp.entities.BasaltGiantEntity;
import org.jetbrains.annotations.NotNull;

public class BasaltGiantRenderer extends MobRenderer<BasaltGiantEntity, BasaltGiantModel<BasaltGiantEntity>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/basalt_giant.png");

    public BasaltGiantRenderer(EntityRendererProvider.Context context) {
        super(context, new BasaltGiantModel<>(context.bakeLayer(BasaltGiantModel.LAYER_LOCATION)), 0.7F);
        this.addLayer(new BasaltGiantGlowLayer<>(this));
    }

    @Override
    public void render(BasaltGiantEntity entityIn, float entityYaw, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        shadowRadius = 0.25F * entityIn.getEntitySize();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected void scale(BasaltGiantEntity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(entitylivingbaseIn.getEntitySize(), entitylivingbaseIn.getEntitySize(), entitylivingbaseIn.getEntitySize());
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BasaltGiantEntity entity) {
        return TEXTURE;
    }
}
