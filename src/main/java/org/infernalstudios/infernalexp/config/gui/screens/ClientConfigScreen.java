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

import net.minecraft.client.gui.components.CycleButton;
// import net.minecraft.client.CycleOption;
// import net.minecraft.client.ProgressOption;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
// import net.minecraft.network.chat.TextComponent;
// import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;

@OnlyIn(Dist.CLIENT)
public class ClientConfigScreen extends IESettingsScreen {

    public ClientConfigScreen(Screen parentScreen) {
        super(parentScreen, Component.translatable(InfernalExpansion.MOD_ID + ".config.title.clientConfig"));
    }

    @Override
    public void addSettings() {
        for (InfernalExpansionConfig.ClientConfig clientConfig : InfernalExpansionConfig.ClientConfig.values()) {
            // TODO
            if (clientConfig.isSlider()) {
                // optionsRowList.addBig(new ProgressOption(InfernalExpansion.MOD_ID + ".config.option." + clientConfig.getTranslationName(), clientConfig.getMinValue(), clientConfig.getMaxValue(), clientConfig.getStepSize(),
                //     settings -> clientConfig.getDouble(), (settings, value) -> clientConfig.set(value),
                //     (settings, option) -> Component.translatable("options.generic_value", option.getCaption(),
                //         new TextComponent(Integer.toString((int) option.get(settings)))),
                //     (minecraft) -> minecraft.font.split(Component.translatable(InfernalExpansion.MOD_ID + ".config.tooltip." + clientConfig.getTranslationName()), 200)));
            } else {
                // optionsRowList.addBig(CycleButton.createOnOff(InfernalExpansion.MOD_ID + ".config.option." + clientConfig.getTranslationName(),
                //     Component.translatable(InfernalExpansion.MOD_ID + ".config.tooltip." + clientConfig.getTranslationName()),
                //     settings -> clientConfig.getBool(), (settings, option, value) -> clientConfig.set(value)));
            }
        }
    }

}
