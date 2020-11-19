package com.nekomaster1000.infernalexp.client.entity.render;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.client.entity.model.ShroomloinModel;
import com.nekomaster1000.infernalexp.client.entity.model.VolineModel;
import com.nekomaster1000.infernalexp.entities.ShroomloinEntity;
import com.nekomaster1000.infernalexp.entities.VolineEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class ShroomloinRenderer extends MobRenderer<ShroomloinEntity, ShroomloinModel<ShroomloinEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity" + "/shroomloin.png");

    public ShroomloinRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ShroomloinModel<>(), 0.7f);
    }

    @Override
    public ResourceLocation getEntityTexture(ShroomloinEntity entity) {
        return TEXTURE;
    }
}
