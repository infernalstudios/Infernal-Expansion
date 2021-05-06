package com.nekomaster1000.infernalexp.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import com.nekomaster1000.infernalexp.entities.BlindsightEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class BlindsightModel<T extends BlindsightEntity> extends EntityModel<T> {
	private final ModelRenderer all;
	private final ModelRenderer Body;
	private final ModelRenderer Head;
	private final ModelRenderer MouthRoof;
	private final ModelRenderer LowerJaw;
	private final ModelRenderer FrontLeftLeg;
	private final ModelRenderer FrontRightLeg;
	private final ModelRenderer BackLeftLeg;
	private final ModelRenderer BackRightLeg;

    public BlindsightModel() {
        textureWidth = 64;
        textureHeight = 64;

        all = new ModelRenderer(this);
        all.setRotationPoint(0.0F, 24.0F, -3.0F);


        Body = new ModelRenderer(this);
        Body.setRotationPoint(0.0F, -4.0F, 7.0F);
        all.addChild(Body);


        Head = new ModelRenderer(this);
        Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        Body.addChild(Head);
        setRotationAngle(Head, -0.3927F, 0.0F, 0.0F);
        Head.setTextureOffset(0, 0).addBox(-8.0F, -8.0F, -12.0F, 16.0F, 8.0F, 12.0F, 0.0F, false);

        MouthRoof = new ModelRenderer(this);
        MouthRoof.setRotationPoint(0.0F, 6.0F, -8.0F);
        Head.addChild(MouthRoof);
        setRotationAngle(MouthRoof, -1.5708F, 0.0F, 0.0F);
        MouthRoof.setTextureOffset(32, 41).addBox(-8.0F, -8.0F, -7.0F, 16.0F, 12.0F, 0.0F, 0.0F, false);

        LowerJaw = new ModelRenderer(this);
        LowerJaw.setRotationPoint(0.0F, 0.0F, 0.0F);
        Body.addChild(LowerJaw);
        LowerJaw.setTextureOffset(0, 20).addBox(-8.0F, -1.0F, -12.0F, 16.0F, 3.0F, 12.0F, 0.0F, false);
        LowerJaw.setTextureOffset(4, 41).addBox(-8.0F, 0.0F, -12.0F, 16.0F, 0.0F, 12.0F, 0.0F, false);

        FrontLeftLeg = new ModelRenderer(this);
        FrontLeftLeg.setRotationPoint(6.0F, -2.0F, -3.0F);
        all.addChild(FrontLeftLeg);
        FrontLeftLeg.setTextureOffset(0, 35).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.001F, false);

        FrontRightLeg = new ModelRenderer(this);
        FrontRightLeg.setRotationPoint(-6.0F, -2.0F, -3.0F);
        all.addChild(FrontRightLeg);
        FrontRightLeg.setTextureOffset(8, 35).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.001F, false);

        BackLeftLeg = new ModelRenderer(this);
        BackLeftLeg.setRotationPoint(7.0F, -2.0F, 5.0F);
        all.addChild(BackLeftLeg);
        BackLeftLeg.setTextureOffset(44, 56).addBox(-1.0F, 0.0F, -5.0F, 4.0F, 2.0F, 6.0F, 0.001F, true);

        BackRightLeg = new ModelRenderer(this);
        BackRightLeg.setRotationPoint(-7.0F, -2.0F, 5.0F);
        all.addChild(BackRightLeg);
        BackRightLeg.setTextureOffset(44, 56).addBox(-3.0F, 0.0F, -5.0F, 4.0F, 2.0F, 6.0F, 0.001F, false);
	}

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float partialTick = ageInTicks - (float) entityIn.ticksExisted;

        float jumpRotation = MathHelper.sin(entityIn.getJumpCompletion(partialTick) * (float) Math.PI);
        this.BackLeftLeg.rotateAngleX = jumpRotation * 50.0F * ((float) Math.PI / 180F);
        this.BackRightLeg.rotateAngleX = jumpRotation * 50.0F * ((float) Math.PI / 180F);
    }

	@Override
	public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
		this.Head.rotateAngleX = -MathHelper.abs(MathHelper.cos(0.4662F * limbSwing) * 1.2F * limbSwingAmount);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		all.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
