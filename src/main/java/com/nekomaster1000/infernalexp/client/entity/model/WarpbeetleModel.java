package com.nekomaster1000.infernalexp.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import com.nekomaster1000.infernalexp.entities.WarpbeetleEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class WarpbeetleModel<T extends WarpbeetleEntity> extends EntityModel<T> {
    private final ModelRenderer warpbeetle;
    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer left_shield;
    private final ModelRenderer left_wing;
    private final ModelRenderer right_wing;
    private final ModelRenderer right_shield;
    private final ModelRenderer left_leg_1;
    private final ModelRenderer left_leg_2;
    private final ModelRenderer left_leg_3;
    private final ModelRenderer right_leg_1;
    private final ModelRenderer right_leg_2;
    private final ModelRenderer right_leg_3;

    public WarpbeetleModel() {
        textureWidth = 128;
        textureHeight = 128;

        warpbeetle = new ModelRenderer(this);
        warpbeetle.setRotationPoint(0.0F, 23.75F, 0.0F);


        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, -1.0F, 0.0F);
        warpbeetle.addChild(body);
        body.setTextureOffset(0, 0).addBox(-5.0F, -5.75F, -9.0F, 10.0F, 6.0F, 17.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -2.0F, -9.0F);
        body.addChild(head);
        head.setTextureOffset(43, 8).addBox(-3.0F, -1.75F, -4.0F, 6.0F, 4.0F, 4.0F, 0.0F, false);
        head.setTextureOffset(0, 15).addBox(0.0F, -8.75F, -10.0F, 0.0F, 10.0F, 8.0F, 0.0F, false);

        left_shield = new ModelRenderer(this);
        left_shield.setRotationPoint(5.0F, -6.0F, -6.0F);
        body.addChild(left_shield);
        left_shield.setTextureOffset(28, 29).addBox(-5.0F, -1.75F, 0.0F, 6.0F, 6.0F, 16.0F, 0.0F, false);

        left_wing = new ModelRenderer(this);
        left_wing.setRotationPoint(1.0F, -6.0F, -5.0F);
        body.addChild(left_wing);
        left_wing.setTextureOffset(41, 30).addBox(-3.0F, 0.24F, 0.0F, 6.0F, 0.0F, 15.0F, 0.0F, false);

        right_wing = new ModelRenderer(this);
        right_wing.setRotationPoint(-1.0F, -6.0F, -5.0F);
        body.addChild(right_wing);
        right_wing.setTextureOffset(41, 30).addBox(-3.0F, 0.24F, 0.0F, 6.0F, 0.0F, 15.0F, 0.0F, true);

        right_shield = new ModelRenderer(this);
        right_shield.setRotationPoint(-5.0F, -6.0F, -6.0F);
        body.addChild(right_shield);
        right_shield.setTextureOffset(0, 23).addBox(-1.0F, -1.75F, 0.0F, 6.0F, 6.0F, 16.0F, 0.0F, false);

        left_leg_1 = new ModelRenderer(this);
        left_leg_1.setRotationPoint(5.0F, -1.0F, -6.0F);
        body.addChild(left_leg_1);
        setRotationAngle(left_leg_1, 0.0F, 0.0F, 0.3927F);
        left_leg_1.setTextureOffset(31, 6).addBox(0.0F, 0.25F, -5.0F, 5.0F, 0.0F, 6.0F, 0.0F, false);

        left_leg_2 = new ModelRenderer(this);
        left_leg_2.setRotationPoint(5.0F, -1.0F, -2.0F);
        body.addChild(left_leg_2);
        setRotationAngle(left_leg_2, 0.0F, 0.0F, 0.3927F);
        left_leg_2.setTextureOffset(31, 0).addBox(0.0F, 0.25F, -1.0F, 5.0F, 0.0F, 6.0F, 0.0F, false);

        left_leg_3 = new ModelRenderer(this);
        left_leg_3.setRotationPoint(5.0F, -1.0F, 4.0F);
        body.addChild(left_leg_3);
        setRotationAngle(left_leg_3, 0.0F, 0.0F, 0.3927F);
        left_leg_3.setTextureOffset(22, 29).addBox(0.0F, 0.25F, -1.0F, 5.0F, 0.0F, 6.0F, 0.0F, false);

        right_leg_1 = new ModelRenderer(this);
        right_leg_1.setRotationPoint(-5.0F, -1.0F, -6.0F);
        body.addChild(right_leg_1);
        setRotationAngle(right_leg_1, 0.0F, 0.0F, -0.3927F);
        right_leg_1.setTextureOffset(22, 23).addBox(-5.0F, 0.25F, -5.0F, 5.0F, 0.0F, 6.0F, 0.0F, false);

        right_leg_2 = new ModelRenderer(this);
        right_leg_2.setRotationPoint(-5.0F, -1.0F, -2.0F);
        body.addChild(right_leg_2);
        setRotationAngle(right_leg_2, 0.0F, 0.0F, -0.3927F);
        right_leg_2.setTextureOffset(0, 6).addBox(-5.0F, 0.25F, -1.0F, 5.0F, 0.0F, 6.0F, 0.0F, false);

        right_leg_3 = new ModelRenderer(this);
        right_leg_3.setRotationPoint(-5.0F, -1.0F, 4.0F);
        body.addChild(right_leg_3);
        setRotationAngle(right_leg_3, 0.0F, 0.0F, -0.3927F);
        right_leg_3.setTextureOffset(0, 0).addBox(-5.0F, 0.25F, -1.0F, 5.0F, 0.0F, 6.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.left_leg_1.rotateAngleZ = -(MathHelper.abs(MathHelper.cos(limbSwing * 1.1F)) * 2.2F * limbSwingAmount) + 0.3927F;
        this.left_leg_2.rotateAngleZ = -(MathHelper.abs(MathHelper.cos(Math.max(limbSwing - 4.0F, 0) * 1.1F)) * 2.2F * limbSwingAmount) + 0.3927F;
        this.left_leg_3.rotateAngleZ = -(MathHelper.abs(MathHelper.cos(limbSwing * 1.1F)) * 2.2F * limbSwingAmount) + 0.3927F;
        this.right_leg_1.rotateAngleZ = (MathHelper.abs(MathHelper.cos(Math.max(limbSwing - 4.0F, 0) * 1.1F)) * 2.2F * limbSwingAmount) - 0.3927F;
        this.right_leg_2.rotateAngleZ = (MathHelper.abs(MathHelper.cos(limbSwing * 1.1F)) * 2.2F * limbSwingAmount) - 0.3927F;
        this.right_leg_3.rotateAngleZ = (MathHelper.abs(MathHelper.cos(Math.max(limbSwing - 4.0F, 0) * 1.1F)) * 2.2F * limbSwingAmount) - 0.3927F;

        this.left_leg_1.rotateAngleY = -MathHelper.sin(limbSwing * 1.1F) * 0.8F * limbSwingAmount;
        this.left_leg_2.rotateAngleY = -MathHelper.sin(Math.max(limbSwing - 4.0F, 0) * 1.1F) * 0.9F * limbSwingAmount;
        this.left_leg_3.rotateAngleY = -MathHelper.sin(limbSwing * 1.1F) * 1.4F * limbSwingAmount;
        this.right_leg_1.rotateAngleY = MathHelper.sin(Math.max(limbSwing - 4.0F, 0) * 1.1F) * 0.8F * limbSwingAmount;
        this.right_leg_2.rotateAngleY = MathHelper.sin(limbSwing * 1.1F) * 0.9F * limbSwingAmount;
        this.right_leg_3.rotateAngleY = MathHelper.sin(Math.max(limbSwing - 4.0F, 0) * 1.1F) * 1.4F * limbSwingAmount;

        if (!entity.isOnGround()) {
            entity.shellRotationMultiplier += 0.1F;

            if (entity.shellRotationMultiplier > 1.0F) {
                entity.shellRotationMultiplier = 1.0F;
            }

            float wingRotation = MathHelper.cos(ageInTicks * 2.1F) * (float) Math.PI * 0.15F + 1.0F;

            setRotationAngle(left_wing, wingRotation, 0.5F, 0.3F);
            setRotationAngle(right_wing, wingRotation, -0.5F, -0.3F);
        } else {
            entity.shellRotationMultiplier -= 0.1F;

            if (entity.shellRotationMultiplier < 0.0F) {
                entity.shellRotationMultiplier = 0.0F;
            }

            setRotationAngle(left_wing, 0.0F, 0.0F, 0.0F);
            setRotationAngle(right_wing, 0.0F, 0.0F, 0.0F);
        }

        setRotationAngle(left_shield, 1.2F * entity.shellRotationMultiplier, -0.4F * entity.shellRotationMultiplier, 0.9F * entity.shellRotationMultiplier);
        setRotationAngle(right_shield, 1.2F * entity.shellRotationMultiplier, 0.4F * entity.shellRotationMultiplier, -0.9F * entity.shellRotationMultiplier);
    }


    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        warpbeetle.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
