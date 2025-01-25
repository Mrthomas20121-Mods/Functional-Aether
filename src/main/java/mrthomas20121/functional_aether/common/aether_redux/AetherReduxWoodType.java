package mrthomas20121.functional_aether.common.aether_redux;

import mrthomas20121.functional_aether.api.IModWoodType;
import net.minecraft.world.level.block.Block;
import net.zepalesque.redux.api.blockhandler.WoodHandler;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockhandlers.WoodHandlers;
import teamrazor.deepaether.init.DABlocks;

import java.util.Locale;
import java.util.function.Supplier;

public enum AetherReduxWoodType implements IModWoodType {

    FIELDSPROOT(WoodHandlers.FIELDSPROOT),
    BLIGHTWILLOW(WoodHandlers.BLIGHTWILLOW),
    CLOUDCAP(WoodHandlers.CLOUDCAP),
    JELLYSHROOM(WoodHandlers.JELLYSHROOM),
    CRYSTAL(WoodHandlers.CRYSTAL),
    GLACIA(WoodHandlers.GLACIA);

    private final WoodHandler handler;

    AetherReduxWoodType(WoodHandler woodHandler) {
        this.handler = woodHandler;
    }

    @Override
    public Block getWood() {
        return handler.log.get();
    }

    @Override
    public Block getPlanks() {
        return handler.log.get();
    }

    @Override
    public String getName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    @Override
    public String getModID() {
        return "deep_aether";
    }
}
