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
@Mixin(targets = "net.minecraft.client.render.BackgroundRenderer$DarknessFogModifier")
public class DarknessFogModifierMixin {
    @Inject(method = "applyStartEndModifier", at = @At("HEAD"), cancellable = true)
    private void replaceApplyStartEndModifier(BackgroundRenderer.FogData fogData, LivingEntity entity, StatusEffectInstance effect, float viewDistance, float tickDelta, CallbackInfo ci) {
        ci.cancel();

        float e = effect.getAmplifier() + 1;
        float f = MathHelper.lerp(((StatusEffectInstance.FactorCalculationData)effect.getFactorCalculationData().get()).lerp(entity, tickDelta), viewDistance, 15.0F);
        fogData.fogStart = fogData.fogType == BackgroundRenderer.FogType.FOG_SKY ? 0.0F :  f * e * 0.75F;
        fogData.fogEnd = e * f;
    }
}
