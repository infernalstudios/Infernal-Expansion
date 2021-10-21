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
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.TooltipAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class TextField extends EditBox implements TooltipAccessor {

    private final Options settings;
    private final TextFieldOption option;
    private final List<FormattedCharSequence> tooltip;

    @SuppressWarnings("resource")
    public TextField(Options settings, int x, int y, int width, Component title, TextFieldOption option, List<FormattedCharSequence> tooltip) {
        super(Minecraft.getInstance().font, x + 2 + 100, y, width - 4 - 100, 20, title);
        this.option = option;
        this.settings = settings;
        this.tooltip = tooltip;

        setMaxLength(1892);
        setValue(option.get(settings));
    }

    @SuppressWarnings("resource")
    @Override
    public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.renderButton(matrixStack, mouseX, mouseY, partialTicks);
        // The parameter names for this function are wrong. The three integers at the end should be x, y, color
        drawString(matrixStack, Minecraft.getInstance().font, this.getMessage(), this.x - 100, (this.y + (this.height - 8) / 2), -6250336);
    }

    @Override
    public void onValueChange(String newText) {
        super.onValueChange(newText);
        option.set(settings, newText);
    }

    @Override
    public List<FormattedCharSequence> getTooltip() {
        return tooltip;
    }
}
