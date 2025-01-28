package mrthomas20121.functional_aether;

import com.buuz135.functionalstorage.FunctionalStorage;
import com.buuz135.functionalstorage.block.*;
import com.buuz135.functionalstorage.block.tile.DrawerTile;
import com.buuz135.functionalstorage.client.DrawerRenderer;
import com.hrznstudio.titanium.datagenerator.loot.TitaniumLootTableProvider;
import com.hrznstudio.titanium.datagenerator.model.BlockItemModelGeneratorProvider;
import com.hrznstudio.titanium.event.handler.EventManager;
import com.hrznstudio.titanium.module.ModuleController;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mrthomas20121.functional_aether.api.IModWoodType;
import mrthomas20121.functional_aether.common.AetherDrawerBlock;
import mrthomas20121.functional_aether.common.AetherWoodType;
import mrthomas20121.functional_aether.common.aether_redux.AetherReduxWoodType;
import mrthomas20121.functional_aether.common.ancient_aether.AncientAetherWoodType;
import mrthomas20121.functional_aether.common.deep_aether.DeepAetherWoodType;
import mrthomas20121.functional_aether.common.gravitation.AetherGravitationWoodType;
import mrthomas20121.functional_aether.data.*;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

import static com.buuz135.functionalstorage.FunctionalStorage.*;

@Mod(FunctionalAether.MOD_ID)
public class FunctionalAether extends ModuleController {

	public static final String MOD_ID = "functional_aether";

	public static ConcurrentLinkedQueue<IModWoodType> WOOD_TYPES = new ConcurrentLinkedQueue<>();

	public static HashMap<DrawerType, List<Pair<RegistryObject<Block>, RegistryObject<BlockEntityType<?>>>>> DRAWER_TYPES = new HashMap<>();

