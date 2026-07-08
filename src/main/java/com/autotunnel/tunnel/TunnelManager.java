package com.autotunnel.tunnel;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;

import java.util.*;

public class TunnelManager {
    private PlayerEntity player;
    private BlockPos currentPos;
    private Direction currentDirection = Direction.NORTH;
    private int tickCounter = 0;
    private static final int TICKS_PER_ACTION = 10;
    private Queue<BlockPos> targetBlocks = new LinkedList<>();
    private boolean isActive = false;
    private Random random = new Random();

    private static final Set<Block> IGNORED_BLOCKS = Set.of(
        Blocks.AIR, Blocks.CAVE_AIR, Blocks.VOID_AIR,
        Blocks.WATER, Blocks.LAVA, Blocks.BEDROCK
    );

    public void startMining(PlayerEntity player) {
        this.player = player;
        this.currentPos = player.getBlockPos();
        this.isActive = true;
        this.targetBlocks.clear();
        generateTunnelPath();
    }

    public void stopMining() {
        this.isActive = false;
        this.targetBlocks.clear();
    }

    public void tick(PlayerEntity player) {
        if (!isActive || player == null) return;

        tickCounter++;
        if (tickCounter >= TICKS_PER_ACTION) {
            tickCounter = 0;
            processMining(player);
        }
    }

    private void generateTunnelPath() {
        // Generate 3x3 tunnel ahead
        for (int i = 1; i <= 20; i++) {
            BlockPos offset = currentPos.offset(currentDirection, i);
            for (int y = 0; y < 3; y++) {
                for (int x = -1; x <= 1; x++) {
                    BlockPos blockPos = offset.add(x, y, 0);
                    if (shouldMine(blockPos)) {
                        targetBlocks.add(blockPos);
                    }
                }
            }
        }
    }

    private void processMining(PlayerEntity player) {
        if (targetBlocks.isEmpty()) {
            // Move forward and regenerate path
            if (random.nextDouble() < 0.1) {
                // 10% chance to change direction
                Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
                currentDirection = directions[random.nextInt(directions.length)];
            }
            generateTunnelPath();
            return;
        }

        BlockPos target = targetBlocks.peek();
        breakBlock(player, target);
        targetBlocks.poll();
    }

    private boolean shouldMine(BlockPos pos) {
        Block block = player.getWorld().getBlockState(pos).getBlock();
        return !IGNORED_BLOCKS.contains(block);
    }

    private void breakBlock(PlayerEntity player, BlockPos pos) {
        if (player.getWorld().getBlockState(pos).getMaterial().isReplaceable()) {
            return;
        }
        // Attack block
        player.getWorld().breakBlock(pos, true);
    }
}
