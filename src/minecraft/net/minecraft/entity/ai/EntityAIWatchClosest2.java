package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityAIWatchClosest2 extends EntityAIWatchClosest
{
    private static final String __OBFID = "CL_00001590";

    public EntityAIWatchClosest2(EntityLiving entitylivingIn, Class watchTargetClass, float maxDistance, float chanceIn)
    {
        super(entitylivingIn, watchTargetClass, maxDistance, chanceIn);
        this.setMutexBits(3);
    }
}
