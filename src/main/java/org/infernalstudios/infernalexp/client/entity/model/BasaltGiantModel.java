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
import org.infernalstudios.infernalexp.entities.BasaltGiantEntity;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("FieldCanBeLocal")
public class BasaltGiantModel<T extends BasaltGiantEntity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(InfernalExpansion.MOD_ID, "basalt_giant"), "main");

    private final ModelPart body;
    private final ModelPart torso;
    private final ModelPart torso2;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public BasaltGiantModel(ModelPart root) {
        this.body = root.getChild("body");
        this.torso = body.getChild("torso");
        this.torso2 = torso.getChild("torso_2");
        this.head = torso2.getChild("head");
        this.jaw = head.getChild("jaw");
        this.leftArm = torso2.getChild("left_arm");
        this.rightArm = torso2.getChild("right_arm");
        this.leftLeg = body.getChild("left_leg");
        this.rightLeg = body.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(4, 51).addBox(-9.0F, -15.0F, -4.0F, 18.0F, 15.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -36.0F, 4.0F));
        PartDefinition torso2 = torso.addOrReplaceChild("torso_2", CubeListBuilder.create().texOffs(25, 85).addBox(-1.0F, -26.0F, 0.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(57, 43).addBox(-7.0F, -24.0F, -5.0F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(57, 0).addBox(5.0F, -24.0F, 2.0F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(57, 23).addBox(6.0F, -26.0F, -5.0F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(24, 101).addBox(-9.0F, -28.0F, 1.0F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F))
            .texOffs(0, 50).addBox(-1.0F, -21.0F, -4.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(57, 43).addBox(-10.0F, -17.0F, -7.0F, 20.0F, 17.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, -1.0F));
            PartDefinition head = torso2.addOrReplaceChild("head", CubeListBuilder.create().texOffs(57, 0).addBox(-7.0F, -8.0F, -12.0F, 13.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -4.0F, -7.0F));
        head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(57, 23).addBox(-9.0F, 0.0F, -14.0F, 17.0F, 5.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));
        PartDefinition leftArm = torso2.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(105, 29).addBox(-1.0F, -8.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(105, 29).addBox(1.0F, -6.0F, 0.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(104, 105).mirror().addBox(-2.0F, -4.0F, -3.0F, 6.0F, 17.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(12.0F, -11.0F, -1.0F, 0.0F, 0.0F, -0.0436F));
            leftArm.addOrReplaceChild("left_arm_joint", CubeListBuilder.create().texOffs(49, 75).mirror().addBox(-4.0F, 0.0F, -4.0F, 8.0F, 17.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
            .texOffs(88, 80).addBox(-5.0F, 17.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 12.0F, 0.0F));
            PartDefinition rightArm = torso2.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(105, 23).addBox(0.0F, -8.0F, -0.1F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(105, 23).addBox(-2.0F, -6.0F, -2.1F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(104, 105).addBox(-3.0F, -4.0F, -3.1F, 6.0F, 17.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-13.0F, -11.0F, -0.9F, 0.0F, 0.0F, 0.0436F));
            rightArm.addOrReplaceChild("right_arm_joint", CubeListBuilder.create().texOffs(49, 75).addBox(-4.0F, 0.0F, -4.1F, 8.0F, 17.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(88, 80).mirror().addBox(-5.0F, 17.0F, -5.1F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 12.0F, 0.0F));
            PartDefinition leftLeg = body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(79, 104).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 18.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -36.0F, 4.0F));
        leftLeg.addOrReplaceChild("left_leg_joint", CubeListBuilder.create().texOffs(46, 101).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 19.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, 0.0F));
        PartDefinition rightLeg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(79, 104).mirror().addBox(-3.0F, 0.0F, -3.0F, 6.0F, 18.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, -36.0F, 4.0F));
        rightLeg.addOrReplaceChild("right_leg_joint", CubeListBuilder.create().texOffs(46, 101).mirror().addBox(-14.0F, 0.0F, -4.0F, 8.0F, 19.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(10.0F, 17.0F, 0.0F));
        
        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        int i = entity.getAttackTimer();
        if (i <= 0) {
            rightLeg.xRot = Mth.cos(limbSwing * 0.4F + (float) Math.PI) * 1.0F * limbSwingAmount;
            jaw.xRot = 0.0F;
        }

        leftArm.xRot = Mth.cos(limbSwing * 0.4F + (float) Math.PI) * 0.8F * limbSwingAmount;
        rightArm.xRot = Mth.cos(limbSwing * 0.4F) * 0.8F * limbSwingAmount;
        leftLeg.xRot = Mth.cos(limbSwing * 0.4F) * 1.0F * limbSwingAmount;
        torso2.xRot = Mth.cos(limbSwing * 0.3332F) * 0.25F * limbSwingAmount;
        head.xRot = Mth.cos(limbSwing * 0.1551F) * 0.15F * limbSwingAmount;

        /*
        TOP SECRET FOR RELEASE 3

        this.LeftArmJoint.rotateAngleX = -Math.abs(this.LeftArm.rotateAngleX * 0.6662F);
        this.RightArmJoint.rotateAngleX = -Math.abs(this.RightArm.rotateAngleX * 0.6662F);
        this.Body.rotateAngleZ = MathHelper.cos(limbSwing * 0.3332F + (float)Math.PI) * 0.4F * limbSwingAmount;
        this.LeftLegJoint.rotateAngleX = Math.abs(this.LeftLeg.rotateAngleX * 0.75F);

        THIS ONE NEEDS TO GO UP IN THE IF STATEMENT ABOVE

        this.RightLegJoint.rotateAngleX = Math.abs(this.RightLeg.rotateAngleX * 0.75F);
         */

    }

    @Override
    public void renderToBuffer(@NotNull PoseStack matrixStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public void prepareMobModel(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        int i = entityIn.getAttackTimer();
        if (i > 0) {
            rightLeg.xRot = -0.9F + 0.9F * Mth.triangleWave((float) i - partialTick, 10.0F);
            jaw.xRot = 0.375F - 0.375F * Mth.triangleWave((float) i - partialTick, 10.0F);
        }
    }

}
