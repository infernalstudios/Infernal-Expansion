/*
 * Copyright 2021 Infernal Studios
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

package com.nekomaster1000.infernalexp.entities;

import com.nekomaster1000.infernalexp.init.IEEntityTypes;
import com.nekomaster1000.infernalexp.init.IEItems;
import com.nekomaster1000.infernalexp.network.IENetworkHandler;
import com.nekomaster1000.infernalexp.network.SpawnInfernalPaintingPacket;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.PaintingEntity;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InfernalPaintingEntity extends PaintingEntity {

    public InfernalPaintingEntity(EntityType<? extends InfernalPaintingEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public InfernalPaintingEntity(World worldIn, BlockPos pos, Direction facing) {
        this(IEEntityTypes.INFERNAL_PAINTING.get(), worldIn);
        this.hangingPosition = pos;
        updateFacingWithBoundingBox(facing);

        List<PaintingType> paintings = new ArrayList<>();
        int maxSurfaceArea = 0;

        for (PaintingType paintingType : ForgeRegistries.PAINTING_TYPES) {
            art = paintingType;
            updateFacingWithBoundingBox(facing);

            if (onValidSurface() && paintingType.getRegistryName().getNamespace().equals("infernalexp")) {
                paintings.add(paintingType);

                int surfaceArea = paintingType.getWidth() * paintingType.getHeight();

                if (surfaceArea > maxSurfaceArea) {
                    maxSurfaceArea = surfaceArea;
                }
            }
        }

        if (!paintings.isEmpty()) {
            Iterator<PaintingType> iterator = paintings.iterator();

            while (iterator.hasNext()) {
                PaintingType paintingType = iterator.next();

                if (paintingType.getWidth() * paintingType.getHeight() < maxSurfaceArea) {
                    iterator.remove();
                }
            }

            art = paintings.get(this.rand.nextInt(paintings.size()));
        }

        updateFacingWithBoundingBox(facing);
    }

    @OnlyIn(Dist.CLIENT)
    public InfernalPaintingEntity(World world, BlockPos pos, Direction facing, PaintingType art) {
        this(world, pos, facing);
        this.art = art;
        this.updateFacingWithBoundingBox(facing);
    }

    @Override
    public void onBroken(@Nullable Entity brokenEntity) {
        if (world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            playSound(SoundEvents.ENTITY_PAINTING_BREAK, 1.0F, 1.0F);

            if (brokenEntity instanceof PlayerEntity) {
                if (((PlayerEntity) brokenEntity).abilities.isCreativeMode) {
                    return;
                }
            }

            entityDropItem(IEItems.INFERNAL_PAINTING.get());
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return IENetworkHandler.INSTANCE.toVanillaPacket(new SpawnInfernalPaintingPacket(this), NetworkDirection.PLAY_TO_CLIENT);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return IEItems.INFERNAL_PAINTING.get().getDefaultInstance();
    }
}
