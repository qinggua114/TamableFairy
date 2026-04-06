package com.github.qinggua114.tamablefairy.entity_ai;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum AttackModes implements StringRepresentable{
    ACTIVE("active"),
    PASSIVE("passive"),
    DISABLED("disabled");

    private final String mode;
    AttackModes(String mode) { this.mode = mode; }
    @Override public String getSerializedName() {
        return mode;
    }
    public static final Codec<AttackModes> CODEC = StringRepresentable.fromEnum(AttackModes::values);

}
