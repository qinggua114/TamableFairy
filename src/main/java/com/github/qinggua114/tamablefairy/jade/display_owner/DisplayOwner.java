package com.github.qinggua114.tamablefairy.jade.display_owner;

import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class DisplayOwner implements IWailaPlugin {

    public DisplayOwner(){
    }

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerEntityDataProvider(OwnerNameProvider.INSTANCE, EntityFairy.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerEntityComponent(OwnerNameProvider.INSTANCE, EntityFairy.class);
    }
}
