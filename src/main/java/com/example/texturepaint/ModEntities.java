package com.example.texturepaint;

import com.example.texturepaint.entity.NpcEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<NpcEntity> NPC;

    static {
        RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(TexturePainterMod.MOD_ID, "npc"));
        NPC = Registry.register(
            Registries.ENTITY_TYPE,
            key,
            EntityType.Builder.create(NpcEntity::new, SpawnGroup.MISC)
                .dimensions(0.6f, 1.8f)
                .maxTrackingRange(10)
                .build(key)
        );
    }

    public static void initialize() {
        FabricDefaultAttributeRegistry.register(NPC, NpcEntity.createAttributes());
    }
}