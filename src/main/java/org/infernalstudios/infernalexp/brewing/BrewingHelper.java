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

package org.infernalstudios.infernalexp.brewing;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.infernalstudios.infernalexp.mixin.common.IngredientAccessor;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class BrewingHelper {

    /**
     * This code is a slightly modified version of Quark's PotionReflection class.
     *
     * @see <a href="https://github.com/VazkiisMods/Quark/blob/master/src/main/java/vazkii/quark/base/util/PotionReflection.java">PotionReflection.java</a>
     */

    private static final MethodHandle CREATE_NEW_MIX_PREDICATE, GET_POTION_TYPE_CONVERSIONS_LIST;

    static {
        try {
            Class<?> mixPredicateClass = Class.forName("net.minecraft.world.item.alchemy.PotionBrewing$Mix");

            MethodType constructorType = MethodType.methodType(Void.TYPE, ForgeRegistryEntry.class, Ingredient.class, ForgeRegistryEntry.class);
            Constructor<?> constructor = mixPredicateClass.getConstructor(constructorType.parameterArray());
            constructor.setAccessible(true);

            CREATE_NEW_MIX_PREDICATE = MethodHandles.lookup().unreflectConstructor(constructor).asType(constructorType.changeReturnType(Object.class));

            Field typeConversions = ObfuscationReflectionHelper.findField(PotionBrewing.class, "POTION_MIXES"); // POTION_TYPE_CONVERSIONS
            GET_POTION_TYPE_CONVERSIONS_LIST = MethodHandles.lookup().unreflectGetter(typeConversions).asType(MethodType.methodType(List.class));

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static void addBrewingRecipe(Potion input, Supplier<Ingredient> reagent, Potion output) {
        try {
            Object mixPredicate = CREATE_NEW_MIX_PREDICATE.invokeExact((ForgeRegistryEntry<?>) input, reagent.get(), (ForgeRegistryEntry<?>) output);
            List<Object> typeConversions = (List<Object>) GET_POTION_TYPE_CONVERSIONS_LIST.invokeExact();
            typeConversions.add(mixPredicate);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public static void registerBrewingRecipe(Item ingredient, Potion normalPotion, Potion longPotion, Potion strongPotion) {
        Supplier<Ingredient> reagent = () -> createIngredient(ingredient);

        // Allow reagent to be used to make mundane potions
        addBrewingRecipe(Potions.WATER, reagent, Potions.MUNDANE);

        // Add base potion
        addBrewingRecipe(Potions.AWKWARD, reagent, normalPotion);

        // Add strong and long variants
        addBrewingRecipe(normalPotion, () -> createIngredient(Items.REDSTONE), longPotion);
        addBrewingRecipe(normalPotion, () -> createIngredient(Items.GLOWSTONE_DUST), strongPotion);
    }

    private static Ingredient createIngredient(Item ingredient) {
        return IngredientAccessor.createIngredient(Stream.of(new Ingredient.ItemValue(new ItemStack(ingredient))));
    }

}
