package com.example.texturepaint.network;

import com.example.texturepaint.TexturePainterMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record UpdateNpcPosePayload(int entityId, float leftArmPitch, float rightArmPitch, float headPitch, float headYaw)
    implements CustomPayload {

    public static final CustomPayload.Id<UpdateNpcPosePayload> ID =
        new CustomPayload.Id<>(Identifier.of(TexturePainterMod.MOD_ID, "update_npc_pose"));

    public static final PacketCodec<RegistryByteBuf, UpdateNpcPosePayload> CODEC =
        PacketCodec.tuple(
            PacketCodecs.INTEGER, UpdateNpcPosePayload::entityId,
            PacketCodecs.FLOAT, UpdateNpcPosePayload::leftArmPitch,
            PacketCodecs.FLOAT, UpdateNpcPosePayload::rightArmPitch,
            PacketCodecs.FLOAT, UpdateNpcPosePayload::headPitch,
            PacketCodecs.FLOAT, UpdateNpcPosePayload::headYaw,
            UpdateNpcPosePayload::new
        );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}