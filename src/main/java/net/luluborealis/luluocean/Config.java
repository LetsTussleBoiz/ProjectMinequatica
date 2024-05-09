package net.luluborealis.luluocean;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = LuluOcean.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public record Config(boolean appendBiomePlacedFeatures, boolean customStructures, LoggerSettings loggerSettings)
{
    private static final Config DEFAULT = new Config(true, true, new LoggerSettings(false, true, false));

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    static final ForgeConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {}

    public static Object getConfig() {
        return DEFAULT;
    }

    public static LoggerSettings getLoggerSettings() {
        return DEFAULT.loggerSettings;
    }

    public record LoggerSettings(boolean logInfo, boolean logWarnings, boolean logDebug) {

        public static final Codec<LoggerSettings> CODEC = RecordCodecBuilder.create(builder ->
                builder.group(
                        Codec.BOOL.fieldOf("log_info").orElse(false).forGetter(config -> config.logInfo),
                        Codec.BOOL.fieldOf("log_warnings").orElse(false).forGetter(config -> config.logWarnings),
                        Codec.BOOL.fieldOf("log_debug").orElse(false).forGetter(config -> config.logDebug)
                ).apply(builder, LoggerSettings::new)
        );
    }
}
