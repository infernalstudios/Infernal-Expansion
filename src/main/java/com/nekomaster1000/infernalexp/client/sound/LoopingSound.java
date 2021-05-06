package com.nekomaster1000.infernalexp.client.sound;

import net.minecraft.client.audio.TickableSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class LoopingSound<E extends Entity> extends TickableSound {
	protected final E entity;

	public LoopingSound(E entity, SoundEvent event, SoundCategory category) {
		super(event, category);
		this.entity = entity;
		this.x = entity.getPosX();
		this.y = entity.getPosY();
		this.z = entity.getPosZ();
		this.repeat = true;
		this.repeatDelay = 0;
		this.volume = 0.0F;
	}

	public void tick() {
		if (this.entity.isAlive()) {
			this.x = (float) this.entity.getPosX();
			this.y = (float) this.entity.getPosY();
			this.z = (float) this.entity.getPosZ();
			float f = MathHelper.sqrt(Entity.horizontalMag(this.entity.getMotion()));
			if ((double) f >= 0.01D) {
				this.pitch = MathHelper.lerp(MathHelper.clamp(f, this.getMinPitch(), this.getMaxPitch()), this.getMinPitch(), this.getMaxPitch());
				this.volume = MathHelper.lerp(MathHelper.clamp(f, 0.0F, 0.5F), 0.0F, 1.2F);
			} else {
				this.pitch = 0.0F;
				this.volume = 0.0F;
			}

		} else {
			this.finishPlaying();
		}
	}

	private float getMinPitch() {
		return 0.7F;
	}

	private float getMaxPitch() {
		return 1.1F;
	}

	public boolean canBeSilent() {
		return true;
	}

	public boolean shouldPlaySound() {
		return !this.entity.isSilent();
	}
}
