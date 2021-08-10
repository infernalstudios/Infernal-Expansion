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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.BooleanOption;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.config.CommonConfig;
import org.infernalstudios.infernalexp.config.values.CachedBooleanValue;
import org.infernalstudios.infernalexp.config.values.CachedConfigValue;
import org.infernalstudios.infernalexp.config.values.CachedDoubleValue;

@OnlyIn(Dist.CLIENT)
public class MobInteractionsScreen extends IESettingsScreen {

    public MobInteractionsScreen(Screen parentScreen) {
        super(parentScreen, new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.title.mobInteractions"));
    }

    @Override
    public void addSettings() {
        for (CachedConfigValue<?> mobInteraction : CommonConfig.mobInteractions.values()) {
            if (mobInteraction instanceof CachedDoubleValue) {
                CachedDoubleValue mobInteractionDouble = (CachedDoubleValue) mobInteraction;

                optionsRowList.addOption(new SliderPercentageOption(InfernalExpansion.MOD_ID + ".config.option." + mobInteractionDouble.getTranslationName(),
                    mobInteractionDouble.getMinValue(), mobInteractionDouble.getMaxValue(), mobInteractionDouble.getStepValue(),
                    settings -> mobInteractionDouble.get(), (settings, value) -> mobInteractionDouble.set(value),
                    (settings, option) -> {
                        option.setOptionValues(Minecraft.getInstance().fontRenderer.trimStringToWidth(
                            new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + mobInteractionDouble.getTranslationName()), 200));

                        return new TranslationTextComponent("options.generic_value", option.getBaseMessageTranslation(),
                            new StringTextComponent(Double.toString((double) Math.round(option.get(settings) * 100) / 100)));
                    }));
            } else if (mobInteraction instanceof CachedBooleanValue) {
                CachedBooleanValue mobInteractionBoolean = (CachedBooleanValue) mobInteraction;

                optionsRowList.addOption(new BooleanOption(InfernalExpansion.MOD_ID + ".config.option." + mobInteractionBoolean.getTranslationName(),
                    new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + mobInteractionBoolean.getTranslationName()),
                    settings -> mobInteractionBoolean.get(), (settings, value) -> mobInteractionBoolean.set(value)));
            }
        }
    }
}
