package mrthomas20121.functional_aether.common;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mrthomas20121.functional_aether.api.IModWoodType;
import mrthomas20121.functional_aether.common.deep_aether.DeepAetherCompat;
import mrthomas20121.functional_aether.common.gravitation.GravitationCompat;
import net.minecraftforge.fml.ModList;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class WoodTypes {

    private static final List<IModWoodType> woodTypes = new ObjectArrayList<>();

    public static void addWoodType(IModWoodType woodType) {
       woodTypes.add(woodType);
    }

    public static void addAllWoodType(IModWoodType[] types) {
        for(IModWoodType type: types) {
            addWoodType(type);
        }
    }

    public static List<IModWoodType> getWoodTypes() {
        return woodTypes;
    }

    public static void init() {

        woodTypes.addAll(List.of(AetherWoodType.values()));

        ModCompat.init();
    }
}
