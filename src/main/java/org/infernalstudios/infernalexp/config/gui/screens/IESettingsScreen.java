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

package org.infernalstudios.infernalexp.config.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.infernalstudios.infernalexp.config.ConfigHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public abstract class IESettingsScreen extends Screen {

    private final Screen parentScreen;
    protected OptionsList optionsRowList;

    protected IESettingsScreen(Screen parentScreen, Component titleIn) {
        super(titleIn);
        this.parentScreen = parentScreen;
    }

    public abstract void addSettings();

    @Override
    protected void init() {
        optionsRowList = new OptionsList(minecraft, width, height, 24, height - 32, 25);

        addSettings();

        addWidget(optionsRowList);
        addWidget(new Button((width - 200) / 2, height - 26, 200, 20, new TranslatableComponent("gui.done"), button -> onClose()));
    }

    @Override
    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);

        optionsRowList.render(matrixStack, mouseX, mouseY, partialTicks);

        // Render widgets not in OptionsList
        for (GuiEventListener child : children()) {
            if (child instanceof Widget widget) {
                widget.render(matrixStack, mouseX, mouseY, partialTicks);
            }
        }

        List<FormattedCharSequence> list = OptionsSubScreen.tooltipAt(optionsRowList, mouseX, mouseY);
        if (list != null) {
            this.renderTooltip(matrixStack, list, mouseX, mouseY);
        }

        // The parameter names for this function are wrong. The three integers at the end should be x, y, color
        drawCenteredString(matrixStack, font, title, width / 2, 8, 0xFFFFFF);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onClose() {
        ConfigHelper.saveToClient();
        ConfigHelper.saveToCommon();
        Minecraft.getInstance().setScreen(parentScreen);
    }
}
