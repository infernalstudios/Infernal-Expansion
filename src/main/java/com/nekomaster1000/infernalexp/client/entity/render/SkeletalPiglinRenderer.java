package com.nekomaster1000.infernalexp.client.entity.render;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.client.entity.model.SkeletalPiglinModel;
import com.nekomaster1000.infernalexp.entities.SkeletalPiglinEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

public class SkeletalPiglinRenderer extends BipedRenderer<SkeletalPiglinEntity, SkeletalPiglinModel<SkeletalPiglinEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/skeletal_piglin.png");

    public SkeletalPiglinRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new SkeletalPiglinModel<>(), 0.7f);
        this.addLayer(new BipedArmorLayer<>(this, new BipedModel(0.5F), new BipedModel(1.02F)));
    }

    @Override
    public ResourceLocation getEntityTexture(SkeletalPiglinEntity entity) {
        return TEXTURE;
    }
}
