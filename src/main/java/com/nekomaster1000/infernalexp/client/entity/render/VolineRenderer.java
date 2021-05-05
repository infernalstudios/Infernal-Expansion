package com.nekomaster1000.infernalexp.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.client.entity.model.VolineModel;
import com.nekomaster1000.infernalexp.entities.VolineEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.ResourceLocation;

public class VolineRenderer extends MobRenderer<VolineEntity, VolineModel<VolineEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/voline.png");
    protected static final ResourceLocation TIRED_TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/voline_tired.png");

    public VolineRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new VolineModel<>(), 0.7F);
		this.addLayer(new VolineGlowLayer<>(this));
	}

	@Override
	public void render(VolineEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		shadowSize = 0.25F * entityIn.getVolineSize();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	protected void preRenderCallback(VolineEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
		matrixStackIn.scale(entitylivingbaseIn.getVolineSize(), entitylivingbaseIn.getVolineSize(), entitylivingbaseIn.getVolineSize());
	}

	@Override
	public ResourceLocation getEntityTexture(VolineEntity entity) {
		return entity.getAttributeValue(Attributes.MOVEMENT_SPEED) <= 0 ? TIRED_TEXTURE : TEXTURE;
	}
}
