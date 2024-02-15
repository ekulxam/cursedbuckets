package survivalblock.cursedbuckets.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Map;
import static net.minecraft.block.cauldron.CauldronBehavior.*;
import static survivalblock.cursedbuckets.item.ModItems.BATER_WUCKET;
import static survivalblock.cursedbuckets.item.ModItems.BAVA_LUCKET;

@Mixin(CauldronBehavior.class)
public interface CauldronBehaviorMixin {
	@Inject(method = "registerBucketBehavior", at = @At("RETURN"))
	private static void onRegisterBucketBehavior(Map<Item, CauldronBehavior> behavior, CallbackInfo ci){
		behavior.put(BATER_WUCKET, FILL_WITH_WATER);
		behavior.put(BAVA_LUCKET, FILL_WITH_LAVA);
	}

	@Local
	@WrapWithCondition(
			method = "targetMethod",
			at = @At(value = "INVOKE", target = "Lsome/package/TargetClass;fillCauldron")
	)
	private boolean isInfinite(World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, BlockState state, SoundEvent soundEvent) {
		return true;
	}
}
