package com.nekomaster1000.infernalexp.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.client.entity.model.ShroomloinModel;
import com.nekomaster1000.infernalexp.entities.ShroomloinEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShroomloinGlowLayer extends LayerRenderer<ShroomloinEntity, ShroomloinModel<ShroomloinEntity>> {
    private static final ResourceLocation[] SHROOMLOIN_GLOW_TEXTURES = new ResourceLocation[]{
        new ResourceLocation(InfernalExpansion.MOD_ID,"textures/entity/shroomloin/layer/1_shroomloin_glow.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/layer/2_shroomloin_glow.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/layer/3_shroomloin_glow.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/layer/4_shroomloin_glow.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/layer/5_shroomloin_glow.png")
    };

    public ShroomloinGlowLayer(IEntityRenderer<ShroomloinEntity, ShroomloinModel<ShroomloinEntity>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int packedLightIn, ShroomloinEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ResourceLocation textures = SHROOMLOIN_GLOW_TEXTURES[entitylivingbaseIn.getFungusType()];
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.getRenderType(textures));
        this.getEntityModel().render(matrixStack, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public RenderType getRenderType(ResourceLocation texture) {
        return RenderType.getEyes(texture);
    }
}
