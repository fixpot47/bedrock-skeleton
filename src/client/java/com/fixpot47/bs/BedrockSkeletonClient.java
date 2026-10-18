package com.fixpot47.bs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.resources.Identifier;

public class BedrockSkeletonClient implements ClientModInitializer {
    @Override public void onInitializeClient() {
        EntityRendererRegistry.register(BedrockSkeletonMod.BEDROCK_SKELETON, BedrockSkeletonRenderer::new);
        EntityRendererRegistry.register(BedrockSkeletonMod.ARROW_ENTITY, ArrowRenderer::new);
    }
    private static final class BedrockSkeletonRenderer extends SkeletonRenderer {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("bs", "textures/entity/bedrock_skeleton.png");
        private BedrockSkeletonRenderer(EntityRendererProvider.Context context) {
            super(context);
        }
        @Override public Identifier getTextureLocation(SkeletonRenderState state) { return TEXTURE; }
    }
}
