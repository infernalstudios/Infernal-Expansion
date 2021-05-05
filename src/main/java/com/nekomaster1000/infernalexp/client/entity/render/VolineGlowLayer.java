package com.nekomaster1000.infernalexp.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.entities.VolineEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VolineGlowLayer<T extends VolineEntity, M extends EntityModel<T>> extends AbstractEyesLayer<T, M> {
    private static final RenderType RENDER_TYPE = RenderType.getEyes(new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/voline_glow.png"));
    private static final RenderType TIRED_RENDER_TYPE = RenderType.getEyes(new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/voline_tired_glow.png"));

    public VolineGlowLayer(IEntityRenderer<T, M> rendererIn) {
        super(rendererIn);
    }

    public RenderType getVolineRenderType(VolineEntity entity) {
        return entity.getAttributeValue(Attributes.MOVEMENT_SPEED) <= 0 ? TIRED_RENDER_TYPE : RENDER_TYPE;
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.getVolineRenderType(entitylivingbaseIn));
        this.getEntityModel().render(matrixStackIn, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    // I've created a custom getRenderType method above because I need to be able to change the glow layer texture
    // It returns something just in case something breaks and it gets called (it shouldn't though), thanks Swan for the reminder
    @Override
    public RenderType getRenderType() {
        return RENDER_TYPE;
    }
}
