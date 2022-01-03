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

package org.infernalstudios.infernalexp.items;

import org.infernalstudios.infernalexp.access.AbstractArrowEntityAccess;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class GlowsilkBowItem extends BowItem {

    public GlowsilkBowItem(Properties builder) {
        super(builder);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) entityLiving;
            ItemStack itemStack = playerEntity.findAmmo(stack);
            boolean hasInfinity = playerEntity.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;

            int ticksUsed = this.getUseDuration(stack) - timeLeft;
            ticksUsed = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerEntity, ticksUsed, !itemStack.isEmpty() || hasInfinity);
            if (ticksUsed < 0) return;

            if (!itemStack.isEmpty() || hasInfinity) {

                if (itemStack.isEmpty()) {
                    itemStack = new ItemStack(Items.ARROW);
                }

                float velocity = getArrowVelocity(ticksUsed);

                if (!((double) velocity < 0.1D)) {
                    boolean isArrowInfinite = playerEntity.abilities.isCreativeMode || (itemStack.getItem() instanceof ArrowItem && ((ArrowItem) itemStack.getItem()).isInfinite(itemStack, stack, playerEntity));

                    if (!worldIn.isRemote) {
                        ArrowItem arrowItem = (ArrowItem) (itemStack.getItem() instanceof ArrowItem ? itemStack.getItem() : Items.ARROW);

                        AbstractArrowEntity abstractArrowEntity = arrowItem.createArrow(worldIn, itemStack, playerEntity);

                        abstractArrowEntity = customArrow(abstractArrowEntity);
                        abstractArrowEntity.setDirectionAndMovement(playerEntity, playerEntity.rotationPitch, playerEntity.rotationYaw, 0.0F, velocity * 6.0F, 1.0F);

                        // Half the damage to counteract doubling the arrow velocity
                        abstractArrowEntity.setDamage((abstractArrowEntity.getDamage() / 2.0D) + 0.1D);

                        if (velocity == 1.0F) {
                            abstractArrowEntity.setIsCritical(true);
                        }

                        int power = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                        if (power > 0) {
                            abstractArrowEntity.setDamage(abstractArrowEntity.getDamage() + (double) power * 0.5D + 0.5D);
                        }

                        int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
                        if (knockback > 0) {
                            abstractArrowEntity.setKnockbackStrength(knockback);
                        }

                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
                            abstractArrowEntity.setFire(100);
                        }

                        stack.damageItem(1, playerEntity, (player) -> player.sendBreakAnimation(playerEntity.getActiveHand()));

                        if (isArrowInfinite || playerEntity.abilities.isCreativeMode && (itemStack.getItem() == Items.SPECTRAL_ARROW || itemStack.getItem() == Items.TIPPED_ARROW)) {
                            abstractArrowEntity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                        }

                        ((AbstractArrowEntityAccess) abstractArrowEntity).setGlow(true);

                        worldIn.addEntity(abstractArrowEntity);
                    }

                    worldIn.playSound(null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);

                    if (!isArrowInfinite && !playerEntity.abilities.isCreativeMode) {
                        itemStack.shrink(1);

                        if (itemStack.isEmpty()) {
                            playerEntity.inventory.deleteStack(itemStack);
                        }
                    }

                    playerEntity.addStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }
}
