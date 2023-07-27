package fr.atlasworld.contentwork.config;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.atlasworld.contentwork.file.FileManager;
import fr.atlasworld.contentwork.file.loader.GsonFileLoader;
import fr.atlasworld.contentwork.file.loader.JsonFileLoader;
import org.bukkit.Bukkit;
import org.checkerframework.checker.units.qual.C;

public class Config {
    private final WebServerConfig webServer;

    private Config(WebServerConfig webServer) {
        this.webServer = webServer;
    }

    public WebServerConfig getWebServer() {
        return webServer;
    }


    // Static Fields
    private static Config config;

    public static Config getConfig() {
        if (config == null) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            GsonFileLoader<Config> loader = new GsonFileLoader<>(FileManager.getRootDirectoryFile("config.json"), gson, Config.class);

            if (!loader.fileExists()) {
                loader.createFile();
                loader.save(new Config(new WebServerConfig(
                        true,
                        8080,
                        "0.0.0.0",
                        Bukkit.getServer().getMaxPlayers() / 2 //This should be enough for small servers
                )));
            }

            config = loader.load();
        }

        return config;
    }


    public static class WebServerConfig {
        private final boolean enabled;
        private final int port;
        private final String address;
        private final int threads;

        private WebServerConfig(boolean enabled, int port, String address, int threads) {
            this.enabled = enabled;
            this.port = port;
            this.address = address;
            this.threads = threads;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public int getPort() {
            return port;
        }

        public String getAddress() {
            return address;
        }

        public int getThreads() {
            return threads;
        }
    }
}
