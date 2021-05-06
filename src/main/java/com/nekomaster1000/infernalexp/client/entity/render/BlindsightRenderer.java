package com.nekomaster1000.infernalexp.client.entity.render;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.client.entity.model.BlindsightModel;
import com.nekomaster1000.infernalexp.entities.BlindsightEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class BlindsightRenderer extends MobRenderer<BlindsightEntity, BlindsightModel<BlindsightEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/blindsight.png");

    public BlindsightRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new BlindsightModel<>(), 0.7f);
        this.addLayer(new BlindsightGlowLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(BlindsightEntity entity) {
        return TEXTURE;
    }
}
