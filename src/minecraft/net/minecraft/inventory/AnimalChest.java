package net.minecraft.inventory;

import net.minecraft.util.IChatComponent;

public class AnimalChest extends InventoryBasic
{
    private static final String __OBFID = "CL_00001731";

    public AnimalChest(String inventoryName, int slotCount)
    {
        super(inventoryName, false, slotCount);
    }

    public AnimalChest(IChatComponent invTitle, int slotCount)
    {
        super(invTitle, slotCount);
    }
}
