package survivalblock.cursedbuckets.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import survivalblock.cursedbuckets.CursedBuckets;
import survivalblock.cursedbuckets.item.custom.AxolotlSunBucketItem;
import survivalblock.cursedbuckets.item.custom.BilkMucketItem;
import survivalblock.cursedbuckets.item.custom.InfiniteBucketItem;

public class ModItems {

    public static final Item BATER_WUCKET = registerItem("bater_wucket",
            new InfiniteBucketItem(Fluids.WATER, new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item BAVA_LUCKET = registerItem("bava_lucket",
            new InfiniteBucketItem(Fluids.LAVA, new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.UNCOMMON).fireproof()));
    public static final Item BILK_MUCKET = registerItem("bilk_mucket",
            new BilkMucketItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item AXOLOTL_SUN_BUCKET = registerItem("axolotl_sun_bucket",
            new AxolotlSunBucketItem(new FabricItemSettings().group(ItemGroup.FOOD).maxCount(1).rarity(Rarity.COMMON).fireproof().
                    food((new FoodComponent.Builder()).hunger(0).saturationModifier(0f).alwaysEdible().build())));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(CursedBuckets.MOD_ID, name), item);
    }

    public static void registerModItems() {
        CursedBuckets.LOGGER.debug("Registering Mod Items for " + CursedBuckets.MOD_ID);
    }
}
