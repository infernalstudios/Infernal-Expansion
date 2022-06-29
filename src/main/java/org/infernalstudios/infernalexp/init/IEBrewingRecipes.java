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

package org.infernalstudios.infernalexp.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import org.infernalstudios.infernalexp.mixin.common.PotionBrewingAccessor;

public class IEBrewingRecipes {

    public static void register() {
        registerBrewingRecipe(IEItems.MOTH_DUST.get(), IEPotions.LUMINOUS.get(), IEPotions.LONG_LUMINOUS.get(), IEPotions.STRONG_LUMINOUS.get());
        registerBrewingRecipe(IEItems.ASCUS_BOMB.get(), IEPotions.INFECTION.get(), IEPotions.LONG_INFECTION.get(), IEPotions.STRONG_INFECTION.get());
    }

    private static void registerBrewingRecipe(Item ingredient, Potion normalPotion, Potion longPotion, Potion strongPotion) {
        // Allow reagent to be used to make mundane potions
        PotionBrewingAccessor.invokeAddMix(Potions.WATER, ingredient, Potions.MUNDANE);

        // Add base potion
        PotionBrewingAccessor.invokeAddMix(Potions.AWKWARD, ingredient, normalPotion);

        // Add strong and long variants
        PotionBrewingAccessor.invokeAddMix(normalPotion, Items.REDSTONE, longPotion);
        PotionBrewingAccessor.invokeAddMix(normalPotion, Items.GLOWSTONE_DUST, strongPotion);
    }

}
