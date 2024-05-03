package net.luluborealis.luluocean.client;

import net.minecraft.client.Minecraft;

public class KillClient {

    public static void kill() {
        Minecraft.getInstance().stop();
    }
}