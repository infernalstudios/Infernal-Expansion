/*
 * Copyright 2022 Infernal Studios
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.infernalstudios.infernalexp.client.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.entities.CerobeetleEntity;
import org.jetbrains.annotations.NotNull;

public class CerobeetleModel<T extends CerobeetleEntity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(InfernalExpansion.MOD_ID, "cerobeetle"), "main");

    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leftShield;
    private final ModelPart leftWing;
    private final ModelPart leftWing2;
    private final ModelPart rightShield;
    private final ModelPart rightWing;
    private final ModelPart rightWing2;
    private final ModelPart leftLeg1;
    private final ModelPart leftLeg2;
    private final ModelPart leftLeg3;
    private final ModelPart rightLeg1;
    private final ModelPart rightLeg2;
    private final ModelPart rightLeg3;

    public CerobeetleModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.leftShield = root.getChild("left_shield");
        this.leftWing = root.getChild("left_wing");
        this.leftWing2 = root.getChild("left_wing_2");
        this.rightShield = root.getChild("right_shield");
        this.rightWing = root.getChild("right_wing");
        this.rightWing2 = root.getChild("right_wing_2");
        this.leftLeg1 = root.getChild("left_leg_1");
        this.leftLeg2 = root.getChild("left_leg_2");
        this.leftLeg3 = root.getChild("left_leg_3");
        this.rightLeg1 = root.getChild("right_leg_1");
        this.rightLeg2 = root.getChild("right_leg_2");
        this.rightLeg3 = root.getChild("right_leg_3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -12.0F, -18.0F, 20.0F, 12.0F, 34.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, 0.0F));
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(86, 16).addBox(-6.0F, -4.0F, -8.0F, 12.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 31).addBox(0.0F, -17.0F, -16.0F, 0.0F, 19.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, -18.0F));
        partdefinition.addOrReplaceChild("left_shield", CubeListBuilder.create().texOffs(56, 58).addBox(-1.0F, -3.0F, 0.0F, 12.0F, 12.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 10.0F, -12.0F));
        partdefinition.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(94, 41).addBox(-4.0F, 0.0F, -1.0F, 10.0F, 0.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 10.0F, -11.0F));
        partdefinition.addOrReplaceChild("left_wing_2", CubeListBuilder.create().texOffs(82, 60).addBox(-5.0F, 0.0F, 0.0F, 12.0F, 0.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 10.0F, -10.0F));
        partdefinition.addOrReplaceChild("right_shield", CubeListBuilder.create().texOffs(0, 46).addBox(-11.0F, -3.0F, 0.0F, 12.0F, 12.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 10.0F, -12.0F));
        partdefinition.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(94, 41).mirror().addBox(-6.0F, 0.0F, -2.0F, 10.0F, 0.0F, 19.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 10.0F, -10.0F));
        partdefinition.addOrReplaceChild("right_wing_2", CubeListBuilder.create().texOffs(82, 60).mirror().addBox(-7.0F, 0.0F, 0.0F, 12.0F, 0.0F, 30.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 10.0F, -10.0F));
        partdefinition.addOrReplaceChild("left_leg_1", CubeListBuilder.create().texOffs(62, 12).addBox(0.0F, 0.0F, -7.5F, 10.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, 20.0F, -14.5F, 0.0F, 0.0F, 0.3491F));
        partdefinition.addOrReplaceChild("left_leg_2", CubeListBuilder.create().texOffs(62, 0).addBox(0.0F, 0.0F, -7.5F, 10.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, 20.0F, 1.5F, 0.0F, 0.0F, 0.3927F));
        partdefinition.addOrReplaceChild("left_leg_3", CubeListBuilder.create().texOffs(44, 58).addBox(0.0F, 0.0F, -5.5F, 10.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, 20.0F, 11.5F, 0.0F, 0.0F, 0.3491F));
        partdefinition.addOrReplaceChild("right_leg_1", CubeListBuilder.create().texOffs(44, 46).addBox(-10.0F, 0.0F, -7.5F, 10.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, 20.0F, -14.5F, 0.0F, 0.0F, -0.3491F));
        partdefinition.addOrReplaceChild("right_leg_2", CubeListBuilder.create().texOffs(0, 12).addBox(-10.0F, 0.0F, -7.5F, 10.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, 20.0F, 1.5F, 0.0F, 0.0F, -0.3491F));
        partdefinition.addOrReplaceChild("right_leg_3", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, 0.0F, -5.5F, 10.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, 20.0F, 11.5F, 0.0F, 0.0F, -0.3491F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(CerobeetleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
/*
            int i = entity.getAttackTimer();
            if(i <= 0){
            this.head.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
         }
*/
        leftLeg1.zRot = Mth.cos(limbSwing * 1.6662F) * 1.4F * limbSwingAmount;
        leftLeg2.zRot = Mth.cos(limbSwing * 1.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        leftLeg3.zRot = Mth.cos(limbSwing * 1.6662F) * 1.4F * limbSwingAmount;
        rightLeg1.zRot = Mth.cos(limbSwing * 1.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        rightLeg2.zRot = Mth.cos(limbSwing * 1.6662F) * 1.4F * limbSwingAmount;
        rightLeg3.zRot = Mth.cos(limbSwing * 1.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;

        if (!entity.isOnGround()) {
            entity.shellRotationMultiplier += 0.1F;

            if (entity.shellRotationMultiplier > 1.0F) {
                entity.shellRotationMultiplier = 1.0F;
            }

            float wingRotation = Mth.cos(ageInTicks * 2.1F) * (float) Math.PI * 0.15F + 1.0F;

            setRotationAngle(leftWing, wingRotation, 0.5F, 0.3F);
            setRotationAngle(rightWing, wingRotation, -0.5F, -0.3F);
            setRotationAngle(leftWing2, wingRotation, 0.4F, 0.2F);
            setRotationAngle(rightWing2, wingRotation, -0.4F, -0.2F);


        } else {
            entity.shellRotationMultiplier -= 0.1F;

            if (entity.shellRotationMultiplier < 0.0F) {
                entity.shellRotationMultiplier = 0.0F;
            }

            setRotationAngle(leftWing, 0.0F, 0.0F, 0.0F);
            setRotationAngle(rightWing, 0.0F, 0.0F, 0.0F);
            setRotationAngle(leftWing2, 0.0F, 0.0F, 0.0F);
            setRotationAngle(rightWing2, 0.0F, -0.0F, -0.0F);

        }

        setRotationAngle(leftShield, 1.2F * entity.shellRotationMultiplier, -0.4F * entity.shellRotationMultiplier, 0.9F * entity.shellRotationMultiplier);
        setRotationAngle(rightShield, 1.2F * entity.shellRotationMultiplier, 0.4F * entity.shellRotationMultiplier, -0.9F * entity.shellRotationMultiplier);
    }


    @Override
    public void renderToBuffer(@NotNull PoseStack matrixStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        head.render(matrixStack, buffer, packedLight, packedOverlay);
        leftShield.render(matrixStack, buffer, packedLight, packedOverlay);
        leftWing.render(matrixStack, buffer, packedLight, packedOverlay);
        leftWing2.render(matrixStack, buffer, packedLight, packedOverlay);
        rightShield.render(matrixStack, buffer, packedLight, packedOverlay);
        rightWing.render(matrixStack, buffer, packedLight, packedOverlay);
        rightWing2.render(matrixStack, buffer, packedLight, packedOverlay);
        leftLeg1.render(matrixStack, buffer, packedLight, packedOverlay);
        leftLeg2.render(matrixStack, buffer, packedLight, packedOverlay);
        leftLeg3.render(matrixStack, buffer, packedLight, packedOverlay);
        rightLeg1.render(matrixStack, buffer, packedLight, packedOverlay);
        rightLeg2.render(matrixStack, buffer, packedLight, packedOverlay);
        rightLeg3.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public void prepareMobModel(CerobeetleEntity entity, float limbSwing, float limbSwingAmount, float partialTick) {

        int i = entity.getAttackTimer();
        if (i > 0) {
            head.xRot = -0.9F + 0.9F * Mth.triangleWave((float) i - partialTick, 10.0F);
        }
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
