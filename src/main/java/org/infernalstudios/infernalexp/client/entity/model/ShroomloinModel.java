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
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


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
import org.infernalstudios.infernalexp.entities.ShroomloinEntity;

public class ShroomloinModel<T extends ShroomloinEntity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(InfernalExpansion.MOD_ID, "shroomloin"), "main");

    private final ModelPart all;
    private final ModelPart body;
    private final ModelPart hat;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public ShroomloinModel(ModelPart root) {
        this.all = root.getChild("all");
        this.body = all.getChild("body");
        this.hat = body.getChild("hat");
        this.leftLeg = all.getChild("left_leg");
        this.rightLeg = all.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition all = partDefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(1, 23).addBox(-5.0F, -6.0F, -5.0F, 10.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));
        body.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -6.0F, -8.0F, 16.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -0.5F));
        all.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -4.0F, -0.5F));
        all.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 8).mirror().addBox(-1.5F, -1.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, -4.0F, -0.5F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        body.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.25F * limbSwingAmount;
        hat.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.5F * limbSwingAmount;
        leftLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        rightLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
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
