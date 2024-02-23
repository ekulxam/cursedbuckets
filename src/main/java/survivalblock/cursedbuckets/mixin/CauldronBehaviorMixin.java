package survivalblock.cursedbuckets.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.cursedbuckets.item.ModItems;

import java.util.Map;

//@Debug(export = true)
@Mixin(CauldronBehavior.class)
public interface CauldronBehaviorMixin {
	@Inject(method = "registerBucketBehavior", at = @At("RETURN"))
	private static void onRegisterBucketBehavior(Map<Item, CauldronBehavior> behavior, CallbackInfo ci){
		behavior.put(ModItems.BATER_WUCKET, CauldronBehavior.FILL_WITH_WATER);
		behavior.put(ModItems.BAVA_LUCKET, CauldronBehavior.FILL_WITH_LAVA);
	}

	@WrapOperation(method = "fillCauldron(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;Lnet/minecraft/block/BlockState;Lnet/minecraft/sound/SoundEvent;)Lnet/minecraft/util/ActionResult;",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemUsage;exchangeStack(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"))
	private static ItemStack dontEmptyInfiniteBucket(ItemStack inputStack, PlayerEntity player, ItemStack outputStack, Operation<ItemStack> original) {
		if (inputStack.isOf(ModItems.BATER_WUCKET) || inputStack.isOf(ModItems.BAVA_LUCKET)) {
			return inputStack;
		}
		return original.call(inputStack, player, outputStack);
	}
}
