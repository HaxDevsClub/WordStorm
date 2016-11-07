package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class VboChunkFactory implements IRenderChunkFactory
{
    private static final String __OBFID = "CL_00002451";

    public RenderChunk makeRenderChunk(World worldIn, RenderGlobal globalRenderer, BlockPos pos, int index)
    {
        return new RenderChunk(worldIn, globalRenderer, pos, index);
    }
}
