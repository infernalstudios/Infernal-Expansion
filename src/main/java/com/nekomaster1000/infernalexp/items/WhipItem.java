package com.nekomaster1000.infernalexp.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

public class WhipItem extends TieredItem implements IWhipItem, IVanishable {

	private final float attackDamage;
	private final float attackSpeed;
	private final float attackKnockback;

	private int ticksSinceAttack = 0;
	private boolean attacking = false;

	public WhipItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, float attackKnockbackIn, Item.Properties builderIn) {
		super(tier, builderIn);
		this.attackDamage = attackDamageIn + tier.getAttackDamage();
		this.attackSpeed = attackSpeedIn;
		this.attackKnockback = attackKnockbackIn;
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (attacking) {
			ticksSinceAttack++;
		}

		if (ticksSinceAttack >= 30) {
			ticksSinceAttack = 0;
			attacking = false;
		}
	}

	public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		return !player.isCreative();
	}

	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		super.hitEntity(stack, target, attacker);

		target.applyKnockback(this.attackKnockback, MathHelper.sin(attacker.rotationYaw * ((float) Math.PI / 180F)), -MathHelper.cos(attacker.rotationYaw * ((float) Math.PI / 180F)));

		stack.damageItem(1, attacker, (entity) -> {
			entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
		});
		return true;
	}

	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		if (state.getBlockHardness(worldIn, pos) != 0.0F) {
			stack.damageItem(2, entityLiving, (entity) -> {
				entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
			});
		}

		return true;
	}

	public float getDestroySpeed(ItemStack stack, BlockState state) {
		Material material = state.getMaterial();
		return material != Material.PLANTS && material != Material.TALL_PLANTS && material != Material.CORAL && !state.isIn(BlockTags.LEAVES) && material != Material.GOURD ? 1.0F : 1.5F;
	}

	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment.type == (EnchantmentType.WEAPON);
	}

	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack itemStack) {
		ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
		attributeBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
		attributeBuilder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", this.attackSpeed, AttributeModifier.Operation.ADDITION));
		attributeBuilder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(REACH_MODIFIER_UUID, "Tool modifier", this.getReachDistanceModifier(), AttributeModifier.Operation.ADDITION));
		Multimap<Attribute, AttributeModifier> attributes = attributeBuilder.build();
		return equipmentSlot == EquipmentSlotType.MAINHAND ? attributes : super.getAttributeModifiers(equipmentSlot, itemStack);
	}

	@Override
	public int getTicksSinceAttack() {
		return ticksSinceAttack;
	}

	@Override
	public boolean getAttacking() {
		return attacking;
	}

	@Override
	public void setAttacking(boolean value) {
		attacking = value;
	}
}
