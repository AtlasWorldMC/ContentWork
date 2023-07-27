package fr.atlasworld.contentwork.web.server;

import com.sun.net.httpserver.HttpServer;
import fr.atlasworld.contentwork.ContentWork;
import fr.atlasworld.contentwork.config.Config;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

import java.util.concurrent.Executors;

public class WebServer implements Closeable {
    private final HttpServer server;
    private final Config config;
    private final File resourcePack;

    public WebServer(@NotNull Config config, File resourcePack) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(config.getWebServer().getAddress(), config.getWebServer().getPort()), 0);
        this.config = config;
        this.resourcePack = resourcePack;
    }

    public void initializeServer() {
        this.server.createContext("/resource-pack", new ResourcePackHandler(this.resourcePack));
        this.server.setExecutor(Executors.newFixedThreadPool(this.config.getWebServer().getThreads()));
    }

    public void startServer() {
        this.server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
        ContentWork.logger.info("WebServer started!");
    }

    @Override
    public void close() {
        this.server.stop(0);
        ContentWork.logger.info("WebServer closed!");
    }
}
