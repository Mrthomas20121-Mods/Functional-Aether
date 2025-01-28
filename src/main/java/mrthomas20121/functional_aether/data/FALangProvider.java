package mrthomas20121.functional_aether.data;

import com.buuz135.functionalstorage.FunctionalStorage;
import com.buuz135.functionalstorage.block.DrawerBlock;
import mrthomas20121.functional_aether.FunctionalAether;
import mrthomas20121.functional_aether.api.IModWoodType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Objects;

public class FALangProvider extends LanguageProvider {

    public FALangProvider(PackOutput output) {
        super(output, FunctionalAether.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        for(IModWoodType woodType: FunctionalAether.WOOD_TYPES) {
            for (FunctionalStorage.DrawerType type : FunctionalStorage.DrawerType.values()) {
                // block name
                String name = woodType.getName() + "_" + type.getSlots();
                ResourceLocation blockName = new ResourceLocation(FunctionalAether.MOD_ID, name);
                Block block = Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(blockName));

                this.add(block, WordUtils.capitalize(woodType.getName().replace('_', ' ').toLowerCase()) + " Drawer (" + type.getDisplayName() + ")");

            }
        }
    }
}
