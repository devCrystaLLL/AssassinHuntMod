package me._crystal.assassinhuntmod;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;

public class Game {
    public boolean Started;

    public Player AssassinPlayer;
    public Player RunnerPlayer;
    public List<ServerPlayer> ServerPlayerList;

    public Game(boolean started, Player assassinPlayer, Player runnerPlayer, List<ServerPlayer> serverPlayerList) {
        Started = started;
        AssassinPlayer = assassinPlayer;
        RunnerPlayer = runnerPlayer;
        ServerPlayerList = serverPlayerList;
    }

    public boolean isRunnerLookingAtAssassin() {
        return isPlayerLookingAtPlayer(RunnerPlayer, AssassinPlayer);
    }

    private boolean isPlayerLookingAtPlayer(Player player1, Player player2) {
        HitResult hitResult = player1.pick(10, 0, false);
        BlockHitResult blockHitResult = (BlockHitResult) hitResult;

        if (hitResult.getType() == HitResult.Type.ENTITY) {
            player1.sendSystemMessage(Component.literal(blockHitResult.getBlockPos() + ""));

            return blockHitResult.getBlockPos() == player2.getOnPos();
        }

        return false;

/*        Vec3 playerViewVec = player1.getViewVector(1.0F).normalize();
        Vec3 vec1 = new Vec3(player2.getX() - player1.getX(), player2.getEyeY() - player1.getEyeY(), player2.getZ() - player1.getZ());
        double vec1Length = vec1.length();
        vec1 = vec1.normalize();

        Vec3 vec2 = new Vec3(player2.getX() - player1.getX(), player2.getOnPos().getY() - player1.getOnPos().getY(), player2.getZ() - player1.getZ());
        double vec2Length = vec2.length();
        vec2 = vec1.normalize();

        double d1 = playerViewVec.dot(vec1);
        double d2 = playerViewVec.dot(vec2);

        return (d1 > 1.0D - 0.025D / vec1Length || d2 > 1.0D - 0.025D / vec2Length) && player1.hasLineOfSight(player2);*/
    }

/*    private boolean isPlayerLookingAtPlayer(Player player1, Player player2) {
        Vec3 playerViewVec = player1.getViewVector(1.0F).normalize();
        Vec3 vec = new Vec3(player2.getX() - player1.getX(), player2.getEyeY() - player1.getEyeY(), player2.getZ() - player1.getZ());
        double vecLength = vec.length();
        vec = vec.normalize();

        double d1 = playerViewVec.dot(vec);
        return d1 > 1.0D - 0.025D / vecLength && player1.hasLineOfSight(player2);
    }*/

/*    private boolean isEntityLookingAtEntity(Player player1, Player player2) {
        Vec3 playerViewVec = player1.getViewVector(1.0F).normalize();
        Vec3 vec = new Vec3(player2.getX() - player1.getX(), player2.getEyeY() - player1.getEyeY(), player2.getZ() - player1.getZ());

        EnderMan

        // Use a cone instead of a single vector
        double coneAngle = Math.toRadians(15); // Adjust the cone angle as needed
        double cosConeAngle = Math.acos(coneAngle);

        double d1 = playerViewVec.dot(vec.normalize());

        return d1 > cosConeAngle && AtoElineOfSight(player1, player2);
    }*/

    private boolean AtoElineOfSight(Player player1, Player player2) {
        if (player2.level != player1.level) {
            return false;
        } else {
            Vec3 player1LocVec = new Vec3(player1.getX(), player1.getEyeY(), player1.getZ());
            Vec3 player2LocVec = new Vec3(player2.getX(), player2.getEyeY(), player2.getZ());
            if (player2LocVec.distanceTo(player1LocVec) > 128.0D) {
                return false;
            } else {
                return player1.level.clip(new ClipContext(player1LocVec, player2LocVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player1)).getType() == HitResult.Type.MISS;
            }
        }
    }
}