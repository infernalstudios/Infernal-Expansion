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

import net.minecraft.client.CycleOption;
import net.minecraft.client.ProgressOption;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;

@OnlyIn(Dist.CLIENT)
public class MiscellaneousScreen extends IESettingsScreen {

    public MiscellaneousScreen(Screen parentScreen) {
        super(parentScreen, new TranslatableComponent(InfernalExpansion.MOD_ID + ".config.title.miscellaneous"));
    }

    @Override
    public void addSettings() {
        for (InfernalExpansionConfig.Miscellaneous miscellaneous : InfernalExpansionConfig.Miscellaneous.values()) {
            if (miscellaneous.isSlider()) {
                optionsRowList.addBig(new ProgressOption(InfernalExpansion.MOD_ID + ".config.option." + miscellaneous.getTranslationName(), miscellaneous.getMinValue(), miscellaneous.getMaxValue(), miscellaneous.getStepSize(),
                    settings -> miscellaneous.getDouble(), (settings, value) -> miscellaneous.set(value),
                    (settings, option) -> new TranslatableComponent("options.generic_value", option.getCaption(),
                        new TextComponent(Double.toString((double) Math.round(option.get(settings) * 100) / 100))),
                    minecraft -> minecraft.font.split(new TranslatableComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + miscellaneous.getTranslationName()), 200)));
            } else {
                optionsRowList.addBig(CycleOption.createOnOff(InfernalExpansion.MOD_ID + ".config.option." + miscellaneous.getTranslationName(),
                    new TranslatableComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + miscellaneous.getTranslationName()),
                    settings -> miscellaneous.getBool(), (settings, option, value) -> miscellaneous.set(value)));
            }
        }
    }
}
