package net.minecraft.client.resources;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import net.minecraft.util.ResourceLocation;

public interface IResourceManager
{
    Set getResourceDomains();

    IResource getResource(ResourceLocation location) throws IOException;

    List getAllResources(ResourceLocation location) throws IOException;
}
