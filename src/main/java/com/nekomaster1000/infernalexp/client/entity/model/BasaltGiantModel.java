package com.nekomaster1000.infernalexp.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.nekomaster1000.infernalexp.entities.BasaltGiantEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class BasaltGiantModel<T extends BasaltGiantEntity> extends EntityModel<BasaltGiantEntity> {
    private final ModelRenderer Body;
    private final ModelRenderer Torso;
    private final ModelRenderer Torso2;
    private final ModelRenderer Head;
    private final ModelRenderer Jaw;
    private final ModelRenderer Arm1;
    private final ModelRenderer ArmJoint1;
    private final ModelRenderer Arm2;
    private final ModelRenderer ArmJoint2;
    private final ModelRenderer Leg1;
    private final ModelRenderer LegJoint1;
    private final ModelRenderer Leg2;
    private final ModelRenderer LegJoint2;

    public BasaltGiantModel() {
        textureWidth = 128;
        textureHeight = 128;

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0.0F, 24.0F, 0.0F);


        Torso = new ModelRenderer(this);
        Torso.setRotationPoint(0.0F, -36.0F, 4.0F);
        Body.addChild(Torso);
        Torso.setTextureOffset(4, 51).addBox(-9.0F, -15.0F, -4.0F, 18.0F, 15.0F, 8.0F, 0.0F, false);

        Torso2 = new ModelRenderer(this);
        Torso2.setRotationPoint(0.0F, -15.0F, -1.0F);
        Torso.addChild(Torso2);
        Torso2.setTextureOffset(25, 85).addBox(-1.0F, -26.0F, 0.0F, 4.0F, 11.0F, 4.0F, 0.0F, false);
        Torso2.setTextureOffset(57, 43).addBox(-7.0F, -24.0F, -5.0F, 3.0F, 9.0F, 3.0F, 0.0F, false);
        Torso2.setTextureOffset(57, 0).addBox(5.0F, -24.0F, 2.0F, 3.0F, 7.0F, 3.0F, 0.0F, false);
        Torso2.setTextureOffset(57, 23).addBox(6.0F, -26.0F, -5.0F, 3.0F, 11.0F, 3.0F, 0.0F, false);
        Torso2.setTextureOffset(24, 101).addBox(-9.0F, -28.0F, 1.0F, 5.0F, 11.0F, 5.0F, 0.0F, false);
        Torso2.setTextureOffset(0, 50).addBox(-1.0F, -21.0F, -4.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
        Torso2.setTextureOffset(57, 43).addBox(-10.0F, -17.0F, -7.0F, 20.0F, 17.0F, 14.0F, 0.0F, false);

        Head = new ModelRenderer(this);
        Head.setRotationPoint(0.5F, -4.0F, -7.0F);
        Torso2.addChild(Head);
        Head.setTextureOffset(57, 0).addBox(-7.0F, -8.0F, -12.0F, 13.0F, 10.0F, 12.0F, 0.0F, false);

        Jaw = new ModelRenderer(this);
        Jaw.setRotationPoint(0.0F, 2.0F, 0.0F);
        Head.addChild(Jaw);
        Jaw.setTextureOffset(57, 23).addBox(-9.0F, 0.0F, -14.0F, 17.0F, 5.0F, 14.0F, 0.0F, false);

        Arm1 = new ModelRenderer(this);
        Arm1.setRotationPoint(12.0F, -11.0F, -1.0F);
        Torso2.addChild(Arm1);
        setRotationAngle(Arm1, 0.0F, 0.0F, -0.0436F);
        Arm1.setTextureOffset(105, 29).addBox(-1.0F, -8.0F, -2.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
        Arm1.setTextureOffset(105, 29).addBox(1.0F, -6.0F, 0.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
        Arm1.setTextureOffset(104, 105).addBox(-2.0F, -4.0F, -3.0F, 6.0F, 17.0F, 6.0F, 0.0F, true);

        ArmJoint1 = new ModelRenderer(this);
        ArmJoint1.setRotationPoint(1.0F, 12.0F, 0.0F);
        Arm1.addChild(ArmJoint1);
        ArmJoint1.setTextureOffset(49, 75).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 17.0F, 8.0F, 0.0F, true);
        ArmJoint1.setTextureOffset(88, 80).addBox(-5.0F, 17.0F, -5.0F, 10.0F, 10.0F, 10.0F, 0.0F, false);

        Arm2 = new ModelRenderer(this);
        Arm2.setRotationPoint(-13.0F, -11.0F, -0.9F);
        Torso2.addChild(Arm2);
        setRotationAngle(Arm2, 0.0F, 0.0F, 0.0436F);
        Arm2.setTextureOffset(105, 23).addBox(0.0F, -8.0F, -0.1F, 2.0F, 4.0F, 2.0F, 0.0F, false);
        Arm2.setTextureOffset(105, 23).addBox(-2.0F, -6.0F, -2.1F, 2.0F, 4.0F, 2.0F, 0.0F, false);
        Arm2.setTextureOffset(104, 105).addBox(-3.0F, -4.0F, -3.1F, 6.0F, 17.0F, 6.0F, 0.0F, false);

        ArmJoint2 = new ModelRenderer(this);
        ArmJoint2.setRotationPoint(0.0F, 12.0F, 0.0F);
        Arm2.addChild(ArmJoint2);
        ArmJoint2.setTextureOffset(49, 75).addBox(-4.0F, 0.0F, -4.1F, 8.0F, 17.0F, 8.0F, 0.0F, false);
        ArmJoint2.setTextureOffset(88, 80).addBox(-5.0F, 17.0F, -5.1F, 10.0F, 10.0F, 10.0F, 0.0F, true);

        Leg1 = new ModelRenderer(this);
        Leg1.setRotationPoint(-5.0F, -36.0F, 4.0F);
        Body.addChild(Leg1);
        Leg1.setTextureOffset(79, 104).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 18.0F, 6.0F, 0.0F, true);

        LegJoint1 = new ModelRenderer(this);
        LegJoint1.setRotationPoint(10.0F, 17.0F, 0.0F);
        Leg1.addChild(LegJoint1);
        LegJoint1.setTextureOffset(46, 101).addBox(-14.0F, 0.0F, -4.0F, 8.0F, 19.0F, 8.0F, 0.0F, true);

        Leg2 = new ModelRenderer(this);
        Leg2.setRotationPoint(5.0F, -36.0F, 4.0F);
        Body.addChild(Leg2);
        Leg2.setTextureOffset(79, 104).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 18.0F, 6.0F, 0.0F, false);

        LegJoint2 = new ModelRenderer(this);
        LegJoint2.setRotationPoint(0.0F, 17.0F, 0.0F);
        Leg2.addChild(LegJoint2);
        LegJoint2.setTextureOffset(46, 101).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 19.0F, 8.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(BasaltGiantEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}