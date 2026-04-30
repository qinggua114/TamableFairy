package com.github.qinggua114.tamablefairy.data.actstate;

import com.github.qinggua114.tamablefairy.entity_ai.AttackModes;
import net.minecraft.world.phys.Vec3;

public interface IActState {
    AttackModes attackMode();
    boolean followOwnerEnabled();
    boolean moveAroundEnabled();
    int followDistance();
    int moveRange();
    Vec3 actRangeCenter();
    void setData(AttackModes attackMode,
                 boolean followOwnerEnabled,
                 boolean moveAroundEnabled,
                 int followDistance,
                 int moveRange,
                 Vec3 actRangeCenter
    );
}
