package com.github.qinggua114.tamablefairy.data;

import com.github.qinggua114.tamablefairy.TamableFairy;
//import com.google.common.eventbus.EventBus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class Attachments {
    public Attachments(){
    }

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, TamableFairy.MODID);
    public static final Supplier<AttachmentType<TameData>> TAME_DATA =
            ATTACHMENT_TYPES.register("tame_data",
                    () -> AttachmentType.builder(() -> TameData.EMPTY)
                            .serialize(TameData.CODEC)
                            .sync(TameData.STREAM_CODEC)
                            .build()
            );
    public static void register(IEventBus modBus){
        ATTACHMENT_TYPES.register(modBus);
    }
}
