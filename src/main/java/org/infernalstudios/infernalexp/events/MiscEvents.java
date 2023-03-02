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

package org.infernalstudios.infernalexp.events;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.PotionColorCalculationEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.blocks.DullthornsBlock;
import org.infernalstudios.infernalexp.blocks.HorizontalBushBlock;
import org.infernalstudios.infernalexp.config.ConfigHelper;
import org.infernalstudios.infernalexp.config.ConfigHolder;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig.Miscellaneous;
import org.infernalstudios.infernalexp.data.SpawnrateManager;
import org.infernalstudios.infernalexp.data.VolineEatTable;
import org.infernalstudios.infernalexp.data.providers.recipes.QuarkFlagCondition;
import org.infernalstudios.infernalexp.entities.ShroomloinEntity;
import org.infernalstudios.infernalexp.entities.ThrowableBrickEntity;
import org.infernalstudios.infernalexp.entities.ThrowableFireChargeEntity;
import org.infernalstudios.infernalexp.entities.ThrowableMagmaCreamEntity;
import org.infernalstudios.infernalexp.entities.ThrowableNetherBrickEntity;
import org.infernalstudios.infernalexp.init.IEBlockTags;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IEEffects;
import org.infernalstudios.infernalexp.init.IEItemTags;
import org.infernalstudios.infernalexp.init.IEItems;
import org.infernalstudios.infernalexp.init.IEParticleTypes;
import org.infernalstudios.infernalexp.init.IEShroomloinTypes;
import org.infernalstudios.infernalexp.init.IESoundEvents;
import org.infernalstudios.infernalexp.items.IFuel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = InfernalExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MiscEvents {

    // Called When Config is Changed
    @SubscribeEvent
    public static void onModConfigEvent(final ModConfigEvent event) {
        final ModConfig config = event.getConfig();
        //Recalculates what the configs should be when changed
        if (config.getSpec() == ConfigHolder.CLIENT_SPEC) {
            ConfigHelper.bakeClient(config);
        } else if (config.getSpec() == ConfigHolder.COMMON_SPEC) {
            ConfigHelper.bakeCommon(config);
        }
    }

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        int cost = event.getCost();

        if (left.isDamageableItem() && right.getItem() == IEItems.GLOWSILK.get() && !left.is(IEItemTags.GLOWSILK_REPAIR_BLACKLIST)) {
            if (left.getDamageValue() == 0) {
                return;
            }

            ItemStack output = left.copy();
            output.setDamageValue(left.getDamageValue() - 200);
            cost += 5;

            if (event.getName().equals("")) {
                if (left.hasCustomHoverName()) {
                    cost += 1;
                    output.resetHoverName();
                }
            } else if (!left.getHoverName().getString().equals(event.getName())) {
                cost += 1;
                output.setHoverName(new TextComponent(event.getName()));
            }

            event.setMaterialCost(1);
            event.setCost(cost);
            event.setOutput(output);
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer().isSpectator() || event.getPlayer().isCreative())
            return;

        BlockState state = event.getState();
        for (ShroomloinEntity shroomloin : event.getPlayer().level.getEntitiesOfClass(ShroomloinEntity.class, event.getPlayer().getBoundingBox().inflate(32.0D))) {
            if (shroomloin.getShroomloinType() == IEShroomloinTypes.CRIMSON) {
                if (state.is(IEBlockTags.ANGER_CRIMSON_SHROOMLOIN_BLOCKS)) {
                    shroomloin.becomeAngryAt(event.getPlayer());
                }
            }
            if (shroomloin.getShroomloinType() == IEShroomloinTypes.WARPED) {
                if (state.is(IEBlockTags.ANGER_WARPED_SHROOMLOIN_BLOCKS)) {
                    shroomloin.becomeAngryAt(event.getPlayer());
                }
            }
            if (shroomloin.getShroomloinType() == IEShroomloinTypes.LUMINOUS) {
                if (state.is(IEBlockTags.ANGER_LUMINOUS_SHROOMLOIN_BLOCKS)) {
                    shroomloin.becomeAngryAt(event.getPlayer());
                }
            }
            if (shroomloin.getShroomloinType() == IEShroomloinTypes.RED) {
                if (state.is(IEBlockTags.ANGER_RED_SHROOMLOIN_BLOCKS)) {
                    shroomloin.becomeAngryAt(event.getPlayer());
                }
            }
            if (shroomloin.getShroomloinType() == IEShroomloinTypes.BROWN) {
                if (state.is(IEBlockTags.ANGER_BROWN_SHROOMLOIN_BLOCKS)) {
                    shroomloin.becomeAngryAt(event.getPlayer());
                }
            }
        }
    }

    @SubscribeEvent
    public void onRightClickBlock(PostRightClickBlockEvent event) {
        ItemStack heldItemStack = event.getItemStack();
        Level world = event.getWorld();
        BlockPos pos = event.getPos();
        Direction face = event.getDirection();
        Player player = event.getPlayer();
        if (heldItemStack.getItem() == net.minecraft.world.item.Items.BONE) {
            pos = pos.relative(face);
            BlockState blockstate = IEBlocks.BURIED_BONE.get().getPlaceableState(world, pos, face);
            if (blockstate != null) {
                player.swing(event.getHand());
                if (!world.isEmptyBlock(pos) && !world.isClientSide() && world.getBlockState(pos).getFluidState().isEmpty()) {
                    world.destroyBlock(pos, true);
                }
                world.setBlock(pos, blockstate, 3);
                world.playSound(player, pos, blockstate.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!player.isCreative()) {
                    heldItemStack.shrink(1);
                }
                ForgeEventFactory.onBlockPlace(player, BlockSnapshot.create(world.dimension(), world, pos), face);
            }
        } else if (heldItemStack.getItem() == net.minecraft.world.item.Items.QUARTZ) {
            pos = pos.relative(face);
            BlockState blockstate = IEBlocks.PLANTED_QUARTZ.get().getPlaceableState(world, pos, face);
            if (blockstate != null) {
                player.swing(event.getHand());
                if (!world.isEmptyBlock(pos) && !world.isClientSide() && world.getBlockState(pos).getFluidState().isEmpty()) {
                    world.destroyBlock(pos, true);
                }
                world.setBlock(pos, blockstate, 3);
                world.playSound(player, pos, blockstate.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!player.isCreative()) {
                    heldItemStack.shrink(1);
                }
                ForgeEventFactory.onBlockPlace(player, BlockSnapshot.create(world.dimension(), world, pos), face);
            }
        } else if (heldItemStack.getItem() == net.minecraft.world.item.Items.GLOWSTONE_DUST) {
            if (heldItemStack.getCount() >= 2) {
                if (world.getBlockState(pos).getBlock() == IEBlocks.DIMSTONE.get()) {
                    player.swing(event.getHand());
                    for (int i = 0; i < 20; i++) {
                        world.addParticle(IEParticleTypes.GLOWSTONE_SPARKLE.get(), pos.getX(), pos.getY(), pos.getZ(), 0.0, 0.0, 0.0);
                    }
                    world.playSound(null, event.getPos(), IESoundEvents.GLOWSTONE_RECHARGE.get(), SoundSource.BLOCKS, 1.0F, (float) (0.75F + event.getWorld().getRandom().nextDouble() / 2));
                    world.setBlockAndUpdate(pos, net.minecraft.world.level.block.Blocks.GLOWSTONE.defaultBlockState());
                    if (!player.isCreative()) {
                        heldItemStack.shrink(2);
                    }
                } else if (world.getBlockState(pos).getBlock() == IEBlocks.DULLSTONE.get()) {
                    player.swing(event.getHand());
                    for (int i = 0; i < 20; i++) {
                        world.addParticle(IEParticleTypes.GLOWSTONE_SPARKLE.get(), pos.getX(), pos.getY(), pos.getZ(), 0.0, 0.0, 0.0);
                    }
                    world.playSound(null, event.getPos(), IESoundEvents.GLOWSTONE_RECHARGE.get(), SoundSource.BLOCKS, 1.0F, (float) (0.5F + event.getWorld().getRandom().nextDouble() / 3));
                    world.setBlockAndUpdate(pos, IEBlocks.DIMSTONE.get().defaultBlockState());
                    if (!player.isCreative()) {
                        heldItemStack.shrink(2);
                    }
                }
            }
            if (world.getBlockState(pos).getBlock() == IEBlocks.DULLTHORNS.get()) {
                player.swing(event.getHand());
                ((DullthornsBlock) world.getBlockState(pos).getBlock()).bonemealGrow(world.getBlockState(pos), world, pos);
                world.levelEvent(2005, pos, 0);
                if (!player.isCreative()) {
                    heldItemStack.shrink(1);
                }
            }
        }
    }

    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Level world = event.getWorld();
        Player player = event.getPlayer();
        ItemStack heldItemStack = player.getItemInHand(event.getHand());

        if (heldItemStack.getItem() == net.minecraft.world.item.Items.MAGMA_CREAM) {
            player.swing(event.getHand());

            if (!world.isClientSide) {
                ThrowableMagmaCreamEntity throwableMagmaCreamEntity = new ThrowableMagmaCreamEntity(world, player);
                throwableMagmaCreamEntity.setItem(heldItemStack);
                throwableMagmaCreamEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), -20, 0.5f, 1);
                world.addFreshEntity(throwableMagmaCreamEntity);
                world.playSound(null, event.getPos(), SoundEvents.SNOWBALL_THROW, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            player.awardStat(Stats.ITEM_USED.get(heldItemStack.getItem()));

            if (!player.getAbilities().instabuild) {
                heldItemStack.shrink(1);
            }
        } else if (heldItemStack.getItem() == net.minecraft.world.item.Items.FIRE_CHARGE) {
            player.swing(event.getHand());

            if (!world.isClientSide) {
                ThrowableFireChargeEntity throwableFireChargeEntity = new ThrowableFireChargeEntity(world, player, player.getLookAngle().x(), player.getLookAngle().y(), player.getLookAngle().z());
                world.addFreshEntity(throwableFireChargeEntity);
                world.playSound(null, event.getPos(), SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            player.awardStat(Stats.ITEM_USED.get(heldItemStack.getItem()));

            if (!player.getAbilities().instabuild) {
                heldItemStack.shrink(1);
            }
        }
        if (InfernalExpansionConfig.Miscellaneous.USE_THROWABLE_BRICKS.getBool()) {
            if (heldItemStack.getItem() == net.minecraft.world.item.Items.BRICK) {
                player.swing(event.getHand());

                if (!world.isClientSide) {
                    ThrowableBrickEntity throwableBrickEntity = new ThrowableBrickEntity(world, player);
                    throwableBrickEntity.setItem(heldItemStack);
                    throwableBrickEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), -20, 0.5F, 1);
                    world.addFreshEntity(throwableBrickEntity);
                    world.playSound(null, event.getPos(), SoundEvents.SNOWBALL_THROW, SoundSource.BLOCKS, 1.0F, 1.0F);
                }

                player.awardStat(Stats.ITEM_USED.get(heldItemStack.getItem()));

                if (!player.getAbilities().instabuild) {
                    heldItemStack.shrink(1);
                }
        }
            if (heldItemStack.getItem() == net.minecraft.world.item.Items.NETHER_BRICK) {
                player.swing(event.getHand());

                if (!world.isClientSide) {
                    ThrowableNetherBrickEntity throwableNetherBrickEntity = new ThrowableNetherBrickEntity(world, player);
                    throwableNetherBrickEntity.setItem(heldItemStack);
                    throwableNetherBrickEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), -20, 0.3F, 1);
                    world.addFreshEntity(throwableNetherBrickEntity);
                    world.playSound(null, event.getPos(), SoundEvents.SNOWBALL_THROW, SoundSource.BLOCKS, 1.0F, 1.0F);
                }

                player.awardStat(Stats.ITEM_USED.get(heldItemStack.getItem()));

                if (!player.getAbilities().instabuild) {
                    heldItemStack.shrink(1);
                }
            }
        }
    }

    @SubscribeEvent
    public void onApplyBonemeal(BonemealEvent event) {
        Block block = event.getBlock().getBlock();
        Level world = event.getWorld();
        BlockPos pos = event.getPos();
        if (block == net.minecraft.world.level.block.Blocks.SHROOMLIGHT && Miscellaneous.SHROOMLIGHT_GROWABLE.getBool()) {
            pos = pos.below();
            if (world.isEmptyBlock(pos)) {
                event.setResult(Event.Result.ALLOW);
                if (world.getRandom().nextDouble() < Miscellaneous.SHROOMLIGHT_GROW_CHANCE.getDouble() && !world.isClientSide()) {
                    world.setBlock(pos, IEBlocks.SHROOMLIGHT_FUNGUS.get().defaultBlockState().setValue(HorizontalBushBlock.FACE, AttachFace.CEILING), 3);
                }
            }
        } else if (block == IEBlocks.DULLTHORNS.get()) {
            if (((DullthornsBlock) world.getBlockState(pos).getBlock()).bonemealGrow(world.getBlockState(pos), world, pos)) {
                event.setResult(Event.Result.ALLOW);
            }
        }
    }

    @SubscribeEvent
    public void onPotionColorCalculate(PotionColorCalculationEvent event) {
        List<MobEffectInstance> effects = new ArrayList<>(event.getEffects());
        int customEffects = 0;

        // Hide base infection effect particles
        for (MobEffectInstance effectInstance : effects) {
            if (effectInstance.getEffect() == IEEffects.INFECTION.get() || effectInstance.getEffect() == IEEffects.LUMINOUS.get()) {
                customEffects++;
            }
        }

        if (customEffects == effects.size()) {
            event.shouldHideParticles(true);
        }
    }

    @SubscribeEvent
    public void onLivingEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();

        // Make sure we are checking potion effects on the server, not client
        if (entity.isEffectiveAi() && entity.getCommandSenderWorld() instanceof ServerLevel world) {
            if (entity.hasEffect(IEEffects.INFECTION.get())) {
                if ((entity.getEffect(IEEffects.INFECTION.get()).getDuration() & 10) == 0 && entity.getEffect(IEEffects.INFECTION.get()).isVisible()) {
                    // Use ServerWorld#spawnParticle instead of World#addParticle because this code is running on the server side
                    world.sendParticles(IEParticleTypes.INFECTION.get(), entity.getRandomX(entity.getBoundingBox().getXsize()), entity.getRandomY(), entity.getRandomZ(entity.getBoundingBox().getZsize()), 0, 0, 0, 0, 1);
                }
            }

            if (entity.hasEffect(IEEffects.LUMINOUS.get())) {
                if ((entity.getEffect(IEEffects.LUMINOUS.get()).getDuration() & 50) == 0 && entity.getEffect(IEEffects.LUMINOUS.get()).isVisible()) {
                    // Use ServerWorld#spawnParticle instead of World#addParticle because this code is running on the server side
                    world.sendParticles(IEParticleTypes.GLOWSTONE_SPARKLE.get(), entity.getRandomX(entity.getBoundingBox().getXsize()), entity.getRandomY(), entity.getRandomZ(entity.getBoundingBox().getZsize()), 0, 0, 0, 0, 0.2);
                }

                if ((entity instanceof Zombie && !(entity instanceof ZombifiedPiglin)) || entity instanceof Skeleton) {
                    entity.setSecondsOnFire(1);
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingFinishUse(LivingEntityUseItemEvent.Finish event) {
        ItemStack item = event.getItem();
        LivingEntity entity = (LivingEntity) event.getEntity();

        if (item.getItem() == IEItems.CURED_JERKY.get() && item.getUseAnimation() == UseAnim.EAT) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * Miscellaneous.JERKY_EFFECT_DURATION.getInt(), Miscellaneous.JERKY_EFFECT_AMPLIFIER.getInt()));
        }
    }

    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof AreaEffectCloud entity) {
            for (MobEffectInstance effect : entity.potion.getEffects()) {
                if (effect.getEffect() == IEEffects.INFECTION.get()) {
                    entity.setParticle(IEParticleTypes.INFECTION.get());
                } else if (effect.getEffect() == IEEffects.LUMINOUS.get()) {
                    entity.setParticle(IEParticleTypes.GLOWSTONE_SPARKLE.get());
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingEntityAttack(LivingAttackEvent event) {
        LivingEntity entity = event.getEntityLiving();

        // If entity has infection, on hit, make a splash of particles
        if (entity.isEffectiveAi() && entity.getCommandSenderWorld() instanceof ServerLevel world) {
            if (entity.hasEffect(IEEffects.INFECTION.get())) {
                if (event.getSource() != DamageSource.MAGIC) {
                    for (int i = 0; i < 32; i++) {
                        world.sendParticles(IEParticleTypes.INFECTION.get(), entity.getRandomX(1), entity.getRandomY(), entity.getRandomZ(1), 1, 0, 0, 0, 1);
                    }
                }
            }
        }
    }

    private static VolineEatTable volineEatTable;
    private static SpawnrateManager spawnrateManager;

    @SubscribeEvent
    public void onResourceReload(AddReloadListenerEvent event) {
        volineEatTable = new VolineEatTable();
        spawnrateManager = new SpawnrateManager();

        event.addListener(volineEatTable);
        spawnrateManager.loadResources();
    }

    public static Map<Item, Map<Item, Integer>> getVolineEatTable() {
        if (volineEatTable == null) {
            throw new IllegalStateException("Can not retrieve VolineEatTable until resources have loaded once.");
        }

        return volineEatTable.getVolineEatTable();
    }

    public static Map<String, Map<String, SpawnrateManager.SpawnInfo>> getSpawnrateManager() {
        if (spawnrateManager == null) {
            spawnrateManager = new SpawnrateManager();
        }

        return spawnrateManager.getSpawnrates();
    }

    @SubscribeEvent
    public void onCheckFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().getItem() instanceof IFuel fuel) {
            event.setBurnTime(fuel.getBurnTime());
        }
    }

    @SubscribeEvent
    public static void registerRecipeConditions(RegistryEvent.Register<RecipeSerializer<?>> event) {
        CraftingHelper.register(QuarkFlagCondition.Serializer.INSTANCE);
    }
}
