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
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;
import org.infernalstudios.infernalexp.config.gui.widgets.TextFieldOption;

@OnlyIn(Dist.CLIENT)
public class WorldGenerationScreen extends IESettingsScreen {

    public WorldGenerationScreen(Screen parentScreen) {
        super(parentScreen, new TranslatableComponent(InfernalExpansion.MOD_ID + ".config.title.worldGeneration"));
    }

    @Override
    public void addSettings() {
        for (InfernalExpansionConfig.WorldGeneration worldGeneration : InfernalExpansionConfig.WorldGeneration.values()) {
            if (worldGeneration.get() instanceof Boolean) {
                optionsRowList.addBig(CycleOption.createOnOff(InfernalExpansion.MOD_ID + ".config.option." + worldGeneration.getTranslationName(),
                    new TranslatableComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + worldGeneration.getTranslationName()),
                    settings -> (Boolean) worldGeneration.get(), (settings, option, value) -> worldGeneration.set(value)));

            } else if (worldGeneration.get() instanceof String) {
                optionsRowList.addBig(new TextFieldOption(InfernalExpansion.MOD_ID + ".config.option." + worldGeneration.getTranslationName(),
                    settings -> (String) worldGeneration.get(), (settings, value) -> worldGeneration.set(value),
                    minecraft -> minecraft.font.split(new TranslatableComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + worldGeneration.getTranslationName()), 200)));
            }
        }
    }
}
