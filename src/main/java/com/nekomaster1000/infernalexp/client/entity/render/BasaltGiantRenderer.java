package com.nekomaster1000.infernalexp.client.entity.render;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.client.entity.model.BasaltGiantModel;
import com.nekomaster1000.infernalexp.entities.BasaltGiantEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class BasaltGiantRenderer extends MobRenderer<BasaltGiantEntity, BasaltGiantModel<BasaltGiantEntity>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/basalt_giant.png");

    public BasaltGiantRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new BasaltGiantModel<>(), 0.7F);
        this.addLayer(new BasaltGiantGlowLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(BasaltGiantEntity entity) {
        return TEXTURE;
    }
}
