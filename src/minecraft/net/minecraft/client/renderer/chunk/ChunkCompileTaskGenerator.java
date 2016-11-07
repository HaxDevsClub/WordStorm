package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;

public class ChunkCompileTaskGenerator
{
    private final RenderChunk renderChunk;
    private final ReentrantLock lock = new ReentrantLock();
    private final List listFinishRunnables = Lists.newArrayList();
    private final ChunkCompileTaskGenerator.Type type;
    private RegionRenderCacheBuilder regionRenderCacheBuilder;
    private CompiledChunk compiledChunk;
    private ChunkCompileTaskGenerator.Status status;
    private boolean finished;
    private static final String __OBFID = "CL_00002466";

    public ChunkCompileTaskGenerator(RenderChunk renderChunkIn, ChunkCompileTaskGenerator.Type typeIn)
    {
        this.status = ChunkCompileTaskGenerator.Status.PENDING;
        this.renderChunk = renderChunkIn;
        this.type = typeIn;
    }

    public ChunkCompileTaskGenerator.Status getStatus()
    {
        return this.status;
    }

    public RenderChunk getRenderChunk()
    {
        return this.renderChunk;
    }

    public CompiledChunk getCompiledChunk()
    {
        return this.compiledChunk;
    }

    public void setCompiledChunk(CompiledChunk compiledChunkIn)
    {
        this.compiledChunk = compiledChunkIn;
    }

    public RegionRenderCacheBuilder getRegionRenderCacheBuilder()
    {
        return this.regionRenderCacheBuilder;
    }

    public void setRegionRenderCacheBuilder(RegionRenderCacheBuilder regionRenderCacheBuilderIn)
    {
        this.regionRenderCacheBuilder = regionRenderCacheBuilderIn;
    }

    public void setStatus(ChunkCompileTaskGenerator.Status statusIn)
    {
        this.lock.lock();

        try
        {
            this.status = statusIn;
        }
        finally
        {
            this.lock.unlock();
        }
    }

    public void finish()
    {
        this.lock.lock();

        try
        {
            this.finished = true;
            this.status = ChunkCompileTaskGenerator.Status.DONE;
            Iterator var1 = this.listFinishRunnables.iterator();

            while (var1.hasNext())
            {
                Runnable var2 = (Runnable)var1.next();
                var2.run();
            }
        }
        finally
        {
            this.lock.unlock();
        }
    }

    public void addFinishRunnable(Runnable p_178539_1_)
    {
        this.lock.lock();

        try
        {
            this.listFinishRunnables.add(p_178539_1_);

            if (this.finished)
            {
                p_178539_1_.run();
            }
        }
        finally
        {
            this.lock.unlock();
        }
    }

    public ReentrantLock getLock()
    {
        return this.lock;
    }

    public ChunkCompileTaskGenerator.Type getType()
    {
        return this.type;
    }

    public boolean isFinished()
    {
        return this.finished;
    }

    public static enum Status
    {
        PENDING("PENDING", 0),
        COMPILING("COMPILING", 1),
        UPLOADING("UPLOADING", 2),
        DONE("DONE", 3);

        private static final ChunkCompileTaskGenerator.Status[] $VALUES = new ChunkCompileTaskGenerator.Status[]{PENDING, COMPILING, UPLOADING, DONE};
        private static final String __OBFID = "CL_00002465";

        private Status(String p_i46207_1_, int p_i46207_2_) {}
    }

    public static enum Type
    {
        REBUILD_CHUNK("REBUILD_CHUNK", 0),
        RESORT_TRANSPARENCY("RESORT_TRANSPARENCY", 1);

        private static final ChunkCompileTaskGenerator.Type[] $VALUES = new ChunkCompileTaskGenerator.Type[]{REBUILD_CHUNK, RESORT_TRANSPARENCY};
        private static final String __OBFID = "CL_00002464";

        private Type(String p_i46206_1_, int p_i46206_2_) {}
    }
}
