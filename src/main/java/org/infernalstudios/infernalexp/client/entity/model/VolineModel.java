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
import org.infernalstudios.infernalexp.entities.VolineEntity;

public class VolineModel<T extends VolineEntity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(InfernalExpansion.MOD_ID, "voline"), "main");

    private final ModelPart body;
    private final ModelPart mouth;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;

    public VolineModel(ModelPart root) {
        this.body = root.getChild("body");
        this.mouth = root.getChild("mouth");
        this.leg1 = root.getChild("leg_1");
        this.leg2 = root.getChild("leg_2");
        this.leg3 = root.getChild("leg_3");
        this.leg4 = root.getChild("leg_4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 4.0F));

        PartDefinition mouth = partDefinition.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition mouthInside = mouth.addOrReplaceChild("mouth_inside", CubeListBuilder.create().texOffs(0, 27).addBox(-4.0F, -4.0F, 1.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(8, 27).addBox(0.0F, -4.0F, 1.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(16, 27).addBox(0.0F, 0.0F, 1.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(24, 27).addBox(-4.0F, 0.0F, 1.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition mouthInside2 = body.addOrReplaceChild("mouth_inside_2", CubeListBuilder.create().texOffs(0, 27).addBox(-4.0F, -4.0F, 1.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(8, 27).addBox(0.0F, -4.0F, 1.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(16, 27).addBox(0.0F, 0.0F, 1.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(24, 27).addBox(-4.0F, 0.0F, 1.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -4.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition leg1 = partDefinition.addOrReplaceChild("leg_1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 22.0F, -2.0F));

        PartDefinition leg2 = partDefinition.addOrReplaceChild("leg_2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 22.0F, -2.0F));

        PartDefinition leg3 = partDefinition.addOrReplaceChild("leg_3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 22.0F, 2.0F));

        PartDefinition leg4 = partDefinition.addOrReplaceChild("leg_4", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 22.0F, 2.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        leg1.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        leg2.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        leg3.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        leg4.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        body.xRot = -Mth.abs(Mth.cos(limbSwing * 0.4662F) * 1.4F * limbSwingAmount);

        if (entityIn.isEating()) {
            body.xRot = (Mth.cos(ageInTicks) * (float) Math.PI * 0.1F);
        }

    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        mouth.render(matrixStack, buffer, packedLight, packedOverlay);
        leg1.render(matrixStack, buffer, packedLight, packedOverlay);
        leg2.render(matrixStack, buffer, packedLight, packedOverlay);
        leg3.render(matrixStack, buffer, packedLight, packedOverlay);
        leg4.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
