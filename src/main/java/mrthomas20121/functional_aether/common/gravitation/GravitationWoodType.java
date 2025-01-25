package mrthomas20121.functional_aether.common.gravitation;

import com.aetherteam.aether.block.AetherBlocks;
import mrthomas20121.functional_aether.api.IModWoodType;
import mrthomas20121.gravitation.block.GravitationBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;

import java.util.Locale;
import java.util.function.Supplier;

public enum GravitationWoodType implements IModWoodType {

    AERFIN(GravitationBlocks.AERFIN_LOG, GravitationBlocks.AERFIN_PLANKS),
    BELADON(GravitationBlocks.BELADON_LOG, GravitationBlocks.BELADON_PLANKS),
    ENCHANTED(GravitationBlocks.ENCHANTED_LOG, GravitationBlocks.ENCHANTED_PLANKS);

    private final Supplier<RotatedPillarBlock> log;
    private final Supplier<Block> plank;

    GravitationWoodType(Supplier<RotatedPillarBlock> log, Supplier<Block> plank) {
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
        return "aether_gravitation";
    }
}
