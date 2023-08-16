package fr.atlasworld.contentwork.listener;

import fr.atlasworld.contentwork.config.Config;
import fr.atlasworld.contentwork.data.generator.DataManager;
import fr.atlasworld.contentwork.file.FileManager;
import fr.atlasworld.contentwork.file.FileUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Config config = Config.getConfig();
        if (config.getWebServer().isEnabled()) {
            event.getPlayer().setResourcePack("http://" + config.getWebServer().getPublicAddress() + ":" +
                    config.getWebServer().getPort() + "/resource-pack",
                    FileUtils.getFileSha1(FileManager.getCacheDirectoryFile(DataManager.RESOURCE_PACK_CACHE)),
                    true);
        }
    }
}
