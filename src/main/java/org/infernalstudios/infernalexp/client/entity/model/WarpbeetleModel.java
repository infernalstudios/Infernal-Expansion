/*
 * Copyright 2021 Infernal Studios
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
import org.infernalstudios.infernalexp.entities.WarpbeetleEntity;

@SuppressWarnings("unused")
public class WarpbeetleModel<T extends WarpbeetleEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(InfernalExpansion.MOD_ID, "warpbeetle"), "main");

    private final ModelPart all;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leftWing;
    private final ModelPart leftShield;
    private final ModelPart rightWing;
    private final ModelPart rightShield;
    private final ModelPart leftLeg1;
    private final ModelPart leftLeg2;
    private final ModelPart leftLeg3;
    private final ModelPart rightLeg1;
    private final ModelPart rightLeg2;
    private final ModelPart rightLeg3;

    public WarpbeetleModel(ModelPart root) {
        this.all = root.getChild("all");
        this.body = all.getChild("body");
        this.head = body.getChild("head");
        this.leftWing = body.getChild("left_wing");
        this.leftShield = body.getChild("left_shield");
        this.rightWing = body.getChild("right_wing");
        this.rightShield = body.getChild("right_shield");
        this.leftLeg1 = body.getChild("left_leg_1");
        this.leftLeg2 = body.getChild("left_leg_2");
        this.leftLeg3 = body.getChild("left_leg_3");
        this.rightLeg1 = body.getChild("right_leg_1");
        this.rightLeg2 = body.getChild("right_leg_2");
        this.rightLeg3 = body.getChild("right_leg_3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition all = partDefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 23.75F, 0.0F));
        PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -5.75F, -9.0F, 10.0F, 6.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));
        body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(43, 8).addBox(-3.0F, -1.75F, -4.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(0, 15).addBox(0.0F, -8.75F, -10.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -9.0F));
        body.addOrReplaceChild("left_shield", CubeListBuilder.create().texOffs(28, 29).addBox(-5.0F, -1.75F, 0.0F, 6.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -6.0F, -6.0F));
        body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(41, 30).addBox(-3.0F, 0.24F, 0.0F, 6.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -6.0F, -5.0F));
        body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(41, 30).mirror().addBox(-3.0F, 0.24F, 0.0F, 6.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, -6.0F, -5.0F));
        body.addOrReplaceChild("right_shield", CubeListBuilder.create().texOffs(0, 23).addBox(-1.0F, -1.75F, 0.0F, 6.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -6.0F, -6.0F));
        body.addOrReplaceChild("left_leg_1", CubeListBuilder.create().texOffs(31, 6).addBox(0.0F, 0.25F, -5.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -1.0F, -6.0F, 0.0F, 0.0F, 0.3927F));
        body.addOrReplaceChild("left_leg_2", CubeListBuilder.create().texOffs(31, 0).addBox(0.0F, 0.25F, -1.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -1.0F, -2.0F, 0.0F, 0.0F, 0.3927F));
        body.addOrReplaceChild("left_leg_3", CubeListBuilder.create().texOffs(22, 29).addBox(0.0F, 0.25F, -1.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -1.0F, 4.0F, 0.0F, 0.0F, 0.3927F));
        body.addOrReplaceChild("right_leg_1", CubeListBuilder.create().texOffs(22, 23).addBox(-5.0F, 0.25F, -5.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -1.0F, -6.0F, 0.0F, 0.0F, -0.3927F));
        body.addOrReplaceChild("right_leg_2", CubeListBuilder.create().texOffs(0, 6).addBox(-5.0F, 0.25F, -1.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -1.0F, -2.0F, 0.0F, 0.0F, -0.3927F));
        body.addOrReplaceChild("right_leg_3", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 0.25F, -1.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -1.0F, 4.0F, 0.0F, 0.0F, -0.3927F));

        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        leftLeg1.zRot = -(Mth.abs(Mth.cos(limbSwing * 1.1F)) * 2.2F * limbSwingAmount) + 0.3927F;
        leftLeg2.zRot = -(Mth.abs(Mth.cos(Math.max(limbSwing - 4.0F, 0) * 1.1F)) * 2.2F * limbSwingAmount) + 0.3927F;
        leftLeg3.zRot = -(Mth.abs(Mth.cos(limbSwing * 1.1F)) * 2.2F * limbSwingAmount) + 0.3927F;
        rightLeg1.zRot = (Mth.abs(Mth.cos(Math.max(limbSwing - 4.0F, 0) * 1.1F)) * 2.2F * limbSwingAmount) - 0.3927F;
        rightLeg2.zRot = (Mth.abs(Mth.cos(limbSwing * 1.1F)) * 2.2F * limbSwingAmount) - 0.3927F;
        rightLeg3.zRot = (Mth.abs(Mth.cos(Math.max(limbSwing - 4.0F, 0) * 1.1F)) * 2.2F * limbSwingAmount) - 0.3927F;

        leftLeg1.yRot = -Mth.sin(limbSwing * 1.1F) * 0.8F * limbSwingAmount;
        leftLeg2.yRot = -Mth.sin(Math.max(limbSwing - 4.0F, 0) * 1.1F) * 0.9F * limbSwingAmount;
        leftLeg3.yRot = -Mth.sin(limbSwing * 1.1F) * 1.4F * limbSwingAmount;
        rightLeg1.yRot = Mth.sin(Math.max(limbSwing - 4.0F, 0) * 1.1F) * 0.8F * limbSwingAmount;
        rightLeg2.yRot = Mth.sin(limbSwing * 1.1F) * 0.9F * limbSwingAmount;
        rightLeg3.yRot = Mth.sin(Math.max(limbSwing - 4.0F, 0) * 1.1F) * 1.4F * limbSwingAmount;

        if (!entity.isOnGround()) {
            entity.shellRotationMultiplier += 0.1F;

            if (entity.shellRotationMultiplier > 1.0F) {
                entity.shellRotationMultiplier = 1.0F;
            }

            float wingRotation = Mth.cos(ageInTicks * 2.1F) * (float) Math.PI * 0.15F + 1.0F;

            setRotationAngle(leftWing, wingRotation, 0.5F, 0.3F);
            setRotationAngle(rightWing, wingRotation, -0.5F, -0.3F);
        } else {
            entity.shellRotationMultiplier -= 0.1F;

            if (entity.shellRotationMultiplier < 0.0F) {
                entity.shellRotationMultiplier = 0.0F;
            }

            setRotationAngle(leftWing, 0.0F, 0.0F, 0.0F);
            setRotationAngle(rightWing, 0.0F, 0.0F, 0.0F);
        }

        setRotationAngle(leftShield, 1.2F * entity.shellRotationMultiplier, -0.4F * entity.shellRotationMultiplier, 0.9F * entity.shellRotationMultiplier);
        setRotationAngle(rightShield, 1.2F * entity.shellRotationMultiplier, 0.4F * entity.shellRotationMultiplier, -0.9F * entity.shellRotationMultiplier);
    }


    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        all.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
