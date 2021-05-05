package com.nekomaster1000.infernalexp.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.client.entity.model.ShroomloinModel;
import com.nekomaster1000.infernalexp.entities.ShroomloinEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class ShroomloinRenderer extends MobRenderer<ShroomloinEntity, ShroomloinModel<ShroomloinEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/shroomloin.png");

    public ShroomloinRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ShroomloinModel<>(), 0.7f);
        this.addLayer(new ShroomloinGlowLayer(this));
    }

    protected void preRenderCallback(ShroomloinEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        float f = entitylivingbaseIn.getShroomloinFlashIntensity(partialTickTime);
        float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        matrixStackIn.scale(f2, f3, f2);
    }

    protected float getOverlayProgress(ShroomloinEntity livingEntityIn, float partialTicks) {
        float f = livingEntityIn.getShroomloinFlashIntensity(partialTicks);
        return (int)(f * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(f, 0.5F, 1.0F);
    }

    @Override
    public ResourceLocation getEntityTexture(ShroomloinEntity entity) {
        return TEXTURE;
    }
}
