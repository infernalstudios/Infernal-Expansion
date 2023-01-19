/*
 * Copyright 2022 Infernal Studios
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.infernalstudios.infernalexp.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.infernalstudios.infernalexp.client.entity.model.ShroomloinModel;
import org.infernalstudios.infernalexp.entities.ShroomloinEntity;
import org.infernalstudios.infernalexp.init.IEShroomloinTypes;
import org.jetbrains.annotations.NotNull;

public class ShroomloinRenderer extends MobRenderer<ShroomloinEntity, ShroomloinModel<ShroomloinEntity>> {

    public ShroomloinRenderer(EntityRendererProvider.Context context) {
        super(context, new ShroomloinModel<>(context.bakeLayer(ShroomloinModel.LAYER_LOCATION)), 0.7f);
        this.addLayer(new ShroomloinDecorLayer(this));
        this.addLayer(new ShroomloinGlowLayer(this));
    }

    @Override
    protected void scale(ShroomloinEntity shroomloinEntity, PoseStack poseStack, float partialTickTime) {
        float f = shroomloinEntity.getShroomloinFlashIntensity(partialTickTime);
        float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        poseStack.scale(f2, f3, f2);
    }

    @Override
    protected float getWhiteOverlayProgress(ShroomloinEntity shroomloinEntity, float partialTicks) {
        float f = shroomloinEntity.getShroomloinFlashIntensity(partialTicks);
        return (int) (f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ShroomloinEntity entity) {
        return IEShroomloinTypes.CRIMSON.getTextureLocation();
    }

    @Override
    protected boolean isShaking(@NotNull ShroomloinEntity entity) {
        return super.isShaking(entity) || entity.isShaking();
    }
}
