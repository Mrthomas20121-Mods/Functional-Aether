package mrthomas20121.functional_aether.common;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import mrthomas20121.functional_aether.api.IModWoodType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;

import java.util.Locale;
import java.util.function.Supplier;

public enum AetherWoodType implements IModWoodType {

    SKYROOT(AetherBlocks.SKYROOT_LOG, AetherBlocks.SKYROOT_PLANKS);

    private final Supplier<RotatedPillarBlock> log;
    private final Supplier<Block> plank;

    AetherWoodType(Supplier<RotatedPillarBlock> log, Supplier<Block> plank) {
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
        return Aether.MODID;
    }
}
