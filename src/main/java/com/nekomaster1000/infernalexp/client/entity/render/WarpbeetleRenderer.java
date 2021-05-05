package com.nekomaster1000.infernalexp.client.entity.render;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.client.entity.model.WarpbeetleModel;
import com.nekomaster1000.infernalexp.entities.WarpbeetleEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class WarpbeetleRenderer extends MobRenderer<WarpbeetleEntity, WarpbeetleModel<WarpbeetleEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
        "textures/entity/warpbeetle.png");

    public WarpbeetleRenderer(EntityRendererManager renderManagerIn) { super(renderManagerIn, new WarpbeetleModel<>(), 0.7f); }

    @Override
    public ResourceLocation getEntityTexture(WarpbeetleEntity entity) { return TEXTURE; }
}
