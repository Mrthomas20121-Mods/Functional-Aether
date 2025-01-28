package mrthomas20121.functional_aether.common.deep_aether;

import mrthomas20121.functional_aether.api.IModWoodType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import teamrazor.deepaether.datagen.tags.DATags;
import teamrazor.deepaether.init.DABlocks;

import java.util.Locale;
import java.util.function.Supplier;

public enum DeepAetherWoodType implements IModWoodType {

    CONBERRY(DABlocks.CONBERRY_LOG, DABlocks.CONBERRY_PLANKS),
    CRUDEROOT(DABlocks.CRUDEROOT_LOG, DABlocks.CRUDEROOT_PLANKS),
    ROSEROOT(DABlocks.ROSEROOT_LOG, DABlocks.ROSEROOT_PLANKS),
    SUNROOT(DABlocks.SUNROOT_LOG, DABlocks.SUNROOT_PLANKS),
    YAGROOT(DABlocks.YAGROOT_LOG, DABlocks.YAGROOT_PLANKS);

    private final Supplier<Block> log;
    private final Supplier<Block> plank;

    DeepAetherWoodType(Supplier<Block> log, Supplier<Block> plank) {
        this.log = log;
        this.plank = plank;
    }

    @Override
    public Block getWood() {
        return log.get();
    }

    @Override
    public Block getPlanks() {
        return plank.get();
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
