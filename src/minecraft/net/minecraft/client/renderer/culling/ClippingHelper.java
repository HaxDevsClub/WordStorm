package net.minecraft.client.renderer.culling;

public class ClippingHelper
{
    public float[][] frustum = new float[6][4];
    public float[] projectionMatrix = new float[16];
    public float[] modelviewMatrix = new float[16];
    public float[] clippingMatrix = new float[16];
    private static final String __OBFID = "CL_00000977";

    private double dot(float[] p_178624_1_, double p_178624_2_, double p_178624_4_, double p_178624_6_)
    {
        return (double)p_178624_1_[0] * p_178624_2_ + (double)p_178624_1_[1] * p_178624_4_ + (double)p_178624_1_[2] * p_178624_6_ + (double)p_178624_1_[3];
    }

    /**
     * Returns true if the box is inside all 6 clipping planes, otherwise returns false.
     */
    public boolean isBoxInFrustum(double p_78553_1_, double p_78553_3_, double p_78553_5_, double p_78553_7_, double p_78553_9_, double p_78553_11_)
    {
        for (int var13 = 0; var13 < 6; ++var13)
        {
            float[] var14 = this.frustum[var13];

            if (this.dot(var14, p_78553_1_, p_78553_3_, p_78553_5_) <= 0.0D && this.dot(var14, p_78553_7_, p_78553_3_, p_78553_5_) <= 0.0D && this.dot(var14, p_78553_1_, p_78553_9_, p_78553_5_) <= 0.0D && this.dot(var14, p_78553_7_, p_78553_9_, p_78553_5_) <= 0.0D && this.dot(var14, p_78553_1_, p_78553_3_, p_78553_11_) <= 0.0D && this.dot(var14, p_78553_7_, p_78553_3_, p_78553_11_) <= 0.0D && this.dot(var14, p_78553_1_, p_78553_9_, p_78553_11_) <= 0.0D && this.dot(var14, p_78553_7_, p_78553_9_, p_78553_11_) <= 0.0D)
            {
                return false;
            }
        }

        return true;
    }
}
