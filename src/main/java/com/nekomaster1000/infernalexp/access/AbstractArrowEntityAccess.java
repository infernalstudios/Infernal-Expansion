package com.nekomaster1000.infernalexp.access;

import net.minecraft.network.datasync.DataParameter;

public interface AbstractArrowEntityAccess {
	DataParameter<Boolean> getLuminous();

	void setLuminous(boolean isLuminous);
}
