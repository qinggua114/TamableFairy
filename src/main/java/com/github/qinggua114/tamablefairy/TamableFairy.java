package com.github.qinggua114.tamablefairy;

import com.github.qinggua114.tamablefairy.gui.Menus;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TamableFairy.MODID)
public class TamableFairy {
    public static final String MODID = "tamablefairy";

    public TamableFairy() {

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        Menus.register(modBus);
    }
}