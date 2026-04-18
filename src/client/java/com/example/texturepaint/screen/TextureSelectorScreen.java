package com.example.texturepaint.screen;

import com.example.texturepaint.network.UpdateTexturePayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class TextureSelectorScreen extends Screen {

    private static final int COLS = 9;
    private static final int ROWS = 5;
    private static final int CELL = 18;
    private static final int PADDING = 8;

    private final BlockPos targetPos;
    private final List<Block> blocks = new ArrayList<>();
    private int page = 0;
    private int selectedIndex = -1;
    private int gridOriginX;
    private int gridOriginY;

    public TextureSelectorScreen(BlockPos targetPos) {
        super(Text.translatable("screen.texturepaint.texture_selector"));
        this.targetPos = targetPos;
    }

    @Override
    protected void init() {
        blocks.clear();
        for (Block block : Registries.BLOCK) {
            if (block == Blocks.AIR || block == Blocks.CAVE_AIR || block == Blocks.VOID_AIR) continue;
            if (block.asItem() == net.minecraft.item.Items.AIR) continue;
            blocks.add(block);
        }

        int gridW = COLS * CELL;
        int gridH = ROWS * CELL;
        gridOriginX = width / 2 - gridW / 2;
        gridOriginY = height / 2 - gridH / 2;

        int totalPages = Math.max(1, (blocks.size() + COLS * ROWS - 1) / (COLS * ROWS));

        addDrawableChild(ButtonWidget.builder(Text.literal("<"),
            btn -> { page = Math.max(0, page - 1); })
            .dimensions(gridOriginX - 24, gridOriginY + gridH / 2 - 10, 20, 20)
            .build());

        addDrawableChild(ButtonWidget.builder(Text.literal(">"),
            btn -> { page = Math.min(totalPages - 1, page + 1); })
            .dimensions(gridOriginX + gridW + 4, gridOriginY + gridH / 2 - 10, 20, 20)
            .build());

        addDrawableChild(ButtonWidget.builder(
            Text.translatable("screen.texturepaint.apply"),
            btn -> applyTexture()
        ).dimensions(width / 2 - 40, gridOriginY + gridH + 6, 80, 20).build());
    }

    private void applyTexture() {
        if (selectedIndex < 0 || selectedIndex >= blocks.size()) return;
        Block selected = blocks.get(selectedIndex);
        Identifier id = Registries.BLOCK.getId(selected);
        ClientPlayNetworking.send(new UpdateTexturePayload(targetPos, id.toString()));
        this.close();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);

        context.fill(gridOriginX - PADDING, gridOriginY - PADDING,
            gridOriginX + COLS * CELL + PADDING, gridOriginY + ROWS * CELL + PADDING,
            0xCC1A1A1A);
        context.drawBorder(gridOriginX - PADDING, gridOriginY - PADDING,
            COLS * CELL + PADDING * 2, ROWS * CELL + PADDING * 2, 0xFF555555);

        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, gridOriginY - PADDING - 14, 0xFFFFFF);

        int startIndex = page * COLS * ROWS;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int idx = startIndex + row * COLS + col;
                if (idx >= blocks.size()) break;
                int cx = gridOriginX + col * CELL;
                int cy = gridOriginY + row * CELL;

                if (idx == selectedIndex) {
                    context.fill(cx, cy, cx + CELL, cy + CELL, 0x80FFFFFF);
                } else if (mouseX >= cx && mouseX < cx + CELL && mouseY >= cy && mouseY < cy + CELL) {
                    context.fill(cx, cy, cx + CELL, cy + CELL, 0x40FFFFFF);
                }

                ItemStack stack = new ItemStack(blocks.get(idx).asItem());
                context.drawItem(stack, cx + 1, cy + 1);
            }
        }

        if (selectedIndex >= 0 && selectedIndex < blocks.size()) {
            Identifier id = Registries.BLOCK.getId(blocks.get(selectedIndex));
            context.drawCenteredTextWithShadow(textRenderer, Text.literal(id.toString()),
                width / 2, gridOriginY + ROWS * CELL + PADDING + 32, 0xAAAAAA);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int col = ((int) mouseX - gridOriginX) / CELL;
            int row = ((int) mouseY - gridOriginY) / CELL;
            if (col >= 0 && col < COLS && row >= 0 && row < ROWS) {
                int idx = page * COLS * ROWS + row * COLS + col;
                if (idx >= 0 && idx < blocks.size()) {
                    selectedIndex = idx;
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}