package com.example.texturepaint.block;

import com.example.texturepaint.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class TexturedBlockEntity extends BlockEntity {

    private String sourceBlockId = "minecraft:stone";

    public TexturedBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TEXTURED_BLOCK_ENTITY, pos, state);
    }

    public String getSourceBlockId() {
        return sourceBlockId;
    }

    public void setSourceBlockId(String id) {
        this.sourceBlockId = id;
        markDirty();
        if (world != null && !world.isClient()) {
            world.updateListeners(getPos(), getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        if (nbt.contains("source_block")) {
            sourceBlockId = nbt.getString("source_block");
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        nbt.putString("source_block", sourceBlockId);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }
}