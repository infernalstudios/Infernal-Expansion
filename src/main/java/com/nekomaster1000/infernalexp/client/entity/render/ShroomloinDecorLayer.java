package com.nekomaster1000.infernalexp.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.client.entity.model.ShroomloinModel;
import com.nekomaster1000.infernalexp.entities.ShroomloinEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class ShroomloinDecorLayer extends LayerRenderer<ShroomloinEntity, ShroomloinModel<ShroomloinEntity>> {
    public static final ResourceLocation[] SHROOMLOIN_TEXTURES = new ResourceLocation[]{
        new ResourceLocation(InfernalExpansion.MOD_ID,"textures/entity/shroomloin/shroomloin_crimson.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/shroomloin_warped.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/shroomloin_luminous.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/shroomloin_red.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/shroomloin_brown.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_ddepths_glowshroom.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_blewit.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_blue_glowshroom.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_bulbis.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_death.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_embur.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_fungal_imparius.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_green.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_imparius_mushroom.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_milk.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_puff.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_purple_glowshroom.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_shulk.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_soul.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_synthian.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_toadstool.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_poise.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_impostor.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_inverted.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_pizza.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_sans.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_fairy_ring.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_quark_glowshroom.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_blue.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_orange.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_purple.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_mass.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_blood.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_indigo.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_ink.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_veil.png"),
        new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_bop_glowshroom.png")
    };

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
