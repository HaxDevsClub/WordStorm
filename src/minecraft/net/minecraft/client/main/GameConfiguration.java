package net.minecraft.client.main;

import com.mojang.authlib.properties.PropertyMap;
import java.io.File;
import java.net.Proxy;
import net.minecraft.util.Session;

public class GameConfiguration
{
    public final GameConfiguration.UserInformation userInfo;
    public final GameConfiguration.DisplayInformation displayInfo;
    public final GameConfiguration.FolderInformation folderInfo;
    public final GameConfiguration.GameInformation gameInfo;
    public final GameConfiguration.ServerInformation serverInfo;
    private static final String __OBFID = "CL_00001918";

    public GameConfiguration(GameConfiguration.UserInformation userInfoIn, GameConfiguration.DisplayInformation displayInfoIn, GameConfiguration.FolderInformation folderInfoIn, GameConfiguration.GameInformation gameInfoIn, GameConfiguration.ServerInformation serverInfoIn)
    {
        this.userInfo = userInfoIn;
        this.displayInfo = displayInfoIn;
        this.folderInfo = folderInfoIn;
        this.gameInfo = gameInfoIn;
        this.serverInfo = serverInfoIn;
    }

    public static class DisplayInformation
    {
        public final int width;
        public final int height;
        public final boolean fullscreen;
        public final boolean checkGlErrors;
        private static final String __OBFID = "CL_00001917";

        public DisplayInformation(int widthIn, int heightIn, boolean fullscreenIn, boolean checkGlErrorsIn)
        {
            this.width = widthIn;
            this.height = heightIn;
            this.fullscreen = fullscreenIn;
            this.checkGlErrors = checkGlErrorsIn;
        }
    }

    public static class FolderInformation
    {
        public final File mcDataDir;
        public final File resourcePacksDir;
        public final File assetsDir;
        public final String assetIndex;
        private static final String __OBFID = "CL_00001916";

        public FolderInformation(File mcDataDirIn, File resourcePacksDirIn, File assetsDirIn, String assetIndexIn)
        {
            this.mcDataDir = mcDataDirIn;
            this.resourcePacksDir = resourcePacksDirIn;
            this.assetsDir = assetsDirIn;
            this.assetIndex = assetIndexIn;
        }
    }

    public static class GameInformation
    {
        public final boolean isDemo;
        public final String version;
        private static final String __OBFID = "CL_00001915";

        public GameInformation(boolean isDemoIn, String versionIn)
        {
            this.isDemo = isDemoIn;
            this.version = versionIn;
        }
    }

    public static class ServerInformation
    {
        public final String serverName;
        public final int serverPort;
        private static final String __OBFID = "CL_00001914";

        public ServerInformation(String serverNameIn, int serverPortIn)
        {
            this.serverName = serverNameIn;
            this.serverPort = serverPortIn;
        }
    }

    public static class UserInformation
    {
        public final Session session;
        public final PropertyMap userProperties;
        public final Proxy proxy;
        private static final String __OBFID = "CL_00001913";

        public UserInformation(Session sessionIn, PropertyMap userPropertiesIn, Proxy proxyIn)
        {
            this.session = sessionIn;
            this.userProperties = userPropertiesIn;
            this.proxy = proxyIn;
        }
    }
}
