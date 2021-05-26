package com.nekomaster1000.infernalexp.events;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.blocks.HorizontalBushBlock;
import com.nekomaster1000.infernalexp.config.ConfigHelper;
import com.nekomaster1000.infernalexp.config.ConfigHolder;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig.Miscellaneous;
import com.nekomaster1000.infernalexp.data.SpawnrateManager;
import com.nekomaster1000.infernalexp.data.VolineEatTable;
import com.nekomaster1000.infernalexp.entities.ShroomloinEntity;
import com.nekomaster1000.infernalexp.entities.ThrowableFireChargeEntity;
import com.nekomaster1000.infernalexp.entities.ThrowableMagmaCreamEntity;
import com.nekomaster1000.infernalexp.init.IEBlocks;
import com.nekomaster1000.infernalexp.init.IEEffects;
import com.nekomaster1000.infernalexp.init.IEItems;
import com.nekomaster1000.infernalexp.init.IEParticleTypes;
import com.nekomaster1000.infernalexp.init.IESoundEvents;
import com.nekomaster1000.infernalexp.init.IETags;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.PotionColorCalculationEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = InfernalExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MiscEvents {

    //    Called When Config is Changed
    @SubscribeEvent
    public static void onModConfigEvent(final ModConfig.ModConfigEvent event) {
        final ModConfig config = event.getConfig();
        //Recalculates what the configs should be when changed
        if (config.getSpec() == ConfigHolder.CLIENT_SPEC) {
            ConfigHelper.bakeClient(config);
        } else if (config.getSpec() == ConfigHolder.COMMON_SPEC) {
            ConfigHelper.bakeCommon(config);
        }
    }

    //Blocks being broken
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getState().isIn(IETags.Blocks.GUARDED_BY_SHROOMLOIN)) {
            if (event.getPlayer().isCreative()) {
                return;
            }
            List<?> list = event.getPlayer().world.getEntitiesWithinAABB(ShroomloinEntity.class,
                event.getPlayer().getBoundingBox().grow(32.0D));
            for (int j = 0; j < list.size(); j++) {
                Entity entity = (Entity) list.get(j);
                if (entity instanceof ShroomloinEntity) {
                    ShroomloinEntity shroomloinEntity = (ShroomloinEntity) entity;
                    shroomloinEntity.becomeAngryAt(event.getPlayer());
                }
            }
        }
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack heldItemStack = event.getItemStack();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        Direction face = event.getFace();
        PlayerEntity player = event.getPlayer();
        if (heldItemStack.getItem() == Items.BONE) {
            pos = pos.offset(face);
            BlockState blockstate = IEBlocks.BURIED_BONE.get().getPlaceableState(world, pos, face);
            if (blockstate != null) {
                player.swingArm(event.getHand());
                if (!world.isAirBlock(pos) && !world.isRemote() && world.getBlockState(pos).getFluidState().isEmpty()) {
                    world.destroyBlock(pos, true);
                }
                world.setBlockState(pos, blockstate, 3);
                world.playSound(player, pos, blockstate.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!player.isCreative()) {
                    heldItemStack.shrink(1);
                }
                ForgeEventFactory.onBlockPlace(player, BlockSnapshot.create(world.getDimensionKey(), world, pos), face);
            }
        }

        if (heldItemStack.getItem() == Items.GLOWSTONE_DUST) {
            if (world.getBlockState(pos).getBlock() == IEBlocks.DIMSTONE.get()) {
                player.swingArm(event.getHand());
                world.playSound(null, event.getPos(), IESoundEvents.GLOWSTONE_RECHARGE.get(), SoundCategory.BLOCKS, 1.0F, (float) (0.75F + event.getWorld().getRandom().nextDouble() / 2));
                world.setBlockState(pos, Blocks.GLOWSTONE.getDefaultState());
            } else if (world.getBlockState(pos).getBlock() == IEBlocks.DULLSTONE.get()) {
                player.swingArm(event.getHand());
                world.playSound(null, event.getPos(), IESoundEvents.GLOWSTONE_RECHARGE.get(), SoundCategory.BLOCKS, 1.0F, (float) (0.5F + event.getWorld().getRandom().nextDouble() / 3));
                world.setBlockState(pos, IEBlocks.DIMSTONE.get().getDefaultState());
            }
        }
    }

    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        World world = event.getWorld();
        PlayerEntity player = event.getPlayer();
        ItemStack heldItemStack = player.getHeldItem(event.getHand());

        if (heldItemStack.getItem() == Items.MAGMA_CREAM) {
            player.swingArm(event.getHand());

            if (!world.isRemote) {
                ThrowableMagmaCreamEntity throwableMagmaCreamEntity = new ThrowableMagmaCreamEntity(world, player);
                throwableMagmaCreamEntity.setItem(heldItemStack);
				throwableMagmaCreamEntity.setDirectionAndMovement(player, player.rotationPitch, player.rotationYaw, -20, 0.5f, 1);
				world.addEntity(throwableMagmaCreamEntity);
                world.playSound(null, event.getPos(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            player.addStat(Stats.ITEM_USED.get(heldItemStack.getItem()));

            if (!player.abilities.isCreativeMode) {
                heldItemStack.shrink(1);
            }
        } else if (heldItemStack.getItem() == Items.FIRE_CHARGE) {
            player.swingArm(event.getHand());

            if (!world.isRemote) {
                ThrowableFireChargeEntity throwableFireChargeEntity = new ThrowableFireChargeEntity(world, player, player.getLookVec().getX(), player.getLookVec().getY(), player.getLookVec().getZ());
                world.addEntity(throwableFireChargeEntity);
                world.playSound(null, event.getPos(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            player.addStat(Stats.ITEM_USED.get(heldItemStack.getItem()));

            if (!player.abilities.isCreativeMode) {
                heldItemStack.shrink(1);
            }
        }
    }

    @SubscribeEvent
    public void onApplyBonemeal(BonemealEvent event) {
        Block block = event.getBlock().getBlock();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        if (block == Blocks.SHROOMLIGHT && Miscellaneous.SHROOMLIGHT_GROWABLE.getBool()) {
            pos = pos.down();
            if (world.isAirBlock(pos)) {
                event.setResult(Event.Result.ALLOW);
                if (world.getRandom().nextDouble() < Miscellaneous.SHROOMLIGHT_GROW_CHANCE.getDouble() && !world.isRemote()) {
                    world.setBlockState(pos, IEBlocks.SHROOMLIGHT_FUNGUS.get().getDefaultState().with(HorizontalBushBlock.FACE, AttachFace.CEILING), 3);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPotionColorCalculate(PotionColorCalculationEvent event) {
        List<EffectInstance> effects = new ArrayList<>(event.getEffects());
        int customEffects = 0;

        // Hide base infection effect particles
        for (EffectInstance effectInstance : effects) {
            if (effectInstance.getPotion() == IEEffects.INFECTION.get() || effectInstance.getPotion() == IEEffects.LUMINOUS.get()) {
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
        if (entity.isServerWorld() && entity.getEntityWorld() instanceof ServerWorld) {
            if (entity.isPotionActive(IEEffects.INFECTION.get())) {
                if ((entity.getActivePotionEffect(IEEffects.INFECTION.get()).getDuration() & 10) == 0 && entity.getActivePotionEffect(IEEffects.INFECTION.get()).doesShowParticles()) {
                    // Use ServerWorld#spawnParticle instead of World#addParticle because this code is running on the server side
                    ((ServerWorld) entity.getEntityWorld()).spawnParticle(IEParticleTypes.INFECTION.get(), entity.getPosXRandom(entity.getBoundingBox().getXSize()), entity.getPosYRandom(), entity.getPosZRandom(entity.getBoundingBox().getZSize()), 0, 0, 0, 0, 1);
                }
            }

            if (entity.isPotionActive(IEEffects.LUMINOUS.get())) {
                if ((entity.getActivePotionEffect(IEEffects.LUMINOUS.get()).getDuration() & 10) == 0 && entity.getActivePotionEffect(IEEffects.LUMINOUS.get()).doesShowParticles()) {
                    // Use ServerWorld#spawnParticle instead of World#addParticle because this code is running on the server side
                    ((ServerWorld) entity.getEntityWorld()).spawnParticle(IEParticleTypes.GLOWSTONE_SPARKLE.get(), entity.getPosXRandom(entity.getBoundingBox().getXSize()), entity.getPosYRandom(), entity.getPosZRandom(entity.getBoundingBox().getZSize()), 0, 0, 0, 0, 1);
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingFinishUse(LivingEntityUseItemEvent.Finish event) {
        ItemStack item = event.getItem();
        LivingEntity entity = (LivingEntity) event.getEntity();

        if (item.getItem() == IEItems.CURED_JERKY.get() && item.getUseAction() == UseAction.EAT) {
            entity.addPotionEffect(new EffectInstance(Effects.SPEED, 20 * Miscellaneous.JERKY_EFFECT_DURATION.getInt(), Miscellaneous.JERKY_EFFECT_AMPLIFIER.getInt()));
        }
    }

    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof AreaEffectCloudEntity) {
            for (EffectInstance effect : ((AreaEffectCloudEntity) event.getEntity()).potion.getEffects()) {
                if (effect.getPotion() == IEEffects.INFECTION.get()) {
                    ((AreaEffectCloudEntity) event.getEntity()).setParticleData(IEParticleTypes.INFECTION.get());
                } else if (effect.getPotion() == IEEffects.LUMINOUS.get()) {
                    ((AreaEffectCloudEntity) event.getEntity()).setParticleData(IEParticleTypes.GLOWSTONE_SPARKLE.get());
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingEntityAttack(LivingAttackEvent event) {
        LivingEntity entity = event.getEntityLiving();

        // If entity has infection, on hit, make a splash of particles
        if (entity.isServerWorld() && entity.getEntityWorld() instanceof ServerWorld) {
            if (entity.isPotionActive(IEEffects.INFECTION.get())) {
                if (event.getSource() != DamageSource.MAGIC) {
                    for (int i = 0; i < 32; i++) {
                        ((ServerWorld) entity.getEntityWorld()).spawnParticle(IEParticleTypes.INFECTION.get(), entity.getPosXRandom(1), entity.getPosYRandom(), entity.getPosZRandom(1), 1, 0, 0, 0, 1);
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
}
