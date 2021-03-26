package com.nekomaster1000.infernalexp.items;

import java.util.UUID;

public interface IWhipItem {
	UUID REACH_MODIFIER_UUID = UUID.fromString("c6aac07c-3300-4994-be4a-8d42d171de2e");

	default double getReachDistanceModifier() {
		return 1.0D;
	}

	int getTicksSinceAttack();

	boolean getAttacking();

	void setAttacking(boolean value);
}
