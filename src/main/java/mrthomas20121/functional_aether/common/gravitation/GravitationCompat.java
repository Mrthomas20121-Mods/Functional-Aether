package mrthomas20121.functional_aether.common.gravitation;

import mrthomas20121.functional_aether.api.IModWoodType;
import mrthomas20121.functional_aether.common.IModCompat;

public class GravitationCompat implements IModCompat {

    @Override
    public String getModID() {
        return "gravitation";
    }

    @Override
    public IModWoodType[] getWoodTypes() {
        return GravitationWoodType.values();
    }
}
