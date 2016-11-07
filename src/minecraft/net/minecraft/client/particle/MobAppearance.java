package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MobAppearance extends EntityFX
{
    private EntityLivingBase entity;
    private static final String __OBFID = "CL_00002594";

    protected MobAppearance(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn)
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        this.motionX = this.motionY = this.motionZ = 0.0D;
        this.particleGravity = 0.0F;
        this.particleMaxAge = 30;
    }

    public int getFXLayer()
    {
        return 3;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (this.entity == null)
        {
            EntityGuardian var1 = new EntityGuardian(this.worldObj);
            var1.setElder();
            this.entity = var1;
        }
    }

    /**
     * Renders the particle
     *  
     * @param worldRendererIn The WorldRenderer instance
     */
    public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_)
    {
        if (this.entity != null)
        {
            RenderManager var9 = Minecraft.getMinecraft().getRenderManager();
            var9.setRenderPosition(EntityFX.interpPosX, EntityFX.interpPosY, EntityFX.interpPosZ);
            float var10 = 0.42553192F;
            float var11 = ((float)this.particleAge + partialTicks) / (float)this.particleMaxAge;
            GlStateManager.depthMask(true);
            GlStateManager.enableBlend();
            GlStateManager.enableDepth();
            GlStateManager.blendFunc(770, 771);
            float var12 = 240.0F;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var12, var12);
            GlStateManager.pushMatrix();
            float var13 = 0.05F + 0.5F * MathHelper.sin(var11 * (float)Math.PI);
            GlStateManager.color(1.0F, 1.0F, 1.0F, var13);
            GlStateManager.translate(0.0F, 1.8F, 0.0F);
            GlStateManager.rotate(180.0F - entityIn.rotationYaw, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(60.0F - 150.0F * var11 - entityIn.rotationPitch, 1.0F, 0.0F, 0.0F);
            GlStateManager.translate(0.0F, -0.4F, -1.5F);
            GlStateManager.scale(var10, var10, var10);
            this.entity.rotationYaw = this.entity.prevRotationYaw = 0.0F;
            this.entity.rotationYawHead = this.entity.prevRotationYawHead = 0.0F;
            var9.renderEntityWithPosYaw(this.entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks);
            GlStateManager.popMatrix();
            GlStateManager.enableDepth();
        }
    }

    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID = "CL_00002593";

        public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int ... p_178902_15_)
        {
            return new MobAppearance(worldIn, xCoordIn, yCoordIn, zCoordIn);
        }
    }
}
