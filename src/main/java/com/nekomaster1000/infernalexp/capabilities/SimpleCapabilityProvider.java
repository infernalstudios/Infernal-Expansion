package com.nekomaster1000.infernalexp.capabilities;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SimpleCapabilityProvider<H> implements ICapabilityProvider {

    protected final Capability<H> capability;
    protected final Direction facing;
    protected final H instance;
    protected final LazyOptional<H> lazyOptional;

    public SimpleCapabilityProvider(Capability<H> capability, @Nullable Direction facing, @Nullable H instance) {
        this.capability = capability;
        this.facing = facing;
        this.instance = instance;

        if (this.instance != null) {
            lazyOptional = LazyOptional.of(() -> this.instance);
        } else {
            lazyOptional = LazyOptional.empty();
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        return getCapability().orEmpty(capability, lazyOptional);
    }

    public Capability<H> getCapability() {
        return capability;
    }

    @Nullable
    public Direction getFacing() {
        return facing;
    }

    @Nullable
    public H getInstance() {
        return instance;
    }
}
