package survivalblock.cursedbuckets.mixin;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.cursedbuckets.item.ModItems;

//@Debug(export = true)
@Mixin(DispenserBehavior.class)
public interface DispenserBehaviorMixin {
	@Inject(method = "registerDefaults", at = @At("TAIL"))
	private static void registerInfiniteBucket(CallbackInfo ci) {
		DispenserBehavior dispenseInfiniteBucket = new ItemDispenserBehavior() {
			private final ItemDispenserBehavior fallbackBehavior = new ItemDispenserBehavior();

			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				FluidModificationItem fluidModificationItem = (FluidModificationItem)stack.getItem();
				BlockPos blockPos = pointer.getPos().offset((Direction)pointer.getBlockState().get(DispenserBlock.FACING));
				World world = pointer.getWorld();
				if (fluidModificationItem.placeFluid((PlayerEntity)null, world, blockPos, (BlockHitResult)null)) {
					fluidModificationItem.onEmptied((PlayerEntity)null, world, stack, blockPos);
					return stack;
				} else {
					return this.fallbackBehavior.dispense(pointer, stack);
				}
			}
		};
		DispenserBlock.registerBehavior(ModItems.BATER_WUCKET, dispenseInfiniteBucket);
		DispenserBlock.registerBehavior(ModItems.BAVA_LUCKET, dispenseInfiniteBucket);
	}
}