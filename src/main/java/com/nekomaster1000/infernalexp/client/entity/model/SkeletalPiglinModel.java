package com.nekomaster1000.infernalexp.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;

import com.nekomaster1000.infernalexp.entities.SkeletalPiglinEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class SkeletalPiglinModel<T extends SkeletalPiglinEntity> extends PlayerModel<T> {

    private final ModelRenderer Body;
    private final ModelRenderer Head;
    private final ModelRenderer LeftEar;
    private final ModelRenderer RightEar;
    private final ModelRenderer RightArm;
    private final ModelRenderer LeftArm;

    public SkeletalPiglinModel() {
        super(0.0f, false);
        textureWidth = 64;
        textureHeight = 64;

        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
        this.bipedHead = new ModelRenderer(this);
        this.bipedHead.setTextureOffset(0, 0).addBox(-5.0F, -8.0F, -4.0F, 10.0F, 8.0F, 8.0F, 0.0F, false);
        this.bipedHead.setTextureOffset(31, 1).addBox(-2.0F, -4.0F, -5.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        this.bipedHead.setTextureOffset(2, 0).addBox(-3.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        this.bipedHead.setTextureOffset(2, 4).addBox(2.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        this.LeftEar = new ModelRenderer(this);
        this.LeftEar.setRotationPoint(4.5F, -6.0F, 0.0F);
        this.LeftEar.setTextureOffset(51, 6).addBox(0.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, 0.0F, false);
        this.LeftEar.setTextureOffset(54, 10).addBox(0.0F, 0.0F, 1.0F, 1.0F, 5.0F, 0.0F, 0.0F, false);
        this.LeftEar.setTextureOffset(54, 10).addBox(0.0F, 0.0F, -1.0F, 1.0F, 5.0F, 0.0F, 0.0F, false);
        this.LeftEar.setTextureOffset(54, 10).addBox(0.0F, 4.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        this.bipedHead.addChild(this.LeftEar);
        this.RightEar = new ModelRenderer(this);
        this.RightEar.setRotationPoint(-4.5F, -6.0F, 0.0F);
        this.RightEar.setTextureOffset(51, 6).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, 0.0F, true);
        this.RightEar.setTextureOffset(54, 10).addBox(-1.0F, 0.0F, 1.0F, 1.0F, 5.0F, 0.0F, 0.0F, true);
        this.RightEar.setTextureOffset(54, 10).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 5.0F, 0.0F, 0.0F, true);
        this.RightEar.setTextureOffset(54, 10).addBox(-1.0F, 4.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, true);
        this.bipedHead.addChild(this.RightEar);

        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 3.0F, 12.0F, 3.0F, 0.0f, true);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);

        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.addBox(-2.0F, -2.0F, -1.0F, 3.0F, 12.0F, 3.0F, 0.0f, false);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);

        this.bipedLeftLeg = new ModelRenderer(this, 50, 15);
        this.bipedLeftLeg.addBox(-0.9F, 0.001F, -1.0F, 3.0F, 12.0F, 3.0F, 0.0F, true);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, -0.5F);

        this.bipedRightLeg = new ModelRenderer(this, 50, 15);
        this.bipedRightLeg.addBox(-2.1F, 0.001F, -1.0F, 3.0F, 12.0F, 3.0F, 0.0F, false);
        this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, -0.5F);

        this.bipedHeadwear = new ModelRenderer(this);

        this.Body = this.bipedBody.getModelAngleCopy();
        this.Head = this.bipedHead.getModelAngleCopy();
        this.LeftArm = this.bipedLeftArm.getModelAngleCopy();
        this.RightArm = this.bipedLeftArm.getModelAngleCopy();
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.bipedBody.copyModelAngles(this.Body);
        this.bipedHead.copyModelAngles(this.Head);
        this.bipedLeftArm.copyModelAngles(this.LeftArm);
        this.bipedRightArm.copyModelAngles(this.RightArm);
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        float f1 = ageInTicks * 0.1F + limbSwing * 0.5F;
        float f2 = 0.08F + limbSwingAmount * 0.4F;
        this.LeftEar.rotateAngleZ = (-(float)Math.PI / 6F) - MathHelper.cos(f1 * 1.2F) * f2;
        this.RightEar.rotateAngleZ = ((float)Math.PI / 6F) + MathHelper.cos(f1) * f2;

        this.bipedLeftLegwear.copyModelAngles(this.bipedLeftLeg);
        this.bipedRightLegwear.copyModelAngles(this.bipedRightLeg);
        this.bipedLeftArmwear.copyModelAngles(this.bipedLeftArm);
        this.bipedRightArmwear.copyModelAngles(this.bipedRightArm);
        this.bipedBodyWear.copyModelAngles(this.bipedBody);
        this.bipedHeadwear.copyModelAngles(this.bipedHead);
    }

    public void translateHand(HandSide sideIn, MatrixStack matrixStackIn) {
        float f = sideIn == HandSide.RIGHT ? 1.5F : -1.5F;
        ModelRenderer modelrenderer = this.getArmForSide(sideIn);
        modelrenderer.rotationPointX += f;
        modelrenderer.translateRotate(matrixStackIn);
        modelrenderer.rotationPointX -= f;
    }
}
