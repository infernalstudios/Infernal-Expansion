package com.nekomaster1000.infernalexp.config.gui.screens;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;
import com.nekomaster1000.infernalexp.config.gui.widgets.TextFieldOption;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.BooleanOption;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WorldGenerationScreen extends IESettingsScreen {

    public WorldGenerationScreen(Screen parentScreen) {
        super(parentScreen, new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.title.worldGeneration"));
    }

    @Override
    public void addSettings() {
        for (InfernalExpansionConfig.WorldGeneration worldGeneration : InfernalExpansionConfig.WorldGeneration.values()) {
            if (worldGeneration.get() instanceof Boolean) {
                optionsRowList.addOption(new BooleanOption(InfernalExpansion.MOD_ID + ".config.option." + worldGeneration.getTranslationName(),
                    new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + worldGeneration.getTranslationName()),
                    settings -> (Boolean) worldGeneration.get(), (settings, value) -> worldGeneration.set(value)));

            } else if (worldGeneration.get() instanceof String) {
                optionsRowList.addOption(new TextFieldOption(InfernalExpansion.MOD_ID + ".config.option." + worldGeneration.getTranslationName(),
                    new TranslationTextComponent(InfernalExpansion.MOD_ID + ".config.tooltip." + worldGeneration.getTranslationName()),
                    settings -> (String) worldGeneration.get(), (settings, value) -> worldGeneration.set(value)));
            }
        }
    }
}
