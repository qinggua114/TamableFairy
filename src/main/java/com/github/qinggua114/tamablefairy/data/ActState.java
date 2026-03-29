package com.github.qinggua114.tamablefairy.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;

public record ActState(
        boolean attackEnabled,
        boolean followOwnerEnabled,
        boolean moveAroundEnabled,
        int followDistance,
        int moveRange,
        Vec3 actRangeCenter
) {

    public static final Codec<Vec3> VEC3_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.DOUBLE.fieldOf("x").forGetter(Vec3::x),
                    Codec.DOUBLE.fieldOf("y").forGetter(Vec3::y),
                    Codec.DOUBLE.fieldOf("z").forGetter(Vec3::z)
            ).apply(instance, Vec3::new)
    );

    public static final Codec<ActState> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("attack_enabled").forGetter(ActState::attackEnabled),
                    Codec.BOOL.fieldOf("follow_owner_enabled").forGetter(ActState::followOwnerEnabled),
                    Codec.BOOL.fieldOf("move_around_enabled").forGetter(ActState::moveAroundEnabled),
                    Codec.INT.fieldOf("follow_distance").forGetter(ActState::followDistance),
                    Codec.INT.fieldOf("move_range").forGetter(ActState::moveRange),
                    VEC3_CODEC.fieldOf("act_range_center").forGetter(ActState::actRangeCenter)

            ).apply(instance, ActState::new)
    );


    public static final StreamCodec<FriendlyByteBuf, ActState> STREAM_CODEC =
            StreamCodec.composite(
                    StreamCodec.of(FriendlyByteBuf::writeBoolean, FriendlyByteBuf::readBoolean),
                    ActState::attackEnabled,
                    StreamCodec.of(FriendlyByteBuf::writeBoolean, FriendlyByteBuf::readBoolean),
                    ActState::followOwnerEnabled,
                    StreamCodec.of(FriendlyByteBuf::writeBoolean, FriendlyByteBuf::readBoolean),
                    ActState::moveAroundEnabled,
                    StreamCodec.of(FriendlyByteBuf::writeInt, FriendlyByteBuf::readInt),
                    ActState::followDistance,
                    StreamCodec.of(FriendlyByteBuf::writeInt, FriendlyByteBuf::readInt),
                    ActState::moveRange,
                    StreamCodec.of(FriendlyByteBuf::writeVec3, FriendlyByteBuf::readVec3),
                    ActState::actRangeCenter,
                    ActState::new
            );

    public static final ActState DEFAULT = new ActState(true, true, true, 7, 5, Vec3.ZERO);//创建默认数据
}
