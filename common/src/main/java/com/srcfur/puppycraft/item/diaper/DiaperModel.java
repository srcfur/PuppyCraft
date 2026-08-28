package com.srcfur.puppycraft.item.diaper;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Display;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class DiaperModel extends Model<LivingEntityRenderState> {
    private static float ptw(float pixels){
        return pixels / 2f;
    }
    public DiaperModel() {
        ModelPart.Cube diapercore = new ModelPart.Cube(0, 0,
                -2.5f, 8.75f, -3f,
                5, 4, 6,
                0,0,0,
                false,
                64, 64,
                Set.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.UP, Direction.DOWN));
        ModelPart.Cube diaperlanding = new ModelPart.Cube(0,11,
                -4f, 7, -2f,
                8f, 2f, 4f,
                0.25f, 0, 0.25f,
                false,
                64, 64,
                Set.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.UP, Direction.DOWN));
        ModelPart diaper = new ModelPart(List.of(diapercore, diaperlanding), Map.of());
        super(diaper, RenderTypes::entitySolid);
    }

}
