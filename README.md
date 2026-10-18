# Bedrock Skeleton 1.0.0 (Minecraft 26.3 / Fabric)

**Status: source candidate — not yet compiled or tested in Minecraft.**

Adds `bs:bedrock_skeleton`, `bs:bedrock_skeleton_spawn_egg`, and `bs:autoaim_arrow`.

- 50 health, 1.5x movement speed, 10-tick ranged attack interval.
- Red (not glowing) eyes; separate texture does not alter vanilla skeletons.
- Homing arrows for mob and creative-mode players, blocked by solid blocks.
- Monster-style darkness-only spawning with extra spawn weight 20 (approximate 1:5 vs standard skeleton spawn weight 100 where weights apply).
- Does not ignite in sunlight.
- 0–2 bones, a bow with a 3% chance, and intended 5 XP (requires in-game verification).

Commands:

```mcfunction
/summon bs:bedrock_skeleton
/give @s bs:bedrock_skeleton_spawn_egg
/give @s bs:autoaim_arrow
```

## Build

Push this folder to your repository `fixpot47/bedrock-skeleton` and open **Actions → Build Bedrock Skeleton → Run workflow**. If successful, obtain the JAR from the workflow's `bedrock-skeleton-26.3-jar` artifact. GitHub Actions installs Java 25 and Gradle 9.6.0.

The project has not been compiled in the assistant environment (no Java 25 or dependency downloads), so this build could fail; errors in the Actions log should be fixed before publishing.
