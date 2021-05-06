package com.nekomaster1000.infernalexp.client.entity.model;

import com.google.common.collect.ImmutableList;

import com.nekomaster1000.infernalexp.entities.GlowsquitoEntity;

import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class GlowsquitoModel<T extends GlowsquitoEntity> extends AgeableModel<T> {
    private final ModelRenderer Head;
    private final ModelRenderer Stinger;
    private final ModelRenderer Body;
    private final ModelRenderer LeftWing;
    private final ModelRenderer RightWing;
    private final ModelRenderer Butt;
    private final ModelRenderer LeftArm;
    private final ModelRenderer RightArm;
    private final ModelRenderer LeftLeg;
    private final ModelRenderer RightLeg;

    public GlowsquitoModel() {
        super(true, 6.0F, 2.0F, 2.0F, 2.3F, 24.0F);
        textureWidth = 64;
        textureHeight = 64;

        Head = new ModelRenderer(this);
        Head.setRotationPoint(0.0F, 19.6F, -4.0F);
        Head.setTextureOffset(26, 19).addBox(-2.0F, -2.6F, -4.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

        Stinger = new ModelRenderer(this);
        Stinger.setRotationPoint(0.0F, 19.0F, -7.0F);
        setRotationAngle(Stinger, 0.3927F, 0.0F, 0.0F);
        Stinger.setTextureOffset(0, 0).addBox(-0.5F, 0.0F, -6.5F, 1.0F, 0.0F, 7.0F, 0.0F, false);

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0.0F, 19.5F, -4.0F);
        setRotationAngle(Body, -0.1745F, 0.0F, 0.0F);
        Body.setTextureOffset(0, 17).addBox(-3.0F, -4.5F, -0.636F, 6.0F, 5.0F, 6.0F, 0.0F, false);

        LeftWing = new ModelRenderer(this);
        LeftWing.setRotationPoint(2.3F, 14.7F, -3.0F);
        setRotationAngle(LeftWing, 0.6545F, 0.2182F, 0.0F);
        LeftWing.setTextureOffset(27, 9).addBox(0.0F, -0.01F, -0.6F, 5.0F, 0.0F, 9.0F, 0.0F, false);

        RightWing = new ModelRenderer(this);
        RightWing.setRotationPoint(-2.3F, 14.7F, -3.0F);
        setRotationAngle(RightWing, 0.6545F, -0.2182F, 0.0F);
        RightWing.setTextureOffset(17, 0).addBox(-5.0F, -0.01F, -0.6F, 5.0F, 0.0F, 9.0F, 0.0F, false);

        Butt = new ModelRenderer(this);
        Butt.setRotationPoint(0.0F, 18.3218F, 2.1213F);
        setRotationAngle(Butt, -0.2618F, 0.0F, 0.0F);
        Butt.setTextureOffset(0, 0).addBox(-4.0F, -3.2218F, -1.6213F, 8.0F, 7.0F, 10.0F, 0.0F, false);

        LeftArm = new ModelRenderer(this);
        LeftArm.setRotationPoint(3.0F, 19.0F, -3.5F);
        LeftArm.setTextureOffset(2, 0).addBox(0.01F, 0.0F, -0.5F, 0.0F, 8.0F, 1.0F, 0.0F, false);

        RightArm = new ModelRenderer(this);
        RightArm.setRotationPoint(-3.0F, 19.0F, -3.5F);
        RightArm.setTextureOffset(4, 0).addBox(-0.01F, 0.0F, -0.5F, 0.0F, 8.0F, 1.0F, 0.0F, false);

        LeftLeg = new ModelRenderer(this);
        LeftLeg.setRotationPoint(3.0F, 21.0F, -1.5F);
        LeftLeg.setTextureOffset(6, 0).addBox(0.01F, -1.5F, -0.5F, 0.0F, 8.0F, 1.0F, 0.0F, false);

        RightLeg = new ModelRenderer(this);
        RightLeg.setRotationPoint(-3.0F, 21.0F, -1.5F);
        RightLeg.setTextureOffset(0, 0).addBox(-0.01F, -1.5F, -0.5F, 0.0F, 8.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        // Flap wings back and forth constantly
        this.LeftWing.rotateAngleZ = MathHelper.cos(2.0F * ageInTicks);
        this.RightWing.rotateAngleZ = -MathHelper.cos(2.0F * ageInTicks);
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of(this.Head, this.Stinger);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(this.Body, this.Butt, this.RightWing, this.LeftWing,
                this.RightArm, this.LeftArm, this.RightLeg, this.LeftLeg);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
