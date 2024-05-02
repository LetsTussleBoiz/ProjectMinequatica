package net.luluborealis.luluocean;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = LuluOcean.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public record Config(boolean appendBiomePlacedFeatures, boolean appendLootTables, boolean customVillagers,
                     boolean customStructures, boolean useBYGWorldGen, LoggerSettings loggerSettings)
{

    private static final Config DEFAULT = new Config(true, true, true, true, true, new LoggerSettings(false, true, false));

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
            .comment("Whether to log the dirt block on common setup")
            .define("logDirtBlock", true);

    private static final ForgeConfigSpec.IntValue MAGIC_NUMBER = BUILDER
            .comment("A magic number")
            .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);

    public static final ForgeConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
            .comment("What you want the introduction message to be for the magic number")
            .define("magicNumberIntroduction", "The magic number is... ");

    // a list of strings that are treated as resource locations for items
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("A list of items to log on common setup.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), Config::validateItemName);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean logDirtBlock;
    public static int magicNumber;
    public static String magicNumberIntroduction;
    public static Set<Item> items;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        logDirtBlock = LOG_DIRT_BLOCK.get();
        magicNumber = MAGIC_NUMBER.get();
        magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();

        // convert the list of strings into a set of items
        items = ITEM_STRINGS.get().stream()
                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
                .collect(Collectors.toSet());
    }

    public static Object getConfig() {
        return DEFAULT;
    }

    public static boolean getAppendLootTables() {
        return DEFAULT.appendBiomePlacedFeatures;
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
