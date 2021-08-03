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
import com.mojang.blaze3d.vertex.IVertexBuilder;

import org.infernalstudios.infernalexp.InfernalExpansion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PaintingRenderer;
import net.minecraft.client.renderer.texture.PaintingSpriteUploader;
import net.minecraft.entity.item.PaintingEntity;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class InfernalPaintingRenderer extends PaintingRenderer {
    public static final ResourceLocation BACK_TEXTURE_ATLAS_LOCATION = new ResourceLocation(InfernalExpansion.MOD_ID,
        "infernal_back");

    public InfernalPaintingRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void render(PaintingEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
        matrixStackIn.scale(0.0625F, 0.0625F, 0.0625F);

        PaintingType paintingType = entityIn.art;
        IVertexBuilder iVertexBuilder = bufferIn.getBuffer(RenderType.getEntitySolid(this.getEntityTexture(entityIn)));
        PaintingSpriteUploader paintingSpriteUploader = Minecraft.getInstance().getPaintingSpriteUploader();
        func_229122_a_(matrixStackIn, iVertexBuilder, entityIn, paintingType.getWidth(), paintingType.getHeight(), paintingSpriteUploader.getSpriteForPainting(paintingType), paintingSpriteUploader.getSprite(BACK_TEXTURE_ATLAS_LOCATION));

        matrixStackIn.pop();

        // Can't call super because it will override the custom back texture
        net.minecraftforge.client.event.RenderNameplateEvent renderNameplateEvent = new net.minecraftforge.client.event.RenderNameplateEvent(entityIn, entityIn.getDisplayName(), this, matrixStackIn, bufferIn, packedLightIn, partialTicks);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
        if (renderNameplateEvent.getResult() != net.minecraftforge.eventbus.api.Event.Result.DENY && (renderNameplateEvent.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || this.canRenderName(entityIn))) {
            renderName(entityIn, renderNameplateEvent.getContent(), matrixStackIn, bufferIn, packedLightIn);
        }
    }
}
