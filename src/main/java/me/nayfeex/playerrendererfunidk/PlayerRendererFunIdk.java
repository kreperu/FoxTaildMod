package me.nayfeex.playerrendererfunidk;

import me.nayfeex.playerrendererfunidk.client.TailRenderer;
import me.nayfeex.playerrendererfunidk.client.TailSemiBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class PlayerRendererFunIdk implements ModInitializer {


    @Override
    public void onInitialize() {
        Block tail = new TailSemiBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).nonOpaque().noCollision().luminance(1));
        Registry.register(Registry.BLOCK, new Identifier("player-renderer-fun-idk", "tail_clr_opt_x2"), tail);
        Registry.register(Registry.ITEM, new Identifier("player-renderer-fun-idk", "tail_clr_opt_x2"), new BlockItem(tail, new FabricItemSettings().group(ItemGroup.MISC)));
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof PlayerEntityRenderer) {
                registrationHelper.register(new TailRenderer((PlayerEntityRenderer) entityRenderer));
            }
        });

    }
}
