package com.nekomaster1000.infernalexp.client.entity.render;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.client.entity.model.VolineModel;
import com.nekomaster1000.infernalexp.entities.VolineEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class VolineRenderer extends MobRenderer<VolineEntity, VolineModel<VolineEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/voline.png");

    public VolineRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new VolineModel<>(), 0.7f);
        this.addLayer(new VolineGlowLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(VolineEntity entity) {
        return TEXTURE;
    }
}
