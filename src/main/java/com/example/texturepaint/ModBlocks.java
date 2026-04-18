package com.example.texturepaint;

import com.example.texturepaint.block.TexturedBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block TEXTURED_BLOCK;

    static {
        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(TexturePainterMod.MOD_ID, "textured_block"));
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TexturePainterMod.MOD_ID, "textured_block"));

        TEXTURED_BLOCK = Registry.register(
            Registries.BLOCK,
            blockKey,
            new TexturedBlock(AbstractBlock.Settings.create()
                .strength(1.5f, 6.0f)
                .sounds(BlockSoundGroup.STONE)
                .nonOpaque()
                .registryKey(blockKey))
        );

        Registry.register(
            Registries.ITEM,
            itemKey,
            new BlockItem(TEXTURED_BLOCK, new Item.Settings()
                .registryKey(itemKey)
                .useBlockPrefixedTranslationKey())
        );
    }

    public static void initialize() {}
}