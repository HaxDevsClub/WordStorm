package net.minecraft.network.login.server;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.util.UUID;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;

public class S02PacketLoginSuccess implements Packet
{
    private GameProfile profile;
    private static final String __OBFID = "CL_00001375";

    public S02PacketLoginSuccess() {}

    public S02PacketLoginSuccess(GameProfile profileIn)
    {
        this.profile = profileIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        String var2 = buf.readStringFromBuffer(36);
        String var3 = buf.readStringFromBuffer(16);
        UUID var4 = UUID.fromString(var2);
        this.profile = new GameProfile(var4, var3);
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        UUID var2 = this.profile.getId();
        buf.writeString(var2 == null ? "" : var2.toString());
        buf.writeString(this.profile.getName());
    }

    public void processPacket(INetHandlerLoginClient handler)
    {
        handler.handleLoginSuccess(this);
    }

    public GameProfile getProfile()
    {
        return this.profile;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandler handler)
    {
        this.processPacket((INetHandlerLoginClient)handler);
    }
}