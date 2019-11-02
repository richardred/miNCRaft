package com.hackgsu.mincraft;

import net.minecraft.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("mincraft")
public class Mincraft
{
    private static Mincraft instance;

    private static final Logger LOGGER = LogManager.getLogger();

    private BackendPoster backendPoster;

    public Mincraft()
    {
        instance = this;

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        try
        {
            backendPoster = new BackendPoster();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        // Register ourselves for server and other game events we are interested in

        LOGGER.debug("Registering miNCRaft event listener");
        MinecraftForge.EVENT_BUS.register(AdvancementListener.class);
        MinecraftForge.EVENT_BUS.register(BlockBreakListener.class);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    public static Mincraft getInstance()
    {
        return instance;
    }

    public BackendPoster getBackendPoster()
    {
        return backendPoster;
    }
}
