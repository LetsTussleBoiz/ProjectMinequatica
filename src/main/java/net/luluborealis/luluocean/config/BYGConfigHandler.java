package net.luluborealis.luluocean.config;

import net.luluborealis.luluocean.BYGConstants;
import net.luluborealis.luluocean.util.ModPlatform;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerPlayer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class BYGConfigHandler {


    public static final Set<Exception> CONFIG_EXCEPTIONS = new HashSet<>();

    public static String loadAllConfigs(boolean serialize, boolean recreate) {
        return loadAllConfigs(serialize, recreate, ModPlatform.INSTANCE.isClientEnvironment());
    }

    public static String loadAllConfigs(boolean serialize, boolean recreate, boolean isClientSide) {
        StringBuilder errors = new StringBuilder();
        errors.append(tryCatchErrors(BYGConfigHandler::makeREADME));

        if (!errors.isEmpty()) {
            return String.format("Config errors were found:\n\n%s", errors);
        }

        return errors.toString();
    }

    private static String tryCatchErrors(Runnable run) {
        String error = "";

        try {
            run.run();
        } catch (Exception e) {
            error = e.getMessage();
        }
        return error + (!error.isEmpty() ? "\n" : "");
    }

    private static void makeREADME() {
        try {
            Path configPath = ModPlatform.INSTANCE.configPath();
            Files.createDirectories(configPath);
            Files.write(configPath.resolve("README.txt"), "For information on how BYG configs work, you can find that here: https://github.com/AOCAWOL/BYG/wiki/Configs".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayChatErrors(ServerPlayer serverPlayer) {
        serverPlayer.displayClientMessage(Component.translatable("byg.config.error").withStyle(ChatFormatting.RED), false);
        Path latestLogPath = ModPlatform.INSTANCE.configPath().getParent().getParent().resolve("logs").resolve("latest.log");
        serverPlayer.displayClientMessage(Component.translatable("byg.config.error.latestlog").withStyle(ChatFormatting.UNDERLINE).withStyle(text -> text.withColor(TextColor.fromLegacyFormat(ChatFormatting.DARK_RED)).withBold(true).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, latestLogPath.toString())).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("corgilib.clickevent.hovertext")))), false);
        serverPlayer.displayClientMessage(Component.literal(""), false);


        serverPlayer.displayClientMessage(Component.translatable("byg.config.error.commandvalidate").withStyle(ChatFormatting.RED), false);
        serverPlayer.displayClientMessage(Component.translatable("byg.config.error.resetconfigs").withStyle(ChatFormatting.RED), false);
        serverPlayer.displayClientMessage(Component.translatable("byg.config.error.restart").withStyle(ChatFormatting.RED), false);
    }
}
