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
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PaintingRenderer;
import net.minecraft.client.resources.PaintingTextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.infernalstudios.infernalexp.InfernalExpansion;

@OnlyIn(Dist.CLIENT)
public class InfernalPaintingRenderer extends PaintingRenderer {
    public static final ResourceLocation BACK_TEXTURE_ATLAS_LOCATION = new ResourceLocation(InfernalExpansion.MOD_ID,
        "infernal_back");

    public InfernalPaintingRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(Painting entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
        matrixStackIn.scale(0.0625F, 0.0625F, 0.0625F);

        PaintingVariant paintingType = entityIn.getVariant().get();
        VertexConsumer iVertexBuilder = bufferIn.getBuffer(RenderType.entitySolid(this.getTextureLocation(entityIn)));
        PaintingTextureManager paintingSpriteUploader = Minecraft.getInstance().getPaintingTextures();
        renderPainting(matrixStackIn, iVertexBuilder, entityIn, paintingType.getWidth(), paintingType.getHeight(), paintingSpriteUploader.get(paintingType), paintingSpriteUploader.getSprite(BACK_TEXTURE_ATLAS_LOCATION));

        matrixStackIn.popPose();

        // Can't call super because it will override the custom back texture
        net.minecraftforge.client.event.RenderNameTagEvent renderNameplateEvent = new net.minecraftforge.client.event.RenderNameTagEvent(entityIn, entityIn.getDisplayName(), this, matrixStackIn, bufferIn, packedLightIn, partialTicks);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
        if (renderNameplateEvent.getResult() != net.minecraftforge.eventbus.api.Event.Result.DENY && (renderNameplateEvent.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || this.shouldShowName(entityIn))) {
            renderNameTag(entityIn, renderNameplateEvent.getContent(), matrixStackIn, bufferIn, packedLightIn);
        }
    }
}
