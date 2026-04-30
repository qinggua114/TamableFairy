package com.github.qinggua114.tamablefairy.gui;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;

public class Menus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Registries.MENU, MODID);

    public static final RegistryObject<MenuType<FairyGuiMenu>> FAIRY_GUI_MENU =
            MENU_TYPES.register("fairy_gui_menu", () -> IForgeMenuType.create(FairyGuiMenu::new));

    public static void register(IEventBus modBus) {
        MENU_TYPES.register(modBus);
    }
}
