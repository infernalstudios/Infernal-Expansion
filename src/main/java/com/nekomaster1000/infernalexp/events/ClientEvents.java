package com.nekomaster1000.infernalexp.events;

import com.nekomaster1000.infernalexp.items.IWhipItem;
import com.nekomaster1000.infernalexp.packets.IEPacketHandler;
import com.nekomaster1000.infernalexp.packets.WhipReachPacket;
import net.minecraft.entity.Entity;
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
		PlayerEntity player = event.getPlayer();
		if(event.getItemStack().getItem() instanceof IWhipItem) {
			handleExtendedReach(player);
		}
	}

	@SubscribeEvent
	public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
		PlayerEntity player = event.getPlayer();
		if(event.getItemStack().getItem() instanceof IWhipItem) {
			event.setCanceled(handleExtendedReach(player));
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
			Entity target = traceResult.getEntity();
			Vector3d hitVec = traceResult.getHitVec();
			double distance = eyePos.squareDistanceTo(hitVec);
			if (distance < (reach * reach) * player.getCooledAttackStrength(0.5F)) {
				IEPacketHandler.sendToServer(new WhipReachPacket(player.getUniqueID(), target.getEntityId()));
				return true;
			}
		}
		return false;
	}
}
