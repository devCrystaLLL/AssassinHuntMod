package me._crystal.assassinhuntmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import me._crystal.assassinhuntmod.AssassinHuntMod;
import me._crystal.assassinhuntmod.Game;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.Chicken;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

public class StartModCommand {
    public StartModCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("assassin-hunt")
                        .then(Commands.literal("start")
                        .then(Commands.argument("assassin", EntityArgument.player())
                        .then(Commands.argument("runner", EntityArgument.player())
                        .executes((ctx) -> startMod(ctx.getSource(),
                                EntityArgument.getPlayer(ctx, "assassin"),
                                EntityArgument.getPlayer(ctx, "runner")))))));

        dispatcher.register(
                Commands.literal("assassin-hunt")
                        .then(Commands.literal("test")
                                .executes(this::test)));
    }

    private int test(CommandContext<CommandSourceStack> ctx) {
        for (Game game : AssassinHuntMod.GameList) {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal(game.AssassinPlayer.getName().toString()));
            Minecraft.getInstance().player.sendSystemMessage(Component.literal(game.Started + ""));

            for (ServerPlayer player : game.ServerPlayerList) {
                Minecraft.getInstance().player.sendSystemMessage(Component.literal(player.getName().toString()));
            }
        }
        return 0;
    }

    private int startMod(CommandSourceStack src, ServerPlayer assassinPlayer, ServerPlayer runnerPlayer) {
        List<ServerPlayer> serverPlayerList = new ArrayList<>();
        serverPlayerList.add(assassinPlayer);
        serverPlayerList.add(runnerPlayer);

        AssassinHuntMod.GameList.add(new Game(true, assassinPlayer, runnerPlayer, serverPlayerList));
        return 1;
    }
}