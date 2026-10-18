package com.fixpot47.bs;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class HomingArrow extends Arrow {
    private LivingEntity target;

    public HomingArrow(EntityType<? extends Arrow> type, Level level) {
        super(type, level);
    }

    public void setTarget(LivingEntity target) { this.target = target; }

    @Override public void tick() {
        if (!level().isClientSide() && !this.inGround) {
            if (target == null || !target.isAlive() || target.isRemoved()) {
                target = null;
                var nearby = level().getEntitiesOfClass(LivingEntity.class,
                    this.getBoundingBox().inflate(32.0),
                    entity -> entity.isAlive() && entity != getOwner()
                        && !(getOwner() instanceof BedrockSkeleton && entity instanceof BedrockSkeleton));
                double distance = Double.MAX_VALUE;
                for (LivingEntity entity : nearby) {
                    double candidate = entity.distanceToSqr(this);
                    if (candidate < distance) { target = entity; distance = candidate; }
                }
            }
            if (target != null) {
                Vec3 toTarget = target.getEyePosition().subtract(position());
                if (toTarget.lengthSqr() > 0.0001) {
                    Vec3 velocity = getDeltaMovement();
                    double speed = Math.max(1.5, velocity.length());
                    Vec3 current = velocity.lengthSqr() > 0.0001 ? velocity.normalize() : toTarget.normalize();
                    setDeltaMovement(current.scale(0.45).add(toTarget.normalize().scale(0.55)).normalize().scale(speed));
                }
            }
        }
        super.tick();
    }
}
