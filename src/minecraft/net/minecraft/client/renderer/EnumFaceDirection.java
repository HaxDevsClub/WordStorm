package net.minecraft.client.renderer;

import net.minecraft.util.EnumFacing;

public enum EnumFaceDirection
{
    DOWN(new EnumFaceDirection.VertexInformation[]{new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.WEST_INDEX, EnumFaceDirection.Constants.DOWN_INDEX, EnumFaceDirection.Constants.SOUTH_INDEX, null), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.WEST_INDEX, EnumFaceDirection.Constants.DOWN_INDEX, EnumFaceDirection.Constants.NORTH_INDEX, null), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.EAST_INDEX, EnumFaceDirection.Constants.DOWN_INDEX, EnumFaceDirection.Constants.NORTH_INDEX, null), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.EAST_INDEX, EnumFaceDirection.Constants.DOWN_INDEX, EnumFaceDirection.Constants.SOUTH_INDEX, null)}),
    UP(new EnumFaceDirection.VertexInformation[]{new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.WEST_INDEX, EnumFaceDirection.Constants.UP_INDEX, EnumFaceDirection.Constants.NORTH_INDEX, null), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.WEST_INDEX, EnumFaceDirection.Constants.UP_INDEX, EnumFaceDirection.Constants.SOUTH_INDEX, null), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.EAST_INDEX, EnumFaceDirection.Constants.UP_INDEX, EnumFaceDirection.Constants.SOUTH_INDEX, null), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.EAST_INDEX, EnumFaceDirection.Constants.UP_INDEX, EnumFaceDirection.Constants.NORTH_INDEX, null)}),
    NORTH(new EnumFaceDirection.VertexInformation[]{new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.EAST_INDEX, EnumFaceDirection.Constants.UP_INDEX, EnumFaceDirection.Constants.NORTH_INDEX, null), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.EAST_INDEX, EnumFaceDirection.Constants.DOWN_INDEX, EnumFaceDirection.Constants.NORTH_INDEX, null), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.WEST_INDEX, EnumFaceDirection.Constants.DOWN_INDEX, EnumFaceDirection.Constants.NORTH_INDEX, null), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.WEST_INDEX, EnumFaceDirection.Constants.UP_INDEX, EnumFaceDirection.Constants.NORTH_INDEX, null)}),
    SOUTH(new EnumFaceDirection.VertexInformation[]{new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.WEST_INDEX, EnumFaceDirection.Constants.UP_INDEX, EnumFaceDirection.Constants.SOUTH_INDEX, null), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.WEST_INDEX, EnumFaceDirection.Constants.DOWN_INDEX, EnumFaceDirection.Constants.SOUTH_INDEX, null), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.EAST_INDEX, EnumFaceDirection.Constants.DOWN_INDEX, EnumFaceDirection.Constants.SOUTH_INDEX, null), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.EAST_INDEX, EnumFaceDirection.Constants.UP_INDEX, EnumFaceDirection.Constants.SOUTH_INDEX, null)}),
    WEST(new EnumFaceDirection.VertexInformation[]{new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.WEST_INDEX, EnumFaceDirection.Constants.UP_INDEX, EnumFaceDirection.Constants.NORTH_INDEX, null), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.WEST_INDEX, EnumFaceDirection.Constants.DOWN_INDEX, EnumFaceDirection.Constants.NORTH_INDEX, null), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.WEST_INDEX, EnumFaceDirection.Constants.DOWN_INDEX, EnumFaceDirection.Constants.SOUTH_INDEX, null), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.WEST_INDEX, EnumFaceDirection.Constants.UP_INDEX, EnumFaceDirection.Constants.SOUTH_INDEX, null)}),
    EAST(new EnumFaceDirection.VertexInformation[]{new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.EAST_INDEX, EnumFaceDirection.Constants.UP_INDEX, EnumFaceDirection.Constants.SOUTH_INDEX, null), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.EAST_INDEX, EnumFaceDirection.Constants.DOWN_INDEX, EnumFaceDirection.Constants.SOUTH_INDEX, null), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.EAST_INDEX, EnumFaceDirection.Constants.DOWN_INDEX, EnumFaceDirection.Constants.NORTH_INDEX, null), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.EAST_INDEX, EnumFaceDirection.Constants.UP_INDEX, EnumFaceDirection.Constants.NORTH_INDEX, null)});
    private static final EnumFaceDirection[] facings = new EnumFaceDirection[6];
    private final EnumFaceDirection.VertexInformation[] vertexInfos;
    private static final String __OBFID = "CL_00002562";

    public static EnumFaceDirection getFacing(EnumFacing facing)
    {
        return facings[facing.getIndex()];
    }

    private EnumFaceDirection(EnumFaceDirection.VertexInformation ... vertexInfosIn)
    {
        this.vertexInfos = vertexInfosIn;
    }

    public EnumFaceDirection.VertexInformation func_179025_a(int p_179025_1_)
    {
        return this.vertexInfos[p_179025_1_];
    }

    static {
        facings[EnumFaceDirection.Constants.DOWN_INDEX] = DOWN;
        facings[EnumFaceDirection.Constants.UP_INDEX] = UP;
        facings[EnumFaceDirection.Constants.NORTH_INDEX] = NORTH;
        facings[EnumFaceDirection.Constants.SOUTH_INDEX] = SOUTH;
        facings[EnumFaceDirection.Constants.WEST_INDEX] = WEST;
        facings[EnumFaceDirection.Constants.EAST_INDEX] = EAST;
    }

    public static final class Constants {
        public static final int SOUTH_INDEX = EnumFacing.SOUTH.getIndex();
        public static final int UP_INDEX = EnumFacing.UP.getIndex();
        public static final int EAST_INDEX = EnumFacing.EAST.getIndex();
        public static final int NORTH_INDEX = EnumFacing.NORTH.getIndex();
        public static final int DOWN_INDEX = EnumFacing.DOWN.getIndex();
        public static final int WEST_INDEX = EnumFacing.WEST.getIndex();
        private static final String __OBFID = "CL_00002560";
    }

    public static class VertexInformation {
        public final int field_179184_a;
        public final int field_179182_b;
        public final int field_179183_c;
        private static final String __OBFID = "CL_00002559";

        private VertexInformation(int p_i46270_1_, int p_i46270_2_, int p_i46270_3_)
        {
            this.field_179184_a = p_i46270_1_;
            this.field_179182_b = p_i46270_2_;
            this.field_179183_c = p_i46270_3_;
        }

        VertexInformation(int p_i46271_1_, int p_i46271_2_, int p_i46271_3_, Object p_i46271_4_)
        {
            this(p_i46271_1_, p_i46271_2_, p_i46271_3_);
        }
    }
}
