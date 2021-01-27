package com.nekomaster1000.infernalexp.client.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.nekomaster1000.infernalexp.entities.BlackstoneDwarfEntity;

import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class BlackstoneDwarfModel<T extends BlackstoneDwarfEntity> extends SegmentedModel<T> {

        private final ModelRenderer Body;
        private final ModelRenderer Torso;
        private final ModelRenderer Torso2;
        private final ModelRenderer Head;
        private final ModelRenderer Jaw;
        private final ModelRenderer LeftArm;
        private final ModelRenderer LeftArmJoint;
        private final ModelRenderer RightArm;
        private final ModelRenderer RightArmJoint;
        private final ModelRenderer LeftLeg;
        private final ModelRenderer LeftLegJoint;
        private final ModelRenderer RightLeg;
        private final ModelRenderer RightLegJoint;

        public BlackstoneDwarfModel() {
            textureWidth = 128;
            textureHeight = 128;

            Body = new ModelRenderer(this);
            Body.setRotationPoint(0.0F, 24.0F, 0.0F);


            Torso = new ModelRenderer(this);
            Torso.setRotationPoint(0.0F, -36.0F, 4.0F);
            Body.addChild(Torso);
            Torso.setTextureOffset(0, 50).addBox(-9.0F, 3.0F, -4.0F, 18.0F, 15.0F, 8.0F, 0.0F, false);

            Torso2 = new ModelRenderer(this);
            Torso2.setRotationPoint(0.0F, 7.0F, -1.0F);
            Torso.addChild(Torso2);
            Torso2.setTextureOffset(0, 0).addBox(-10.0F, -17.0F, -7.0F, 20.0F, 17.0F, 14.0F, 0.0F, false);

            Head = new ModelRenderer(this);
            Head.setRotationPoint(0.0F, -6.0F, -7.0F);
            Torso2.addChild(Head);
            Head.setTextureOffset(52, 52).addBox(-7.0F, -5.0F, -12.0F, 13.0F, 10.0F, 12.0F, 0.0F, false);

            Jaw = new ModelRenderer(this);
            Jaw.setRotationPoint(0.0F, 5.0F, 0.0F);
            Head.addChild(Jaw);
            Jaw.setTextureOffset(114, 50).addBox(-9.0F, -6.0F, -14.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
            Jaw.setTextureOffset(90, 50).addBox(6.0F, -6.0F, -14.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
            Jaw.setTextureOffset(0, 31).addBox(-9.0F, 0.0F, -14.0F, 17.0F, 5.0F, 14.0F, 0.0F, false);

            LeftArm = new ModelRenderer(this);
            LeftArm.setRotationPoint(12.0F, -11.0F, -1.0F);
            Torso2.addChild(LeftArm);
            LeftArm.setTextureOffset(0, 100).addBox(-2.0F, -4.0F, -3.0F, 6.0F, 17.0F, 6.0F, 0.0F, false);

            LeftArmJoint = new ModelRenderer(this);
            LeftArmJoint.setRotationPoint(1.0F, 14.0F, 0.0F);
            LeftArm.addChild(LeftArmJoint);
            LeftArmJoint.setTextureOffset(90, 33).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 11.0F, 8.0F, 0.0F, false);
            LeftArmJoint.setTextureOffset(68, 0).addBox(-5.0F, 9.0F, -5.0F, 10.0F, 10.0F, 10.0F, 0.0F, false);

            RightArm = new ModelRenderer(this);
            RightArm.setRotationPoint(-13.0F, -11.0F, -0.9F);
            Torso2.addChild(RightArm);
            RightArm.setTextureOffset(58, 99).addBox(-3.0F, -4.0F, -3.1F, 6.0F, 17.0F, 6.0F, 0.0F, false);

            RightArmJoint = new ModelRenderer(this);
            RightArmJoint.setRotationPoint(0.0F, 14.0F, 0.0F);
            RightArm.addChild(RightArmJoint);
            RightArmJoint.setTextureOffset(64, 74).addBox(-4.0F, -2.0F, -4.1F, 8.0F, 11.0F, 8.0F, 0.0F, false);
            RightArmJoint.setTextureOffset(58, 21).addBox(-5.0F, 9.0F, -5.1F, 10.0F, 10.0F, 10.0F, 0.0F, false);

            LeftLeg = new ModelRenderer(this);
            LeftLeg.setRotationPoint(-5.0F, -19.0F, 4.0F);
            Body.addChild(LeftLeg);
            LeftLeg.setTextureOffset(96, 68).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 18.0F, 6.0F, 0.0F, false);

            LeftLegJoint = new ModelRenderer(this);
            LeftLegJoint.setRotationPoint(0.0F, 10.0F, 0.0F);
            LeftLeg.addChild(LeftLegJoint);
            LeftLegJoint.setTextureOffset(32, 74).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0.0F, false);

            RightLeg = new ModelRenderer(this);
            RightLeg.setRotationPoint(5.0F, -19.0F, 4.0F);
            Body.addChild(RightLeg);
            RightLeg.setTextureOffset(90, 93).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 18.0F, 6.0F, 0.0F, false);

            RightLegJoint = new ModelRenderer(this);
            RightLegJoint.setRotationPoint(0.0F, 10.0F, 0.0F);
            RightLeg.addChild(RightLegJoint);
            RightLegJoint.setTextureOffset(0, 73).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0.0F, false);
        }

    @Override
    public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        int i = entity.getAttackTimer();
        if(i <= 0){
            this.LeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.RightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.Jaw.rotateAngleX = 0.0F;
        }

        this.LeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.RightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.0F * limbSwingAmount;
        this.Torso2.rotateAngleX = MathHelper.cos(limbSwing * 0.3332F) * 0.25F * limbSwingAmount;
        this.Head.rotateAngleX = MathHelper.cos(limbSwing * 0.1551F) * 0.15F * limbSwingAmount;
    }

    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.Body, this.Head, this.Jaw, this.Torso, this.Torso2, this.LeftArm, this.LeftArmJoint, this.RightArm, this.RightArmJoint, this.LeftLeg, this.LeftLegJoint, this.RightLeg, this.RightLegJoint);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Body.render(matrixStack, buffer, packedLight, packedOverlay);

    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        int i = entityIn.getAttackTimer();
        if (i > 0) {
            this.LeftArm.rotateAngleX = -0.9F + 0.9F * MathHelper.func_233021_e_((float)i - partialTick, 10.0F);
            this.RightArm.rotateAngleX = -0.9F + 0.9F * MathHelper.func_233021_e_((float)i - partialTick, 10.0F);
            this.Jaw.rotateAngleX = 0.375F - 0.375F * MathHelper.func_233021_e_((float)i - partialTick, 10.0F);

        }
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}