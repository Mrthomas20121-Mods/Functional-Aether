package mrthomas20121.functional_aether.common.aether_redux;

import mrthomas20121.functional_aether.api.IModWoodType;
import mrthomas20121.functional_aether.common.IModCompat;
import mrthomas20121.functional_aether.common.deep_aether.DeepAetherWoodType;

public class AetherReduxCompat implements IModCompat {

    @Override
    public String getModID() {
        return "aether_redux";
    }

    @Override
    public IModWoodType[] getWoodTypes() {
        return AetherReduxWoodType.values();
    }
}
