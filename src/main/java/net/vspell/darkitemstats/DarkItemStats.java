package net.vspell.darkitemstats;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.vspell.darkitemstats.item.ModCreativeTabs;
import net.vspell.darkitemstats.item.ModItems;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DarkItemStats.MOD_ID)
public class DarkItemStats {
    public static final String MOD_ID = "darkitemstats";

    private static final Logger LOGGER = LogUtils.getLogger();

    public DarkItemStats() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus(); //this warning is fine
        modEventBus.addListener(this::commonSetup); // looks at commonSetup's params and automatically adds it as a listener for the FMLCommonSetupEvent event (dawg this shi crazy frfr)

        ModCreativeTabs.register(modEventBus); // register creative tabs
        ModItems.register(modEventBus); // register items

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
