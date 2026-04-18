package com.example.texturepaint.render;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.Identifier;

public class NpcEntityModel extends BipedEntityModel<NpcRenderState> {

    public static final EntityModelLayer LAYER = new EntityModelLayer(
        Identifier.of("texturepaint", "npc"), "main"
    );

    public NpcEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        ModelPartData head = root.addChild(
            EntityModelPartNames.HEAD,
            ModelPartBuilder.create().uv(0, 0).cuboid(-4f, -8f, -4f, 8, 8, 8),
            ModelTransform.pivot(0f, 0f, 0f)
        );
        head.addChild(
            EntityModelPartNames.HAT,
            ModelPartBuilder.create().uv(32, 0).cuboid(-4f, -8f, -4f, 8, 8, 8, new net.minecraft.client.model.Dilation(0.5f)),
            ModelTransform.NONE
        );

        root.addChild(
            EntityModelPartNames.BODY,
            ModelPartBuilder.create().uv(16, 16).cuboid(-4f, 0f, -2f, 8, 12, 4),
            ModelTransform.pivot(0f, 0f, 0f)
        );

        root.addChild(
            EntityModelPartNames.RIGHT_ARM,
            ModelPartBuilder.create().uv(40, 16).cuboid(-3f, -2f, -2f, 4, 12, 4),
            ModelTransform.pivot(-5f, 2f, 0f)
        );

        root.addChild(
            EntityModelPartNames.LEFT_ARM,
            ModelPartBuilder.create().uv(32, 48).cuboid(-1f, -2f, -2f, 4, 12, 4),
            ModelTransform.pivot(5f, 2f, 0f)
        );

        root.addChild(
            EntityModelPartNames.RIGHT_LEG,
            ModelPartBuilder.create().uv(0, 16).cuboid(-2f, 0f, -2f, 4, 12, 4),
            ModelTransform.pivot(-1.9f, 12f, 0f)
        );

        root.addChild(
            EntityModelPartNames.LEFT_LEG,
            ModelPartBuilder.create().uv(16, 48).cuboid(-2f, 0f, -2f, 4, 12, 4),
            ModelTransform.pivot(1.9f, 12f, 0f)
        );

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(NpcRenderState state) {
        super.setAngles(state);
        this.leftArm.pitch = state.leftArmPitch;
        this.rightArm.pitch = state.rightArmPitch;
        this.head.pitch = state.customHeadPitch;
        this.head.yaw = state.customHeadYaw;
    }
}