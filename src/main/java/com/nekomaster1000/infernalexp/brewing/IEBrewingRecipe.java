package com.nekomaster1000.infernalexp.brewing;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class IEBrewingRecipe implements IBrewingRecipe {
    private final ItemStack input;
    private final ItemStack ingredient;
    private final ItemStack output;


    public IEBrewingRecipe(ItemStack input, ItemStack ingredient, ItemStack output){
        this.input = input;
        this.ingredient = ingredient;
        this.output = output;
    }

    @Override
    public boolean isInput(ItemStack input) {
        return input.isItemEqual(this.input) &&
                PotionUtils.getPotionFromItem(input).equals(PotionUtils.getPotionFromItem(this.input));
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.isItemEqual(this.ingredient);
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        return this.isInput(input) && this.isIngredient(ingredient) ?
                this.output.copy() :
                ItemStack.EMPTY;
    }
}
