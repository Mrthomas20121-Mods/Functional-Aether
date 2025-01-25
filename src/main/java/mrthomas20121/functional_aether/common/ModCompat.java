package mrthomas20121.functional_aether.common;

import mrthomas20121.functional_aether.common.deep_aether.DeepAetherCompat;
import mrthomas20121.functional_aether.common.gravitation.GravitationCompat;
import net.minecraftforge.fml.ModList;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModCompat {

    private static final Map<String, Supplier<IModCompat>> modCompatMap = Map.of(
            "deep_aether", DeepAetherCompat::new,
            "aether_gravitation", GravitationCompat::new);

    public static void init() {

        modCompatMap.forEach((id, compat) -> {
            if(ModList.get().isLoaded(id)) {
                WoodTypes.addAllWoodType(compat.get().getWoodTypes());
            }
        });
    }
}
