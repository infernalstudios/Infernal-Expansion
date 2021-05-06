package com.nekomaster1000.infernalexp.client.entity.render;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.client.entity.model.PyrnoModel;
import com.nekomaster1000.infernalexp.entities.PyrnoEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class PyrnoRenderer extends MobRenderer<PyrnoEntity, PyrnoModel<PyrnoEntity>> {

protected static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID,
    "textures/entity/pyrno.png");

public PyrnoRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new PyrnoModel<>(), 0.7f);
        }

@Override
public ResourceLocation getEntityTexture(PyrnoEntity entity) {
        return TEXTURE;
        }
        }
