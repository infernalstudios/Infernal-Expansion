package com.nekomaster1000.infernalexp.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.client.entity.model.BasaltGiantModel;
import com.nekomaster1000.infernalexp.entities.BasaltGiantEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class BasaltGiantRenderer extends MobRenderer<BasaltGiantEntity, BasaltGiantModel<BasaltGiantEntity>> {
	public static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/basalt_giant.png");

	public BasaltGiantRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new BasaltGiantModel<>(), 0.7F);
		this.addLayer(new BasaltGiantGlowLayer<>(this));
	}

	@Override
	public void render(BasaltGiantEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		shadowSize = 0.25F * entityIn.getGiantSize();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	protected void preRenderCallback(BasaltGiantEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
		matrixStackIn.scale(entitylivingbaseIn.getGiantSize(), entitylivingbaseIn.getGiantSize(), entitylivingbaseIn.getGiantSize());
	}

	@Override
	public ResourceLocation getEntityTexture(BasaltGiantEntity entity) {
		return TEXTURE;
	}
}
