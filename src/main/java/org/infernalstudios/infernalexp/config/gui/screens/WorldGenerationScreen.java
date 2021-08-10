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

package org.infernalstudios.infernalexp.config.gui.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.BooleanOption;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.config.IEConfig;
import org.infernalstudios.infernalexp.config.gui.widgets.TextFieldOption;
import org.infernalstudios.infernalexp.config.values.CachedBooleanValue;
import org.infernalstudios.infernalexp.config.values.CachedConfigValue;
import org.infernalstudios.infernalexp.config.values.CachedStringValue;

@OnlyIn(Dist.CLIENT)
public class WorldGenerationScreen extends IESettingsScreen {

    public WorldGenerationScreen(Screen parentScreen) {
        super(parentScreen, new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.title.worldGeneration"));
    }

    @Override
    public void addSettings() {
        for (CachedConfigValue<?> configValue : IEConfig.worldGeneration.values()) {
            // Do not allow dangerous configs to appear in config
            if (configValue.isDangerous()) {
                continue;
            }

            if (configValue instanceof CachedBooleanValue) {
                CachedBooleanValue castedConfigValue = (CachedBooleanValue) configValue;

                optionsRowList.addOption(new BooleanOption(InfernalExpansion.MOD_ID + ".config.option." + castedConfigValue.getTranslationName(),
                    new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + castedConfigValue.getTranslationName()),
                    settings -> castedConfigValue.get(), (settings, value) -> castedConfigValue.set(value)));

            } else if (configValue instanceof CachedStringValue) {
                CachedStringValue castedConfigValue = (CachedStringValue) configValue;

                optionsRowList.addOption(new TextFieldOption(InfernalExpansion.MOD_ID + ".config.option." + castedConfigValue.getTranslationName(),
                    new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + castedConfigValue.getTranslationName()),
                    settings -> castedConfigValue.get(), (settings, value) -> castedConfigValue.set(value)));
            }
        }
    }
}
