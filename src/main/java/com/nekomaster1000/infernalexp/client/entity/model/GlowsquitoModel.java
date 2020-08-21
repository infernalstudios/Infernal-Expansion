package com.nekomaster1000.infernalexp.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.nekomaster1000.infernalexp.entities.GlowsquitoEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class GlowsquitoModel<T extends GlowsquitoEntity> extends EntityModel<T> {
    private final ModelRenderer glowsquito;
    private final ModelRenderer head;
    private final ModelRenderer body;
    private final ModelRenderer left_wing;
    private final ModelRenderer right_wing;
    private final ModelRenderer leg_1;
    private final ModelRenderer leg_2;
    private final ModelRenderer leg_3;
    private final ModelRenderer leg_4;
    private final ModelRenderer leg_5;
    private final ModelRenderer leg_6;

    public GlowsquitoModel() {
        textureWidth = 64;
        textureHeight = 64;

        glowsquito = new ModelRenderer(this);
        glowsquito.setRotationPoint(0.0F, 24.0F, 0.0F);


        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -6.0F, -4.0F);
        glowsquito.addChild(head);
        head.setTextureOffset(21, 0).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);
        head.setTextureOffset(0, 0).addBox(-0.5F, 2.0F, -4.0F, 1.0F, 4.0F, 0.0F, 0.0F, false);

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, -7.0F, -1.0F);
        glowsquito.addChild(body);
        body.setTextureOffset(23, 23).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 4.0F, 3.0F, 0.0F, false);
        body.setTextureOffset(0, 0).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 7.0F, 9.0F, 0.0F, false);

        left_wing = new ModelRenderer(this);
        left_wing.setRotationPoint(0.0F, -3.0F, 1.0F);
        body.addChild(left_wing);
        left_wing.setTextureOffset(8, 16).addBox(0.0F, 0.0F, 0.0F, 4.0F, 0.0F, 10.0F, 0.0F, false);

        right_wing = new ModelRenderer(this);
        right_wing.setRotationPoint(0.0F, -3.0F, 1.0F);
        body.addChild(right_wing);
        right_wing.setTextureOffset(0, 16).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 0.0F, 10.0F, 0.0F, false);

        leg_1 = new ModelRenderer(this);
        leg_1.setRotationPoint(2.0F, -1.0F, -2.0F);
        body.addChild(leg_1);
        setRotationAngle(leg_1, 0.0F, 0.0F, -0.3491F);
        leg_1.setTextureOffset(0, 25).addBox(0.0F, 0.0F, 0.0F, 0.0F, 10.0F, 1.0F, 0.0F, false);

        leg_2 = new ModelRenderer(this);
        leg_2.setRotationPoint(3.0F, -1.0F, 2.0F);
        body.addChild(leg_2);
        setRotationAngle(leg_2, 0.0F, 0.0F, -0.3491F);
        leg_2.setTextureOffset(8, 15).addBox(0.0F, 0.0F, 0.0F, 0.0F, 10.0F, 1.0F, 0.0F, false);

        leg_3 = new ModelRenderer(this);
        leg_3.setRotationPoint(3.0F, -1.0F, 5.0F);
        body.addChild(leg_3);
        setRotationAngle(leg_3, 0.0F, 0.0F, -0.3491F);
        leg_3.setTextureOffset(6, 15).addBox(0.0F, 0.0F, 0.0F, 0.0F, 10.0F, 1.0F, 0.0F, false);

        leg_4 = new ModelRenderer(this);
        leg_4.setRotationPoint(-2.0F, -1.0F, -2.0F);
        body.addChild(leg_4);
        setRotationAngle(leg_4, 0.0F, 0.0F, 0.3491F);
        leg_4.setTextureOffset(4, 15).addBox(0.0F, 0.0F, 0.0F, 0.0F, 10.0F, 1.0F, 0.0F, false);

        leg_5 = new ModelRenderer(this);
        leg_5.setRotationPoint(-3.0F, -1.0F, 2.0F);
        body.addChild(leg_5);
        setRotationAngle(leg_5, 0.0F, 0.0F, 0.3491F);
        leg_5.setTextureOffset(2, 15).addBox(0.0F, 0.0F, 0.0F, 0.0F, 10.0F, 1.0F, 0.0F, false);

        leg_6 = new ModelRenderer(this);
        leg_6.setRotationPoint(-3.0F, -1.0F, 5.0F);
        body.addChild(leg_6);
        setRotationAngle(leg_6, 0.0F, 0.0F, 0.3491F);
        leg_6.setTextureOffset(0, 15).addBox(0.0F, 0.0F, 0.0F, 0.0F, 10.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }


    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        glowsquito.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
