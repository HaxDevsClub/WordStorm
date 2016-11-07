package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;

public class TextureAtlasSprite
{
    private final String iconName;
    protected List framesTextureData = Lists.newArrayList();
    protected int[][] interpolatedFrameData;
    private AnimationMetadataSection animationMetadata;
    protected boolean rotated;
    protected int originX;
    protected int originY;
    protected int width;
    protected int height;
    private float minU;
    private float maxU;
    private float minV;
    private float maxV;
    protected int frameCounter;
    protected int tickCounter;
    private static String locationNameClock = "builtin/clock";
    private static String locationNameCompass = "builtin/compass";
    private static final String __OBFID = "CL_00001062";

    protected TextureAtlasSprite(String spriteName)
    {
        this.iconName = spriteName;
    }

    protected static TextureAtlasSprite makeAtlasSprite(ResourceLocation spriteResourceLocation)
    {
        String var1 = spriteResourceLocation.toString();
        return (TextureAtlasSprite)(locationNameClock.equals(var1) ? new TextureClock(var1) : (locationNameCompass.equals(var1) ? new TextureCompass(var1) : new TextureAtlasSprite(var1)));
    }

    public static void setLocationNameClock(String clockName)
    {
        locationNameClock = clockName;
    }

    public static void setLocationNameCompass(String compassName)
    {
        locationNameCompass = compassName;
    }

    public void initSprite(int inX, int inY, int originInX, int originInY, boolean rotatedIn)
    {
        this.originX = originInX;
        this.originY = originInY;
        this.rotated = rotatedIn;
        float var6 = (float)(0.009999999776482582D / (double)inX);
        float var7 = (float)(0.009999999776482582D / (double)inY);
        this.minU = (float)originInX / (float)((double)inX) + var6;
        this.maxU = (float)(originInX + this.width) / (float)((double)inX) - var6;
        this.minV = (float)originInY / (float)inY + var7;
        this.maxV = (float)(originInY + this.height) / (float)inY - var7;
    }

    public void copyFrom(TextureAtlasSprite atlasSpirit)
    {
        this.originX = atlasSpirit.originX;
        this.originY = atlasSpirit.originY;
        this.width = atlasSpirit.width;
        this.height = atlasSpirit.height;
        this.rotated = atlasSpirit.rotated;
        this.minU = atlasSpirit.minU;
        this.maxU = atlasSpirit.maxU;
        this.minV = atlasSpirit.minV;
        this.maxV = atlasSpirit.maxV;
    }

    /**
     * Returns the X position of this icon on its texture sheet, in pixels.
     */
    public int getOriginX()
    {
        return this.originX;
    }

    /**
     * Returns the Y position of this icon on its texture sheet, in pixels.
     */
    public int getOriginY()
    {
        return this.originY;
    }

    /**
     * Returns the width of the icon, in pixels.
     */
    public int getIconWidth()
    {
        return this.width;
    }

    /**
     * Returns the height of the icon, in pixels.
     */
    public int getIconHeight()
    {
        return this.height;
    }

    /**
     * Returns the minimum U coordinate to use when rendering with this icon.
     */
    public float getMinU()
    {
        return this.minU;
    }

    /**
     * Returns the maximum U coordinate to use when rendering with this icon.
     */
    public float getMaxU()
    {
        return this.maxU;
    }

    /**
     * Gets a U coordinate on the icon. 0 returns uMin and 16 returns uMax. Other arguments return in-between values.
     */
    public float getInterpolatedU(double u)
    {
        float var3 = this.maxU - this.minU;
        return this.minU + var3 * (float)u / 16.0F;
    }

    /**
     * Returns the minimum V coordinate to use when rendering with this icon.
     */
    public float getMinV()
    {
        return this.minV;
    }

    /**
     * Returns the maximum V coordinate to use when rendering with this icon.
     */
    public float getMaxV()
    {
        return this.maxV;
    }

    /**
     * Gets a V coordinate on the icon. 0 returns vMin and 16 returns vMax. Other arguments return in-between values.
     */
    public float getInterpolatedV(double v)
    {
        float var3 = this.maxV - this.minV;
        return this.minV + var3 * ((float)v / 16.0F);
    }

    public String getIconName()
    {
        return this.iconName;
    }

