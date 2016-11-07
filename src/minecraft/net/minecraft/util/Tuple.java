package net.minecraft.util;

public class Tuple
{
    private Object a;
    private Object b;
    private static final String __OBFID = "CL_00001502";

    public Tuple(Object aIn, Object bIn)
    {
        this.a = aIn;
        this.b = bIn;
    }

    /**
     * Get the first Object in the Tuple
     */
    public Object getFirst()
    {
        return this.a;
    }

    /**
     * Get the second Object in the Tuple
     */
    public Object getSecond()
    {
        return this.b;
    }
}
