package com.example.texturepaint.screen.widget;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class PoseSliderWidget extends ClickableWidget {

    private float value;
    private final float min;
    private final float max;
    private final String label;
    private final Consumer<Float> onChange;
    private boolean sliding = false;

    public PoseSliderWidget(int x, int y, int width, int height, String label,
                             float min, float max, float initial, Consumer<Float> onChange) {
        super(x, y, width, height, Text.empty());
        this.label = label;
        this.min = min;
        this.max = max;
        this.value = clamp(initial);
        this.onChange = onChange;
    }

    private float clamp(float v) {
        return Math.max(min, Math.min(max, v));
    }

    public float getValue() {
        return value;
    }

    public void setValue(float v) {
        this.value = clamp(v);
    }

    private void updateFromMouse(double mouseX) {
        float ratio = (float) ((mouseX - getX() - 4) / (getWidth() - 8));
        ratio = Math.max(0f, Math.min(1f, ratio));
        value = min + ratio * (max - min);
        onChange.accept(value);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int x = getX();
        int y = getY();
        int w = getWidth();
        int h = getHeight();

        context.fill(x, y, x + w, y + h, 0xFF333333);
        context.drawBorder(x, y, w, h, 0xFFAAAAAA);

        float ratio = (value - min) / (max - min);
        int handleX = x + 4 + (int) (ratio * (w - 8));
        context.fill(handleX - 3, y + 2, handleX + 3, y + h - 2, 0xFFFFFFFF);

        int degrees = (int) Math.round(Math.toDegrees(value));
        String display = label + ": " + degrees + "\u00b0";
        context.drawCenteredTextWithShadow(
            net.minecraft.client.MinecraftClient.getInstance().textRenderer,
            display, x + w / 2, y + (h - 8) / 2, 0xFFFFFF
        );
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && isHovered()) {
            sliding = true;
            updateFromMouse(mouseX);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (sliding && button == 0) {
            updateFromMouse(mouseX);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        sliding = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void appendClickableNarrations(NarrationMessageBuilder builder) {
        appendDefaultNarrations(builder);
    }
}