    public void updateAnimation()
    {
        ++this.tickCounter;

        if (this.tickCounter >= this.animationMetadata.getFrameTimeSingle(this.frameCounter))
        {
            int var1 = this.animationMetadata.getFrameIndex(this.frameCounter);
            int var2 = this.animationMetadata.getFrameCount() == 0 ? this.framesTextureData.size() : this.animationMetadata.getFrameCount();
            this.frameCounter = (this.frameCounter + 1) % var2;
            this.tickCounter = 0;
            int var3 = this.animationMetadata.getFrameIndex(this.frameCounter);

            if (var1 != var3 && var3 >= 0 && var3 < this.framesTextureData.size())
            {
                TextureUtil.uploadTextureMipmap((int[][])this.framesTextureData.get(var3), this.width, this.height, this.originX, this.originY, false, false);
            }
        }
        else if (this.animationMetadata.isInterpolate())
        {
            this.updateAnimationInterpolated();
        }
    }

    private void updateAnimationInterpolated()
    {
        double var1 = 1.0D - (double)this.tickCounter / (double)this.animationMetadata.getFrameTimeSingle(this.frameCounter);
        int var3 = this.animationMetadata.getFrameIndex(this.frameCounter);
        int var4 = this.animationMetadata.getFrameCount() == 0 ? this.framesTextureData.size() : this.animationMetadata.getFrameCount();
        int var5 = this.animationMetadata.getFrameIndex((this.frameCounter + 1) % var4);

        if (var3 != var5 && var5 >= 0 && var5 < this.framesTextureData.size())
        {
            int[][] var6 = (int[][])this.framesTextureData.get(var3);
            int[][] var7 = (int[][])this.framesTextureData.get(var5);

            if (this.interpolatedFrameData == null || this.interpolatedFrameData.length != var6.length)
            {
                this.interpolatedFrameData = new int[var6.length][];
            }

            for (int var8 = 0; var8 < var6.length; ++var8)
            {
                if (this.interpolatedFrameData[var8] == null)
                {
                    this.interpolatedFrameData[var8] = new int[var6[var8].length];
                }

                if (var8 < var7.length && var7[var8].length == var6[var8].length)
                {
                    for (int var9 = 0; var9 < var6[var8].length; ++var9)
                    {
                        int var10 = var6[var8][var9];
                        int var11 = var7[var8][var9];
                        int var12 = (int)((double)((var10 & 16711680) >> 16) * var1 + (double)((var11 & 16711680) >> 16) * (1.0D - var1));
                        int var13 = (int)((double)((var10 & 65280) >> 8) * var1 + (double)((var11 & 65280) >> 8) * (1.0D - var1));
                        int var14 = (int)((double)(var10 & 255) * var1 + (double)(var11 & 255) * (1.0D - var1));
                        this.interpolatedFrameData[var8][var9] = var10 & -16777216 | var12 << 16 | var13 << 8 | var14;
                    }
                }
            }

            TextureUtil.uploadTextureMipmap(this.interpolatedFrameData, this.width, this.height, this.originX, this.originY, false, false);
        }
    }

    public int[][] getFrameTextureData(int index)
    {
        return (int[][])this.framesTextureData.get(index);
    }

    public int getFrameCount()
    {
        return this.framesTextureData.size();
    }

    public void setIconWidth(int newWidth)
    {
        this.width = newWidth;
    }

    public void setIconHeight(int newHeight)
    {
        this.height = newHeight;
    }

