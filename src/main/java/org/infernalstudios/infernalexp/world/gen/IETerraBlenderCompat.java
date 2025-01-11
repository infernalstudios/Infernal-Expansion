package org.infernalstudios.infernalexp.world.gen;

import net.minecraftforge.fml.ModList;
import org.infernalstudios.infernalexp.world.gen.region.TerraBlenderRegion;

public class IETerraBlenderCompat {
    public static void register() {
        if (!ModList.get().isLoaded("terrablender"))
            return;

        TerraBlenderRegion.register();
    }
}
