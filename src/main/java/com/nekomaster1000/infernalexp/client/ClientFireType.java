package com.nekomaster1000.infernalexp.client;

import net.minecraft.client.renderer.model.RenderMaterial;

public class ClientFireType {

	private final RenderMaterial associatedSprite0;
	private final RenderMaterial associatedSprite1;

	public ClientFireType(RenderMaterial associatedSprite0, RenderMaterial associatedSprite1) {
		this.associatedSprite0 = associatedSprite0;
		this.associatedSprite1 = associatedSprite1;
	}

	public RenderMaterial getAssociatedSprite0() {
		return associatedSprite0;
	}

	public RenderMaterial getAssociatedSprite1() {
		return associatedSprite1;
	}

}
