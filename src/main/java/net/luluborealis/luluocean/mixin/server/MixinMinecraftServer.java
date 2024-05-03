package net.luluborealis.luluocean.mixin.server;

import net.luluborealis.luluocean.BYGConstants;
import net.luluborealis.luluocean.LuluOcean;
import net.luluborealis.luluocean.config.ConfigVersionTracker;
import net.luluborealis.luluocean.server.command.UpdateConfigsCommand;
import net.luluborealis.luluocean.util.ServerKillCountDown;
import net.minecraft.ChatFormatting;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.function.BooleanSupplier;

@Mixin(value = MinecraftServer.class, remap = false)
public abstract class MixinMinecraftServer implements ServerKillCountDown {
    @Shadow
    public abstract PlayerList getPlayerList();

    @Shadow public abstract RegistryAccess.Frozen registryAccess();

    private long byg$killTime = -1;
    private boolean byg$killClient = false;

    private int byg$notifyErrorFrequency = 0;

    @Inject(method = "tickServer", at = @At("RETURN"))
    private void displayDisconnectWarning(BooleanSupplier $$0, CallbackInfo ci) {
        if (byg$killTime > 0) {
            if (byg$killTime % 100 == 0) {
                for (ServerPlayer player : getPlayerList().getPlayers()) {
                    long killTimeInSeconds = byg$killTime / 20;
                    player.displayClientMessage(
                            Component.translatable("byg.serverkill.countdown", killTimeInSeconds).
                                    withStyle(
                                            byg$killTime < 300 ? ChatFormatting.RED : ChatFormatting.YELLOW), false);
                }
            }
            byg$killTime--;

            if (byg$killTime == 0) {
                UpdateConfigsCommand.backupAndKillGameInstance((MinecraftServer) (Object) this,
                        new ConfigVersionTracker(BYGConstants.CONFIG_VERSION), this.byg$killClient);
            }
        }

        if (byg$notifyErrorFrequency >= 36000) {
            LuluOcean.logConfigErrors();
            byg$notifyErrorFrequency = 0;
        }
        byg$notifyErrorFrequency++;
    }

    @Override
    public void setKillCountdown(long killCountdownInTicks, boolean isClient) {
        this.byg$killTime = killCountdownInTicks;
        this.byg$killClient = isClient;
    }
}