    public void loadSprite(BufferedImage[] images, AnimationMetadataSection meta)
    {
        this.resetSprite();
        int var3 = images[0].getWidth();
        int var4 = images[0].getHeight();
        this.width = var3;
        this.height = var4;
        int[][] var5 = new int[images.length][];
        int var6;

        for (var6 = 0; var6 < images.length; ++var6)
        {
            BufferedImage var7 = images[var6];

            if (var7 != null)
            {
                if (var6 > 0 && (var7.getWidth() != var3 >> var6 || var7.getHeight() != var4 >> var6))
                {
                    throw new RuntimeException(String.format("Unable to load miplevel: %d, image is size: %dx%d, expected %dx%d", new Object[] {Integer.valueOf(var6), Integer.valueOf(var7.getWidth()), Integer.valueOf(var7.getHeight()), Integer.valueOf(var3 >> var6), Integer.valueOf(var4 >> var6)}));
                }

                var5[var6] = new int[var7.getWidth() * var7.getHeight()];
                var7.getRGB(0, 0, var7.getWidth(), var7.getHeight(), var5[var6], 0, var7.getWidth());
            }
        }

        if (meta == null)
        {
            if (var4 != var3)
            {
                throw new RuntimeException("broken aspect ratio and not an animation");
            }

            this.framesTextureData.add(var5);
        }
        else
        {
            var6 = var4 / var3;
            int var11 = var3;
            int var8 = var3;
            this.height = this.width;
            int var10;

            if (meta.getFrameCount() > 0)
            {
                Iterator var9 = meta.getFrameIndexSet().iterator();

                while (var9.hasNext())
                {
                    var10 = ((Integer)var9.next()).intValue();

                    if (var10 >= var6)
                    {
                        throw new RuntimeException("invalid frameindex " + var10);
                    }

                    this.allocateFrameTextureData(var10);
                    this.framesTextureData.set(var10, getFrameTextureData(var5, var11, var8, var10));
                }

                this.animationMetadata = meta;
            }
            else
            {
                ArrayList var12 = Lists.newArrayList();

                for (var10 = 0; var10 < var6; ++var10)
                {
                    this.framesTextureData.add(getFrameTextureData(var5, var11, var8, var10));
                    var12.add(new AnimationFrame(var10, -1));
                }

                this.animationMetadata = new AnimationMetadataSection(var12, this.width, this.height, meta.getFrameTime(), meta.isInterpolate());
            }
        }
    }

    public void generateMipmaps(int level)
    {
        ArrayList var2 = Lists.newArrayList();

        for (int var3 = 0; var3 < this.framesTextureData.size(); ++var3)
        {
            final int[][] var4 = (int[][])this.framesTextureData.get(var3);

            if (var4 != null)
            {
                try
                {
                    var2.add(TextureUtil.generateMipmapData(level, this.width, var4));
                }
                catch (Throwable var8)
                {
                    CrashReport var6 = CrashReport.makeCrashReport(var8, "Generating mipmaps for frame");
                    CrashReportCategory var7 = var6.makeCategory("Frame being iterated");
                    var7.addCrashSection("Frame index", Integer.valueOf(var3));
                    var7.addCrashSectionCallable("Frame sizes", new Callable()
                    {
                        private static final String __OBFID = "CL_00001063";
                        public String call()
                        {
                            StringBuilder var1 = new StringBuilder();
                            int[][] var2 = var4;
                            int var3 = var2.length;

                            for (int var4x = 0; var4x < var3; ++var4x)
                            {
                                int[] var5 = var2[var4x];

                                if (var1.length() > 0)
                                {
                                    var1.append(", ");
                                }

                                var1.append(var5 == null ? "null" : Integer.valueOf(var5.length));
                            }

                            return var1.toString();
                        }
                    });
                    throw new ReportedException(var6);
                }
            }
        }

        this.setFramesTextureData(var2);
    }

    private void allocateFrameTextureData(int index)
    {
        if (this.framesTextureData.size() <= index)
        {
            for (int var2 = this.framesTextureData.size(); var2 <= index; ++var2)
            {
                this.framesTextureData.add((Object)null);
            }
        }
    }

    private static int[][] getFrameTextureData(int[][] data, int rows, int columns, int p_147962_3_)
    {
        int[][] var4 = new int[data.length][];

        for (int var5 = 0; var5 < data.length; ++var5)
        {
            int[] var6 = data[var5];

            if (var6 != null)
            {
                var4[var5] = new int[(rows >> var5) * (columns >> var5)];
                System.arraycopy(var6, p_147962_3_ * var4[var5].length, var4[var5], 0, var4[var5].length);
            }
        }

        return var4;
    }

    public void clearFramesTextureData()
    {
        this.framesTextureData.clear();
    }

    public boolean hasAnimationMetadata()
    {
        return this.animationMetadata != null;
    }

    public void setFramesTextureData(List newFramesTextureData)
    {
        this.framesTextureData = newFramesTextureData;
    }

    private void resetSprite()
    {
        this.animationMetadata = null;
        this.setFramesTextureData(Lists.newArrayList());
        this.frameCounter = 0;
        this.tickCounter = 0;
    }

    public String toString()
    {
        return "TextureAtlasSprite{name=\'" + this.iconName + '\'' + ", frameCount=" + this.framesTextureData.size() + ", rotated=" + this.rotated + ", x=" + this.originX + ", y=" + this.originY + ", height=" + this.height + ", width=" + this.width + ", u0=" + this.minU + ", u1=" + this.maxU + ", v0=" + this.minV + ", v1=" + this.maxV + '}';
    }
}
