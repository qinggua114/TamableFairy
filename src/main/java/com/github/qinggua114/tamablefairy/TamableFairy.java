package com.github.qinggua114.tamablefairy;

import com.github.qinggua114.tamablefairy.data.Attachments;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import com.github.qinggua114.tamablefairy.events.*;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(TamableFairy.MODID)
public class TamableFairy {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "tamablefairy";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public TamableFairy(IEventBus modBus){
        NeoForge.EVENT_BUS.addListener(TestEvent::onJump);
        Attachments.register(modBus);
    }
}