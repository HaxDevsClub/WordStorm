package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureManager implements ITickable, IResourceManagerReloadListener
{
    private static final Logger logger = LogManager.getLogger();
    private final Map mapTextureObjects = Maps.newHashMap();
    private final List listTickables = Lists.newArrayList();
    private final Map mapTextureCounters = Maps.newHashMap();
    private IResourceManager theResourceManager;
    private static final String __OBFID = "CL_00001064";

    public TextureManager(IResourceManager resourceManager)
    {
        this.theResourceManager = resourceManager;
    }

    public void bindTexture(ResourceLocation resource)
    {
        Object var2 = (ITextureObject)this.mapTextureObjects.get(resource);

        if (var2 == null)
        {
            var2 = new SimpleTexture(resource);
            this.loadTexture(resource, (ITextureObject)var2);
        }

        TextureUtil.bindTexture(((ITextureObject)var2).getGlTextureId());
    }

    public boolean loadTickableTexture(ResourceLocation textureLocation, ITickableTextureObject textureObj)
    {
        if (this.loadTexture(textureLocation, textureObj))
        {
            this.listTickables.add(textureObj);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean loadTexture(ResourceLocation textureLocation, final ITextureObject textureObj)
    {
        boolean var3 = true;
        ITextureObject textureObj2 = textureObj;

        try
        {
            ((ITextureObject)textureObj).loadTexture(this.theResourceManager);
        }
        catch (IOException var8)
        {
            logger.warn("Failed to load texture: " + textureLocation, var8);
            textureObj2 = TextureUtil.missingTexture;
            this.mapTextureObjects.put(textureLocation, textureObj2);
            var3 = false;
        }
        catch (Throwable var9)
        {
            CrashReport var5 = CrashReport.makeCrashReport(var9, "Registering texture");
            CrashReportCategory var6 = var5.makeCategory("Resource location being registered");
            var6.addCrashSection("Resource location", textureLocation);
            var6.addCrashSectionCallable("Texture object class", new Callable()
            {
                private static final String __OBFID = "CL_00001065";
                public String call()
                {
                    return textureObj.getClass().getName();
                }
            });
            throw new ReportedException(var5);
        }

        this.mapTextureObjects.put(textureLocation, textureObj2);
        return var3;
    }

    public ITextureObject getTexture(ResourceLocation textureLocation)
    {
        return (ITextureObject)this.mapTextureObjects.get(textureLocation);
    }

    public ResourceLocation getDynamicTextureLocation(String name, DynamicTexture texture)
    {
        Integer var3 = (Integer)this.mapTextureCounters.get(name);

        if (var3 == null)
        {
            var3 = Integer.valueOf(1);
        }
        else
        {
            var3 = Integer.valueOf(var3.intValue() + 1);
        }

        this.mapTextureCounters.put(name, var3);
        ResourceLocation var4 = new ResourceLocation(String.format("dynamic/%s_%d", new Object[] {name, var3}));
        this.loadTexture(var4, texture);
        return var4;
    }

    public void tick()
    {
        Iterator var1 = this.listTickables.iterator();

        while (var1.hasNext())
        {
            ITickable var2 = (ITickable)var1.next();
            var2.tick();
        }
    }

    public void deleteTexture(ResourceLocation textureLocation)
    {
        ITextureObject var2 = this.getTexture(textureLocation);

        if (var2 != null)
        {
            TextureUtil.deleteTexture(var2.getGlTextureId());
        }
    }

    public void onResourceManagerReload(IResourceManager resourceManager)
    {
        Iterator var2 = this.mapTextureObjects.entrySet().iterator();

        while (var2.hasNext())
        {
            Entry var3 = (Entry)var2.next();
            this.loadTexture((ResourceLocation)var3.getKey(), (ITextureObject)var3.getValue());
        }
    }
}
