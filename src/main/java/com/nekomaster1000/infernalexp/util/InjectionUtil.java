package com.nekomaster1000.infernalexp.util;

public class InjectionUtil {

    /**
     * Used to suppress IntelliJ's warnings when using auto injection systems like {@link net.minecraftforge.common.capabilities.CapabilityInject}
     *
     * @param <T> The field's type
     * @return null
     */
    @SuppressWarnings({"ConstantConditions", "SameReturnValue"})
    public static <T> T Null() {
        return null;
    }

}
