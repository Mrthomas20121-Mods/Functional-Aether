package mrthomas20121.functional_aether.data;

import com.buuz135.functionalstorage.FunctionalStorage;
import mrthomas20121.functional_aether.FunctionalAether;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FABlockTagsProvider extends BlockTagsProvider {

    public FABlockTagsProvider(DataGenerator dataGenerator, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator.getPackOutput(), lookupProvider, FunctionalAether.mod_id, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        IntrinsicTagAppender<Block> axeMineable = this.tag(BlockTags.MINEABLE_WITH_AXE);
        for (FunctionalStorage.DrawerType drawerType : FunctionalStorage.DRAWER_TYPES.keySet()) {
            for (RegistryObject<Block> blockRegistryObject : FunctionalStorage.DRAWER_TYPES.get(drawerType).stream().map(Pair::getLeft).toList()) {
                if(FunctionalAether.getBlockName(blockRegistryObject.get()).contains("tfc")) {
                    axeMineable.addOptional(FunctionalAether.getRegistryName(blockRegistryObject.get()));
                }
            }
        }
    }

}