package com.nekomaster1000.infernalexp.client.entity.render;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.client.entity.model.GlowsquitoModel;
import com.nekomaster1000.infernalexp.entities.GlowsquitoEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class GlowsquitoRenderer extends MobRenderer<GlowsquitoEntity, GlowsquitoModel<GlowsquitoEntity>> {
    protected static final ResourceLocation UNBRED_TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/glowsquitoid.png");

    protected static final ResourceLocation BRED_TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/glowsquitoid_shroomlight.png");

    public GlowsquitoRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new GlowsquitoModel<>(), 0.7f);
        this.addLayer(new GlowsquitoGlowLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(GlowsquitoEntity entity) {
        if (entity.getBred()) {
            return BRED_TEXTURE;
        } else {
            return UNBRED_TEXTURE;
        }
    }
}
