package me.nayfeex.playerrendererfunidk.client;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.floats.Float2LongMap;
import me.nayfeex.playerrendererfunidk.client.util.Scale;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadView;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class TailRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public TailRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }

    private float angle = 50;
    private float rot = 0;
    private boolean en = false;
    private boolean inv = false;
    private int i = 0;

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        matrices.push();
        matrices.scale(0.9F, 0.9F, 0.9F);
        if(!entity.isSneaking()) {
            matrices.translate(0.6F, -entity.getHeight() + 3.3F, 0.25F);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(195));
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
        } else {
            matrices.translate(0.6F, -entity.getHeight() + 3.0F, 0.9F);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(225));
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
        }

        if (entity.getMovementSpeed() > 0.11) {
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion((float) (Math.sin(entity.getWorld().getTime() * 0.1) * 2 - 12)));
            matrices.translate(0.0F, 0.0F , 0.1F);
        }

        Random random = new Random((long) (entity.getWorld().getTime() + entity.age + entity.deathTime - entity.bodyYaw - entity.distanceTraveled + entity.randomLargeSeed - entity.randomSmallSeed + entity.getWorld().getMoonPhase() + entity.getWorld().getSpawnAngle() + entity.getWorld().getTickOrder() - entity.prevX));
        int randInt = random.nextInt(1000);
        if(randInt > 4 && randInt <= 6) {
            if(randInt == 5) {
                if(TailSemiBlock.wind > 2.2) {
                    TailSemiBlock.wind = TailSemiBlock.wind - 0.5F;
                } else {
                    TailSemiBlock.wind = TailSemiBlock.wind + 0.5F;
                }
            } else {
                if(TailSemiBlock.wind < 1) {
                    TailSemiBlock.wind = TailSemiBlock.wind + 0.5F;
                } else {
                    TailSemiBlock.wind = TailSemiBlock.wind - 0.5F;
                }
            }
        }


        angle = (float) (Math.sin(entity.world.getTime() * (((10 * TailSemiBlock.wind) / 50) / (10 * TailSemiBlock.wind))) * (10.0F * TailSemiBlock.wind));
        if(entity.prevBodyYaw - entity.bodyYaw > 0) {
            en = true;
            inv = false;
            i = 40;
        }
        if(entity.prevBodyYaw - entity.bodyYaw < 0) {
            en = true;
            inv = true;
            i = 40;
        }
        if(en) {
            if(i > 1) {
                if(inv) {
                    angle = angle - (i / 4);
                } else {
                    angle = angle + (i / 4);
                }
                i--;
            } else {
                en = false;
            }
        }
        PlayerRendererFunIdkClient.logger.info(String.valueOf(entity.prevBodyYaw-entity.bodyYaw));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(angle));
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(angle/7));
        if(TailSemiBlock.wind > 0 && entity.isSneaking()) {
            float ang_map1 = Scale.map(angle, -50.0F, 50.0F, -0.52F, 0.52F);
            float ang_map2 = Scale.map(angle, -50.0F, 50.0F, 0.0F, -0.8F);
            float ang_map3 = Scale.map(angle, -50.0F, 50.0F, 0.0F, 0.15F);

            matrices.translate(ang_map1, ang_map2, ang_map3);
        } else if(TailSemiBlock.wind > 0 && !entity.isSneaking()) {
            float ang_map1 = Scale.map(angle, -50.0F, 50.0F, -0.52F, 0.52F);
            float ang_map2 = Scale.map(angle, -50.0F, 50.0F, 0.0F, -0.8F);
            float ang_map3 = Scale.map(angle, -50.0F, 50.0F, 0.0F, 0.0F);

            matrices.translate(ang_map1, ang_map2, ang_map3);
        }

        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(TailSemiBlock.state, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV);

        matrices.pop();
    }
}
