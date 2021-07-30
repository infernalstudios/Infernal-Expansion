package com.nekomaster1000.infernalexp.access;

public interface AbstractArrowEntityAccess {
	boolean getLuminous();

	void setLuminous(boolean isLuminous);

	boolean getInfection();

	void setInfection(boolean isInfection);

	boolean getInfectedSource();

	void setInfectedSource(boolean isInfectedSource);

	boolean getGlow();

	void setGlow(boolean shouldGlow);
}
