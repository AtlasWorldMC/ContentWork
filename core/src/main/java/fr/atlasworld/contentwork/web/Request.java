package fr.atlasworld.contentwork.web;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import fr.atlasworld.contentwork.ContentWork;
import fr.atlasworld.contentwork.file.loader.JsonFileLoader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public class Request<T> {
    private String url;
    private final HashMap<String, String> params;
    private final HashMap<String, String> header;
    private File cache;
    private Consumer<Integer> callback = progress -> {};
    private boolean updateCache = false;
    private int timeout = 30*1000; //30Secs

    public Request() {
        this.params = new HashMap<>();
        this.header = new HashMap<>();
    }

    public Request<T> url(String url) {
        this.url = url;
        return this;
    }

    public Request<T> params(String parameter, String data) {
        this.params.put(parameter, data);
        return this;
    }

    public Request<T> params(Map<String, String> params) {
        this.params.putAll(params);
        return this;
    }

    public Request<T> header(String parameter, String data) {
        this.header.put(parameter, data);
        return this;
    }

    public Request<T> header(Map<String, String> header) {
        this.header.putAll(header);
        return this;
    }

    public Request<T> cache(File cache) {
        this.cache = cache;
        return this;
    }

    public Request<T> updateCache() {
        this.updateCache = true;
        return this;
    }

    public Request<T> callBack(Consumer<Integer> callback) {
        this.callback = callback;
        return this;
    }

    public Request<T> timeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public CompletableFuture<T> execute(Function<JsonElement, T> function) {
        CompletableFuture<T> future = new CompletableFuture<>();
        this.callback.accept(0);

        //Check for cache
        if (this.cache != null && !this.updateCache) {
            JsonFileLoader loader = new JsonFileLoader(this.cache);
            if (loader.fileExists()) {
                try {
                    JsonElement data = loader.load();
                    future.complete(function.apply(data));
                } catch (Throwable t) {
                    future.completeExceptionally(t);
                    callback.accept(-1);
                }
            }
        }

        try {
            URL apiUrl = new URL(this.buildUrl());
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(this.timeout);
            connection.setReadTimeout(this.timeout);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    JsonElement json  = JsonParser.parseString(response.toString());

                    if (this.cache != null) {
                        JsonFileLoader loader = new JsonFileLoader(this.cache);
                        if (loader.fileExists()) {
                            loader.getFile().delete();
                        }

                        loader.createFile();
                        loader.save(json);
                    }

                    future.complete(function.apply(json));
                }
            } else {
                throw new IOException("Request failed with response code : " + responseCode);
            }
        } catch (IOException e) {
            ContentWork.logger.error("Unable to make a request -> {}", this.buildUrl(), e);
            return null;
        }

        return future;
    }

    public CompletableFuture<File> download(File file) {
        return CompletableFuture.supplyAsync(() -> {
            File destFile = this.cache == null ? file : this.cache;

            if (!destFile.getParentFile().isDirectory()) {
                destFile.getParentFile().mkdirs();
            }

            try {
                URL url = new URL(this.buildUrl());
                ReadableByteChannel byteChannel = Channels.newChannel(url.openStream());
                FileOutputStream fileOutputStream = new FileOutputStream(destFile);

                long contentLength = url.openConnection().getContentLengthLong();
                long bytesTransferred = 0;

                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = byteChannel.read(ByteBuffer.wrap(buffer))) > 0) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                    bytesTransferred += bytesRead;

                    int progress = (int) ((bytesTransferred * 100) / contentLength);
                    this.callback.accept(progress);
                }

                fileOutputStream.close();
                byteChannel.close();

                if (this.cache != null) {
                    if (!file.getParentFile().isDirectory()) {
                        file.getParentFile().mkdirs();
                    }
                    try {
                        Files.copy(cache.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        callback.accept(-1);
                    }
                }

                return file;
            } catch (IOException e) {
                throw new RuntimeException("Failed to download the file: " + e.getMessage(), e);
            }
        });
    }

    private String buildUrl() {
        if (this.params.isEmpty()) {
            return this.url;
        }

        StringBuilder urlBuilder = new StringBuilder(this.url);

        urlBuilder.append("?");

        this.params.forEach((key, value) -> {
            urlBuilder.append("&");
            urlBuilder.append(key);
            urlBuilder.append("=");
            urlBuilder.append(value);
        });

        return urlBuilder.toString();
    }
}
