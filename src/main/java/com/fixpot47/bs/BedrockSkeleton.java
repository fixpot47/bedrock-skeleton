package com.fixpot47.bs;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class BedrockSkeleton extends Skeleton {
    public BedrockSkeleton(EntityType<? extends Skeleton> type, Level level) { super(type, level); }
    public static AttributeSupplier.Builder createAttributes() {
        return Skeleton.createAttributes().add(Attributes.MAX_HEALTH, 50.0)
            .add(Attributes.MOVEMENT_SPEED, 0.375);
    }
    // Unlike vanilla skeletons, this mob never ignites in daylight.
    @Override protected boolean isSunBurnTick() { return false; }
    @Override protected void registerGoals() {
        super.registerGoals();
        // Replace the vanilla bow goal so shots are fired every 10 ticks.
        this.goalSelector.removeAllGoals(goal -> goal instanceof RangedBowAttackGoal<?>);
        this.goalSelector.addGoal(4, new RangedBowAttackGoal<>(this, 1.0, 10, 15.0f));
    }
    @Override public void performRangedAttack(LivingEntity target, float distanceFactor) {
        if (this.level().isClientSide()) return;
        HomingArrow arrow = new HomingArrow(BedrockSkeletonMod.ARROW_ENTITY, this.level());
        arrow.setOwner(this);
        arrow.setPos(this.getX(), this.getEyeY() - 0.15, this.getZ());
        Vec3 delta = target.getEyePosition().subtract(arrow.position());
        arrow.shoot(delta.x, delta.y, delta.z, 2.0f, 0.0f);
        arrow.setTarget(target);
        this.level().addFreshEntity(arrow);
    }
}
