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

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.infernalstudios.infernalexp.capabilities.IWhipUpdate;
import org.infernalstudios.infernalexp.capabilities.WhipUpdateCapability;
import org.infernalstudios.infernalexp.init.IEItems;
import org.infernalstudios.infernalexp.network.IENetworkHandler;
import org.infernalstudios.infernalexp.network.WhipReachPacket;

import javax.annotation.Nullable;
import java.util.List;

public class WhipItem extends TieredItem implements IVanishable {

    private final float attackDamage;
    private final float attackSpeed;

    public WhipItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
        super(tier, builderIn);
        this.attackDamage = attackDamageIn + tier.getAttackDamage();
        this.attackSpeed = attackSpeedIn;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("\u00A76Hold right click to charge, then when fully charged, release to strike!"));
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (isInGroup(group)) {
            ItemStack itemStack = new ItemStack(this);
            if (itemStack.isItemEqual(IEItems.KINETIC_TONGUE_WHIP.get().getDefaultInstance())) {
                itemStack.addEnchantment(Enchantments.KNOCKBACK, 3);
            }

            items.add(itemStack);
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) entityLiving;

            setCharging(stack, false);

            int ticksSinceStart = this.getUseDuration(stack) - timeLeft;

            if (ticksSinceStart < 0 || getTicksSinceAttack(stack) < 15) {
                setTicksSinceAttack(stack, 0);
                return;
            } else {
                setAttacking(stack, true);
                setTicksSinceAttack(stack, 18);
            }

            playerEntity.getEntityWorld().playSound(playerEntity, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F));
            playerEntity.addStat(Stats.ITEM_USED.get(this));

            if (worldIn.isRemote()) {
                IENetworkHandler.sendToServer(new WhipReachPacket(playerEntity.getUniqueID()));
            }
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (handIn.equals(Hand.MAIN_HAND)) {
            playerIn.setActiveHand(handIn);
        }
        return ActionResult.resultPass(itemstack);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if (getAttacking(stack)) {
            setTicksSinceAttack(stack, 0);
            setAttacking(stack, false);
        }

        setCharging(stack, true);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if ((getCharging(stack) && getTicksSinceAttack(stack) <= 15) || getAttacking(stack)) {
            setTicksSinceAttack(stack, getTicksSinceAttack(stack) + 1);
        }

        if (getTicksSinceAttack(stack) >= 30 || (!isSelected && entityIn instanceof PlayerEntity && ((PlayerEntity) entityIn).getHeldItemOffhand() != stack)) {
            setTicksSinceAttack(stack, 0);
            setAttacking(stack, false);
            setCharging(stack, false);
        }
    }

    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.hitEntity(stack, target, attacker);


        stack.damageItem(1, attacker, (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(2, entityLiving, (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        }

        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        Material material = state.getMaterial();
        return material != Material.PLANTS && material != Material.TALL_PLANTS && material != Material.CORAL && !state.isIn(BlockTags.LEAVES) && material != Material.GOURD ? 1.0F : 1.5F;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.type == (EnchantmentType.WEAPON);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack itemStack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
        attributeBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        attributeBuilder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", this.attackSpeed, AttributeModifier.Operation.ADDITION));
        Multimap<Attribute, AttributeModifier> attributes = attributeBuilder.build();
        return equipmentSlot == EquipmentSlotType.MAINHAND ? attributes : super.getAttributeModifiers(equipmentSlot, itemStack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (WhipUpdateCapability.INSTANCE == null) return null;

        return WhipUpdateCapability.createProvider();
    }

    public void setTicksSinceAttack(ItemStack itemStack, int ticksSinceAttack) {
        WhipUpdateCapability.getWhipUpdate(itemStack).ifPresent(whipUpdate -> whipUpdate.setTicksSinceAttack(ticksSinceAttack));
    }

    public int getTicksSinceAttack(ItemStack itemStack) {
        IWhipUpdate whipUpdate = WhipUpdateCapability.getWhipUpdate(itemStack).orElse(null);
        return whipUpdate == null ? 0 : whipUpdate.getTicksSinceAttack();
    }

    public void setAttacking(ItemStack itemStack, boolean attacking) {
        WhipUpdateCapability.getWhipUpdate(itemStack).ifPresent(whipUpdate -> whipUpdate.setAttacking(attacking));
    }

    public boolean getAttacking(ItemStack itemStack) {
        IWhipUpdate whipUpdate = WhipUpdateCapability.getWhipUpdate(itemStack).orElse(null);
        return whipUpdate != null && whipUpdate.getAttacking();
    }

    public void setCharging(ItemStack itemStack, boolean charging) {
        WhipUpdateCapability.getWhipUpdate(itemStack).ifPresent(whipUpdate -> whipUpdate.setCharging(charging));
    }

    public boolean getCharging(ItemStack itemStack) {
        IWhipUpdate whipUpdate = WhipUpdateCapability.getWhipUpdate(itemStack).orElse(null);
        return whipUpdate != null && whipUpdate.getCharging();
    }
}
