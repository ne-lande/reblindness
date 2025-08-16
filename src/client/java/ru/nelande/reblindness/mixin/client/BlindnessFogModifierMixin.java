package ru.nelande.reblindness.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(targets = "net.minecraft.client.render.BackgroundRenderer$BlindnessFogModifier")
public class BlindnessFogModifierMixin {
    @Inject(method = "applyStartEndModifier", at = @At("HEAD"), cancellable = true)
    private void replaceApplyStartEndModifier(BackgroundRenderer.FogData fogData, LivingEntity entity, StatusEffectInstance effect, float viewDistance, float tickDelta, CallbackInfo ci) {
        ci.cancel();

        float e = effect.getAmplifier() + 1;
        float f = effect.isInfinite() ? 5.0F : MathHelper.lerp(Math.min(1.0F, (float)effect.getDuration() / 20.0F), viewDistance, 5.0F);
        if (fogData.fogType == BackgroundRenderer.FogType.FOG_SKY) {
            fogData.fogStart = 0.0F;
            fogData.fogEnd = f * e * 0.8F;
        } else {
            fogData.fogStart = f * e * 0.25F;
            fogData.fogEnd = f * e;
        }
    }
}
