package com.github.qinggua114.tamablefairy.data.fairy_enchantments;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.EnumMap;
import java.util.Map;

public class FairyEnchs implements IFairyEnchs, INBTSerializable<CompoundTag> {

    private Map<FairyEnchsEnum, Integer> fairyEnchsMap = new EnumMap<>(FairyEnchsEnum.class);

    @Override
    public Map<FairyEnchsEnum, Integer> enchantmentsMap() {
        return fairyEnchsMap;
    }

    @Override
    public void addEnchantment(FairyEnchsEnum enchantment, int level) {
        fairyEnchsMap.put(enchantment, level);
    }

    @Override
    public void removeEnchantment(FairyEnchsEnum enchantment) {
        fairyEnchsMap.remove(enchantment);
    }

    @Override
    public void upgradeEnchantment(FairyEnchsEnum enchantment, int addedLevel) {
        fairyEnchsMap.computeIfPresent(enchantment, (key, originalValue) -> originalValue + addedLevel);
    }

    @Override
    public void modifyEnchantment(FairyEnchsEnum enchantment, int level) {
        fairyEnchsMap.put(enchantment, level);
    }

    @Override
    public void clearEnchantments() {
        fairyEnchsMap.clear();
    }

    @Override
    public void setEnchantmentsMap(Map<FairyEnchsEnum, Integer> newEnchantmentsMap) {
        fairyEnchsMap = newEnchantmentsMap;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        CompoundTag innerTag = new CompoundTag();
        fairyEnchsMap.forEach((enchantment, level) -> innerTag.putInt(enchantment.name(), level));
        tag.put("fairy_enchantments", innerTag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        fairyEnchsMap.clear();
        if (compoundTag.contains("fairy_enchantments", net.minecraft.nbt.Tag.TAG_COMPOUND)) {
            CompoundTag innerTag = compoundTag.getCompound("fairy_enchantments");
            for (FairyEnchsEnum enchantment : FairyEnchsEnum.values()) {
                if (innerTag.contains(enchantment.name())) {
                    fairyEnchsMap.put(enchantment, innerTag.getInt(enchantment.name()));
                }
            }
        }

    }
}
