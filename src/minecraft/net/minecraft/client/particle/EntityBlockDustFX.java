package net.minecraft.client.particle;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class EntityBlockDustFX extends EntityDiggingFX
{
    private static final String __OBFID = "CL_00000931";

    protected EntityBlockDustFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, IBlockState state)
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, state);
        this.motionX = xSpeedIn;
        this.motionY = ySpeedIn;
        this.motionZ = zSpeedIn;
    }

    public static class Factory implements IParticleFactory
    {
        private static final String __OBFID = "CL_00002576";

        public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int ... p_178902_15_)
        {
            IBlockState var16 = Block.getStateById(p_178902_15_[0]);
            return var16.getBlock().getRenderType() == -1 ? null : (new EntityBlockDustFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, var16)).func_174845_l();
        }
    }
}
