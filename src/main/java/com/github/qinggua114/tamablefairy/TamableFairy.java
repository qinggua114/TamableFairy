package com.github.qinggua114.tamablefairy;

import com.github.qinggua114.tamablefairy.data.Attachments;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import com.github.qinggua114.tamablefairy.events.*;

@Mod(TamableFairy.MODID)
public class TamableFairy {
    public static final String MODID = "tamablefairy";

    public TamableFairy(IEventBus modBus){
        NeoForge.EVENT_BUS.addListener(TestEvent::onJump);
        Attachments.register(modBus);
    }
}