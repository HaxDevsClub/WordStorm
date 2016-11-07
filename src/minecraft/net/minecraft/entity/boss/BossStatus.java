package net.minecraft.entity.boss;

public final class BossStatus
{
    public static float healthScale;
    public static int statusBarTime;
    public static String bossName;
    public static boolean hasColorModifier;
    private static final String __OBFID = "CL_00000941";

    public static void setBossStatus(IBossDisplayData displayData, boolean hasColorModifierIn)
    {
        healthScale = displayData.getHealth() / displayData.getMaxHealth();
        statusBarTime = 100;
        bossName = displayData.getDisplayName().getFormattedText();
        hasColorModifier = hasColorModifierIn;
    }
}
