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
import org.infernalstudios.infernalexp.config.gui.widgets.DoubleRangeOption;

@OnlyIn(Dist.CLIENT)
public class MobInteractionsScreen extends IESettingsScreen {

    public MobInteractionsScreen(Screen parentScreen) {
        super(parentScreen, Component.translatable(InfernalExpansion.MOD_ID + ".config.title.mobInteractions"));
    }

    @Override
    public void addSettings() {
        for (InfernalExpansionConfig.MobInteractions mobInteraction : InfernalExpansionConfig.MobInteractions.values()) {
            if (mobInteraction.isSlider()) {
                optionsRowList.addBig(new OptionInstance<>(InfernalExpansion.MOD_ID + ".config.option." + mobInteraction.getTranslationName(), OptionInstance.cachedConstantTooltip(Component.translatable(InfernalExpansion.MOD_ID + ".config.tooltip." + mobInteraction.getTranslationName())), (caption, value) -> Options.genericValueLabel(caption, Component.literal(String.valueOf(Math.round(value * 100.0D) / 100.0D))), new DoubleRangeOption(0.2F, 10.0F, 0.2F), mobInteraction.getDouble(), mobInteraction::setDouble));
            } else {
                optionsRowList.addBig(OptionInstance.createBoolean(InfernalExpansion.MOD_ID + ".config.option." + mobInteraction.getTranslationName(), OptionInstance.cachedConstantTooltip(Component.translatable(InfernalExpansion.MOD_ID + ".config.tooltip." + mobInteraction.getTranslationName())), mobInteraction.getBoolean(), mobInteraction::setBoolean));
            }
        }
    }
}
