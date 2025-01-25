package mrthomas20121.functional_aether;

import com.buuz135.functionalstorage.FunctionalStorage;
import com.buuz135.functionalstorage.block.*;
import com.hrznstudio.titanium.datagenerator.loot.TitaniumLootTableProvider;
import com.hrznstudio.titanium.datagenerator.model.BlockItemModelGeneratorProvider;
import com.hrznstudio.titanium.module.ModuleController;
import mrthomas20121.functional_aether.api.IModWoodType;
import mrthomas20121.functional_aether.common.ModCompat;
import mrthomas20121.functional_aether.common.WoodTypes;
import mrthomas20121.functional_aether.data.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static com.buuz135.functionalstorage.FunctionalStorage.DRAWER_TYPES;
import static com.buuz135.functionalstorage.FunctionalStorage.MOD_ID;

@Mod(FunctionalAether.mod_id)
public class FunctionalAether extends ModuleController {

	public static final String mod_id = "functional_aether";

	@Override
	protected void initModules() {
		WoodTypes.init();

		FunctionalStorage.WOOD_TYPES.addAll(WoodTypes.getWoodTypes());
	}

	public static String getBlockName(Block block) {
		return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
	}

	public static ResourceLocation getRegistryName(Block block) {
		return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block));
	}

	public static String getModelBlockName(Block block) {
		ResourceLocation loc = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block));
		return loc.getNamespace()+":block/"+loc.getPath();
	}

	@Override
	public void addDataProvider(GatherDataEvent event) {
		NonNullLazy<List<Block>> blocksToProcess = NonNullLazy.of(() ->
				ForgeRegistries.BLOCKS.getValues()
						.stream()
						.filter(basicBlock -> Optional.ofNullable(ForgeRegistries.BLOCKS.getKey(basicBlock))
								.map(ResourceLocation::getNamespace)
								.filter(MOD_ID::equalsIgnoreCase)
								.isPresent())
						.filter(basicBlock -> getBlockName(basicBlock).contains("aether"))
						.toList()
		);

		List<IModWoodType> woodTypes = WoodTypes.getWoodTypes();

		DataGenerator generator = event.getGenerator();
		ExistingFileHelper fileHelper = event.getExistingFileHelper();

		event.getGenerator().addProvider(event.includeServer(), new FABlockstateProvider(generator, fileHelper, blocksToProcess));
		event.getGenerator().addProvider(event.includeClient(), new BlockItemModelGeneratorProvider(event.getGenerator(), MOD_ID, blocksToProcess));

		event.getGenerator().addProvider(event.includeClient(), new ItemModelProvider(event.getGenerator().getPackOutput(), MOD_ID, fileHelper) {
			@Override
			protected void registerModels() {
				blocksToProcess.get().forEach(block -> {
					if ((block instanceof DrawerBlock) || (block instanceof CompactingDrawerBlock) || (block instanceof SimpleCompactingDrawerBlock) || (block instanceof FluidDrawerBlock)){
						withUnchecked(ForgeRegistries.BLOCKS.getKey(block).getPath(), new ResourceLocation("minecraft", "builtin/entity"));
					} else {
						withUnchecked(ForgeRegistries.BLOCKS.getKey(block).getPath(), new ResourceLocation(MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(block).getPath()));
					}
				});
			}

			private void item(Item item) {
				withUnchecked(ForgeRegistries.ITEMS.getKey(item).getPath(), new ResourceLocation("minecraft:item/generated")).texture( "layer0", new ResourceLocation(MOD_ID, "item/" + ForgeRegistries.ITEMS.getKey(item).getPath()));
			}

			private ItemModelBuilder withUnchecked(String name, ResourceLocation parent){
				return getBuilder(name).parent(new ModelFile.UncheckedModelFile(parent));
			}
		});
		event.getGenerator().addProvider(event.includeClient(), new BlockModelProvider(event.getGenerator().getPackOutput(), MOD_ID, fileHelper) {
			@Override
			protected void registerModels() {
				for (FunctionalStorage.DrawerType value : FunctionalStorage.DrawerType.values()) {
					for (RegistryObject<Block> blockRegistryObject : DRAWER_TYPES.get(value).stream().map(Pair::getLeft).toList()) {
						if (blockRegistryObject.get() instanceof FramedDrawerBlock) {
							continue;
						}
						String blockName = getBlockName(blockRegistryObject.get());
						if(blockName.contains("aether")) {

							if(blockName.contains("1")) {
								withExistingParent(blockName, MOD_ID+":base_x_1")
										.texture("particle", getModelBlockName(blockRegistryObject.get()).replace("1", "front_1"))
										.texture("front", getModelBlockName(blockRegistryObject.get()).replace("1", "front_1"))
										.texture("side", getModelBlockName(blockRegistryObject.get()).replace("1", "side"));
							}
							else if(blockName.contains("2")) {
								withExistingParent(blockName, MOD_ID+":base_x_2")
										.texture("particle", getModelBlockName(blockRegistryObject.get()).replace("2", "front_2"))
										.texture("front", getModelBlockName(blockRegistryObject.get()).replace("2", "front_2"))
										.texture("side", getModelBlockName(blockRegistryObject.get()).replace("2", "side"));
							}
							else if(blockName.contains("4")) {
								withExistingParent(blockName, MOD_ID+":base_x_4")
										.texture("particle", getModelBlockName(blockRegistryObject.get()).replace("4", "front_4"))
										.texture("front", getModelBlockName(blockRegistryObject.get()).replace("4", "front_4"))
										.texture("side", getModelBlockName(blockRegistryObject.get()).replace("4", "side"));
							}

							withExistingParent(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath() + "_locked", modLoc(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()))
									.texture("lock_icon", modLoc("block/lock"));
						}
					}
				}
			}
		});

		var blockTags = new FABlockTagsProvider(event.getGenerator(), event.getLookupProvider(), event.getExistingFileHelper());
		event.getGenerator().addProvider(event.includeServer(), blockTags);
		event.getGenerator().addProvider(event.includeServer(), new FAItemTagsProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), blockTags.contentsGetter(), mod_id, fileHelper));
		event.getGenerator().addProvider(event.includeClient(), new FALangProvider(event.getGenerator().getPackOutput()));

		event.getGenerator().addProvider(event.includeServer(), new TitaniumLootTableProvider(event.getGenerator(), blocksToProcess));

		event.getGenerator().addProvider(event.includeServer(), new FARecipesProvider<>(generator, woodTypes));
	}
}
