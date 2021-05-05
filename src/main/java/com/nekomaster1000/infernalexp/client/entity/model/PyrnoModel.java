package com.nekomaster1000.infernalexp.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import com.nekomaster1000.infernalexp.entities.PyrnoEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class PyrnoModel<T extends PyrnoEntity> extends EntityModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer left_ear;
    private final ModelRenderer right_ear;
    private final ModelRenderer leg_1;
    private final ModelRenderer leg_2;
    private final ModelRenderer leg_3;
    private final ModelRenderer leg_4;
    private final ModelRenderer tail;

    public PyrnoModel() {
        textureWidth = 128;
        textureHeight = 128;

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 14.0F, 1.0F);
        body.setTextureOffset(0, 0).addBox(-8.0F, -14.0F, -14.0F, 16.0F, 14.0F, 26.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -12.0F, -14.0F);
        body.addChild(head);
        setRotationAngle(head, 0.6109F, 0.0F, 0.0F);
        head.setTextureOffset(0, 0).addBox(-2.0F, -13.0F, -19.0F, 4.0F, 16.0F, 6.0F, 0.0F, false);
        head.setTextureOffset(0, 40).addBox(-8.0F, -3.0F, -15.0F, 16.0F, 8.0F, 15.0F, 0.0F, false);

        left_ear = new ModelRenderer(this);
        left_ear.setRotationPoint(7.5F, -2.4649F, 0.1563F);
        head.addChild(left_ear);
        setRotationAngle(left_ear, 0.0F, 0.0F, 1.5708F);
        left_ear.setTextureOffset(41, 41).addBox(-4.0F, -0.5351F, -0.1563F, 5.0F, 0.0F, 6.0F, 0.0F, false);

        right_ear = new ModelRenderer(this);
        right_ear.setRotationPoint(-7.35F, -2.4649F, 0.1563F);
        head.addChild(right_ear);
        setRotationAngle(right_ear, 0.0F, 0.0F, -1.5708F);
        right_ear.setTextureOffset(8, 0).addBox(-1.0F, -0.5351F, -0.1563F, 5.0F, 0.0F, 6.0F, 0.0F, false);

        leg_1 = new ModelRenderer(this);
        leg_1.setRotationPoint(5.0F, 0.0F, -11.0F);
        body.addChild(leg_1);
        leg_1.setTextureOffset(0, 63).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 10.0F, 6.0F, 0.0F, false);

        leg_2 = new ModelRenderer(this);
        leg_2.setRotationPoint(-5.0F, 0.0F, -11.0F);
        body.addChild(leg_2);
        leg_2.setTextureOffset(62, 40).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 10.0F, 6.0F, 0.0F, false);

        leg_3 = new ModelRenderer(this);
        leg_3.setRotationPoint(-5.0F, 0.0F, 9.0F);
        body.addChild(leg_3);
        leg_3.setTextureOffset(58, 0).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 10.0F, 6.0F, 0.0F, false);

        leg_4 = new ModelRenderer(this);
        leg_4.setRotationPoint(5.0F, 0.0F, 9.0F);
        body.addChild(leg_4);
        leg_4.setTextureOffset(56, 57).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 10.0F, 6.0F, 0.0F, false);

        tail = new ModelRenderer(this);
        tail.setRotationPoint(0.0F, -12.0F, 12.0F);
        body.addChild(tail);
        setRotationAngle(tail, 0.2618F, 0.0F, 0.0F);
        tail.setTextureOffset(0, 40).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 12.0F, 0.0F, 0.0F, false);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

        @Override
        public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
            this.leg_1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.leg_2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.leg_3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.leg_4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        }

        @Override
        public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
            body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        }

}
