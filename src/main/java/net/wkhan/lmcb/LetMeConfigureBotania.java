package net.wkhan.lmcb;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.wkhan.lmcb.data.reload.LogCleaner;
import net.wkhan.lmcb.common.network.ModNetwork;
import net.wkhan.lmcb.data.reload.ReloadListener;
import net.wkhan.lmcb.common.config.GenerationalConfig;
import net.wkhan.lmcb.common.config.FunctionalConfig;
import net.wkhan.lmcb.common.config.GameplayConfig;
import org.apache.logging.log4j.Logger;

import static org.apache.logging.log4j.LogManager.getLogger;

@Mod(LetMeConfigureBotania.MODID)
public class LetMeConfigureBotania
{
    public static final String MODID = "lmcb";
    public static final Logger LMCB_LOGGER = getLogger();

    public static boolean isKubeJsLoaded;

    public LetMeConfigureBotania(FMLJavaModLoadingContext context)
    {
        LogCleaner.init();
        IEventBus modEventBus = context.getModEventBus();

        isKubeJsLoaded = ModList.get().isLoaded("kubejs");

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::onReload);
        ModNetwork.init();

        context.registerConfig(ModConfig.Type.COMMON, GenerationalConfig.SPEC, MODID + "/gen_config.toml");
        context.registerConfig(ModConfig.Type.COMMON, FunctionalConfig.SPEC, MODID + "/func_config.toml");
        context.registerConfig(ModConfig.Type.COMMON, GameplayConfig.SPEC, MODID + "/gameplay_config.toml");
    }

    private void onReload(AddReloadListenerEvent event) {
        event.addListener(new ReloadListener());
    }


}
