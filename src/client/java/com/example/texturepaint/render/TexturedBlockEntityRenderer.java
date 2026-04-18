package com.example.texturepaint.render;

import com.example.texturepaint.block.TexturedBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

public class TexturedBlockEntityRenderer implements BlockEntityRenderer<TexturedBlockEntity> {

    private final Random random = Random.create();

    public TexturedBlockEntityRenderer(BlockEntityRendererFactory.Context context) {}

    @Override
    public void render(TexturedBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (entity.getWorld() == null) return;

        String blockId = entity.getSourceBlockId();
        Identifier id = Identifier.tryParse(blockId);
        if (id == null) return;

        BlockState sourceState = Registries.BLOCK.getOptionalValue(id)
            .orElse(Blocks.STONE)
            .getDefaultState();

        MinecraftClient client = MinecraftClient.getInstance();

        matrices.push();
        client.getBlockRenderManager().renderBlock(
            sourceState,
            entity.getPos(),
            entity.getWorld(),
            matrices,
            vertexConsumers.getBuffer(RenderLayer.getCutoutMipped()),
            false,
            random
        );
        matrices.pop();
    }
}