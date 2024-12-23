/**
 * This class was implemented by <JaSpr>. It is distributed as part
 * of the FasterLadderClimbing Mod.
 * https://github.com/JaSpr/FasterLadderClimbing
 *
 * FasterLadderClimbing is Open Source and distributed under the
 * CC-BY-NC-SA 3.0 License: https://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB
 *
 * This class was derived from works created by <Vazkii> which were distributed as
 * part of the Quark Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Quark
 *
 * Quark is Open Source and distributed under the
 * CC-BY-NC-SA 3.0 License: https://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB
 */
package net.jaspr.fasterladderclimbing;

import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.ModLoadingContext;

import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(FasterLadderClimbing.MOD_ID)
@Mod.EventBusSubscriber
public class FasterLadderClimbing {

	public static final String MOD_ID = "fasterladderclimbing";
	public static final Logger LOGGER = LogManager.getLogger(FasterLadderClimbing.MOD_ID);

	public FasterLadderClimbing(FMLJavaModLoadingContext context) {
		// Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
		context.registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true));

		// Load Config
		context.registerConfig(ModConfig.Type.CLIENT, FasterLadderClimbingConfig.CONFIG_SPEC);

		// Register ourselves for server and other game events we are interested in
		//MinecraftForge.EVENT_BUS.register(this);

		IEventBus modEventBus = context.getModEventBus();
		modEventBus.addListener(this::loadComplete);
	}

	private void loadComplete(final FMLLoadCompleteEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new PlayerEventListener());
	}

}