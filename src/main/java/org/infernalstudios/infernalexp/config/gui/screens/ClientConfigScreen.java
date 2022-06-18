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

import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
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
            if (clientConfig.isSlider()) {
                optionsRowList.addBig(new OptionInstance<>(InfernalExpansion.MOD_ID + ".config.option." + clientConfig.getTranslationName(), OptionInstance.cachedConstantTooltip(Component.translatable(InfernalExpansion.MOD_ID + ".config.tooltip." + clientConfig.getTranslationName())), Options::genericValueLabel, new OptionInstance.IntRange((int) clientConfig.getMinValue(), (int) clientConfig.getMaxValue()), (int) clientConfig.getDouble(), clientConfig::set));
            } else {
                optionsRowList.addBig(OptionInstance.createBoolean(InfernalExpansion.MOD_ID + ".config.option." + clientConfig.getTranslationName(), OptionInstance.cachedConstantTooltip(Component.translatable(InfernalExpansion.MOD_ID + ".config.tooltip." + clientConfig.getTranslationName())), clientConfig.getBool(), clientConfig::set));
            }
        }
    }

}
