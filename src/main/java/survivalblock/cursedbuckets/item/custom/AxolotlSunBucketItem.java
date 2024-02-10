package survivalblock.cursedbuckets.item.custom;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AxolotlSunBucketItem extends Item {
    private static final int MAX_USE_TIME = 32;

    public AxolotlSunBucketItem(Settings settings) {
        super(settings);
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }

        if (user instanceof PlayerEntity) {
            stack.decrement(1);
        }

        if (!world.isClient) {
            user.damage(DamageSource.ON_FIRE
                    .setBypassesProtection()
                    .setBypassesArmor()
                    .setUnblockable()
                    .setOutOfWorld()
                    .setScaledWithDifficulty(),
                    (float) (Math.abs(user.getMaxHealth()) + Math.abs(user.getHealth() + Math.abs(user.getAbsorptionAmount())))*100
            );
        }

        return stack.isEmpty() ? new ItemStack(Items.AIR) : stack;
    }

    public int getMaxUseTime(ItemStack stack) {
        return MAX_USE_TIME;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown() || Screen.hasAltDown()){
            tooltip.add(Text.translatable("item.cursedbuckets.axolotl_sun_bucket.warningshown").formatted(Formatting.GOLD));
            tooltip.add(Text.translatable("item.cursedbuckets.infinitebucketitem.lavaimmune").formatted(Formatting.GOLD));
            tooltip.add(Text.translatable("item.cursedbuckets.infinitebucketitem.fuelsource").formatted(Formatting.GOLD));
        } else {
            tooltip.add(Text.translatable("item.cursedbuckets.axolotl_sun_bucket.warninghidden").formatted(Formatting.GOLD));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }
}
