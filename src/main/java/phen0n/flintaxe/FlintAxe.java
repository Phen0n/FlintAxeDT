package phen0n.flintaxe;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod("flintaxedt")
public class FlintAxe {
	public FlintAxe() {
		MinecraftForge.EVENT_BUS.register(FlintAxeEventHandler.class);
	}
}