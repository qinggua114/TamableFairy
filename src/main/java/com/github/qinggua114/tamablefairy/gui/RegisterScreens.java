package com.github.qinggua114.tamablefairy.gui;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import static com.github.qinggua114.tamablefairy.gui.Menus.FAIRY_GUI_MENU;

@EventBusSubscriber
public class RegisterScreens {
    public RegisterScreens() {
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(FAIRY_GUI_MENU.get(), FairyGuiScreen::new);
    }
}
