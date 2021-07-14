package com.nekomaster1000.infernalexp.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

public class SerializableCapabilityProvider<H> extends SimpleCapabilityProvider<H> implements INBTSerializable<INBT> {

    public SerializableCapabilityProvider(Capability<H> capability, @Nullable Direction facing) {
        this(capability, facing, capability.getDefaultInstance());
    }

    public SerializableCapabilityProvider(Capability<H> capability, @Nullable Direction facing, @Nullable H instance) {
        super(capability, facing, instance);
    }

    @Override
    public INBT serializeNBT() {
        final H instance = getInstance();

        if (instance == null) {
            return null;
        }

        return getCapability().writeNBT(instance, getFacing());
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        final H instance = getInstance();

        if (instance == null) {
            return;
        }

        getCapability().readNBT(instance, getFacing(), nbt);
    }
}
