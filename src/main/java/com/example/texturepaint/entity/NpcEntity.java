package com.example.texturepaint.entity;

import com.example.texturepaint.ModEntities;
import com.example.texturepaint.network.OpenNpcScreenPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class NpcEntity extends MobEntity {

    private static final TrackedData<Float> LEFT_ARM_PITCH =
        DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> RIGHT_ARM_PITCH =
        DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> CUSTOM_HEAD_PITCH =
        DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> CUSTOM_HEAD_YAW =
        DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.FLOAT);

    public NpcEntity(EntityType<? extends NpcEntity> type, World world) {
        super(type, world);
        this.setNoGravity(false);
        this.setAiDisabled(true);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(LEFT_ARM_PITCH, 0f);
        builder.add(RIGHT_ARM_PITCH, 0f);
        builder.add(CUSTOM_HEAD_PITCH, 0f);
        builder.add(CUSTOM_HEAD_YAW, 0f);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.MAX_HEALTH, 20.0)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.0);
    }

    public float getLeftArmPitch() {
        return dataTracker.get(LEFT_ARM_PITCH);
    }

    public void setLeftArmPitch(float value) {
        dataTracker.set(LEFT_ARM_PITCH, value);
    }

    public float getRightArmPitch() {
        return dataTracker.get(RIGHT_ARM_PITCH);
    }

    public void setRightArmPitch(float value) {
        dataTracker.set(RIGHT_ARM_PITCH, value);
    }

    public float getCustomHeadPitch() {
        return dataTracker.get(CUSTOM_HEAD_PITCH);
    }

    public void setCustomHeadPitch(float value) {
        dataTracker.set(CUSTOM_HEAD_PITCH, value);
    }

    public float getCustomHeadYaw() {
        return dataTracker.get(CUSTOM_HEAD_YAW);
    }

    public void setCustomHeadYaw(float value) {
        dataTracker.set(CUSTOM_HEAD_YAW, value);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (hand != Hand.MAIN_HAND) return ActionResult.PASS;
        if (!getWorld().isClient() && player instanceof ServerPlayerEntity sp) {
            ServerPlayNetworking.send(sp, new OpenNpcScreenPayload(
                getId(),
                getLeftArmPitch(),
                getRightArmPitch(),
                getCustomHeadPitch(),
                getCustomHeadYaw()
            ));
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        setLeftArmPitch(nbt.getFloat("left_arm_pitch"));
        setRightArmPitch(nbt.getFloat("right_arm_pitch"));
        setCustomHeadPitch(nbt.getFloat("custom_head_pitch"));
        setCustomHeadYaw(nbt.getFloat("custom_head_yaw"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putFloat("left_arm_pitch", getLeftArmPitch());
        nbt.putFloat("right_arm_pitch", getRightArmPitch());
        nbt.putFloat("custom_head_pitch", getCustomHeadPitch());
        nbt.putFloat("custom_head_yaw", getCustomHeadYaw());
    }
}