package com.example.texturepaint.screen;

import com.example.texturepaint.network.UpdateNpcPosePayload;
import com.example.texturepaint.screen.widget.PoseSliderWidget;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class NpcPoseScreen extends Screen {

    private static final float ARM_MIN = (float) -Math.PI / 2f;
    private static final float ARM_MAX = (float) Math.PI / 2f;
    private static final float YAW_MIN = (float) -Math.PI;
    private static final float YAW_MAX = (float) Math.PI;

    private final int entityId;
    private float leftArmPitch;
    private float rightArmPitch;
    private float headPitch;
    private float headYaw;

    private PoseSliderWidget leftArmSlider;
    private PoseSliderWidget rightArmSlider;
    private PoseSliderWidget headPitchSlider;
    private PoseSliderWidget headYawSlider;

    public NpcPoseScreen(int entityId, float leftArmPitch, float rightArmPitch, float headPitch, float headYaw) {
        super(Text.translatable("screen.texturepaint.npc_pose"));
        this.entityId = entityId;
        this.leftArmPitch = leftArmPitch;
        this.rightArmPitch = rightArmPitch;
        this.headPitch = headPitch;
        this.headYaw = headYaw;
    }

    @Override
    protected void init() {
        int cx = width / 2;
        int sliderW = 200;
        int sliderH = 20;
        int startY = height / 2 - 60;
        int gap = 26;

        leftArmSlider = new PoseSliderWidget(cx - sliderW / 2, startY, sliderW, sliderH,
            Text.translatable("screen.texturepaint.left_arm").getString(),
            ARM_MIN, ARM_MAX, leftArmPitch, v -> leftArmPitch = v);

        rightArmSlider = new PoseSliderWidget(cx - sliderW / 2, startY + gap, sliderW, sliderH,
            Text.translatable("screen.texturepaint.right_arm").getString(),
            ARM_MIN, ARM_MAX, rightArmPitch, v -> rightArmPitch = v);

        headPitchSlider = new PoseSliderWidget(cx - sliderW / 2, startY + gap * 2, sliderW, sliderH,
            Text.translatable("screen.texturepaint.head_pitch").getString(),
            ARM_MIN, ARM_MAX, headPitch, v -> headPitch = v);

        headYawSlider = new PoseSliderWidget(cx - sliderW / 2, startY + gap * 3, sliderW, sliderH,
            Text.translatable("screen.texturepaint.head_yaw").getString(),
            YAW_MIN, YAW_MAX, headYaw, v -> headYaw = v);

        addDrawableChild(leftArmSlider);
        addDrawableChild(rightArmSlider);
        addDrawableChild(headPitchSlider);
        addDrawableChild(headYawSlider);

        addDrawableChild(ButtonWidget.builder(
            Text.translatable("screen.texturepaint.done"),
            btn -> onDone()
        ).dimensions(cx - 50, startY + gap * 4 + 4, 100, 20).build());
    }

    private void onDone() {
        ClientPlayNetworking.send(new UpdateNpcPosePayload(entityId, leftArmPitch, rightArmPitch, headPitch, headYaw));
        this.close();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, height / 2 - 80, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}