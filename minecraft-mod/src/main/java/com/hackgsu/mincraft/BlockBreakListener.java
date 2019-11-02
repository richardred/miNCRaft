package com.hackgsu.mincraft;

import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = "mincraft")
public class BlockBreakListener
{
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onBlockBreak(final BlockEvent.BreakEvent event)
    {
        LOGGER.info("Triggered a block break event.");

        Mincraft.getInstance().getBackendPoster().postBlockBreak(
                event.getState().getBlock().getRegistryName().toString(),
                event.getPlayer().getScoreboardName()
        );
    }
}
