package com.example.texturepaint;

import com.example.texturepaint.item.NpcSpawnerItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item NPC_SPAWNER;

    static {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TexturePainterMod.MOD_ID, "npc_spawner"));
        NPC_SPAWNER = Registry.register(
            Registries.ITEM,
            key,
            new NpcSpawnerItem(new Item.Settings().registryKey(key).maxCount(1))
        );
    }

    public static void initialize() {}
}