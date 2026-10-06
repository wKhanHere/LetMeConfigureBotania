package net.wkhan.lmcb.mixin.block.terraplate;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.botania.client.patchouli.component.TerraPlateComponent;
import vazkii.patchouli.api.IComponentRenderContext;

@Mixin(TerraPlateComponent.class)
public abstract class TerraPlateComponentMixin {

//    @Inject(
//            method = "render",
//            at = @At("HEAD"),
//            remap = false,
//            cancellable = true
//    )
//    private void lmcb$renderCustomTerraMulti(GuiGraphics gui, IComponentRenderContext context, float pticks,
//                                             int mouseX, int mouseY, CallbackInfo ci) {
//        ci.cancel();
//        PoseStack ms = gui.pose();
//        ms.pushPose();
//        ms.translate(0.0F, 0.0F, -10.0F);
//        context.renderItemStack(gui, this.x + 13, this.y + 1, mouseX, mouseY, this.cornerBlock);
//        ms.translate(0.0F, 0.0F, 5.0F);
//        context.renderItemStack(gui, this.x + 20, this.y + 4, mouseX, mouseY, this.middleBlock);
//        context.renderItemStack(gui, this.x + 7, this.y + 4, mouseX, mouseY, this.middleBlock);
//        ms.translate(0.0F, 0.0F, 5.0F);
//        context.renderItemStack(gui, this.x + 13, this.y + 8, mouseX, mouseY, this.cornerBlock);
//        context.renderItemStack(gui, this.x + 27, this.y + 8, mouseX, mouseY, this.centerBlock);
//        context.renderItemStack(gui, this.x, this.y + 8, mouseX, mouseY, this.cornerBlock);
//        ms.translate(0.0F, 0.0F, 5.0F);
//        context.renderItemStack(gui, this.x + 7, this.y + 12, mouseX, mouseY, this.middleBlock);
//        context.renderItemStack(gui, this.x + 20, this.y + 12, mouseX, mouseY, this.middleBlock);
//        ms.translate(0.0F, 0.0F, 5.0F);
//        context.renderItemStack(gui, this.x + 14, this.y + 15, mouseX, mouseY, this.cornerBlock);
//        ms.translate(0.0F, 0.0F, 5.0F);
//        context.renderItemStack(gui, this.x + 13, this.y, mouseX, mouseY, this.plateBlock);
//        ms.popPose();
//    }
}
