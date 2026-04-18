package com.example.texturepaint.render;

import com.example.texturepaint.entity.NpcEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class NpcEntityRenderer extends MobEntityRenderer<NpcEntity, NpcRenderState, NpcEntityModel> {

    private static final Identifier TEXTURE = Identifier.of("minecraft", "textures/entity/player/wide/steve.png");

    public NpcEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new NpcEntityModel(context.getPart(NpcEntityModel.LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(NpcRenderState state) {
        return TEXTURE;
    }

    @Override
    public NpcRenderState createRenderState() {
        return new NpcRenderState();
    }

    @Override
    public void updateRenderState(NpcEntity entity, NpcRenderState state, float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);
        state.leftArmPitch = entity.getLeftArmPitch();
        state.rightArmPitch = entity.getRightArmPitch();
        state.customHeadPitch = entity.getCustomHeadPitch();
        state.customHeadYaw = entity.getCustomHeadYaw();
    }
}