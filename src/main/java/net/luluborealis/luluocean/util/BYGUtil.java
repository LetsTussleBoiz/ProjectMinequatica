package net.luluborealis.luluocean.util;


import com.google.common.collect.ImmutableList;
import net.luluborealis.luluocean.LuluOcean;
import net.luluborealis.luluocean.common.world.biome.LayersBiomeData;
import net.luluborealis.luluocean.mixin.access.WeightedEntryWrapperAccess;
import net.luluborealis.luluocean.mixin.access.WeightedListAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BYGUtil {

    public static boolean useTagReplacements = false;


    public static <V> List<List<V>> convert2DArray(V[][] arrayToConvert) {
        List<List<V>> convertedArrays = new ArrayList<>();
        for (V[] vs : arrayToConvert) {
            convertedArrays.add(Arrays.asList(vs));
        }
        return convertedArrays;
    }

    @SuppressWarnings("unchecked")
    public static <T> ResourceKey<T>[][] _2DResourceKeyArrayTo2DList(List<List<ResourceKey<T>>> listToConvert) {
        List<ResourceKey<T>[]> resultList = new ArrayList<>(listToConvert.size());
        for (List<ResourceKey<T>> vs : listToConvert) {
            resultList.add(vs.toArray(ResourceKey[]::new));
        }

        return resultList.toArray(ResourceKey[][]::new);
    }

    public static <T> String print2DResourceKeyArray(ResourceKey<T>[][] valueToPrint) {
        StringBuilder builder = new StringBuilder();

        for (ResourceKey<T>[] value : valueToPrint) {
            builder.append(Arrays.toString(Arrays.stream(value).map(ResourceKey::location).toArray(ResourceLocation[]::new))).append("\n");
        }
        return builder.toString();
    }

    public static <T> String dumpRegistry(Registry<T> registry) {
        StringBuilder registryElements = new StringBuilder();
        for (int i = 0; i < registry.entrySet().size(); i++) {
            final T object = registry.byId(i);
            registryElements.append(i).append(". \"").append(registry.getKey(object).toString()).append("\"\n");
        }
        return registryElements.toString();
    }

    public static boolean createMarkerFile(Path path, String markerText) {
        if (path.toFile().exists()) {
            return false;
        } else {
            try {
                Files.createDirectories(path.getParent());
                Files.write(path, markerText.getBytes());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static boolean isNearby(LevelReader levelReader, BlockPos origin, int searchRange, BiPredicate<LevelReader, BlockPos> passes) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos().set(origin);
        for (int xOffset = -searchRange; xOffset < searchRange; xOffset++) {
            for (int yOffset = -searchRange; yOffset < searchRange; yOffset++) {
                for (int zOffset = -searchRange; zOffset < searchRange; zOffset++) {
                    mutableBlockPos.set(origin).move(xOffset, yOffset, zOffset);
                    if (passes.test(levelReader, mutableBlockPos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static IOException configFileFailureException(Path path) {
        return new IOException(String.format("BYG config found at: \"%s\" could not be read. The fastest solution is to rename this failed file and let a new file generate from BYG and replace the fields in the new file with the broken file's fields.", path.toFile()));
    }

    @SuppressWarnings("unchecked")
    public static <T> SimpleWeightedRandomList<T> combineWeightedRandomLists(BiPredicate<Collection<T>, T> filter, SimpleWeightedRandomList<T>... builders) {
        SimpleWeightedRandomList.Builder<T> combinedBuilder = new SimpleWeightedRandomList.Builder<>();
        for (SimpleWeightedRandomList<T> build : builders) {
            for (WeightedEntry.Wrapper<T> item : ((WeightedListAccess<WeightedEntry.Wrapper<T>>) build).byg_getItems()) {
                Set<T> collection = ((WeightedListAccess<WeightedEntry.Wrapper<T>>) combinedBuilder.build()).byg_getItems().stream().map(item1 -> ((WeightedEntryWrapperAccess<T>) item1).byg_getData()).collect(Collectors.toSet());
                T data = ((WeightedEntryWrapperAccess<T>) item).byg_getData();

                if (filter.test(collection, data)) {
                    combinedBuilder.add(data, ((WeightedEntryWrapperAccess<T>) item).byg_getWeight().asInt());
                }
            }
        }
        return combinedBuilder.build();
    }

    @SuppressWarnings("unchecked")
    public static List<Holder<Biome>> createBiomesFromBiomeData(Registry<Biome> biomeRegistry, LayersBiomeData... layersBiomeDatas) {
        List<Holder<Biome>> biomes = new ArrayList<>();
        for (LayersBiomeData layersBiomeData : layersBiomeDatas) {
            ImmutableList<WeightedEntry.Wrapper<ResourceKey<Biome>>> items = ((WeightedListAccess<WeightedEntry.Wrapper<ResourceKey<Biome>>>) layersBiomeData.biomeWeights()).byg_getItems();

            for (WeightedEntry.Wrapper<ResourceKey<Biome>> key : items) {
                ResourceKey<Biome> resourceKey = key.getData();
                Optional<Holder.Reference<Biome>> biome = biomeRegistry.getHolder(resourceKey);
                if (biome.isPresent()) {
                    biomes.add(biome.get());
                } else {
                    LuluOcean.logInfo("\"" + resourceKey.location() + "\" is not a value in the biome registry at this point, ignore this warning if the data pack containing this biome is not yet added during world creation.");
                }
            }
        }
        return biomes;
    }

    public static String dumpCollection(Collection<String> collection) {
        String[] values = collection.toArray(String[]::new);
        StringBuilder result = new StringBuilder();
        int length = values.length;
        for (int i = 0; i < length; i++) {
            String value = values[i];
            result.append(i + 1).append(". \"");
            if (i < length - 1) {
                result.append(value).append("\",\n");
            } else {
                result.append(value).append("\"");
            }
        }
        return result.toString();
    }

    public static <T, C, V, MAP extends Map<T, V>> MAP convertMapValueType(Map<T, C> from, Supplier<MAP> mapType, Function<C, V> newValueType) {
        MAP result = mapType.get();
        from.forEach((key, oldValue) -> result.put(key, newValueType.apply(oldValue)));
        return result;
    }

    public static boolean isInside(int minX, int minY, int maxX, int maxY, int x, int y) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    public static boolean isInside(int minX, int minY, int maxX, int maxY, double x, double y) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }
}
