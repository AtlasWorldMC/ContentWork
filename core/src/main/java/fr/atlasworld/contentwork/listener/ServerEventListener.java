package fr.atlasworld.contentwork.listener;

import fr.atlasworld.contentwork.ContentWork;
import fr.atlasworld.contentwork.command.GiveCommand;
import fr.atlasworld.contentwork.config.Config;
import fr.atlasworld.contentwork.data.generator.DataManager;
import fr.atlasworld.contentwork.file.FileManager;
import fr.atlasworld.contentwork.registering.DefaultRegistries;
import fr.atlasworld.contentwork.web.server.WebServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public class ServerEventListener implements Listener {
    @EventHandler
    public void onServerInitialize(ServerLoadEvent event) {
        if (event.getType() == ServerLoadEvent.LoadType.RELOAD) {
            ContentWork.logger.warn("ContentWork cannot reload! Please restart the server to apply changes!");
            return;
        }

        Config config = Config.getConfig();

        //Trigger registering event
        DefaultRegistries.register(Bukkit.getPluginManager());

        //Register command
        CraftServer server = (CraftServer) Bukkit.getServer();
        GiveCommand.register(server.getServer().getCommands().getDispatcher());

        //Launch DataGeneration
        DataManager manager = new DataManager();
        manager.initialize();

        if (config.getWebServer().isEnabled()) {
            //Start WebServer
            try {
                WebServer webServer = new WebServer(config, FileManager.getCacheDirectoryFile(DataManager.RESOURCE_PACK_CACHE));
                webServer.initializeServer();
                webServer.startServer();
            } catch (Exception e) {
                ContentWork.logger.error("Unable to start web server on {}:{}!", config.getWebServer().getAddress(),
                        config.getWebServer().getPort(), e);
            }
        } else {
            ContentWork.logger.warn("""
                    
                    /!\\----------------------------------------/!\\
                     |                 WARNING                  |
                     |           Web Server: DISABLED           |
                     |----------------------------------------- |
                     | With the web server disabled, You will   |
                     | be required to manually enable the       |
                     | resource pack through the                |
                     | the server.properties. Location:         |
                     | plugins/content-work/cache/resource-pack |
                    /!\\----------------------------------------/!\\
                    """);
        }
    }
}
