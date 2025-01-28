package mrthomas20121.functional_aether.common;

import com.buuz135.functionalstorage.FunctionalStorage;
import com.buuz135.functionalstorage.block.DrawerBlock;
import com.buuz135.functionalstorage.block.tile.DrawerTile;
import com.buuz135.functionalstorage.util.IWoodType;
import mrthomas20121.functional_aether.FunctionalAether;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.apache.commons.lang3.tuple.Pair;

/**
 * We are extending {@link DrawerBlock} because getTileEntityFactory(); uses FS DRAWER_TYPES variable and not our own.
 */
public class AetherDrawerBlock extends DrawerBlock {

    private final FunctionalStorage.DrawerType type;
    private final IWoodType woodType;

    public AetherDrawerBlock(IWoodType woodType, FunctionalStorage.DrawerType type, Properties properties) {
        super(woodType, type, properties);
        this.woodType = woodType;
        this.type = type;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<DrawerTile> getTileEntityFactory() {
        return (blockPos, state) -> new DrawerTile(this, (BlockEntityType<DrawerTile>) FunctionalAether.DRAWER_TYPES.get(type).stream().filter(registryObjectRegistryObjectPair -> registryObjectRegistryObjectPair.getLeft().get().equals(this)).map(Pair::getRight).findFirst().get().get(), blockPos, state, type, woodType);
    }
}
