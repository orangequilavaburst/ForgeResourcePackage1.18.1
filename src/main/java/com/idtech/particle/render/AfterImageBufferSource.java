package com.idtech.particle.render;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AfterImageBufferSource implements MultiBufferSource {

    public static Map<RenderType, BufferBuilder> renderBuilders = new HashMap<>();
    public static final int BUFFER_BUILDER_CAPACITY = 2048;// 786432; // TODO copied this number from the RenderBuffers class

    @Override
    public @NotNull VertexConsumer getBuffer(RenderType pRenderType) {
        BufferBuilder buffer = getOrCreateBuffer(pRenderType);
        buffer.begin(pRenderType.mode(), pRenderType.format());
        return buffer;
    }

    public BufferBuilder getOrCreateBuffer(RenderType pRenderType){
        BufferBuilder buffer = renderBuilders.get(pRenderType);
        if (buffer == null){
            BufferBuilder newBuffer = new BufferBuilder(BUFFER_BUILDER_CAPACITY);
            renderBuilders.put(pRenderType, newBuffer);
            buffer = newBuffer;
        }
        return buffer;
    }

    public Map<RenderType, BufferBuilder> getRenderBuilders(){
        return renderBuilders;
    }

}
