package com.nekomaster1000.infernalexp.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.nekomaster1000.infernalexp.entities.SkeletalPiglinEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class SkeletalPiglinModel<T extends SkeletalPiglinEntity> extends EntityModel<T> {

    private final ModelRenderer Body;
    private final ModelRenderer Head;
    private final ModelRenderer LeftEar;
    private final ModelRenderer RightEar;
    private final ModelRenderer RightArm;
    private final ModelRenderer LeftArm;
    private final ModelRenderer RightLeg;
    private final ModelRenderer LeftLeg;

    public SkeletalPiglinModel() {
        textureWidth = 64;
        textureHeight = 64;

        Head = new ModelRenderer(this);
        Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        Head.setTextureOffset(0, 0).addBox(-5.0F, -8.0F, -4.0F, 10.0F, 8.0F, 8.0F, 0.0F, false);
        Head.setTextureOffset(31, 1).addBox(-2.0F, -4.0F, -5.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        Head.setTextureOffset(2, 0).addBox(-3.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Head.setTextureOffset(2, 4).addBox(2.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);

        LeftEar = new ModelRenderer(this);
        LeftEar.setRotationPoint(4.5F, -6.0F, 0.0F);
        Head.addChild(LeftEar);
        setRotationAngle(LeftEar, 0.0F, 0.0F, -0.4363F);
        LeftEar.setTextureOffset(51, 6).addBox(0.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, 0.0F, false);
        LeftEar.setTextureOffset(54, 10).addBox(0.0F, 0.0F, 1.0F, 1.0F, 5.0F, 0.0F, 0.0F, false);
        LeftEar.setTextureOffset(54, 10).addBox(0.0F, 0.0F, -1.0F, 1.0F, 5.0F, 0.0F, 0.0F, false);
        LeftEar.setTextureOffset(54, 10).addBox(0.0F, 4.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        RightEar = new ModelRenderer(this);
        RightEar.setRotationPoint(-4.5F, -6.0F, 0.0F);
        Head.addChild(RightEar);
        setRotationAngle(RightEar, 0.0F, 0.0F, 0.4363F);
        RightEar.setTextureOffset(51, 6).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, 0.0F, true);
        RightEar.setTextureOffset(54, 10).addBox(-1.0F, 0.0F, 1.0F, 1.0F, 5.0F, 0.0F, 0.0F, true);
        RightEar.setTextureOffset(54, 10).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 5.0F, 0.0F, 0.0F, true);
        RightEar.setTextureOffset(54, 10).addBox(-1.0F, 4.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, true);

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0.0F, 0.0F, 0.0F);
        Body.setTextureOffset(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

        RightArm = new ModelRenderer(this);
        RightArm.setRotationPoint(-5.0F, 1.0F, -1.0F);
        setRotationAngle(RightArm, 0.0F, 0.0F, 0.0436F);
        RightArm.setTextureOffset(40, 16).addBox(-2.0F, -1.0F, 0.0F, 3.0F, 12.0F, 3.0F, 0.0F, false);

        LeftArm = new ModelRenderer(this);
        LeftArm.setRotationPoint(5.0F, 1.0F, -1.0F);
        setRotationAngle(LeftArm, 0.0F, 0.0F, -0.0436F);
        LeftArm.setTextureOffset(40, 16).addBox(-1.0F, -1.0F, 0.0F, 3.0F, 12.0F, 3.0F, 0.0F, true);

        RightLeg = new ModelRenderer(this);
        RightLeg.setRotationPoint(-1.9F, 12.0F, -0.5F);
        RightLeg.setTextureOffset(50, 15).addBox(-2.1F, 0.001F, -1.0F, 3.0F, 12.0F, 3.0F, 0.0F, false);

        LeftLeg = new ModelRenderer(this);
        LeftLeg.setRotationPoint(1.9F, 12.0F, -0.5F);
        LeftLeg.setTextureOffset(50, 15).addBox(-0.9F, 0.001F, -1.0F, 3.0F, 12.0F, 3.0F, 0.0F, true);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // TODO: Set head rotation (see BipedModel class for implementation)
        this.RightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.LeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.RightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.LeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Head.render(matrixStack, buffer, packedLight, packedOverlay);
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
        RightArm.render(matrixStack, buffer, packedLight, packedOverlay);
        LeftArm.render(matrixStack, buffer, packedLight, packedOverlay);
        RightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
        LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
