package net.minecraft.util;

public interface IRegistry extends Iterable
{
    Object getObject(Object name);

    /**
     * Register an object on this registry.
     */
    void putObject(Object p_82595_1_, Object p_82595_2_);
}
