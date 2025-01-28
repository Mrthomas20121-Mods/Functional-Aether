package mrthomas20121.functional_aether.data;

import com.buuz135.functionalstorage.FunctionalStorage;
import mrthomas20121.functional_aether.FunctionalAether;
import mrthomas20121.functional_aether.api.IModWoodType;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FABlockTagsProvider extends BlockTagsProvider {

    public FABlockTagsProvider(DataGenerator dataGenerator, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator.getPackOutput(), lookupProvider, FunctionalAether.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        IntrinsicTagAppender<Block> axeMineable = this.tag(BlockTags.MINEABLE_WITH_AXE);

        for(IModWoodType woodType: FunctionalAether.WOOD_TYPES) {
            for (FunctionalStorage.DrawerType type : FunctionalStorage.DrawerType.values()) {
                // block name
                String name = woodType.getName() + "_" + type.getSlots();
                ResourceLocation blockName = new ResourceLocation(FunctionalAether.MOD_ID, name);

                axeMineable.addOptional(blockName);
            }
        }
    }

}