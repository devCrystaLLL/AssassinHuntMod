package me._crystal.assassinhuntmod.events;

import me._crystal.assassinhuntmod.AssassinHuntMod;
import me._crystal.assassinhuntmod.Game;
import me._crystal.assassinhuntmod.Helper;
import me._crystal.assassinhuntmod.commands.StartModCommand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.Vector;

@Mod.EventBusSubscriber(modid = AssassinHuntMod.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new StartModCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side != LogicalSide.SERVER) return;
        if (event.phase == TickEvent.Phase.END) return;

        Player player = event.player;
        for (Game game : AssassinHuntMod.GameList) {
            if (game.AssassinPlayer.getUUID() == player.getUUID() && game.isRunnerLookingAtAssassin()) {
                // if this is the assassin, and the runner is looking at him

                // get the previous assassin position
                Vec3 previousPosVec = Helper.getPreviousPos(player);
                if (previousPosVec != null) {
                    // if it's not null
                    // then teleport him to it
                    player.teleportTo(previousPosVec.x, previousPosVec.y, previousPosVec.z);
                }
            }
            // set the player previous pos to his current pos
            Helper.setPreviousPos(player, player.getX(), player.getY(), player.getZ());
        }
    }
}