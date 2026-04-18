package com.example.texturepaint;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final RegistryKey<ItemGroup> MAIN_KEY = RegistryKey.of(
        RegistryKeys.ITEM_GROUP,
        Identifier.of(TexturePainterMod.MOD_ID, "main")
    );

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, MAIN_KEY, FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.NPC_SPAWNER))
            .displayName(Text.translatable("itemGroup.texturepaint.main"))
            .entries((ctx, entries) -> {
                entries.add(ModBlocks.TEXTURED_BLOCK);
                entries.add(ModItems.NPC_SPAWNER);
            })
            .build());
    }
}