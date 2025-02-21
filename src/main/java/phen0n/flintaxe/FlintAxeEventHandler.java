package phen0n.flintaxe;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = "flintaxedt")
public class FlintAxeEventHandler {

	@SubscribeEvent
	public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
		if (isHoldingFlint(event.getEntity().getMainHandItem()) && isDTLog(event.getState().getBlock())) {
			event.setNewSpeed(6.0F); // wooden axe speed
		}
	}

	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent event) {
		if (event.getPlayer() instanceof ServerPlayer) {
			ServerPlayer player = (ServerPlayer) event.getPlayer();
			Level level = player.level;
			ItemStack mainHandItem = player.getMainHandItem();

			if (isHoldingFlint(mainHandItem) && isDTLog(event.getState().getBlock())) {
				mainHandItem.shrink(1);

				level.playSound(
						null,
						player.getX(), player.getY(), player.getZ(),
						SoundEvents.ITEM_BREAK,
						player.getSoundSource(),
						1.0F, 1.0F
				);
			}
		}
	}

	private static boolean isHoldingFlint(ItemStack stack) {
		return stack.is(Items.FLINT);
	}

	private static boolean isDTLog(Block block) {
		ResourceLocation blockId = ForgeRegistries.BLOCKS.getKey(block);
		if (blockId == null) return false;
		String namespace = blockId.getNamespace();
		if (namespace.equals("dynamictrees") || namespace.equals("dtbop")) {
			String path = blockId.getPath().toLowerCase();
			return path.contains("trunk") || path.contains("branch");
		}
		return false;
	}
}