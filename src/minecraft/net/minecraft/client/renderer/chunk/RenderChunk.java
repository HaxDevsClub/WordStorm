package net.minecraft.client.renderer.chunk;

import java.nio.FloatBuffer;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RegionRenderCache;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class RenderChunk
{
    private World world;
    private final RenderGlobal renderGlobal;
    public static int renderChunksUpdated;
    private BlockPos position;
    public CompiledChunk compiledChunk;
    private final ReentrantLock lockCompileTask;
    private final ReentrantLock lockCompiledChunk;
    private ChunkCompileTaskGenerator compileTask;
    private final int index;
    private final FloatBuffer modelviewMatrix;
    private final VertexBuffer[] vertexBuffers;
    public AxisAlignedBB boundingBox;
    private int frameIndex;
    private boolean needsUpdate;
    private static final String __OBFID = "CL_00002452";

    public RenderChunk(World worldIn, RenderGlobal renderGlobalIn, BlockPos blockPosIn, int indexIn)
    {
        this.compiledChunk = CompiledChunk.DUMMY;
        this.lockCompileTask = new ReentrantLock();
        this.lockCompiledChunk = new ReentrantLock();
        this.compileTask = null;
        this.modelviewMatrix = GLAllocation.createDirectFloatBuffer(16);
        this.vertexBuffers = new VertexBuffer[EnumWorldBlockLayer.values().length];
        this.frameIndex = -1;
        this.needsUpdate = true;
        this.world = worldIn;
        this.renderGlobal = renderGlobalIn;
        this.index = indexIn;

        if (!blockPosIn.equals(this.getPosition()))
        {
            this.setPosition(blockPosIn);
        }

        if (OpenGlHelper.useVbo())
        {
            for (int var5 = 0; var5 < EnumWorldBlockLayer.values().length; ++var5)
            {
                this.vertexBuffers[var5] = new VertexBuffer(DefaultVertexFormats.BLOCK);
            }
        }
    }

    public boolean setFrameIndex(int frameIndexIn)
    {
        if (this.frameIndex == frameIndexIn)
        {
            return false;
        }
        else
        {
            this.frameIndex = frameIndexIn;
            return true;
        }
    }

    public VertexBuffer getVertexBufferByLayer(int layer)
    {
        return this.vertexBuffers[layer];
    }

    public void setPosition(BlockPos pos)
    {
        this.stopCompileTask();
        this.position = pos;
        this.boundingBox = new AxisAlignedBB(pos, pos.add(16, 16, 16));
        this.initModelviewMatrix();
    }

    public void resortTransparency(float x, float y, float z, ChunkCompileTaskGenerator generator)
    {
        CompiledChunk var5 = generator.getCompiledChunk();

        if (var5.getState() != null && !var5.isLayerEmpty(EnumWorldBlockLayer.TRANSLUCENT))
        {
            this.preRenderBlocks(generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(EnumWorldBlockLayer.TRANSLUCENT), this.position);
            generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(EnumWorldBlockLayer.TRANSLUCENT).setVertexState(var5.getState());
            this.postRenderBlocks(EnumWorldBlockLayer.TRANSLUCENT, x, y, z, generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(EnumWorldBlockLayer.TRANSLUCENT), var5);
        }
    }

    public void rebuildChunk(float x, float y, float z, ChunkCompileTaskGenerator generator)
    {
        CompiledChunk var5 = new CompiledChunk();
        boolean var6 = true;
        BlockPos var7 = this.position;
        BlockPos var8 = var7.add(15, 15, 15);
        generator.getLock().lock();
        RegionRenderCache var9;

        try
        {
            if (generator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING)
            {
                return;
            }

            var9 = new RegionRenderCache(this.world, var7.add(-1, -1, -1), var8.add(1, 1, 1), 1);
            generator.setCompiledChunk(var5);
        }
        finally
        {
            generator.getLock().unlock();
        }

        VisGraph var10 = new VisGraph();

        if (!var9.extendedLevelsInChunkCache())
        {
            ++renderChunksUpdated;
            Iterator var11 = BlockPos.getAllInBoxMutable(var7, var8).iterator();

            while (var11.hasNext())
            {
                BlockPos.MutableBlockPos var12 = (BlockPos.MutableBlockPos)var11.next();
                IBlockState var13 = var9.getBlockState(var12);
                Block var14 = var13.getBlock();

                if (var14.isOpaqueCube())
                {
                    var10.func_178606_a(var12);
                }

                if (var14.hasTileEntity())
                {
                    TileEntity var15 = var9.getTileEntity(new BlockPos(var12));

                    if (var15 != null && TileEntityRendererDispatcher.instance.hasSpecialRenderer(var15))
                    {
                        var5.addTileEntity(var15);
                    }
                }

                EnumWorldBlockLayer var24 = var14.getBlockLayer();
                int var16 = var24.ordinal();

                if (var14.getRenderType() != -1)
                {
                    WorldRenderer var17 = generator.getRegionRenderCacheBuilder().getWorldRendererByLayerId(var16);

                    if (!var5.isLayerStarted(var24))
                    {
                        var5.setLayerStarted(var24);
                        this.preRenderBlocks(var17, var7);
                    }

                    if (Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlock(var13, var12, var9, var17))
                    {
                        var5.setLayerUsed(var24);
                    }
                }
            }

            EnumWorldBlockLayer[] var20 = EnumWorldBlockLayer.values();
            int var21 = var20.length;

            for (int var22 = 0; var22 < var21; ++var22)
            {
                EnumWorldBlockLayer var23 = var20[var22];

                if (var5.isLayerStarted(var23))
                {
                    this.postRenderBlocks(var23, x, y, z, generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(var23), var5);
                }
            }
        }

        var5.setVisibility(var10.computeVisibility());
    }

    protected void finishCompileTask()
    {
        this.lockCompileTask.lock();

        try
        {
            if (this.compileTask != null && this.compileTask.getStatus() != ChunkCompileTaskGenerator.Status.DONE)
            {
                this.compileTask.finish();
                this.compileTask = null;
            }
        }
        finally
        {
            this.lockCompileTask.unlock();
        }
    }

    public ReentrantLock getLockCompileTask()
    {
        return this.lockCompileTask;
    }

    public ChunkCompileTaskGenerator makeCompileTaskChunk()
    {
        this.lockCompileTask.lock();
        ChunkCompileTaskGenerator var1;

        try
        {
            this.finishCompileTask();
            this.compileTask = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.REBUILD_CHUNK);
            var1 = this.compileTask;
        }
        finally
        {
            this.lockCompileTask.unlock();
        }

        return var1;
    }

    public ChunkCompileTaskGenerator makeCompileTaskTransparency()
    {
        this.lockCompileTask.lock();
        ChunkCompileTaskGenerator var1;

        try
        {
            if (this.compileTask == null || this.compileTask.getStatus() != ChunkCompileTaskGenerator.Status.PENDING)
            {
                if (this.compileTask != null && this.compileTask.getStatus() != ChunkCompileTaskGenerator.Status.DONE)
                {
                    this.compileTask.finish();
                    this.compileTask = null;
                }

                this.compileTask = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY);
                this.compileTask.setCompiledChunk(this.compiledChunk);
                var1 = this.compileTask;
                return var1;
            }

            var1 = null;
        }
        finally
        {
            this.lockCompileTask.unlock();
        }

        return var1;
    }

    private void preRenderBlocks(WorldRenderer worldRendererIn, BlockPos pos)
    {
        worldRendererIn.startDrawing(7);
        worldRendererIn.setVertexFormat(DefaultVertexFormats.BLOCK);
        worldRendererIn.setTranslation((double)(-pos.getX()), (double)(-pos.getY()), (double)(-pos.getZ()));
    }

    private void postRenderBlocks(EnumWorldBlockLayer layer, float x, float y, float z, WorldRenderer worldRendererIn, CompiledChunk compiledChunkIn)
    {
        if (layer == EnumWorldBlockLayer.TRANSLUCENT && !compiledChunkIn.isLayerEmpty(layer))
        {
            compiledChunkIn.setState(worldRendererIn.getVertexState(x, y, z));
        }

        worldRendererIn.finishDrawing();
    }

    private void initModelviewMatrix()
    {
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        float var1 = 1.000001F;
        GlStateManager.translate(-8.0F, -8.0F, -8.0F);
        GlStateManager.scale(var1, var1, var1);
        GlStateManager.translate(8.0F, 8.0F, 8.0F);
        GlStateManager.getFloat(2982, this.modelviewMatrix);
        GlStateManager.popMatrix();
    }

    public void multModelviewMatrix()
    {
        GlStateManager.multMatrix(this.modelviewMatrix);
    }

    public CompiledChunk getCompiledChunk()
    {
        return this.compiledChunk;
    }

    public void setCompiledChunk(CompiledChunk compiledChunkIn)
    {
        this.lockCompiledChunk.lock();

        try
        {
            this.compiledChunk = compiledChunkIn;
        }
        finally
        {
            this.lockCompiledChunk.unlock();
        }
    }

    public void stopCompileTask()
    {
        this.finishCompileTask();
        this.compiledChunk = CompiledChunk.DUMMY;
    }

    public void deleteGlResources()
    {
        this.stopCompileTask();
        this.world = null;

        for (int var1 = 0; var1 < EnumWorldBlockLayer.values().length; ++var1)
        {
            if (this.vertexBuffers[var1] != null)
            {
                this.vertexBuffers[var1].deleteGlBuffers();
            }
        }
    }

    public BlockPos getPosition()
    {
        return this.position;
    }

    public boolean isCompileTaskPending()
    {
        this.lockCompileTask.lock();
        boolean var1;

        try
        {
            var1 = this.compileTask == null || this.compileTask.getStatus() == ChunkCompileTaskGenerator.Status.PENDING;
        }
        finally
        {
            this.lockCompileTask.unlock();
        }

        return var1;
    }

    public void setNeedsUpdate(boolean needsUpdateIn)
    {
        this.needsUpdate = needsUpdateIn;
    }

    public boolean isNeedsUpdate()
    {
        return this.needsUpdate;
    }
}
