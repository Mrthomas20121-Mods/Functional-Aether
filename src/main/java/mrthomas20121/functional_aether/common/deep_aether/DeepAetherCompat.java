package mrthomas20121.functional_aether.common.deep_aether;

import mrthomas20121.functional_aether.api.IModWoodType;
import mrthomas20121.functional_aether.common.IModCompat;

public class DeepAetherCompat implements IModCompat {

    @Override
    public String getModID() {
        return "deep_aether";
    }

    @Override
    public IModWoodType[] getWoodTypes() {
        return DeepAetherWoodType.values();
    }
}
