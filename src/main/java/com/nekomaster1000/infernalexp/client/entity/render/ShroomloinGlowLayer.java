package com.nekomaster1000.infernalexp.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.entities.ShroomloinEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class ShroomloinGlowLayer<T extends ShroomloinEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {

    public ShroomloinGlowLayer(IEntityRenderer<T, M> rendererIn) {
        super(rendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        int i = entitylivingbaseIn.getFungusType();
        String pathIn = "textures/entity/shroomloin/layer/" + i + "_shroomloin_glow.png";
        ResourceLocation texture = new ResourceLocation(InfernalExpansion.MOD_ID, pathIn);
        this.render(texture, matrixStackIn, bufferIn);
    }

    private void render(ResourceLocation texture, MatrixStack matrixStack, IRenderTypeBuffer bufferIn) {
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.getRenderType(texture));
        this.getEntityModel().render(matrixStack, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public RenderType getRenderType(ResourceLocation texture) {
        return RenderType.getEyes(texture);
    }
}
