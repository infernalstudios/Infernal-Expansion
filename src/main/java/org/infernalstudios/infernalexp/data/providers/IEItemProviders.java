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

package org.infernalstudios.infernalexp.data.providers;

import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelProvider;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.data.DataGenDeferredRegister;

public class IEItemProviders {

    private static final String ITEM_FOLDER = ModelProvider.ITEM_FOLDER + "/";

    /**
     * For simple items with their own texture
     */
    public static ItemProviderConsumer simple() {
        return (provider, item) -> {
            provider
                .withExistingParent(name(item.get()), new ResourceLocation(ITEM_FOLDER + "generated"))
                .texture("layer0", itemTexture(item.get()));
        };
    }

    /**
     * For items getting their texture from a block
     */
    public static ItemProviderConsumer block() {
        return (provider, item) -> {
            provider
                .withExistingParent(name(item.get()), new ResourceLocation(ITEM_FOLDER + "generated"))
                .texture("layer0", blockTexture(item.get()));
        };
    }

    /**
     * For items getting their model from a block. Will look for "_inventory" model of walls and buttons
     */
    public static ItemProviderConsumer blockModel() {
        return (provider, item) -> {
            ResourceLocation texture = blockTexture(item.get());
            if (texture.getPath().endsWith("wall") || texture.getPath().endsWith("button"))
                texture = extend(texture, "_inventory");

            provider.withExistingParent(name(item.get()), texture);
        };
    }

    /**
     * For items getting their model from a block with many variants
     * @param variant Variant of block model to use
     */
    public static ItemProviderConsumer blockVariant(int variant) {
        return (provider, item) -> {
            provider.withExistingParent(name(item.get()), extend(blockTexture(item.get()), "/" + variant));
        };
    }

    /**
     * For items getting their model from a layer block. E.g: Snow
     */
    public static ItemProviderConsumer blockLayer() {
        return (provider, item) -> {
            provider.withExistingParent(name(item.get()), extend(blockTexture(item.get()), "_height2"));
        };
    }

    /**
     * For spawn egg items
     */
    public static ItemProviderConsumer spawnEgg() {
        return (provider, item) -> {
            provider.withExistingParent(name(item.get()), new ResourceLocation(ITEM_FOLDER + "template_spawn_egg"));
        };
    }

    /**
     * For items of pane blocks
     */
    public static ItemProviderConsumer pane() {
        return (provider, item) -> {
            provider
                .withExistingParent(name(item.get()), new ResourceLocation(ITEM_FOLDER + "generated"))
                .texture("layer0", removeSuffix(blockTexture(item.get()), "_pane"));
        };
    }

    public static ItemProviderConsumer glowsilkBow() {
        return (provider, item) -> {
            provider
                .withExistingParent(name(item.get()), new ResourceLocation(ITEM_FOLDER + "generated"))
                .texture("layer0", itemTexture(item.get()))
                .transforms()
                    .transform(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND).rotation(0, -90, 25).translation(1.13F, 3.2F, 1.13F).scale(0.68F).end()
                    .transform(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND).rotation(0, 90, -25).translation(1.13F, 3.2F, 1.13F).scale(0.68F).end()
                    .transform(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND).rotation(-80, 260, -40).translation(-1.0F, -2.0F, 2.5F).scale(0.9F).end()
                    .transform(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND).rotation(-80, -280, 40).translation(-1.0F, -2.0F, 2.5F).scale(0.9F).end()
                .end()
                .override().model(provider.withExistingParent(name(item.get()) + "_pulling_0", new ResourceLocation(ITEM_FOLDER + "bow")).texture("layer0", extend(itemTexture(item.get()), "_pulling_0")))
                    .predicate(new ResourceLocation(InfernalExpansion.MOD_ID, "pulling"), 1).end()
                .override().model(provider.withExistingParent(name(item.get()) + "_pulling_1", new ResourceLocation(ITEM_FOLDER + "bow")).texture("layer0", extend(itemTexture(item.get()), "_pulling_1")))
                    .predicate(new ResourceLocation(InfernalExpansion.MOD_ID, "pulling"), 1)
                    .predicate(new ResourceLocation(InfernalExpansion.MOD_ID, "pull"), 0.65F).end()
                .override().model(provider.withExistingParent(name(item.get()) + "_pulling_2", new ResourceLocation(ITEM_FOLDER + "bow")).texture("layer0", extend(itemTexture(item.get()), "_pulling_2")))
                    .predicate(new ResourceLocation(InfernalExpansion.MOD_ID, "pulling"), 1)
                    .predicate(new ResourceLocation(InfernalExpansion.MOD_ID, "pull"), 0.9F).end();
        };
    }

    public static ItemProviderConsumer whip() {
        return (provider, item) -> {
            ItemModelBuilder builder = provider
                .withExistingParent(name(item.get()), new ResourceLocation(ITEM_FOLDER + "handheld"))
                .texture("layer0", itemTexture(item.get()));

            for (int i = 0; i < 10; i++) {
                builder.override()
                    .model(
                        provider.withExistingParent(name(item.get()) + "_extending_" + i, new ResourceLocation(ITEM_FOLDER + "handheld"))
                            .texture("layer0", extend(itemTexture(item.get()), "_extending_" + i))
                    )
                    .predicate(new ResourceLocation(InfernalExpansion.MOD_ID, "attacking"), 1)
                    .predicate(new ResourceLocation(InfernalExpansion.MOD_ID, "attack_frame"), i)
                    .end();
            }
        };
    }

    private static String name(Item item) {
        return item.getRegistryName().getPath();
    }

    private static ResourceLocation location(Item item) {
        return item.getRegistryName();
    }

    private static ResourceLocation itemTexture(Item item) {
        return prepend(location(item), ITEM_FOLDER);
    }

    private static ResourceLocation blockTexture(Item item) {
        return prepend(location(item), IEBlockProviders.BLOCK_FOLDER);
    }

    private static ResourceLocation prepend(ResourceLocation location, String prefix) {
        return new ResourceLocation(location.getNamespace(), prefix + location.getPath());
    }

    private static ResourceLocation extend(ResourceLocation location, String suffix) {
        return new ResourceLocation(location.getNamespace(), location.getPath() + suffix);
    }

    private static ResourceLocation removeSuffix(ResourceLocation location, String suffix) {
        if (location.getPath().endsWith(suffix))
            return new ResourceLocation(
                location.getNamespace(),
                location.getPath().substring(0, location.getPath().length() - suffix.length())
            );
        else
            return location;
    }

    @FunctionalInterface
    public interface ItemProviderConsumer extends DataGenDeferredRegister.ProviderConsumer<Item, ItemModelProvider> {}

}
