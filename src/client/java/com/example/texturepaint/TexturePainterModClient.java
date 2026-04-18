package com.example.texturepaint;

import com.example.texturepaint.network.OpenNpcScreenPayload;
import com.example.texturepaint.network.UpdateNpcPosePayload;
import com.example.texturepaint.network.UpdateTexturePayload;
import com.example.texturepaint.render.NpcEntityModel;
import com.example.texturepaint.render.NpcEntityRenderer;
import com.example.texturepaint.render.TexturedBlockEntityRenderer;
import com.example.texturepaint.screen.NpcPoseScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class TexturePainterModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(
            ModBlockEntities.TEXTURED_BLOCK_ENTITY,
            TexturedBlockEntityRenderer::new
        );

        EntityModelLayerRegistry.registerModelLayer(NpcEntityModel.LAYER, NpcEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.NPC, NpcEntityRenderer::new);

        ClientPlayNetworking.registerGlobalReceiver(OpenNpcScreenPayload.ID, (payload, context) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            client.execute(() -> client.setScreen(new NpcPoseScreen(
                payload.entityId(),
                payload.leftArmPitch(),
                payload.rightArmPitch(),
                payload.headPitch(),
                payload.headYaw()
            )));
        });
    }
}