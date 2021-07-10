package com.nekomaster1000.infernalexp.items;

public interface IWhipItem {

	int getTicksSinceAttack();

	boolean getAttacking();

	boolean getCharging();

	void setAttacking(boolean value);
}
