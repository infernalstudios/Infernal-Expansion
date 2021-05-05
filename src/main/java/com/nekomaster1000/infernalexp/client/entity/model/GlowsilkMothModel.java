package com.nekomaster1000.infernalexp.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import com.nekomaster1000.infernalexp.entities.GlowsilkMothEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class GlowsilkMothModel<T extends GlowsilkMothEntity> extends EntityModel<T> {
	private final ModelRenderer all;
	private final ModelRenderer body;
	private final ModelRenderer legs3_r1;
	private final ModelRenderer legs2_r1;
	private final ModelRenderer legs1_r1;
	private final ModelRenderer bone_r1;
	private final ModelRenderer antenna;
	private final ModelRenderer leftwing;
	private final ModelRenderer rightwing;

    public GlowsilkMothModel() {
        textureWidth = 64;
        textureHeight = 64;

        all = new ModelRenderer(this);
        all.setRotationPoint(-1.0F, 14.0F, 0.0F);
        setRotationAngle(all, 0.3927F, 0.0F, 0.0F);


        body = new ModelRenderer(this);
        body.setRotationPoint(1.0F, 0.0F, 0.0F);
        all.addChild(body);


        legs3_r1 = new ModelRenderer(this);
        legs3_r1.setRotationPoint(0.0F, 3.0F, -2.0F);
        body.addChild(legs3_r1);
        setRotationAngle(legs3_r1, 0.7854F, 0.0F, 0.0F);
        legs3_r1.setTextureOffset(48, 60).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 2.0F, 0.0F, false);

        legs2_r1 = new ModelRenderer(this);
        legs2_r1.setRotationPoint(0.0F, 0.0F, -2.0F);
        body.addChild(legs2_r1);
        setRotationAngle(legs2_r1, 0.7854F, 0.0F, 0.0F);
        legs2_r1.setTextureOffset(48, 60).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 2.0F, 0.0F, false);

        legs1_r1 = new ModelRenderer(this);
        legs1_r1.setRotationPoint(0.0F, -3.0F, -2.0F);
        body.addChild(legs1_r1);
        setRotationAngle(legs1_r1, 0.7854F, 0.0F, 0.0F);
        legs1_r1.setTextureOffset(48, 60).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 2.0F, 0.0F, false);

        bone_r1 = new ModelRenderer(this);
        bone_r1.setRotationPoint(0.0F, 10.0F, 0.0F);
        body.addChild(bone_r1);
        setRotationAngle(bone_r1, 0.0F, 3.1416F, 0.0F);
        bone_r1.setTextureOffset(48, 44).addBox(-2.0F, -17.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        bone_r1.setTextureOffset(56, 60).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        antenna = new ModelRenderer(this);
        antenna.setRotationPoint(1.0F, -7.0F, -1.0F);
        all.addChild(antenna);
        antenna.setTextureOffset(51, 40).addBox(-3.0F, -4.0F, 0.0F, 6.0F, 4.0F, 0.0F, 0.0F, true);
        antenna.setTextureOffset(51, 36).addBox(-3.0F, -4.0F, 0.05F, 6.0F, 4.0F, 0.0F, 0.0F, true);

        leftwing = new ModelRenderer(this);
        leftwing.setRotationPoint(3.0F, 1.0F, 0.0F);
        all.addChild(leftwing);
        leftwing.setTextureOffset(0, 41).addBox(0.0F, -14.0F, 0.0F, 13.0F, 23.0F, 0.0F, 0.0F, true);
        leftwing.setTextureOffset(0, 17).addBox(0.0F, -14.0F, 0.05F, 13.0F, 23.0F, 0.0F, 0.0F, true);

        rightwing = new ModelRenderer(this);
        rightwing.setRotationPoint(-1.0F, 1.0F, 0.0F);
        all.addChild(rightwing);
        rightwing.setTextureOffset(0, 41).addBox(-13.0F, -14.0F, 0.0F, 13.0F, 23.0F, 0.0F, 0.0F, false);
        rightwing.setTextureOffset(0, 17).addBox(-13.0F, -14.0F, 0.05F, 13.0F, 23.0F, 0.0F, 0.0F, false);
	}

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        // Flap wings back and forth constantly
        this.leftwing.rotateAngleY = MathHelper.cos(0.75F * ageInTicks);
        this.rightwing.rotateAngleY = -MathHelper.cos(0.75F * ageInTicks);
        this.antenna.rotateAngleX = MathHelper.cos(0.2F * ageInTicks) * 0.2F;
        this.all.rotateAngleX = 0.3927F;
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
