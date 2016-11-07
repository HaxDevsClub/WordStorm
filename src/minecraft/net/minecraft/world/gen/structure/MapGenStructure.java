package net.minecraft.world.gen.structure;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

public abstract class MapGenStructure extends MapGenBase
{
    private MapGenStructureData structureData;

    /**
     * Used to store a list of all structures that have been recursively generated. Used so that during recursive
     * generation, the structure generator can avoid generating structures that intersect ones that have already been
     * placed.
     */
    protected Map structureMap = Maps.newHashMap();
    private static final String __OBFID = "CL_00000505";

    public abstract String getStructureName();

    /**
     * Recursively called by generate()
     */
    protected final void recursiveGenerate(World worldIn, final int chunkX, final int chunkZ, int p_180701_4_, int p_180701_5_, ChunkPrimer chunkPrimerIn)
    {
        this.func_143027_a(worldIn);

        if (!this.structureMap.containsKey(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ))))
        {
            this.rand.nextInt();

            try
            {
                if (this.canSpawnStructureAtCoords(chunkX, chunkZ))
                {
                    StructureStart var7 = this.getStructureStart(chunkX, chunkZ);
                    this.structureMap.put(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ)), var7);
                    this.func_143026_a(chunkX, chunkZ, var7);
                }
            }
            catch (Throwable var10)
            {
                CrashReport var8 = CrashReport.makeCrashReport(var10, "Exception preparing structure feature");
                CrashReportCategory var9 = var8.makeCategory("Feature being prepared");
                var9.addCrashSectionCallable("Is feature chunk", new Callable()
                {
                    private static final String __OBFID = "CL_00000506";
                    public String call()
                    {
                        return MapGenStructure.this.canSpawnStructureAtCoords(chunkX, chunkZ) ? "True" : "False";
                    }
                });
                var9.addCrashSection("Chunk location", String.format("%d,%d", new Object[] {Integer.valueOf(chunkX), Integer.valueOf(chunkZ)}));
                var9.addCrashSectionCallable("Chunk pos hash", new Callable()
                {
                    private static final String __OBFID = "CL_00000507";
                    public String call()
                    {
                        return String.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ));
                    }
                });
                var9.addCrashSectionCallable("Structure type", new Callable()
                {
                    private static final String __OBFID = "CL_00000508";
                    public String call()
                    {
                        return MapGenStructure.this.getClass().getCanonicalName();
                    }
                });
                throw new ReportedException(var8);
            }
        }
    }

    public boolean generateStructure(World worldIn, Random randomIn, ChunkCoordIntPair chunkCoord)
    {
        this.func_143027_a(worldIn);
        int var4 = (chunkCoord.chunkXPos << 4) + 8;
        int var5 = (chunkCoord.chunkZPos << 4) + 8;
        boolean var6 = false;
        Iterator var7 = this.structureMap.values().iterator();

        while (var7.hasNext())
        {
            StructureStart var8 = (StructureStart)var7.next();

            if (var8.isSizeableStructure() && var8.func_175788_a(chunkCoord) && var8.getBoundingBox().intersectsWith(var4, var5, var4 + 15, var5 + 15))
            {
                var8.generateStructure(worldIn, randomIn, new StructureBoundingBox(var4, var5, var4 + 15, var5 + 15));
                var8.func_175787_b(chunkCoord);
                var6 = true;
                this.func_143026_a(var8.getChunkPosX(), var8.getChunkPosZ(), var8);
            }
        }

        return var6;
    }

    public boolean func_175795_b(BlockPos pos)
    {
        this.func_143027_a(this.worldObj);
        return this.func_175797_c(pos) != null;
    }

    protected StructureStart func_175797_c(BlockPos pos)
    {
        Iterator var2 = this.structureMap.values().iterator();

        while (var2.hasNext())
        {
            StructureStart var3 = (StructureStart)var2.next();

            if (var3.isSizeableStructure() && var3.getBoundingBox().isVecInside(pos))
            {
                Iterator var4 = var3.getComponents().iterator();

                while (var4.hasNext())
                {
                    StructureComponent var5 = (StructureComponent)var4.next();

                    if (var5.getBoundingBox().isVecInside(pos))
                    {
                        return var3;
                    }
                }
            }
        }

        return null;
    }

    public boolean func_175796_a(World worldIn, BlockPos pos)
    {
        this.func_143027_a(worldIn);
        Iterator var3 = this.structureMap.values().iterator();
        StructureStart var4;

        do
        {
            if (!var3.hasNext())
            {
                return false;
            }

            var4 = (StructureStart)var3.next();
        }
        while (!var4.isSizeableStructure() || !var4.getBoundingBox().isVecInside(pos));

        return true;
    }

    public BlockPos getClosestStrongholdPos(World worldIn, BlockPos pos)
    {
        this.worldObj = worldIn;
        this.func_143027_a(worldIn);
        this.rand.setSeed(worldIn.getSeed());
        long var3 = this.rand.nextLong();
        long var5 = this.rand.nextLong();
        long var7 = (long)(pos.getX() >> 4) * var3;
        long var9 = (long)(pos.getZ() >> 4) * var5;
        this.rand.setSeed(var7 ^ var9 ^ worldIn.getSeed());
        this.recursiveGenerate(worldIn, pos.getX() >> 4, pos.getZ() >> 4, 0, 0, (ChunkPrimer)null);
        double var11 = Double.MAX_VALUE;
        BlockPos var13 = null;
        Iterator var14 = this.structureMap.values().iterator();
        BlockPos var17;
        double var18;

        while (var14.hasNext())
        {
            StructureStart var15 = (StructureStart)var14.next();

            if (var15.isSizeableStructure())
            {
                StructureComponent var16 = (StructureComponent)var15.getComponents().get(0);
                var17 = var16.getBoundingBoxCenter();
                var18 = var17.distanceSq(pos);

                if (var18 < var11)
                {
                    var11 = var18;
                    var13 = var17;
                }
            }
        }

        if (var13 != null)
        {
            return var13;
        }
        else
        {
            List var20 = this.getCoordList();

            if (var20 != null)
            {
                BlockPos var21 = null;
                Iterator var22 = var20.iterator();

                while (var22.hasNext())
                {
                    var17 = (BlockPos)var22.next();
                    var18 = var17.distanceSq(pos);

                    if (var18 < var11)
                    {
                        var11 = var18;
                        var21 = var17;
                    }
                }

                return var21;
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * Returns a list of other locations at which the structure generation has been run, or null if not relevant to this
     * structure generator.
     */
    protected List getCoordList()
    {
        return null;
    }

    private void func_143027_a(World worldIn)
    {
        if (this.structureData == null)
        {
            this.structureData = (MapGenStructureData)worldIn.loadItemData(MapGenStructureData.class, this.getStructureName());

            if (this.structureData == null)
            {
                this.structureData = new MapGenStructureData(this.getStructureName());
                worldIn.setItemData(this.getStructureName(), this.structureData);
            }
            else
            {
                NBTTagCompound var2 = this.structureData.getTagCompound();
                Iterator var3 = var2.getKeySet().iterator();

                while (var3.hasNext())
                {
                    String var4 = (String)var3.next();
                    NBTBase var5 = var2.getTag(var4);

                    if (var5.getId() == 10)
                    {
                        NBTTagCompound var6 = (NBTTagCompound)var5;

                        if (var6.hasKey("ChunkX") && var6.hasKey("ChunkZ"))
                        {
                            int var7 = var6.getInteger("ChunkX");
                            int var8 = var6.getInteger("ChunkZ");
                            StructureStart var9 = MapGenStructureIO.getStructureStart(var6, worldIn);

                            if (var9 != null)
                            {
                                this.structureMap.put(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(var7, var8)), var9);
                            }
                        }
                    }
                }
            }
        }
    }

    private void func_143026_a(int p_143026_1_, int p_143026_2_, StructureStart start)
    {
        this.structureData.writeInstance(start.writeStructureComponentsToNBT(p_143026_1_, p_143026_2_), p_143026_1_, p_143026_2_);
        this.structureData.markDirty();
    }

    protected abstract boolean canSpawnStructureAtCoords(int chunkX, int chunkZ);

    protected abstract StructureStart getStructureStart(int chunkX, int chunkZ);
}
