package com.fixpot47.bs;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AutoaimArrowItem extends ArrowItem {
    public AutoaimArrowItem(Properties properties) { super(properties); }
    @Override public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, ItemStack weapon) {
        HomingArrow arrow = new HomingArrow(BedrockSkeletonMod.ARROW_ENTITY, level);
        arrow.setOwner(shooter);
        arrow.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        arrow.setPickupItemStack(ammo.copyWithCount(1));
        return arrow;
    }
}
