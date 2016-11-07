package net.minecraft.block.material;

public class MaterialLogic extends Material
{
    private static final String __OBFID = "CL_00000539";

    public MaterialLogic(MapColor color)
    {
        super(color);
        this.setAdventureModeExempt();
    }

    /**
     * Returns true if the block is a considered solid. This is true by default.
     */
    public boolean isSolid()
    {
        return false;
    }

    /**
     * Will prevent grass from growing on dirt underneath and kill any grass below it if it returns true
     */
    public boolean blocksLight()
    {
        return false;
    }

    /**
     * Returns if this material is considered solid or not
     */
    public boolean blocksMovement()
    {
        return false;
    }
}
