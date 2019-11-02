package com.hackgsu.mincraft.listener;

import com.hackgsu.mincraft.Mincraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = "mincraft")
public class ClickSignListener
{
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onClickSign(final PlayerInteractEvent.RightClickBlock event)
    {
        if (!event.getWorld().isRemote)
        {
            BlockPos blockPos = event.getPos();
            BlockState blockState = event.getWorld().getBlockState(blockPos);
            Block block = blockState.getBlock();

            if (block instanceof WallSignBlock || block instanceof StandingSignBlock)
            {
                SignTileEntity signTE = (SignTileEntity) event.getWorld().getTileEntity(blockPos);

                List<String> text = Arrays.stream(signTE.signText)
                        .map(textComp -> ((StringTextComponent) textComp).getText())
                        .collect(Collectors.toList());

                String number = text.get(0);
                String message = text.get(1) + text.get(2) + text.get(3);

                Mincraft.getInstance().getBackendPoster().postClickSign(number, message);
            }
        }
    }
}
