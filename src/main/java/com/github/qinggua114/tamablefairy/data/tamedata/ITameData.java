package com.github.qinggua114.tamablefairy.data.tamedata;

import java.util.UUID;

public interface ITameData {
    boolean tamed();

    UUID owner();

    String ownerName();

    void setTamed(boolean tamed);

    void setOwner(UUID owner);

    void setOwnerName(String playerName);
}
