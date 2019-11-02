package com.hackgsu.mincraft;

import net.minecraft.advancements.Advancement;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = "mincraft")
public class AdvancementListener
{
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onAdvancement(final AdvancementEvent event)
    {
        LOGGER.info("Triggered an advancement event.");

        Advancement adv = event.getAdvancement();

        Mincraft.getInstance().getBackendPoster().postAdvancement(
                adv,
                event.getPlayer().getScoreboardName()
        );
    }
}
