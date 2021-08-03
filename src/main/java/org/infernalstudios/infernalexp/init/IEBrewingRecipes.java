package org.infernalstudios.infernalexp.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.infernalstudios.infernalexp.util.PotionBrewingReflection;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class IEBrewingRecipes {

    private static final List<Triple<Potion, Supplier<Ingredient>, Potion>> brewingRecipes = new ArrayList<>();

    public static void registerBrewingRecipes() {
        // Register potions
        addBrewingRecipe(() -> new IngredientWrapper(IEItems.MOTH_DUST.get()), IEPotions.LUMINOUS.get(), IEPotions.LONG_LUMINOUS.get(), IEPotions.STRONG_LUMINOUS.get());
        addBrewingRecipe(() -> new IngredientWrapper(IEItems.ASCUS_BOMB.get()), IEPotions.INFECTION.get(), IEPotions.LONG_INFECTION.get(), IEPotions.STRONG_INFECTION.get());

        for (Triple<Potion, Supplier<Ingredient>, Potion> recipe : brewingRecipes) {
            PotionBrewingReflection.addBrewingRecipe(recipe.getLeft(), recipe.getMiddle().get(), recipe.getRight());
        }
    }

    private static void addBrewingRecipe(Supplier<Ingredient> reagent, Potion normalPotion, Potion longPotion, Potion strongPotion) {
        // Allow reagent to be used to make mundane potions
        add(Potions.WATER, reagent, Potions.MUNDANE);

        // Add base potion
        add(Potions.AWKWARD, reagent, normalPotion);

        // Add strong and long variants
        add(normalPotion, () -> new IngredientWrapper(Items.GLOWSTONE_DUST), strongPotion);
        add(normalPotion, () -> new IngredientWrapper(Items.REDSTONE), longPotion);
    }

    private static void add(Potion from, Supplier<Ingredient> reagent, Potion to) {
        brewingRecipes.add(new ImmutableTriple<>(from, reagent, to));
    }

    private static class IngredientWrapper extends Ingredient {

        private IngredientWrapper(Item ingredient) {
            super(Stream.of(new SingleItemList(new ItemStack(ingredient))));
        }

    }

}
