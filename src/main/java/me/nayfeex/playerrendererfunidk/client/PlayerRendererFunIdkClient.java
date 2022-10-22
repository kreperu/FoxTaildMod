package me.nayfeex.playerrendererfunidk.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Environment(EnvType.CLIENT)
public class PlayerRendererFunIdkClient implements ClientModInitializer {
    public static final Logger logger = LoggerFactory.getLogger(PlayerRendererFunIdkClient.class);
    @Override
    public void onInitializeClient() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) ->  {
            logger.info("workzzzz");
            if(entityRenderer instanceof PlayerEntityRenderer) {
                registrationHelper.register(new TailRenderer((PlayerEntityRenderer) entityRenderer));
            }
        });
    }
}
