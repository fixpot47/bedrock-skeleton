package com.fixpot47.bs;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.core.Registry;

public final class BedrockSkeletonMod implements ModInitializer {
    public static final String MOD_ID = "bs";
    private static Identifier id(String path) { return Identifier.fromNamespaceAndPath(MOD_ID, path); }
    public static final EntityType<BedrockSkeleton> BEDROCK_SKELETON = Registry.register(
        BuiltInRegistries.ENTITY_TYPE, id("bedrock_skeleton"),
        EntityType.Builder.of(BedrockSkeleton::new, MobCategory.MONSTER).sized(0.6f, 1.99f)
            .build(ResourceKey.create(Registries.ENTITY_TYPE, id("bedrock_skeleton")))
    );
    public static final EntityType<HomingArrow> ARROW_ENTITY = Registry.register(
        BuiltInRegistries.ENTITY_TYPE, id("homing_arrow_projectile"),
        EntityType.Builder.<HomingArrow>of(HomingArrow::new, MobCategory.MISC)
            .sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20)
            .build(ResourceKey.create(Registries.ENTITY_TYPE, id("homing_arrow_projectile")))
    );
    public static final Item AUTOAIM_ARROW = Registry.register(
        BuiltInRegistries.ITEM, id("autoaim_arrow"),
        new AutoaimArrowItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id("autoaim_arrow"))))
    );
    public static final Item SPAWN_EGG = Registry.register(
        BuiltInRegistries.ITEM, id("bedrock_skeleton_spawn_egg"),
        new SpawnEggItem(new Item.Properties().spawnEgg(BEDROCK_SKELETON)
            .setId(ResourceKey.create(Registries.ITEM, id("bedrock_skeleton_spawn_egg"))))
    );
    @Override public void onInitialize() {
        FabricDefaultAttributeRegistry.register(BEDROCK_SKELETON, BedrockSkeleton.createAttributes());
        // Darkness check is the same as for vanilla monsters. Not spawned in sunny areas.
        SpawnPlacements.register(BEDROCK_SKELETON, SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        // A biome spawn weight of 20 relative to vanilla skeleton's typical 100.
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), MobCategory.MONSTER,
            BEDROCK_SKELETON, 20, 1, 2);
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register(tab -> tab.accept(SPAWN_EGG));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(tab -> tab.accept(AUTOAIM_ARROW));
    }
}
