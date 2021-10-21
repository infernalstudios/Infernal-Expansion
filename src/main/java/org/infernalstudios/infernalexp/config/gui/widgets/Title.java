/*
 * Copyright 2021 Infernal Studios
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

package org.infernalstudios.infernalexp.config.gui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Title extends AbstractWidget {

    public Title(int x, int y, int width, int height, Component title) {
        super(x, y, width, height, title);
    }

    @SuppressWarnings("resource")
    @Override
    public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        drawCenteredString(matrixStack, Minecraft.getInstance().font, getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, 0xFFFFFF);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.TITLE, this.createNarrationMessage());
    }
}
