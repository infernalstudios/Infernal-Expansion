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
import org.infernalstudios.infernalexp.entities.PyrnoEntity;

public class PyrnoModel<T extends PyrnoEntity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(InfernalExpansion.MOD_ID, "pyrno"), "main");

    private final ModelPart body;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;

    public PyrnoModel(ModelPart root) {
        this.body = root.getChild("body");
        this.leg1 = body.getChild("leg_1");
        this.leg2 = body.getChild("leg_2");
        this.leg3 = body.getChild("leg_3");
        this.leg4 = body.getChild("leg_4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -14.0F, -14.0F, 16.0F, 14.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 1.0F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -13.0F, -19.0F, 4.0F, 16.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(0, 40).addBox(-8.0F, -3.0F, -15.0F, 16.0F, 8.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.0F, -14.0F, 0.6109F, 0.0F, 0.0F));
        head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(41, 41).addBox(-4.0F, -0.5351F, -0.1563F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.5F, -2.4649F, 0.1563F, 0.0F, 0.0F, 1.5708F));
        head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(8, 0).addBox(-1.0F, -0.5351F, -0.1563F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.35F, -2.4649F, 0.1563F, 0.0F, 0.0F, -1.5708F));
        body.addOrReplaceChild("leg_1", CubeListBuilder.create().texOffs(0, 63).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 0.0F, -11.0F));
        body.addOrReplaceChild("leg_2", CubeListBuilder.create().texOffs(62, 40).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 0.0F, -11.0F));
        body.addOrReplaceChild("leg_3", CubeListBuilder.create().texOffs(58, 0).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 0.0F, 9.0F));
        body.addOrReplaceChild("leg_4", CubeListBuilder.create().texOffs(56, 57).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 0.0F, 9.0F));
        body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 40).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.0F, 12.0F, 0.2618F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        leg1.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        leg2.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        leg3.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        leg4.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }

}
