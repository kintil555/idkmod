package com.example.texturepaint;

import com.example.texturepaint.block.TexturedBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static final BlockEntityType<TexturedBlockEntity> TEXTURED_BLOCK_ENTITY;

    static {
        TEXTURED_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(TexturePainterMod.MOD_ID, "textured_block"),
            BlockEntityType.create(TexturedBlockEntity::new, ModBlocks.TEXTURED_BLOCK)
        );
    }

    public static void initialize() {}
}