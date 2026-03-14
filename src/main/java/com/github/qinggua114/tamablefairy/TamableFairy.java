package com.github.qinggua114.tamablefairy;

import com.github.qinggua114.tamablefairy.networks.NetWorks;
import net.minecraftforge.fml.common.Mod;

@Mod(TamableFairy.MODID)
public class TamableFairy {
    public static final String MODID = "tamablefairy";

    public TamableFairy(){

        NetWorks.register();
    }
}