package com.nekomaster1000.infernalexp.client.entity.model;// Made with Blockbench 3.8.2
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate All required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.nekomaster1000.infernalexp.entities.GlowsilkMothEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class GlowsilkMothModel<T extends GlowsilkMothEntity> extends EntityModel<T> {
	private final ModelRenderer All;
	private final ModelRenderer Body;
	private final ModelRenderer bone_r1;
	private final ModelRenderer Antennae;
	private final ModelRenderer LeftWing;
	private final ModelRenderer RightWing;

	public GlowsilkMothModel() {
		textureWidth = 66;
		textureHeight = 34;

		All = new ModelRenderer(this);
		All.setRotationPoint(-1.0F, 14.0F, 0.0F);
		setRotationAngle(All, 0.3927F, 0.0F, 0.0F);


		Body = new ModelRenderer(this);
		Body.setRotationPoint(1.0F, 0.0F, 0.0F);
		All.addChild(Body);


		bone_r1 = new ModelRenderer(this);
		bone_r1.setRotationPoint(0.0F, 10.0F, 0.0F);
		Body.addChild(bone_r1);
		setRotationAngle(bone_r1, 0.0F, 3.1416F, 0.0F);
		bone_r1.setTextureOffset(27, 9).addBox(-2.0F, -17.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		bone_r1.setTextureOffset(27, 1).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		Antennae = new ModelRenderer(this);
		Antennae.setRotationPoint(1.0F, -7.0F, 0.0F);
		All.addChild(Antennae);
		Antennae.setTextureOffset(36, 4).addBox(0.0F, -5.0F, 0.0F, 4.0F, 5.0F, 0.0F, 0.0F, true);
		Antennae.setTextureOffset(36, 4).addBox(-4.0F, -5.0F, 0.0F, 4.0F, 5.0F, 0.0F, 0.0F, false);

		LeftWing = new ModelRenderer(this);
		LeftWing.setRotationPoint(3.0F, 1.0F, 0.0F);
		All.addChild(LeftWing);
		LeftWing.setTextureOffset(1, 1).addBox(0.0F, -14.0F, 0.0F, 13.0F, 23.0F, 0.0F, 0.0F, true);

		RightWing = new ModelRenderer(this);
		RightWing.setRotationPoint(-1.0F, 1.0F, 0.0F);
		All.addChild(RightWing);
		RightWing.setTextureOffset(1, 1).addBox(-13.0F, -14.0F, 0.0F, 13.0F, 23.0F, 0.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		// Flap wings back and forth constantly
			this.LeftWing.rotateAngleY = MathHelper.cos(0.75F * ageInTicks);
			this.RightWing.rotateAngleY = -MathHelper.cos(0.75F * ageInTicks);
			this.Antennae.rotateAngleX = MathHelper.cos(0.2F * ageInTicks) * 0.2F;
			this.All.rotateAngleX = 0.3927F;
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		All.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}