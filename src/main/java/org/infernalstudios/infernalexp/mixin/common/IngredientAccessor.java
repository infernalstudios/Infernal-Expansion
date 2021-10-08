package org.infernalstudios.infernalexp.mixin.common;

import java.util.stream.Stream;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.item.crafting.Ingredient;

@Mixin(Ingredient.class)
public interface IngredientAccessor {

    @Invoker("<init>")
    public static Ingredient createIngredient(Stream<? extends Ingredient.Value> itemLists) {
        throw new UnsupportedOperationException();
    }

}
