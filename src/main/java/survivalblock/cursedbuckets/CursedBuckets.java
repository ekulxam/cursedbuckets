package survivalblock.cursedbuckets;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import survivalblock.cursedbuckets.item.AxolotlSunBucketItem;
import survivalblock.cursedbuckets.item.BilkMucketItem;
import survivalblock.cursedbuckets.item.InfiniteBucketItem;

public class CursedBuckets implements ModInitializer {

	public static final String MOD_ID = "cursedbuckets";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Item BATER_WUCKET = registerItem("bater_wucket",
			new InfiniteBucketItem(Fluids.WATER, new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.UNCOMMON)), true);
	public static final Item BAVA_LUCKET = registerItem("bava_lucket",
			new InfiniteBucketItem(Fluids.LAVA, new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.UNCOMMON).fireproof()), true);
	public static final Item BILK_MUCKET = registerItem("bilk_mucket",
			new BilkMucketItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.UNCOMMON)), false);
	public static final Item AXOLOTL_SUN_BUCKET = registerItem("axolotl_sun_bucket",
			new AxolotlSunBucketItem(new FabricItemSettings().group(ItemGroup.FOOD).maxCount(1).rarity(Rarity.COMMON).fireproof().
					food((new FoodComponent.Builder()).hunger(0).saturationModifier(0f).alwaysEdible().build())), false);
	private static Item registerItem(String name, Item item, boolean shouldRegisterBehavior) {
		if(shouldRegisterBehavior){
			DispenserBlock.registerBehavior(item, new ItemDispenserBehavior() {
				private final ItemDispenserBehavior fallbackBehavior = new ItemDispenserBehavior();
				public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
					FluidModificationItem fluidModificationItem = (FluidModificationItem) stack.getItem();
					BlockPos blockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
					World world = pointer.getWorld();
					if (fluidModificationItem.placeFluid(null, world, blockPos, null)) {
						fluidModificationItem.onEmptied(null, world, stack, blockPos);
						return stack;
					} else {
						return this.fallbackBehavior.dispense(pointer, stack);
					}
				}
			});
		}
		return Registry.register(Registry.ITEM, new Identifier(CursedBuckets.MOD_ID, name), item);
	}

	public static void registerModItems() {
		CursedBuckets.LOGGER.debug("Registering Mod Items for " + CursedBuckets.MOD_ID);
	}

	@Override
	public void onInitialize() {
		registerModItems();
		FuelRegistry.INSTANCE.add(BAVA_LUCKET, 1000);
		FuelRegistry.INSTANCE.add(AXOLOTL_SUN_BUCKET, 4000);
	}
}
