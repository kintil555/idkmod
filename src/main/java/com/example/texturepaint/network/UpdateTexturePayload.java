package com.example.texturepaint.network;

import com.example.texturepaint.TexturePainterMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record UpdateTexturePayload(BlockPos blockPos, String blockId) implements CustomPayload {

    public static final CustomPayload.Id<UpdateTexturePayload> ID =
        new CustomPayload.Id<>(Identifier.of(TexturePainterMod.MOD_ID, "update_texture"));

    public static final PacketCodec<RegistryByteBuf, UpdateTexturePayload> CODEC =
        PacketCodec.tuple(
            BlockPos.PACKET_CODEC, UpdateTexturePayload::blockPos,
            PacketCodecs.STRING, UpdateTexturePayload::blockId,
            UpdateTexturePayload::new
        );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}