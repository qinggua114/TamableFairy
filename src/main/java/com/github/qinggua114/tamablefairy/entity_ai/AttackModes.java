package com.github.qinggua114.tamablefairy.entity_ai;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum AttackModes implements StringRepresentable {
    ACTIVE("active"),
    PASSIVE("passive"),
    DISABLED("disabled");

    private final String mode;

    AttackModes(String mode) {
        this.mode = mode;
    }

    @Override
    public @NotNull String getSerializedName() {
        return mode;
    }

    public static final Codec<AttackModes> CODEC = StringRepresentable.fromEnum(AttackModes::values);

}
