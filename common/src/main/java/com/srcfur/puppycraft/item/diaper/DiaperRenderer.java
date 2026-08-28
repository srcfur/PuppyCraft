package com.srcfur.puppycraft.item.diaper;

import com.geckolib.object.VanillaModelModifier;
import com.geckolib.renderer.GeoArmorRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.client.TrinketRenderer;
import eu.pb4.trinkets.impl.client.render.ModelAttachementImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DiaperRenderer implements TrinketRenderer {

    private int getDiaperStage(ItemStack stack){
        int stageincrement = stack.getMaxDamage() / 5;
        //in case of weird shit that I am NOT guessing!
        return Math.clamp(stack.getDamageValue() / stageincrement, 0, 5);
    }
    private String getDiaperVariant(ItemStack stack){
        if(!(stack.getItem() instanceof DiaperItem)) return "missing";
        DiaperItem item = (DiaperItem)stack.getItem();
        return item.DiaperTexture;
    }

    @Override
    public void submit(ItemStack itemStack,
                       TrinketSlotAccess trinketSlotAccess,
                       EntityModel<? extends LivingEntityRenderState> entityModel,
                       PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                       int i,
                       LivingEntityRenderState livingEntityRenderState,
                       float v, float v1) {
        Model<? super LivingEntityRenderState> diapermodel = new DiaperModel();
        ModelAttachementImpl.translateToModelPartNoOffset(entityModel, "Body", null, poseStack);
        entityModel.allParts().get(6).translateAndRotate(poseStack);
        submitNodeCollector.submitModel(diapermodel, livingEntityRenderState, poseStack,
                RenderTypes.armorCutoutNoCull(Identifier.fromNamespaceAndPath("puppycraft", "textures/models/armor/diapers/" + getDiaperVariant(itemStack) + getDiaperStage(itemStack) + ".png")),
                i, 0, 0, null);
    }
}
