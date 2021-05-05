package com.nekomaster1000.infernalexp.client.entity.model;

// Made with Blockbench 3.6.5
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import com.nekomaster1000.infernalexp.entities.EmbodyEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class EmbodyModel<E extends EmbodyEntity> extends EntityModel<EmbodyEntity> {
    private final ModelRenderer body;
    private final ModelRenderer left_arm;
    private final ModelRenderer right_arm;
    private final ModelRenderer head;

    public EmbodyModel() {
        textureWidth = 64;
        textureHeight = 64;

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 24.0F, 6.0F);
        setRotationAngle(body, 0.7854F, 0.0F, 0.0F);
        body.setTextureOffset(0, 16).addBox(-4.0F, -12.0F, 0.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

        left_arm = new ModelRenderer(this);
        left_arm.setRotationPoint(4.0F, -11.0F, 2.0F);
        body.addChild(left_arm);
        setRotationAngle(left_arm, -1.7453F, 0.0F, 0.0F);
        left_arm.setTextureOffset(0, 32).addBox(0.0F, -1.0F, -2.0F, 3.0F, 14.0F, 4.0F, 0.0F, false);

        right_arm = new ModelRenderer(this);
        right_arm.setRotationPoint(-4.0F, -11.0F, 2.0F);
        body.addChild(right_arm);
        setRotationAngle(right_arm, -1.7453F, 0.0F, 0.0F);
        right_arm.setTextureOffset(24, 24).addBox(-3.0F, -1.0F, -2.0F, 3.0F, 14.0F, 4.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -12.0F, 2.0F);
        body.addChild(head);
        setRotationAngle(head, -0.7854F, 0.0F, 0.0F);
        head.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(EmbodyEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        right_arm.rotateAngleX = MathHelper.sin(limbSwing)/2 - 45;
        left_arm.rotateAngleX = MathHelper.sin(-limbSwing)/2 - 45;
        body.rotateAngleZ = MathHelper.sin(limbSwing)/8;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
