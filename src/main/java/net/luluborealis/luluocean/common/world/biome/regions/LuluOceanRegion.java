package net.luluborealis.luluocean.common.world.biome.regions;

import com.mojang.datafixers.util.Pair;
import net.luluborealis.luluocean.common.world.biome.LuluOceanBiomes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;
import java.util.function.Consumer;

import static terrablender.api.ParameterUtils.*;

public class LuluOceanRegion extends Region
{
    public LuluOceanRegion(int weight)
    {
        super(LuluOceanBiomes.PROVIDER.getRegistryName(), RegionType.OVERWORLD, weight);
    }

    @SuppressWarnings("FunctionalExpressionCanBeFolded")
    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper)
    {
        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();

        // Frozen Depths
        new ParameterPointListBuilder()
                .temperature(Temperature.FULL_RANGE)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.MUSHROOM_FIELDS)
                .erosion(Erosion.FULL_RANGE)
                .depth(Depth.SURFACE)
                .weirdness(Weirdness.FULL_RANGE)
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.FROZEN_DEPTHS));
        new ParameterPointListBuilder()
                .temperature(Temperature.FULL_RANGE)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.MUSHROOM_FIELDS)
                .erosion(Erosion.FULL_RANGE)
                .depth(Depth.FLOOR)
                .weirdness(Weirdness.FULL_RANGE)
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.FROZEN_DEPTHS));

        // Lush Stacks
        depthBuilderLush(builder, Depth.SURFACE);
        depthBuilderLush(builder, Depth.FLOOR);

        // Dead Seas
        depthBuilderDead(builder, Depth.SURFACE);
        depthBuilderDead(builder, Depth.FLOOR);

        // Add our points to the mapper
        builder.build().forEach(mapper::accept);
    }

    private void depthBuilderDead(VanillaParameterOverlayBuilder builder, Depth depth){
        new ParameterPointListBuilder()
                .temperature(Temperature.FULL_RANGE)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.COAST)
                .erosion(Erosion.span(Erosion.EROSION_0,Erosion.EROSION_2))
                .depth(depth)
                .weirdness(Weirdness.MID_SLICE_NORMAL_ASCENDING)
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.DEAD_SEA));
        new ParameterPointListBuilder()
                .temperature(Temperature.FULL_RANGE)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.COAST)
                .erosion(Erosion.span(Erosion.EROSION_4,Erosion.EROSION_6))
                .depth(depth)
                .weirdness(Weirdness.span(Weirdness.MID_SLICE_NORMAL_DESCENDING, Weirdness.LOW_SLICE_NORMAL_DESCENDING))
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.DEAD_SEA));
        new ParameterPointListBuilder()
                .temperature(Temperature.FULL_RANGE)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.COAST)
                .erosion(Erosion.span(Erosion.EROSION_0,Erosion.EROSION_2))
                .depth(depth)
                .weirdness(Weirdness.span(Weirdness.MID_SLICE_NORMAL_DESCENDING, Weirdness.LOW_SLICE_NORMAL_DESCENDING))
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.DEAD_SEA));
        new ParameterPointListBuilder()
                .temperature(Temperature.FULL_RANGE)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.COAST)
                .erosion(Erosion.span(Erosion.EROSION_4,Erosion.EROSION_6))
                .depth(depth)
                .weirdness(Weirdness.span(Weirdness.MID_SLICE_NORMAL_DESCENDING, Weirdness.LOW_SLICE_NORMAL_DESCENDING))
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.DEAD_SEA));
        new ParameterPointListBuilder()
                .temperature(Temperature.FULL_RANGE)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.COAST)
                .erosion(Erosion.span(Erosion.EROSION_3,Erosion.EROSION_6))
                .depth(depth)
                .weirdness(Weirdness.LOW_SLICE_NORMAL_DESCENDING)
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.DEAD_SEA));
        new ParameterPointListBuilder()
                .temperature(Temperature.FULL_RANGE)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.COAST)
                .erosion(Erosion.span(Erosion.EROSION_0,Erosion.EROSION_4))
                .depth(depth)
                .weirdness(Weirdness.LOW_SLICE_VARIANT_ASCENDING)
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.DEAD_SEA));
        new ParameterPointListBuilder()
                .temperature(Temperature.FULL_RANGE)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.COAST)
                .erosion(Erosion.EROSION_6)
                .depth(depth)
                .weirdness(Weirdness.LOW_SLICE_VARIANT_ASCENDING)
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.DEAD_SEA));
        new ParameterPointListBuilder()
                .temperature(Temperature.FULL_RANGE)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.COAST)
                .erosion(Erosion.span(Erosion.EROSION_0,Erosion.EROSION_2))
                .depth(depth)
                .weirdness(Weirdness.MID_SLICE_VARIANT_ASCENDING)
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.DEAD_SEA));
        new ParameterPointListBuilder()
                .temperature(Temperature.FULL_RANGE)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.COAST)
                .erosion(Erosion.span(Erosion.EROSION_0,Erosion.EROSION_2))
                .depth(depth)
                .weirdness(Weirdness.MID_SLICE_VARIANT_DESCENDING)
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.DEAD_SEA));
    }

    private void depthBuilderLush(VanillaParameterOverlayBuilder builder, Depth depth) {
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.span(Continentalness.COAST, Continentalness.FAR_INLAND))
                .erosion(Erosion.EROSION_3)
                .depth(depth)
                .weirdness(Weirdness.span(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING))
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.NEAR_INLAND)
                .erosion(Erosion.span(Erosion.EROSION_1, Erosion.EROSION_5))
                .depth(depth)
                .weirdness(Weirdness.span(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.LOW_SLICE_NORMAL_DESCENDING))
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.MID_INLAND)
                .erosion(Erosion.span(Erosion.EROSION_1, Erosion.EROSION_4))
                .depth(depth)
                .weirdness(Weirdness.HIGH_SLICE_NORMAL_ASCENDING)
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.COAST)
                .erosion(Erosion.FULL_RANGE)
                .depth(depth)
                .weirdness(Weirdness.HIGH_SLICE_NORMAL_ASCENDING)
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.span(Continentalness.NEAR_INLAND, Continentalness.FAR_INLAND))
                .erosion(Erosion.EROSION_6)
                .depth(depth)
                .weirdness(Weirdness.span(Weirdness.HIGH_SLICE_NORMAL_ASCENDING, Weirdness.HIGH_SLICE_NORMAL_DESCENDING))
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.span(Continentalness.MID_INLAND, Continentalness.FAR_INLAND))
                .erosion(Erosion.span(Erosion.EROSION_2, Erosion.EROSION_4))
                .depth(depth)
                .weirdness(Weirdness.span(Weirdness.HIGH_SLICE_NORMAL_ASCENDING, Weirdness.LOW_SLICE_NORMAL_DESCENDING))
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.span(Continentalness.COAST, Continentalness.NEAR_INLAND))
                .erosion(Erosion.span(Erosion.EROSION_1, Erosion.EROSION_6))
                .depth(depth)
                .weirdness(Weirdness.span(Weirdness.PEAK_NORMAL, Weirdness.HIGH_SLICE_NORMAL_DESCENDING))
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.COAST)
                .erosion(Erosion.FULL_RANGE)
                .depth(depth)
                .weirdness(Weirdness.HIGH_SLICE_NORMAL_DESCENDING)
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.span(Continentalness.MID_INLAND, Continentalness.FAR_INLAND))
                .erosion(Erosion.span(Erosion.EROSION_1, Erosion.EROSION_4))
                .depth(depth)
                .weirdness(Weirdness.span(Weirdness.MID_SLICE_NORMAL_DESCENDING, Weirdness.LOW_SLICE_NORMAL_DESCENDING))
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.span(Continentalness.NEAR_INLAND, Continentalness.FAR_INLAND))
                .erosion(Erosion.span(Erosion.EROSION_0, Erosion.EROSION_5))
                .depth(depth)
                .weirdness(Weirdness.LOW_SLICE_NORMAL_DESCENDING)
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.span(Continentalness.NEAR_INLAND, Continentalness.FAR_INLAND))
                .erosion(Erosion.span(Erosion.EROSION_0, Erosion.EROSION_1))
                .depth(depth)
                .weirdness(Weirdness.span(Weirdness.VALLEY, Weirdness.LOW_SLICE_VARIANT_ASCENDING))
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.span(Continentalness.COAST, Continentalness.NEAR_INLAND))
                .erosion(Erosion.EROSION_5)
                .depth(depth)
                .weirdness(Weirdness.span(Weirdness.LOW_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING))
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.span(Continentalness.NEAR_INLAND, Continentalness.FAR_INLAND))
                .erosion(Erosion.span(Erosion.EROSION_0, Erosion.EROSION_4))
                .depth(depth)
                .weirdness(Weirdness.LOW_SLICE_VARIANT_ASCENDING)
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.span(Continentalness.MID_INLAND, Continentalness.FAR_INLAND))
                .erosion(Erosion.EROSION_5)
                .depth(depth)
                .weirdness(Weirdness.LOW_SLICE_VARIANT_ASCENDING)
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.span(Continentalness.COAST, Continentalness.FAR_INLAND))
                .erosion(Erosion.span(Erosion.EROSION_3, Erosion.EROSION_4))
                .depth(depth)
                .weirdness(Weirdness.span(Weirdness.HIGH_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING))
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.COAST)
                .erosion(Erosion.EROSION_6)
                .depth(depth)
                .weirdness(Weirdness.span(Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING))
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.NEAR_INLAND)
                .erosion(Erosion.span(Erosion.EROSION_1, Erosion.EROSION_4))
                .depth(depth)
                .weirdness(Weirdness.span(Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING))
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.span(Continentalness.MID_INLAND, Continentalness.FAR_INLAND))
                .erosion(Erosion.span(Erosion.EROSION_1, Erosion.EROSION_4))
                .depth(depth)
                .weirdness(Weirdness.MID_SLICE_VARIANT_ASCENDING)
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.COAST)
                .erosion(Erosion.span(Erosion.EROSION_0, Erosion.EROSION_4))
                .depth(depth)
                .weirdness(Weirdness.HIGH_SLICE_VARIANT_ASCENDING)
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.span(Continentalness.NEAR_INLAND, Continentalness.FAR_INLAND))
                .erosion(Erosion.EROSION_6)
                .depth(depth)
                .weirdness(Weirdness.span(Weirdness.HIGH_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING))
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.span(Continentalness.MID_INLAND, Continentalness.FAR_INLAND))
                .erosion(Erosion.EROSION_6)
                .depth(depth)
                .weirdness(Weirdness.span(Weirdness.HIGH_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING))
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.span(Continentalness.MID_INLAND, Continentalness.FAR_INLAND))
                .erosion(Erosion.span(Erosion.EROSION_2, Erosion.EROSION_4))
                .depth(depth)
                .weirdness(Weirdness.span(Weirdness.HIGH_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING))
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.COAST)
                .erosion(Erosion.span(Erosion.EROSION_0, Erosion.EROSION_4))
                .depth(depth)
                .weirdness(Weirdness.HIGH_SLICE_VARIANT_DESCENDING)
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
        new ParameterPointListBuilder()
                .temperature(Temperature.HOT)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.span(Continentalness.MID_INLAND, Continentalness.FAR_INLAND))
                .erosion(Erosion.span(Erosion.EROSION_1, Erosion.EROSION_4))
                .depth(depth)
                .weirdness(Weirdness.MID_SLICE_VARIANT_DESCENDING)
                .build().forEach(point -> builder.add(point, LuluOceanBiomes.LUSH_STACKS));
    }
}