package org.infernalstudios.infernalexp.mixin.common;

import java.util.stream.Stream;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.item.crafting.Ingredient;

@Mixin(Ingredient.class)
public interface MixinIngredientAccessor {

    @Invoker("<init>")
    public static Ingredient createIngredient(Stream<? extends Ingredient.IItemList> itemLists) {
        throw new UnsupportedOperationException();
    }

}
