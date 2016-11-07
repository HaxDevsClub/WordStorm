package net.minecraft.client.resources;

import java.io.InputStream;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;

public interface IResource
{
    ResourceLocation getResourceLocation();

    InputStream getInputStream();

    boolean hasMetadata();

    IMetadataSection getMetadata(String p_110526_1_);

    String getResourcePackName();
}