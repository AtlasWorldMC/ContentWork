package fr.atlasworld.contentwork.web.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.atlasworld.contentwork.ContentWork;

import java.io.*;

public class ResourcePackHandler implements HttpHandler {
    private final File resourcePack;

    public ResourcePackHandler(File resourcePack) {
        this.resourcePack = resourcePack;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/zip");

        if (!this.resourcePack.isFile()) {
            ContentWork.logger.error("Unable to find the resource pack! File is supposed to be located at {}, Unable to full fill request!",
                    this.resourcePack.getAbsolutePath());
            String message = "Unable to full fill request, cannot find resource pack!";
            exchange.sendResponseHeaders(500, message.length());
            exchange.getResponseBody().write(message.getBytes());
            exchange.getResponseBody().close();
            return;
        }

        try (InputStream input = new FileInputStream(this.resourcePack)) {
            exchange.sendResponseHeaders(200, this.resourcePack.length());
            OutputStream output = exchange.getResponseBody();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            output.close();
        }
    }
}
