package tfar.mc5169fix.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.tileentity.SignTileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.mc5169fix.MC5169Fix;

@Mixin(SignTileEntityRenderer.class)
public class SignTileEntityRendererMixin {

	@Inject(method = "render",at = @At(value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;getFontRenderer()Lnet/minecraft/client/gui/FontRenderer;"),cancellable = true)
	private void cancelText(SignTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, CallbackInfo ci) {
		if (Minecraft.getInstance().player.getPosition().distanceSq(tileEntityIn.getPos()) > MC5169Fix.distSquared) {
			matrixStackIn.pop();
			ci.cancel();
		}
	}
}
