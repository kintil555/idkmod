package com.example.texturepaint.item;

import com.example.texturepaint.ModEntities;
import com.example.texturepaint.entity.NpcEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class NpcSpawnerItem extends Item {

    public NpcSpawnerItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient()) return ActionResult.SUCCESS;
        ServerWorld world = (ServerWorld) context.getWorld();
        BlockPos clickedPos = context.getBlockPos();
        Direction side = context.getSide();
        BlockPos spawnPos = clickedPos.offset(side);
        BlockState stateAt = world.getBlockState(spawnPos);
        if (!stateAt.isAir() && !stateAt.isReplaceable()) return ActionResult.FAIL;

        NpcEntity npc = new NpcEntity(ModEntities.NPC, world);
        npc.refreshPositionAndAngles(
            spawnPos.getX() + 0.5,
            spawnPos.getY(),
            spawnPos.getZ() + 0.5,
            context.getPlayerYaw(),
            0f
        );
        world.spawnEntity(npc);

        PlayerEntity player = context.getPlayer();
        if (player != null && !player.isCreative()) {
            context.getStack().decrement(1);
        }
        return ActionResult.SUCCESS;
    }
}