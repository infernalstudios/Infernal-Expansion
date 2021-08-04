package org.infernalstudios.infernalexp.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.client.entity.model.ShroomloinModel;
import org.infernalstudios.infernalexp.entities.ShroomloinEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class ShroomloinNameDecorLayer extends LayerRenderer<ShroomloinEntity, ShroomloinModel<ShroomloinEntity>> {
    public static final ResourceLocation IMPOSTOR = new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_impostor.png");
    public static final ResourceLocation INVERTED = new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_inverted.png");
    public static final ResourceLocation PIZZA = new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_pizza.png");
    public static final ResourceLocation SANS = new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/shroomloin/compat/shroomloin_sans.png");
    private final ShroomloinModel<ShroomloinEntity> model = new ShroomloinModel<>();

    public ShroomloinNameDecorLayer(ShroomloinRenderer shroomloinRenderer) {
        super(shroomloinRenderer);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, ShroomloinEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entitylivingbaseIn.hasCustomName()) {
            if ("sus".equals(entitylivingbaseIn.getName().getUnformattedComponentText()) || "impostor".equals(entitylivingbaseIn.getName().getUnformattedComponentText())) {
                renderCopyCutoutModel(this.getEntityModel(), this.model, IMPOSTOR, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, 1.0F, 1.0F, 1.0F);
            }
            if ("invert".equals(entitylivingbaseIn.getName().getUnformattedComponentText())) {
                renderCopyCutoutModel(this.getEntityModel(), this.model, INVERTED, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, 1.0F, 1.0F, 1.0F);
            }
            if ("pizza".equals(entitylivingbaseIn.getName().getUnformattedComponentText())) {
                renderCopyCutoutModel(this.getEntityModel(), this.model, PIZZA, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, 1.0F, 1.0F, 1.0F);
            }
            if ("sans".equals(entitylivingbaseIn.getName().getUnformattedComponentText())) {
                renderCopyCutoutModel(this.getEntityModel(), this.model, SANS, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}
