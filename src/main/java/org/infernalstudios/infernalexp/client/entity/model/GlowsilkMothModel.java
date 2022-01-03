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
import org.infernalstudios.infernalexp.entities.GlowsilkMothEntity;

public class GlowsilkMothModel<T extends GlowsilkMothEntity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(InfernalExpansion.MOD_ID, "glowsilk_moth"), "main");

    private final ModelPart all;
    private final ModelPart antenna;
    private final ModelPart leftWing;
    private final ModelPart rightWing;

    public GlowsilkMothModel(ModelPart root) {
        this.all = root.getChild("all");
        this.antenna = all.getChild("antenna");
        this.leftWing = all.getChild("left_wing");
        this.rightWing = all.getChild("right_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 14.0F, 0.0F, 0.3927F, 0.0F, 0.0F));
        PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(1.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("legs_1", CubeListBuilder.create().texOffs(48, 60).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -2.0F, 0.7854F, 0.0F, 0.0F));
        body.addOrReplaceChild("legs_2", CubeListBuilder.create().texOffs(48, 60).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.7854F, 0.0F, 0.0F));
        body.addOrReplaceChild("legs_3", CubeListBuilder.create().texOffs(48, 60).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -2.0F, 0.7854F, 0.0F, 0.0F));
        body.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(48, 44).addBox(-2.0F, -17.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(56, 60).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
        all.addOrReplaceChild("antenna", CubeListBuilder.create().texOffs(51, 40).mirror().addBox(-3.0F, -4.0F, 0.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
            .texOffs(51, 36).mirror().addBox(-3.0F, -4.0F, 0.05F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.0F, -7.0F, -1.0F));
        all.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 41).mirror().addBox(0.0F, -14.0F, 0.0F, 13.0F, 23.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
            .texOffs(0, 17).mirror().addBox(0.0F, -14.0F, 0.05F, 13.0F, 23.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 1.0F, 0.0F));
        all.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 41).addBox(-13.0F, -14.0F, 0.0F, 13.0F, 23.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(0, 17).addBox(-13.0F, -14.0F, 0.05F, 13.0F, 23.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 1.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Flap wings back and forth constantly
        leftWing.yRot = Mth.cos(0.75F * ageInTicks);
        rightWing.yRot = -Mth.cos(0.75F * ageInTicks);
        antenna.xRot = Mth.cos(0.2F * ageInTicks) * 0.2F;
        all.xRot = 0.3927F;
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
