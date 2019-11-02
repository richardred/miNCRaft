package com.hackgsu.mincraft;

import com.hackgsu.mincraft.listener.AdvancementListener;
import com.hackgsu.mincraft.listener.BlockBreakListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
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

    public static Mincraft getInstance()
    {
        return instance;
    }

    public BackendPoster getBackendPoster()
    {
        return backendPoster;
    }
}
