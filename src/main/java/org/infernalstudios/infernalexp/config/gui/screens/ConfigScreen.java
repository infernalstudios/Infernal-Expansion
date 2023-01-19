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
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ConfigScreen extends Screen {

    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 20;

    public ConfigScreen() {
        super(new TranslatableComponent(InfernalExpansion.MOD_ID + ".config.title"));
    }

    @Override
    protected void init() {
        addWidget(new Button(width / 2 - 155, height / 6, BUTTON_WIDTH, BUTTON_HEIGHT, new TranslatableComponent(InfernalExpansion.MOD_ID + ".config.button.mobInteractions"), button -> Minecraft.getInstance().setScreen(new MobInteractionsScreen(this))));
        addWidget(new Button(width / 2 + 5, height / 6, BUTTON_WIDTH, BUTTON_HEIGHT, new TranslatableComponent(InfernalExpansion.MOD_ID + ".config.button.mobSpawning"), button -> Minecraft.getInstance().setScreen(new MobSpawningScreen(this))));
        addWidget(new Button(width / 2 - 155, height / 6 + 24, BUTTON_WIDTH, BUTTON_HEIGHT, new TranslatableComponent(InfernalExpansion.MOD_ID + ".config.button.miscellaneous"), button -> Minecraft.getInstance().setScreen(new MiscellaneousScreen(this))));
        addWidget(new Button(width / 2 + 5, height / 6 + 24, BUTTON_WIDTH, BUTTON_HEIGHT, new TranslatableComponent(InfernalExpansion.MOD_ID + ".config.button.clientConfig"), button -> Minecraft.getInstance().setScreen(new ClientConfigScreen(this))));

        addWidget(new Button((width - 200) / 2, height - 26, 200, BUTTON_HEIGHT, new TranslatableComponent("gui.done"), button -> onClose()));
    }

    @Override
    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);

        // Render widgets
        for (GuiEventListener child : children()) {
            if (child instanceof Widget widget) {
                widget.render(matrixStack, mouseX, mouseY, partialTicks);
            }
        }

        // The parameter names for this function are wrong. The three integers at the end should be x, y, color
        drawCenteredString(matrixStack, font, title, width / 2, 8, 0xFFFFFF);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

}
