package com.example.texturepaint.network;

import com.example.texturepaint.TexturePainterMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record OpenNpcScreenPayload(int entityId, float leftArmPitch, float rightArmPitch, float headPitch, float headYaw)
    implements CustomPayload {

    public static final CustomPayload.Id<OpenNpcScreenPayload> ID =
        new CustomPayload.Id<>(Identifier.of(TexturePainterMod.MOD_ID, "open_npc_screen"));

    public static final PacketCodec<RegistryByteBuf, OpenNpcScreenPayload> CODEC =
        PacketCodec.tuple(
            PacketCodecs.INTEGER, OpenNpcScreenPayload::entityId,
            PacketCodecs.FLOAT, OpenNpcScreenPayload::leftArmPitch,
            PacketCodecs.FLOAT, OpenNpcScreenPayload::rightArmPitch,
            PacketCodecs.FLOAT, OpenNpcScreenPayload::headPitch,
            PacketCodecs.FLOAT, OpenNpcScreenPayload::headYaw,
            OpenNpcScreenPayload::new
        );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}