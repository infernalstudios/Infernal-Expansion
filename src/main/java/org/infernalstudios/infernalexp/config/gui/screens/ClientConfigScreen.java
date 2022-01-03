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

import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.BooleanOption;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientConfigScreen extends IESettingsScreen {

    public ClientConfigScreen(Screen parentScreen) {
        super(parentScreen, new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.title.clientConfig"));
    }

    @Override
    public void addSettings() {
        for (InfernalExpansionConfig.ClientConfig clientConfig : InfernalExpansionConfig.ClientConfig.values()) {
            if (clientConfig.isSlider()) {
                optionsRowList.addOption(new SliderPercentageOption(InfernalExpansion.MOD_ID + ".config.option." + clientConfig.getTranslationName(), clientConfig.getMinValue(), clientConfig.getMaxValue(), clientConfig.getStepSize(),
                    settings -> clientConfig.getDouble(), (settings, value) -> clientConfig.set(value),
                    (settings, option) -> {
                        option.setOptionValues(Minecraft.getInstance().fontRenderer.trimStringToWidth(
                            new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + clientConfig.getTranslationName()), 200));

                        return new TranslationTextComponent("options.generic_value", option.getBaseMessageTranslation(),
                            new StringTextComponent(Integer.toString((int) option.get(settings))));
                    }));
            } else {
                optionsRowList.addOption(new BooleanOption(InfernalExpansion.MOD_ID + ".config.option." + clientConfig.getTranslationName(),
                    new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + clientConfig.getTranslationName()),
                    settings -> clientConfig.getBool(), (settings, value) -> clientConfig.set(value)));
            }
        }
    }

}
