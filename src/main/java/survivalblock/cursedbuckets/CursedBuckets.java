package survivalblock.cursedbuckets;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import survivalblock.cursedbuckets.item.ModItems;

import static survivalblock.cursedbuckets.item.ModItems.AXOLOTL_SUN_BUCKET;
import static survivalblock.cursedbuckets.item.ModItems.BAVA_LUCKET;

public class CursedBuckets implements ModInitializer {

	public static final String MOD_ID = "cursedbuckets";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		FuelRegistry.INSTANCE.add(BAVA_LUCKET, 1000);
		FuelRegistry.INSTANCE.add(AXOLOTL_SUN_BUCKET, 4000);
	}
}
