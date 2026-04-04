package com.github.qinggua114.tamablefairy.gui;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;

public class Menus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Registries.MENU, MODID);

    public static final Supplier<MenuType<FairyGuiMenu>> FAIRY_GUI_MENU =
            MENU_TYPES.register("fairy_gui_menu", () -> IMenuTypeExtension.create(FairyGuiMenu::new));

    public static void register(IEventBus modBus) {
        MENU_TYPES.register(modBus);
    }
}
