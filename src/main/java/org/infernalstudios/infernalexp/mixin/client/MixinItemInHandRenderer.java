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

package org.infernalstudios.infernalexp.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.infernalstudios.infernalexp.init.IEItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemInHandRenderer.class)
public class MixinItemInHandRenderer {

    @WrapOperation(
        method = {
            "evaluateWhichHandsToRender",
            "selectionUsingItemWhileHoldingBowLike"
        },
        at = @At(
            value = "INVOKE",
            target = "net/minecraft/world/item/ItemStack.is(Lnet/minecraft/world/item/Item;)Z"
        ),
        require = 0
    )
    private static boolean IE_selectionUsingItemWhileHoldingGlowsilkBow(ItemStack itemStack, Item item, Operation<Boolean> original) {
        return original.call(itemStack, item) || (item == Items.BOW && original.call(itemStack, IEItems.GLOWSILK_BOW.get()));
    }
}
