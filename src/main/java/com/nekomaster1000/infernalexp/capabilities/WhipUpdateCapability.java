package com.nekomaster1000.infernalexp.capabilities;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.items.WhipItem;
import com.nekomaster1000.infernalexp.util.InjectionUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class WhipUpdateCapability {

    @CapabilityInject(IWhipUpdate.class)
    public static final Capability<IWhipUpdate> INSTANCE = InjectionUtil.Null();

    public static final Direction DEFAULT_FACING = null;

    public static final ResourceLocation ID = new ResourceLocation(InfernalExpansion.MOD_ID, "ticks_since_attack");

    public static void register() {
        CapabilityManager.INSTANCE.register(IWhipUpdate.class, new Capability.IStorage<IWhipUpdate>() {

            @Override
            public INBT writeNBT(Capability<IWhipUpdate> capability, IWhipUpdate instance, Direction side) {
                CompoundNBT nbt = new CompoundNBT();

                nbt.putInt("TicksSinceAttack", instance.getTicksSinceAttack());
                nbt.putBoolean("Attacking", instance.getAttacking());
                nbt.putBoolean("Charging", instance.getCharging());

                return nbt;
            }

            @Override
            public void readNBT(Capability<IWhipUpdate> capability, IWhipUpdate instance, Direction side, INBT inbt) {
                CompoundNBT nbt = (CompoundNBT) inbt;

                instance.setTicksSinceAttack(nbt.getInt("TicksSinceAttack"));
                instance.setAttacking(nbt.getBoolean("Attacking"));
                instance.setCharging(nbt.getBoolean("Charging"));
            }
        }, WhipUpdate::new);
    }

    public static LazyOptional<IWhipUpdate> getWhipUpdate(ItemStack itemStack) {
        return itemStack.getCapability(INSTANCE, DEFAULT_FACING);
    }

    public static ICapabilityProvider createProvider() {
        return new SerializableCapabilityProvider<>(INSTANCE, DEFAULT_FACING);
    }

    public static ICapabilityProvider createProvider(IWhipUpdate whipUpdate) {
        return new SerializableCapabilityProvider<>(INSTANCE, DEFAULT_FACING, whipUpdate);
    }
}
