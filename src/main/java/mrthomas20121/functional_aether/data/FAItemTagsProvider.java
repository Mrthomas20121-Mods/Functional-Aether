package mrthomas20121.functional_aether.data;

import com.buuz135.functionalstorage.FunctionalStorage;
import com.buuz135.functionalstorage.util.StorageTags;
import mrthomas20121.functional_aether.FunctionalAether;
import mrthomas20121.functional_aether.api.IModWoodType;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class FAItemTagsProvider extends ItemTagsProvider {

    public FAItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture, CompletableFuture<TagLookup<Block>> lookupCompletableFuture, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, completableFuture, lookupCompletableFuture, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        IntrinsicTagAppender<Item> drawerTag = this.tag(StorageTags.DRAWER);

        for(IModWoodType woodType: FunctionalAether.WOOD_TYPES) {
            for (FunctionalStorage.DrawerType type : FunctionalStorage.DrawerType.values()) {
                // block name
                String name = woodType.getName() + "_" + type.getSlots();
                ResourceLocation blockName = new ResourceLocation(FunctionalAether.MOD_ID, name);

                drawerTag.addOptional(blockName);
            }
        }
    }

    @Override
    public String getName()
    {
        return "Functional Storage Item Tags";
    }

}