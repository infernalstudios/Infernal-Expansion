package com.nekomaster1000.infernalexp.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.PaintingEntity;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InfernalPaintingEntity extends PaintingEntity {

	public InfernalPaintingEntity(EntityType<? extends PaintingEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public InfernalPaintingEntity(World worldIn, BlockPos pos, Direction facing) {
		super(worldIn, pos, facing);

		List<PaintingType> paintingList = new ArrayList<>();
		int maxSurfaceArea = 0;

		for (PaintingType paintingType : ForgeRegistries.PAINTING_TYPES) {
			updateFacingWithBoundingBox(facing);

			if (onValidSurface() && paintingType.getRegistryName().getNamespace().equals("infernalexp")) {
				paintingList.add(paintingType);

				int surfaceArea = paintingType.getWidth() * paintingType.getHeight();

				if (surfaceArea > maxSurfaceArea) {
					maxSurfaceArea = surfaceArea;
				}
			}
		}

		if (!paintingList.isEmpty()) {
			Iterator<PaintingType> iterator = paintingList.iterator();

			while (iterator.hasNext()) {
				PaintingType paintingType = iterator.next();
				if (paintingType.getWidth() * paintingType.getHeight() < maxSurfaceArea) {
					iterator.remove();
				}
			}

			art = paintingList.get(rand.nextInt(paintingList.size()));
		}

		updateFacingWithBoundingBox(facing);
	}

}
