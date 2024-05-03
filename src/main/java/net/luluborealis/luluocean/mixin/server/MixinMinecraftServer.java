package net.luluborealis.luluocean.mixin.server;


import com.mojang.datafixers.DataFixer;
import net.luluborealis.luluocean.BYGConstants;
import net.luluborealis.luluocean.LuluOcean;
import net.luluborealis.luluocean.common.world.feature.GlobalBiomeFeature;
import net.luluborealis.luluocean.common.world.util.BiomeSourceRepairUtils;
import net.luluborealis.luluocean.common.world.util.JigsawUtil;
import net.luluborealis.luluocean.config.ConfigVersionTracker;
import net.luluborealis.luluocean.config.SettingsConfig;
import net.luluborealis.luluocean.server.command.UpdateConfigsCommand;
import net.luluborealis.luluocean.util.ModPlatform;
import net.luluborealis.luluocean.util.ServerKillCountDown;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.Proxy;
import java.util.Map;
import java.util.function.BooleanSupplier;

@Mixin(value = MinecraftServer.class, remap = false)
public abstract class MixinMinecraftServer implements ServerKillCountDown {


    @Shadow
    public abstract WorldData getWorldData();

    @Shadow
    public abstract PlayerList getPlayerList();

    @Shadow public abstract RegistryAccess.Frozen registryAccess();

    private long byg$killTime = -1;
    private boolean byg$killClient = false;

    private int byg$notifyErrorFrequency = 0;


    @Inject(at = @At("RETURN"), method = "<init>")
    private void appendGlobalFeatures(Thread $$0, LevelStorageSource.LevelStorageAccess $$1, PackRepository $$2, WorldStem $$3, Proxy $$4, DataFixer $$5, Services $$6, ChunkProgressListenerFactory $$7, CallbackInfo ci) {
        Registry<Biome> biomeRegistry = this.registryAccess().registryOrThrow(Registries.BIOME);
        if (SettingsConfig.getConfig().appendBiomePlacedFeatures()) {
            Registry<PlacedFeature> placedFeatureRegistry = this.registryAccess().registryOrThrow(Registries.PLACED_FEATURE);
            for (Map.Entry<ResourceKey<Biome>, Biome> biomeEntry : biomeRegistry.entrySet()) {
                GlobalBiomeFeature.appendGlobalFeatures(biomeEntry.getValue().getGenerationSettings(), placedFeatureRegistry);
            }
        }
        if (SettingsConfig.getConfig().useBYGWorldGen()) {
            BiomeSourceRepairUtils.repairBiomeSources(biomeRegistry, (MinecraftServer)(Object) this);
        }

        if (SettingsConfig.getConfig().customVillagers()) {
            Registry<StructureTemplatePool> templatePoolRegistry = this.registryAccess().registry(Registries.TEMPLATE_POOL).orElseThrow();
            Registry<StructureProcessorList> processorListRegistry = this.registryAccess().registry(Registries.PROCESSOR_LIST).orElseThrow();
            JigsawUtil.addBYGBuildingsToPool(templatePoolRegistry, processorListRegistry);
        }

        LuluOcean.logConfigErrors();
    }

    @Inject(method = "tickServer", at = @At("RETURN"))
    private void displayDisconnectWarning(BooleanSupplier $$0, CallbackInfo ci) {
        if (byg$killTime > 0) {
            if (byg$killTime % 100 == 0) {
                for (ServerPlayer player : getPlayerList().getPlayers()) {
                    long killTimeInSeconds = byg$killTime / 20;
                    player.displayClientMessage(Component.translatable("byg.serverkill.countdown", killTimeInSeconds).withStyle(byg$killTime < 300 ? ChatFormatting.RED : ChatFormatting.YELLOW), false);
                }
            }
            byg$killTime--;

            if (byg$killTime == 0) {
                UpdateConfigsCommand.backupAndKillGameInstance((MinecraftServer) (Object) this, new ConfigVersionTracker(BYGConstants.CONFIG_VERSION), this.byg$killClient);
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
