package com.nekomaster1000.infernalexp.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import com.nekomaster1000.infernalexp.entities.CerobeetleEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class CerobeetleModel<T extends CerobeetleEntity> extends EntityModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer left_shield;
    private final ModelRenderer left_wing;
    private final ModelRenderer left_wing2;
    private final ModelRenderer right_shield;
    private final ModelRenderer right_wing;
    private final ModelRenderer right_wing2;
    private final ModelRenderer left_leg_1;
    private final ModelRenderer left_leg_2;
    private final ModelRenderer left_leg_3;
    private final ModelRenderer right_leg_1;
    private final ModelRenderer right_leg_2;
    private final ModelRenderer right_leg_3;

    public CerobeetleModel() {
        textureWidth = 256;
        textureHeight = 256;

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 22.0F, 0.0F);
        body.setTextureOffset(0, 0).addBox(-10.0F, -12.0F, -18.0F, 20.0F, 12.0F, 34.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 18.0F, -18.0F);
        head.setTextureOffset(86, 16).addBox(-6.0F, -4.0F, -8.0F, 12.0F, 8.0F, 8.0F, 0.0F, false);
        head.setTextureOffset(0, 31).addBox(0.0F, -17.0F, -16.0F, 0.0F, 19.0F, 16.0F, 0.0F, false);

        left_shield = new ModelRenderer(this);
        left_shield.setRotationPoint(1.0F, 10.0F, -12.0F);
        left_shield.setTextureOffset(56, 58).addBox(-1.0F, -3.0F, 0.0F, 12.0F, 12.0F, 32.0F, 0.0F, false);

        left_wing = new ModelRenderer(this);
        left_wing.setRotationPoint(3.0F, 10.0F, -11.0F);
        left_wing.setTextureOffset(94, 41).addBox(-4.0F, 0.0F, -1.0F, 10.0F, 0.0F, 19.0F, 0.0F, false);

        left_wing2 = new ModelRenderer(this);
        left_wing2.setRotationPoint(4.0F, 10.0F, -10.0F);
        left_wing2.setTextureOffset(82, 60).addBox(-5.0F, 0.0F, 0.0F, 12.0F, 0.0F, 30.0F, 0.0F, false);

        right_shield = new ModelRenderer(this);
        right_shield.setRotationPoint(-1.0F, 10.0F, -12.0F);
        right_shield.setTextureOffset(0, 46).addBox(-11.0F, -3.0F, 0.0F, 12.0F, 12.0F, 32.0F, 0.0F, false);

        right_wing = new ModelRenderer(this);
        right_wing.setRotationPoint(-3.0F, 10.0F, -10.0F);
        right_wing.setTextureOffset(94, 41).addBox(-6.0F, 0.0F, -2.0F, 10.0F, 0.0F, 19.0F, 0.0F, true);

        right_wing2 = new ModelRenderer(this);
        right_wing2.setRotationPoint(-4.0F, 10.0F, -10.0F);
        right_wing2.setTextureOffset(82, 60).addBox(-7.0F, 0.0F, 0.0F, 12.0F, 0.0F, 30.0F, 0.0F, true);

        left_leg_1 = new ModelRenderer(this);
        left_leg_1.setRotationPoint(10.0F, 20.0F, -14.5F);
        setRotationAngle(left_leg_1, 0.0F, 0.0F, 0.3491F);
        left_leg_1.setTextureOffset(62, 12).addBox(0.0F, 0.0F, -7.5F, 10.0F, 0.0F, 12.0F, 0.0F, false);

        left_leg_2 = new ModelRenderer(this);
        left_leg_2.setRotationPoint(10.0F, 20.0F, 1.5F);
        setRotationAngle(left_leg_2, 0.0F, 0.0F, 0.3927F);
        left_leg_2.setTextureOffset(62, 0).addBox(0.0F, 0.0F, -7.5F, 10.0F, 0.0F, 12.0F, 0.0F, false);

        left_leg_3 = new ModelRenderer(this);
        left_leg_3.setRotationPoint(10.0F, 20.0F, 11.5F);
        setRotationAngle(left_leg_3, 0.0F, 0.0F, 0.3491F);
        left_leg_3.setTextureOffset(44, 58).addBox(0.0F, 0.0F, -5.5F, 10.0F, 0.0F, 12.0F, 0.0F, false);

        right_leg_1 = new ModelRenderer(this);
        right_leg_1.setRotationPoint(-10.0F, 20.0F, -14.5F);
        setRotationAngle(right_leg_1, 0.0F, 0.0F, -0.3491F);
        right_leg_1.setTextureOffset(44, 46).addBox(-10.0F, 0.0F, -7.5F, 10.0F, 0.0F, 12.0F, 0.0F, false);

        right_leg_2 = new ModelRenderer(this);
        right_leg_2.setRotationPoint(-10.0F, 20.0F, 1.5F);
        setRotationAngle(right_leg_2, 0.0F, 0.0F, -0.3491F);
        right_leg_2.setTextureOffset(0, 12).addBox(-10.0F, 0.0F, -7.5F, 10.0F, 0.0F, 12.0F, 0.0F, false);

        right_leg_3 = new ModelRenderer(this);
        right_leg_3.setRotationPoint(-10.0F, 20.0F, 11.5F);
        setRotationAngle(right_leg_3, 0.0F, 0.0F, -0.3491F);
        right_leg_3.setTextureOffset(0, 0).addBox(-10.0F, 0.0F, -5.5F, 10.0F, 0.0F, 12.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(CerobeetleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
/*
            int i = entity.getAttackTimer();
            if(i <= 0){
            this.head.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
         }
*/
        this.left_leg_1.rotateAngleZ = MathHelper.cos(limbSwing * 1.6662F) * 1.4F * limbSwingAmount;
        this.left_leg_2.rotateAngleZ = MathHelper.cos(limbSwing * 1.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.left_leg_3.rotateAngleZ = MathHelper.cos(limbSwing * 1.6662F) * 1.4F * limbSwingAmount;
        this.right_leg_1.rotateAngleZ = MathHelper.cos(limbSwing * 1.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.right_leg_2.rotateAngleZ = MathHelper.cos(limbSwing * 1.6662F) * 1.4F * limbSwingAmount;
        this.right_leg_3.rotateAngleZ = MathHelper.cos(limbSwing * 1.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;

        if (!entity.isOnGround()) {
            entity.shellRotationMultiplier += 0.1F;

            if (entity.shellRotationMultiplier > 1.0F) {
                entity.shellRotationMultiplier = 1.0F;
            }

            float wingRotation = MathHelper.cos(ageInTicks * 2.1F) * (float) Math.PI * 0.15F + 1.0F;

            setRotationAngle(left_wing, wingRotation, 0.5F, 0.3F);
            setRotationAngle(right_wing, wingRotation, -0.5F, -0.3F);
            setRotationAngle(left_wing2, wingRotation, 0.4F, 0.2F);
            setRotationAngle(right_wing2, wingRotation, -0.4F, -0.2F);


        } else {
            entity.shellRotationMultiplier -= 0.1F;

            if (entity.shellRotationMultiplier < 0.0F) {
                entity.shellRotationMultiplier = 0.0F;
            }

            setRotationAngle(left_wing, 0.0F, 0.0F, 0.0F);
            setRotationAngle(right_wing, 0.0F, 0.0F, 0.0F);
            setRotationAngle(left_wing2, 0.0F, 0.0F, 0.0F);
            setRotationAngle(right_wing2, 0.0F, -0.0F, -0.0F);

        }

        setRotationAngle(left_shield, 1.2F * entity.shellRotationMultiplier, -0.4F * entity.shellRotationMultiplier, 0.9F * entity.shellRotationMultiplier);
        setRotationAngle(right_shield, 1.2F * entity.shellRotationMultiplier, 0.4F * entity.shellRotationMultiplier, -0.9F * entity.shellRotationMultiplier);
    }


    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        head.render(matrixStack, buffer, packedLight, packedOverlay);
        left_shield.render(matrixStack, buffer, packedLight, packedOverlay);
        left_wing.render(matrixStack, buffer, packedLight, packedOverlay);
        left_wing2.render(matrixStack, buffer, packedLight, packedOverlay);
        right_shield.render(matrixStack, buffer, packedLight, packedOverlay);
        right_wing.render(matrixStack, buffer, packedLight, packedOverlay);
        right_wing2.render(matrixStack, buffer, packedLight, packedOverlay);
        left_leg_1.render(matrixStack, buffer, packedLight, packedOverlay);
        left_leg_2.render(matrixStack, buffer, packedLight, packedOverlay);
        left_leg_3.render(matrixStack, buffer, packedLight, packedOverlay);
        right_leg_1.render(matrixStack, buffer, packedLight, packedOverlay);
        right_leg_2.render(matrixStack, buffer, packedLight, packedOverlay);
        right_leg_3.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setLivingAnimations(CerobeetleEntity entity, float limbSwing, float limbSwingAmount, float partialTick) {

        int i = entity.getAttackTimer();
        if (i > 0) {
            this.head.rotateAngleX = -0.9F + 0.9F * MathHelper.func_233021_e_((float)i - partialTick, 10.0F);
        }
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
