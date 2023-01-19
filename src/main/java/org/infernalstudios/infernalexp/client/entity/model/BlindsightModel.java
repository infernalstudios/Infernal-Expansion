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
import org.infernalstudios.infernalexp.entities.BlindsightEntity;
import org.jetbrains.annotations.NotNull;

public class BlindsightModel<T extends BlindsightEntity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(InfernalExpansion.MOD_ID, "blindsight"), "main");

    private final ModelPart all;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart backLeftLeg;
    private final ModelPart backRightLeg;

    public BlindsightModel(ModelPart root) {
        this.all = root.getChild("all");
        this.body = all.getChild("body");
        this.head = body.getChild("head");
        this.backLeftLeg = all.getChild("back_left_leg");
        this.backRightLeg = all.getChild("back_right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition all = partDefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -3.0F));
        PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 7.0F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -12.0F, 16.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));
        head.addOrReplaceChild("mouth_roof", CubeListBuilder.create().texOffs(32, 41).addBox(-8.0F, -8.0F, -7.0F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, -8.0F, -1.5708F, 0.0F, 0.0F));
        body.addOrReplaceChild("lower_jaw", CubeListBuilder.create().texOffs(0, 20).addBox(-8.0F, -1.0F, -12.0F, 16.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
            .texOffs(4, 41).addBox(-8.0F, 0.0F, -12.0F, 16.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        all.addOrReplaceChild("front_left_leg", CubeListBuilder.create().texOffs(0, 35).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offset(6.0F, -2.0F, -3.0F));
        all.addOrReplaceChild("front_right_leg", CubeListBuilder.create().texOffs(8, 35).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offset(-6.0F, -2.0F, -3.0F));
        all.addOrReplaceChild("back_left_leg", CubeListBuilder.create().texOffs(44, 56).mirror().addBox(-1.0F, 0.0F, -5.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offset(7.0F, -2.0F, 5.0F));
        all.addOrReplaceChild("back_right_leg", CubeListBuilder.create().texOffs(44, 56).addBox(-3.0F, 0.0F, -5.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.001F)), PartPose.offset(-7.0F, -2.0F, 5.0F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float partialTick = ageInTicks - (float) entityIn.tickCount;

        float jumpRotation = Mth.sin(entityIn.getJumpCompletion(partialTick) * (float) Math.PI);
        backLeftLeg.xRot = jumpRotation * 50.0F * ((float) Math.PI / 180F);
        backRightLeg.xRot = jumpRotation * 50.0F * ((float) Math.PI / 180F);
    }

    @Override
    public void prepareMobModel(@NotNull T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        head.xRot = -Mth.abs(Mth.cos(0.4662F * limbSwing) * 1.2F * limbSwingAmount);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack matrixStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        all.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
