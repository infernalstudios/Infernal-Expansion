package com.nekomaster1000.Infernal.client.entity.render;

import com.nekomaster1000.Infernal.InfernalExpansion;
import com.nekomaster1000.Infernal.client.entity.model.GlowsquitoModel;
import com.nekomaster1000.Infernal.entities.GlowsquitoEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class GlowsquitoRenderer extends MobRenderer<GlowsquitoEntity, GlowsquitoModel<GlowsquitoEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/glowsquito.png");

    public GlowsquitoRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new GlowsquitoModel<>(), 0.7f);
    }

    @Override
    public ResourceLocation getEntityTexture(GlowsquitoEntity entity) {
        return TEXTURE;
    }
}
