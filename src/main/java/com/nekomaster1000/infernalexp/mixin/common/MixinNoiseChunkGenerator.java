package com.nekomaster1000.infernalexp.mixin.common;

import com.nekomaster1000.infernalexp.access.SurfaceBuilderModifyNoise;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.block.BlockState;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(NoiseChunkGenerator.class)
public abstract class MixinNoiseChunkGenerator {

	@Shadow
	@Final
	protected SharedSeedRandom randomSeed;

	@Unique
	BlockPos tempPos = BlockPos.ZERO;

	@Inject(method = "func_230352_b_", at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/ObjectListIterator;back(I)I", shift = Shift.BEFORE), slice = @Slice(from = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/ObjectListIterator;back(I)I", ordinal = 0), to = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/NoiseChunkGenerator;func_236086_a_(DI)Lnet/minecraft/block/BlockState;")), locals = LocalCapture.CAPTURE_FAILHARD)
	private void IE_setModifyNoisePos(IWorld world, StructureManager structureManager, IChunk chunk, CallbackInfo ci, ObjectList objectlist, ObjectList objectlist1, ChunkPos chunkpos, int i, int j, int k, int l, double[][][] adouble, ChunkPrimer chunkprimer, Heightmap heightmap, Heightmap heightmap1, BlockPos.Mutable blockpos$mutable, ObjectListIterator objectlistiterator, ObjectListIterator objectlistiterator1, int i1, int j5, ChunkSection chunksection, int k1, double d0, double d1, double d2, double d3, double d4, double d5, double d6, double d7, int l1, int i2, int j2, int k2, double d8, double d9, double d10, double d11, double d12, int l2, int i3, int j3, double d13, double d14, double d15, int k3, int l3, int i4, double d16, double d17, double d18) {
		tempPos = new BlockPos(chunk.getPos().x * 16 + j3, i2, chunk.getPos().z * 16 + i4);
	}

	@ModifyVariable(method = "func_230352_b_", at = @At("STORE"), ordinal = 0)
	private BlockState IE_modifyNoise(BlockState chosen, IWorld world, StructureManager structureManager, IChunk chunk) {
		Random random = new Random(chunk.getPos().x ^ tempPos.getX() * chunk.getPos().z ^ tempPos.getZ() * tempPos.getY() * this.randomSeed.nextLong());
		BlockState state = ((SurfaceBuilderModifyNoise) world.getBiome(tempPos).getGenerationSettings().getSurfaceBuilder().get().builder).modifyNoise(((NoiseChunkGenerator) (Object) this), tempPos, random, chosen, world, structureManager, chunk);
		return state;
	}

}
