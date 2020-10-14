package com.nekomaster1000.infernalexp.client.entity.render;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.client.entity.model.BasaltTitanModel;
import com.nekomaster1000.infernalexp.entities.BasaltTitanEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class BasaltTitanRenderer extends MobRenderer<BasaltTitanEntity, BasaltTitanModel<BasaltTitanEntity>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/basalt_giant.png");

    public BasaltTitanRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new BasaltTitanModel<>(), 0.7f);
    }

    @Override
    public ResourceLocation getEntityTexture(BasaltTitanEntity entity) {
        return TEXTURE;
    }
}
