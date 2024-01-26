package me._crystal.assassinhuntmod;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class Helper {
    private static final String _TAG_PLAYER_PREVIOUS_X = AssassinHuntMod.MOD_ID + ":PlayerPreviousX";
    private static final String _TAG_PLAYER_PREVIOUS_Y = AssassinHuntMod.MOD_ID + ":PlayerPreviousY";
    private static final String _TAG_PLAYER_PREVIOUS_Z = AssassinHuntMod.MOD_ID + ":PlayerPreviousZ";

    public static void setPreviousPos(Player player, double x, double y, double z) {
        player.getPersistentData().putDouble(_TAG_PLAYER_PREVIOUS_X, x);
        player.getPersistentData().putDouble(_TAG_PLAYER_PREVIOUS_Y, y);
        player.getPersistentData().putDouble(_TAG_PLAYER_PREVIOUS_Z, z);
    }

    @Nullable
    public static Vec3 getPreviousPos(Player player) {
        if (!player.getPersistentData().contains(_TAG_PLAYER_PREVIOUS_X)) return null;

        double x = player.getPersistentData().getDouble(_TAG_PLAYER_PREVIOUS_X);
        double y = player.getPersistentData().getDouble(_TAG_PLAYER_PREVIOUS_Y);
        double z = player.getPersistentData().getDouble(_TAG_PLAYER_PREVIOUS_Z);

        return new Vec3(x, y, z);
    }
}
