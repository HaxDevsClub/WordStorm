package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import org.lwjgl.opengl.GL11;

public class WorldVertexBufferUploader
{
    private static final String __OBFID = "CL_00002567";

    public int draw(WorldRenderer p_178177_1_, int p_178177_2_)
    {
        if (p_178177_2_ > 0)
        {
            VertexFormat var3 = p_178177_1_.getVertexFormat();
            int var4 = var3.getNextOffset();
            ByteBuffer var5 = p_178177_1_.getByteBuffer();
            List var6 = var3.getElements();
            Iterator var7 = var6.iterator();
            VertexFormatElement var8;
            VertexFormatElement.EnumUsage var9;
            int var10;

            while (var7.hasNext())
            {
                var8 = (VertexFormatElement)var7.next();
                var9 = var8.getUsage();
                var10 = var8.getType().getGlConstant();
                int var11 = var8.getIndex();

                switch (WorldVertexBufferUploader.SwitchEnumUsage.VALUES[var9.ordinal()])
                {
                    case 1:
                        var5.position(var8.getOffset());
                        GL11.glVertexPointer(var8.getElementCount(), var10, var4, var5);
                        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
                        break;

                    case 2:
                        var5.position(var8.getOffset());
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + var11);
                        GL11.glTexCoordPointer(var8.getElementCount(), var10, var4, var5);
                        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                        break;

                    case 3:
                        var5.position(var8.getOffset());
                        GL11.glColorPointer(var8.getElementCount(), var10, var4, var5);
                        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
                        break;

                    case 4:
                        var5.position(var8.getOffset());
                        GL11.glNormalPointer(var10, var4, var5);
                        GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
                }
            }

            GL11.glDrawArrays(p_178177_1_.getDrawMode(), 0, p_178177_1_.getVertexCount());
            var7 = var6.iterator();

            while (var7.hasNext())
            {
                var8 = (VertexFormatElement)var7.next();
                var9 = var8.getUsage();
                var10 = var8.getIndex();

                switch (WorldVertexBufferUploader.SwitchEnumUsage.VALUES[var9.ordinal()])
                {
                    case 1:
                        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
                        break;

                    case 2:
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + var10);
                        GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                        break;

                    case 3:
                        GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
                        GlStateManager.resetColor();
                        break;

                    case 4:
                        GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
                }
            }
        }

        p_178177_1_.reset();
        return p_178177_2_;
    }

    static final class SwitchEnumUsage
    {
        static final int[] VALUES = new int[VertexFormatElement.EnumUsage.values().length];
        private static final String __OBFID = "CL_00002566";

        static
        {
            try
            {
                VALUES[VertexFormatElement.EnumUsage.POSITION.ordinal()] = 1;
            }
            catch (NoSuchFieldError var4)
            {
                ;
            }

            try
            {
                VALUES[VertexFormatElement.EnumUsage.UV.ordinal()] = 2;
            }
            catch (NoSuchFieldError var3)
            {
                ;
            }

            try
            {
                VALUES[VertexFormatElement.EnumUsage.COLOR.ordinal()] = 3;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                VALUES[VertexFormatElement.EnumUsage.NORMAL.ordinal()] = 4;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }
        }
    }
}
