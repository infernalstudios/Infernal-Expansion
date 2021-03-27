package com.nekomaster1000.infernalexp.events;

import com.nekomaster1000.infernalexp.items.IWhipItem;
import com.nekomaster1000.infernalexp.network.IENetworkHandler;
import com.nekomaster1000.infernalexp.network.WhipReachPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

	@SubscribeEvent
	public static void onPlayerLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
		if (event.getItemStack().getItem() instanceof IWhipItem) {
			handleExtendedReach(event.getPlayer());
		}
	}

	@SubscribeEvent
	public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
		if (event.getItemStack().getItem() instanceof IWhipItem) {
			event.setCanceled(handleExtendedReach(event.getPlayer()));
		}
	}

	private static boolean handleExtendedReach(PlayerEntity player) {
		double reach = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();

		Vector3d eyePos = player.getEyePosition(1.0F);
		Vector3d lookVec = player.getLookVec();
		Vector3d reachVec = eyePos.add(lookVec.x * reach, lookVec.y * reach, lookVec.z * reach);

		AxisAlignedBB playerBox = player.getBoundingBox().expand(lookVec.scale(reach)).grow(1.0D, 1.0D, 1.0D);
		EntityRayTraceResult traceResult = ProjectileHelper.rayTraceEntities(player, eyePos, reachVec, playerBox, (target) -> !target.isSpectator() && target.isLiving(), reach * reach);

		if (traceResult != null) {
			double distance = eyePos.squareDistanceTo(traceResult.getHitVec());

			if (distance < reach * reach) {
				IENetworkHandler.sendToServer(new WhipReachPacket(player.getUniqueID(), traceResult.getEntity().getEntityId()));

				return true;
			}
		}

		return false;
	}
}
