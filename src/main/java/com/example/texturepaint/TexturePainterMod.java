package com.example.texturepaint;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import com.example.texturepaint.network.UpdateNpcPosePayload;
import com.example.texturepaint.network.UpdateTexturePayload;
import com.example.texturepaint.network.OpenNpcScreenPayload;
import com.example.texturepaint.entity.NpcEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TexturePainterMod implements ModInitializer {

    public static final String MOD_ID = "texturepaint";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModBlocks.initialize();
        ModItems.initialize();
        ModBlockEntities.initialize();
        ModEntities.initialize();
        ModItemGroups.initialize();

        PayloadTypeRegistry.playC2S().register(UpdateTexturePayload.ID, UpdateTexturePayload.CODEC);
        PayloadTypeRegistry.playC2S().register(UpdateNpcPosePayload.ID, UpdateNpcPosePayload.CODEC);
        PayloadTypeRegistry.playS2C().register(OpenNpcScreenPayload.ID, OpenNpcScreenPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(UpdateTexturePayload.ID, (payload, context) -> {
            ServerPlayerEntity player = context.player();
            ServerWorld world = player.getServerWorld();
            BlockPos pos = payload.blockPos();
            if (!world.isInBuildLimit(pos)) return;
            world.setBlockState(pos, ModBlocks.TEXTURED_BLOCK.getDefaultState());
            if (world.getBlockEntity(pos) instanceof com.example.texturepaint.block.TexturedBlockEntity be) {
                be.setSourceBlockId(payload.blockId());
            }
        });

        ServerPlayNetworking.registerGlobalReceiver(UpdateNpcPosePayload.ID, (payload, context) -> {
            ServerPlayerEntity player = context.player();
            ServerWorld world = player.getServerWorld();
            Entity entity = world.getEntityById(payload.entityId());
            if (entity instanceof NpcEntity npc) {
                npc.setLeftArmPitch(payload.leftArmPitch());
                npc.setRightArmPitch(payload.rightArmPitch());
                npc.setCustomHeadPitch(payload.headPitch());
                npc.setCustomHeadYaw(payload.headYaw());
            }
        });
    }
}