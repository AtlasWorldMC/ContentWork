package fr.atlasworld.contentwork.listener;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import fr.atlasworld.contentwork.ContentWork;
import fr.atlasworld.contentwork.command.GiveCommand;
import fr.atlasworld.contentwork.datagen.DataGenManager;
import fr.atlasworld.contentwork.registering.DefaultRegistries;
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

        //Trigger registering event
        DefaultRegistries.register(Bukkit.getPluginManager());

        //Register command
        CraftServer server = (CraftServer) Bukkit.getServer();
        GiveCommand.register(server.getServer().getCommands().getDispatcher());

        //DataGeneration
        DataGenManager dataGenManager = new DataGenManager();
        if (!dataGenManager.cacheAvailable()) {
            ContentWork.logger.info("[DataGen] No cache available.");
            ContentWork.logger.info("[DataGen] Collecting plugins...");
            dataGenManager.collectPlugins();
            ContentWork.logger.info("[DataGen] Found {} plugins to load!", dataGenManager.getDataGenPlugins().size());
            ContentWork.logger.info("[DataGen] Generating Resource Pack & Data Pack...");
            dataGenManager.createDataGenPacksBase();
            ContentWork.logger.info("[DataGen] Starting to download assets...");
            dataGenManager.downloadVanillaAssets();
            ContentWork.logger.info("[DataGen] Assets successfully downloaded.");
            ContentWork.logger.info("[DataGen] Combining Plugins DataGen...");
            dataGenManager.combineAssets();
            ContentWork.logger.info("[DataGen] Packing everything...");
            dataGenManager.packEverything();
            ContentWork.logger.info("[DataGen] Cleaning up...");
            dataGenManager.cleanUp();
        }
        ContentWork.logger.info("[DataGen] Data generation finished!");
    }
}