	public FunctionalAether() {
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> this::onClient);
	}

	@Override
	protected void initModules() {
		WOOD_TYPES.addAll(List.of(AetherWoodType.values()));

		if(ModList.get().isLoaded("ancient_aether")) {
			WOOD_TYPES.addAll(List.of(AncientAetherWoodType.values()));
		}

		if(ModList.get().isLoaded("aether_redux")) {
			WOOD_TYPES.addAll(List.of(AetherReduxWoodType.values()));
		}

		if(ModList.get().isLoaded("deep_aether")) {
			WOOD_TYPES.addAll(List.of(DeepAetherWoodType.values()));
		}

		if(ModList.get().isLoaded("gravitation")) {
			WOOD_TYPES.addAll(List.of(AetherGravitationWoodType.values()));
		}

		for (DrawerType value : DrawerType.values()) {
			for (IModWoodType woodType : WOOD_TYPES) {
				var name = woodType.getName() + "_" + value.getSlots();
				DRAWER_TYPES.computeIfAbsent(value, drawerType -> new ArrayList<>()).add(getRegistries().registerBlockWithTileItem(name, () -> new AetherDrawerBlock(woodType, value, BlockBehaviour.Properties.copy(woodType.getPlanks())), blockRegistryObject -> () ->
						new DrawerBlock.DrawerItem((DrawerBlock) blockRegistryObject.get(), new Item.Properties(), TAB),TAB));
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void onClient() {
		EventManager.mod(EntityRenderersEvent.RegisterRenderers.class).process(registerRenderers -> {
			for (DrawerType value : DrawerType.values()) {
				DRAWER_TYPES.get(value).forEach(blockRegistryObject -> {
					registerRenderers.registerBlockEntityRenderer((BlockEntityType<? extends DrawerTile>) blockRegistryObject.getRight().get(), p_173571_ -> new DrawerRenderer());
				});
			}

		}).subscribe();

		EventManager.mod(FMLClientSetupEvent.class).process(event -> {
			for (DrawerType value : DrawerType.values()) {
				for (RegistryObject<Block> blockRegistryObject : DRAWER_TYPES.get(value).stream().map(Pair::getLeft).toList()) {
					ItemBlockRenderTypes.setRenderLayer(blockRegistryObject.get(), RenderType.cutout());
				}
			}

		}).subscribe();
	}

	public static String getBlockName(Block block) {
		return getRegistryName(block).getPath();
	}

	public static ResourceLocation getRegistryName(Block block) {
		return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block));
	}

	public static String getModelBlockName(Block block) {
		ResourceLocation loc = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block));
		return FunctionalStorage.MOD_ID+":block/"+loc.getPath();
	}

	@Override
	public void addDataProvider(GatherDataEvent event) {

		List<Block> blocks = new ObjectArrayList<>();

		for(IModWoodType woodType: WOOD_TYPES) {
			for (FunctionalStorage.DrawerType type : FunctionalStorage.DrawerType.values()) {
				// block name
				String name = woodType.getName() + "_" + type.getSlots();
				ResourceLocation blockName = new ResourceLocation(FunctionalAether.MOD_ID, name);
				Block block = Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(blockName));

				blocks.add(block);
			}
		}

		NonNullLazy<List<Block>> blocksToProcess = NonNullLazy.of(() -> blocks);

		DataGenerator generator = event.getGenerator();
		ExistingFileHelper fileHelper = event.getExistingFileHelper();

		event.getGenerator().addProvider(event.includeClient(), new FABlockstateProvider(generator, fileHelper, blocksToProcess));
		event.getGenerator().addProvider(event.includeClient(), new BlockItemModelGeneratorProvider(event.getGenerator(), FunctionalAether.MOD_ID, blocksToProcess));

		event.getGenerator().addProvider(event.includeClient(), new ItemModelProvider(event.getGenerator().getPackOutput(), FunctionalAether.MOD_ID, fileHelper) {
			@Override
			protected void registerModels() {
				blocksToProcess.get().forEach(block -> {
					if ((block instanceof DrawerBlock) || (block instanceof CompactingDrawerBlock) || (block instanceof SimpleCompactingDrawerBlock) || (block instanceof FluidDrawerBlock)){
						withUnchecked(ForgeRegistries.BLOCKS.getKey(block).getPath(), new ResourceLocation("minecraft", "builtin/entity"));
					} else {
						withUnchecked(ForgeRegistries.BLOCKS.getKey(block).getPath(), new ResourceLocation(FunctionalAether.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(block).getPath()));
					}
				});
			}

			private void item(Item item) {
				withUnchecked(ForgeRegistries.ITEMS.getKey(item).getPath(), new ResourceLocation("minecraft:item/generated")).texture( "layer0", new ResourceLocation(FunctionalAether.MOD_ID, "item/" + ForgeRegistries.ITEMS.getKey(item).getPath()));
			}

			private ItemModelBuilder withUnchecked(String name, ResourceLocation parent){
				return getBuilder(name).parent(new ModelFile.UncheckedModelFile(parent));
			}
		});
		event.getGenerator().addProvider(event.includeClient(), new BlockModelProvider(event.getGenerator().getPackOutput(), FunctionalAether.MOD_ID, fileHelper) {
			@Override
			protected void registerModels() {
				for(Block block: blocksToProcess.get()) {

					String blockName = getBlockName(block);

					if(blockName.contains("1")) {
						withExistingParent(blockName, FunctionalStorage.MOD_ID +":base_x_1")
								.texture("particle", getModelBlockName(block).replace("1", "front_1"))
								.texture("front", getModelBlockName(block).replace("1", "front_1"))
								.texture("side", getModelBlockName(block).replace("1", "side"));
					}
					else if(blockName.contains("2")) {
						withExistingParent(blockName, FunctionalStorage.MOD_ID +":base_x_2")
								.texture("particle", getModelBlockName(block).replace("2", "front_2"))
								.texture("front", getModelBlockName(block).replace("2", "front_2"))
								.texture("side", getModelBlockName(block).replace("2", "side"));
					}
					else if(blockName.contains("4")) {
						withExistingParent(blockName, FunctionalStorage.MOD_ID +":base_x_4")
								.texture("particle", getModelBlockName(block).replace("4", "front_4"))
								.texture("front", getModelBlockName(block).replace("4", "front_4"))
								.texture("side", getModelBlockName(block).replace("4", "side"));
					}

					withExistingParent(ForgeRegistries.BLOCKS.getKey(block).getPath() + "_locked", modLoc(ForgeRegistries.BLOCKS.getKey(block).getPath()))
							.texture("lock_icon", new ResourceLocation(FunctionalStorage.MOD_ID, "block/lock"));
				}
			}
		});

		var blockTags = new FABlockTagsProvider(event.getGenerator(), event.getLookupProvider(), event.getExistingFileHelper());
		event.getGenerator().addProvider(event.includeServer(), blockTags);
		event.getGenerator().addProvider(event.includeServer(), new FAItemTagsProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), blockTags.contentsGetter(), FunctionalAether.MOD_ID, fileHelper));
		event.getGenerator().addProvider(event.includeClient(), new FALangProvider(event.getGenerator().getPackOutput()));

		event.getGenerator().addProvider(event.includeServer(), new TitaniumLootTableProvider(event.getGenerator(), blocksToProcess));

		event.getGenerator().addProvider(event.includeServer(), new FARecipesProvider(generator));
	}
}
