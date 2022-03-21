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

package org.infernalstudios.infernalexp.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.registries.ForgeRegistries;
import org.infernalstudios.infernalexp.init.IEEntityTypes;
import org.infernalstudios.infernalexp.init.IEItems;
import org.infernalstudios.infernalexp.network.IENetworkHandler;
import org.infernalstudios.infernalexp.network.SpawnInfernalPaintingPacket;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InfernalPaintingEntity extends Painting {

    public InfernalPaintingEntity(EntityType<? extends InfernalPaintingEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public InfernalPaintingEntity(Level worldIn, BlockPos pos, Direction facing) {
        this(IEEntityTypes.INFERNAL_PAINTING.get(), worldIn);
        this.pos = pos;
        setDirection(facing);

        List<Motive> paintings = new ArrayList<>();
        int maxSurfaceArea = 0;

        for (Motive paintingType : ForgeRegistries.PAINTING_TYPES) {
            motive = paintingType;
            setDirection(facing);

            if (survives() && paintingType.getRegistryName().getNamespace().equals("infernalexp")) {
                paintings.add(paintingType);

                int surfaceArea = paintingType.getWidth() * paintingType.getHeight();

                if (surfaceArea > maxSurfaceArea) {
                    maxSurfaceArea = surfaceArea;
                }
            }
        }

        if (!paintings.isEmpty()) {
            Iterator<Motive> iterator = paintings.iterator();

            while (iterator.hasNext()) {
                Motive paintingType = iterator.next();

                if (paintingType.getWidth() * paintingType.getHeight() < maxSurfaceArea) {
                    iterator.remove();
                }
            }

            motive = paintings.get(this.random.nextInt(paintings.size()));
        }

        setDirection(facing);
    }

    @OnlyIn(Dist.CLIENT)
    public InfernalPaintingEntity(Level world, BlockPos pos, Direction facing, Motive art) {
        this(world, pos, facing);
        this.motive = art;
        this.setDirection(facing);
    }

    @Override
    public void dropItem(@Nullable Entity brokenEntity) {
        if (level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            playSound(SoundEvents.PAINTING_BREAK, 1.0F, 1.0F);

            if (brokenEntity instanceof Player player) {
                if (player.getAbilities().instabuild) {
                    return;
                }
            }

            spawnAtLocation(IEItems.INFERNAL_PAINTING.get());
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return IENetworkHandler.INSTANCE.toVanillaPacket(new SpawnInfernalPaintingPacket(this), NetworkDirection.PLAY_TO_CLIENT);
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return IEItems.INFERNAL_PAINTING.get().getDefaultInstance();
    }
}
