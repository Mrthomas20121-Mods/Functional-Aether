package mrthomas20121.functional_aether.common.ancient_aether;

import mrthomas20121.functional_aether.api.IModWoodType;
import net.builderdog.ancient_aether.block.AncientAetherBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import teamrazor.deepaether.init.DABlocks;

import java.util.Locale;
import java.util.function.Supplier;

public enum AncientAetherWoodType implements IModWoodType {

    HIGHSPROOT(AncientAetherBlocks.HIGHSPROOT_LOG, AncientAetherBlocks.HIGHSPROOT_PLANKS),
    SAKURA(AncientAetherBlocks.SAKURA_LOG, AncientAetherBlocks.SAKURA_PLANKS);

    private final Supplier<RotatedPillarBlock> log;
    private final Supplier<Block> plank;

    AncientAetherWoodType(Supplier<RotatedPillarBlock> log, Supplier<Block> plank) {
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
        return "ancient_aether";
    }
}
