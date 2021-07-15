package com.nekomaster1000.infernalexp.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.client.entity.model.ShroomloinModel;
import com.nekomaster1000.infernalexp.entities.ShroomloinEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class ShroomloinDecorLayer extends LayerRenderer<ShroomloinEntity, ShroomloinModel<ShroomloinEntity>> {
    private static final ResourceLocation[] SHROOMLOIN_TEXTURES = new ResourceLocation[]{new ResourceLocation(InfernalExpansion.MOD_ID,"textures/entity/shroomloin/1_shroomloin.png"), new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/2_shroomloin.png"), new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/3_shroomloin.png"), new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/4_shroomloin.png"), new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/5_shroomloin.png")};
    private final ShroomloinModel<ShroomloinEntity> model = new ShroomloinModel<>();

    public ShroomloinDecorLayer(IEntityRenderer<ShroomloinEntity, ShroomloinModel<ShroomloinEntity>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, ShroomloinEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ResourceLocation texture = SHROOMLOIN_TEXTURES[entitylivingbaseIn.getFungusType()];
        renderCopyCutoutModel(this.getEntityModel(), model, texture, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, 1.0F, 1.0F, 1.0F);
    }
}
