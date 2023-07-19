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

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.infernalstudios.infernalexp.entities.GlowsilkArrowEntity;
import org.jetbrains.annotations.NotNull;

public class GlowsilkBowItem extends BowItem {

    public GlowsilkBowItem(Properties builder) {
        super(builder);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player playerEntity) {
            ItemStack itemStack = playerEntity.getProjectile(stack);
            boolean hasInfinity = playerEntity.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;

            int ticksUsed = this.getUseDuration(stack) - timeLeft;
            ticksUsed = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerEntity, ticksUsed, !itemStack.isEmpty() || hasInfinity);
            if (ticksUsed < 0) return;

            if (!itemStack.isEmpty() || hasInfinity) {

                if (itemStack.isEmpty()) {
                    itemStack = new ItemStack(Items.ARROW);
                }

                float velocity = getPowerForTime(ticksUsed);

                if (!((double) velocity < 0.1D)) {
                    boolean isArrowInfinite = playerEntity.getAbilities().instabuild || (itemStack.getItem() instanceof ArrowItem arrowItem && arrowItem.isInfinite(itemStack, stack, playerEntity));

                    if (!worldIn.isClientSide) {
                        ArrowItem arrowItem = (ArrowItem) (itemStack.getItem() instanceof ArrowItem ? itemStack.getItem() : Items.ARROW);

                        AbstractArrow arrowEntity = new GlowsilkArrowEntity(worldIn, itemStack, playerEntity);

                        arrowEntity = customArrow(arrowEntity);
                        arrowEntity.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0F, velocity * 6.0F, 1.0F);

                        // Half the damage to counteract doubling the arrow velocity
                        arrowEntity.setBaseDamage((arrowEntity.getBaseDamage() / 2.0D) + 0.1D);

                        if (velocity == 1.0F) {
                            arrowEntity.setCritArrow(true);
                        }

                        int power = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
                        if (power > 0) {
                            arrowEntity.setBaseDamage(arrowEntity.getBaseDamage() + (double) power * 0.5D + 0.5D);
                        }

                        int knockback = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
                        if (knockback > 0) {
                            arrowEntity.setKnockback(knockback);
                        }

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
                            arrowEntity.setSecondsOnFire(100);
                        }

                        stack.hurtAndBreak(1, playerEntity, (player) -> player.broadcastBreakEvent(playerEntity.getUsedItemHand()));

                        if (isArrowInfinite || playerEntity.getAbilities().instabuild && (itemStack.getItem() == Items.SPECTRAL_ARROW || itemStack.getItem() == Items.TIPPED_ARROW)) {
                            arrowEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }

                        worldIn.addFreshEntity(arrowEntity);
                    }

                    worldIn.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (worldIn.getRandom().nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);

                    if (!isArrowInfinite && !playerEntity.getAbilities().instabuild) {
                        itemStack.shrink(1);

                        if (itemStack.isEmpty()) {
                            playerEntity.getInventory().removeItem(itemStack);
                        }
                    }

                    playerEntity.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }
}
