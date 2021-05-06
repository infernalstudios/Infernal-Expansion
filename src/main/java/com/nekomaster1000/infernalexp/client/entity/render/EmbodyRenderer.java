package com.nekomaster1000.infernalexp.client.entity.render;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.client.entity.model.EmbodyModel;
import com.nekomaster1000.infernalexp.entities.EmbodyEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class EmbodyRenderer extends MobRenderer<EmbodyEntity, EmbodyModel<EmbodyEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/embody.png");

    public EmbodyRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new EmbodyModel<>(), 0.7f);
        this.addLayer(new EmbodyGlowLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(EmbodyEntity entity) {
        return TEXTURE;
    }
}
