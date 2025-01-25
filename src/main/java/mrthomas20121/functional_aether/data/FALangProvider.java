package mrthomas20121.functional_aether.data;

import com.buuz135.functionalstorage.FunctionalStorage;
import com.buuz135.functionalstorage.block.DrawerBlock;
import mrthomas20121.functional_aether.FunctionalAether;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.lang3.tuple.Pair;

public class FALangProvider extends LanguageProvider {


    public FALangProvider(PackOutput output) {
        super(output, FunctionalAether.mod_id, "en_us");
    }

    @Override
    protected void addTranslations() {
        for (FunctionalStorage.DrawerType drawerType : FunctionalStorage.DRAWER_TYPES.keySet()) {
            for (RegistryObject<Block> blockRegistryObject : FunctionalStorage.DRAWER_TYPES.get(drawerType).stream().map(Pair::getLeft).toList()) {
                DrawerBlock drawerBlock = (DrawerBlock) blockRegistryObject.get();
                if(FunctionalAether.getBlockName(drawerBlock).contains("tfc") || FunctionalAether.getBlockName(drawerBlock).contains("afc")) {
                    this.add(drawerBlock, WordUtils.capitalize(drawerBlock.getWoodType().getName().replace('_', ' ').toLowerCase()) + " Drawer (" + drawerBlock.getType().getDisplayName() + ")");
                }
            }
        }
    }
}
