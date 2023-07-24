package fr.atlasworld.contentwork.datagen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.atlasworld.contentwork.ContentWork;
import fr.atlasworld.contentwork.api.data.model.ItemModel;
import fr.atlasworld.contentwork.api.data.model.override.ItemModelOverride;
import fr.atlasworld.contentwork.api.data.model.override.OverridePredicates;
import fr.atlasworld.contentwork.api.datagen.event.BuildAssetsEvent;
import fr.atlasworld.contentwork.file.FileManager;
import fr.atlasworld.contentwork.file.FileUtils;
import fr.atlasworld.contentwork.file.loader.JsonFileLoader;
import fr.atlasworld.contentwork.registering.DefaultRegistries;
import fr.atlasworld.contentwork.registering.registries.ItemRegistry;
import fr.atlasworld.contentwork.web.Request;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class DataGenManager {
    private static final String VERSION_MANIFEST_LINK = "https://piston-meta.mojang.com/v1/packages/715ccf3330885e75b205124f09f8712542cbe7e0/1.20.1.json";
    private static final String RESOURCE_PACK_CACHE = "resource-pack.zip";
    private static final String DATA_PACK_CACHE = "data-pack.zip";
    private static final int RESOURCE_PACK_VERSION = 15; //Pack version for 1.20.1
    private static final int DATA_PACK_VERSION = 15; //Pack version for 1.20.1

    private Set<File> dataGenPlugins;

    public boolean cacheAvailable() {
        boolean cachesExists = FileManager.getCacheDirectoryFile(RESOURCE_PACK_CACHE).isFile() &&
                FileManager.getCacheDirectoryFile(DATA_PACK_CACHE).isFile();

        if (cachesExists) {
            JsonFileLoader cacheEntryLoader = new JsonFileLoader(FileManager.getCacheDirectoryFile("cache.json"));

            if (!cacheEntryLoader.fileExists()) {
                FileManager.getCacheDirectoryFile(RESOURCE_PACK_CACHE).delete();
                FileManager.getCacheDirectoryFile(DATA_PACK_CACHE).delete();
                return false;
            }

            JsonObject data = cacheEntryLoader.load().getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : data.entrySet()) {
                File pluginFile = FileManager.getPluginFile(entry.getKey());
                if (pluginFile.isFile()) {
                    if (!FileUtils.validateFileSha1(pluginFile, entry.getValue().getAsString())) {
                        return false; //File hash doesn't match
                    }
                } else {
                    return false; //File does not exist
                }
            }

            return true;
        }

        return false;
    }

    public void collectPlugins() {
        File[] pluginFiles = FileManager.getPluginDirectory().listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));
        dataGenPlugins = Arrays.stream(pluginFiles)
                .filter(plugin -> hasAssetsDirectory(plugin) || hasDataDirectory(plugin))
                .collect(Collectors.toSet());
    }

    public Set<File> getDataGenPlugins() {
        return dataGenPlugins;
    }

    private boolean hasAssetsDirectory(File file) {
        try (JarFile jarFile = new JarFile(file)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().startsWith("assets/") && entry.isDirectory()) {
                    return true;
                }
            }
        } catch (IOException e) {
            ContentWork.logger.error("Unable to verify {}!", file.getName(), e);
        }
        return false;
    }

    private boolean hasDataDirectory(File file) {
        try (JarFile jarFile = new JarFile(file)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().startsWith("data/") && entry.isDirectory()) {
                    return true;
                }
            }
        } catch (IOException e) {
            ContentWork.logger.error("Unable to verify {}!", file.getName(), e);
        }
        return false;
    }

    public void createDataGenPacksBase() {
        File temp = FileManager.getRootDirectoryFile("temp");
        if (!temp.isDirectory()) {
            temp.mkdirs();
        }

        File texturePack = new File(temp, "resource-pack");
        File dataPack = new File(temp, "data-pack");

        if (!texturePack.isDirectory()) {
            texturePack.mkdirs();
        }

        if (!dataPack.isDirectory()) {
            dataPack.mkdirs();
        }

        //pack.mcmeta
        JsonFileLoader resourceMeta = new JsonFileLoader(new File(texturePack, "pack.mcmeta"));
        JsonFileLoader dataMeta = new JsonFileLoader(new File(dataPack, "pack.mcmeta"));

        if (!resourceMeta.fileExists()) {
            resourceMeta.createFile();
        }

        if (!dataMeta.fileExists()) {
            dataMeta.createFile();
        }

        JsonObject resourceMetaJson = new JsonObject();
        resourceMetaJson.addProperty("pack_format", RESOURCE_PACK_VERSION);
        resourceMetaJson.addProperty("description", "ContentWork assets");

        JsonObject parentResourceMeta = new JsonObject();
        parentResourceMeta.add("pack", resourceMetaJson);

        JsonObject dataMetaJson = new JsonObject();
        dataMetaJson.addProperty("pack_format", DATA_PACK_VERSION);
        dataMetaJson.addProperty("description", "ContentWork DataPack");

        JsonObject parentDataMeta = new JsonObject();
        parentDataMeta.add("pack", dataMetaJson);

        //Save
        resourceMeta.save(parentResourceMeta);
        dataMeta.save(parentDataMeta);
    }

    public void downloadVanillaAssets() {
        File clientJar = FileManager.getCacheDirectoryFile("download/client.jar");

        if (!clientJar.isFile()) {
            JsonObject versionManifest = new Request<JsonObject>()
                    .url(VERSION_MANIFEST_LINK)
                    .timeout(5000)
                    .execute(JsonElement::getAsJsonObject).join();

            String jarUrl = versionManifest.get("downloads").getAsJsonObject().get("client").getAsJsonObject()
                    .get("url").getAsString();

            AtomicInteger lastPercent = new AtomicInteger(0);
            new Request<>()
                    .url(jarUrl)
                    .callBack(integer -> {
                        if (lastPercent.get() == integer) {
                            return;
                        }
                        if (integer % 5 == 0) {
                            ContentWork.logger.info("[DataGen] Downloading assets: {}%", integer);
                            lastPercent.set(integer);
                        }
                    })
                    .download(clientJar).join();
        }
    }

    public void combineAssets() {
        File root = FileManager.getRootDirectoryFile("temp/resource-pack/assets");
        this.copyPluginAssets(FileManager.getCacheDirectoryFile("download/client.jar"), root.toPath());
        this.dataGenPlugins.forEach(plugin -> copyPluginAssets(plugin, root.toPath()));
    }

    public void buildModels() {
        Bukkit.getPluginManager().callEvent(new BuildAssetsEvent(new DataGenerationHandlerImpl()));
        File vanillaItemModelDirectory = FileManager.getRootDirectoryFile("temp/resource-pack/assets/minecraft/models/item");

        //Item
        ItemRegistry itemRegistry = (ItemRegistry) DefaultRegistries.ITEM.getEntries();
        itemRegistry.getEntries().forEach(entry -> {
            Material material = entry.getValue().getParent();
            JsonFileLoader loader = new JsonFileLoader(new File(vanillaItemModelDirectory, material.getKey().getKey() + ".json"));

            JsonObject json = loader.load().getAsJsonObject();
            ItemModel model = ItemModel.fromJson(json);

            NamespacedKey customModelKey = new NamespacedKey(entry.getKey().getNamespace(), "item/" + entry.getKey().getKey());
            ItemModelOverride override = new ItemModelOverride(customModelKey);
            override.addPredicate(OverridePredicates.CUSTOM_MODEL_DATA, itemRegistry.getCustomModel(entry.getKey()));

            model.addOverride(override);
        });
    }

    private void copyPluginAssets(File plugin, Path dest) {
        try (ZipFile zipFile = new ZipFile(plugin)) {
            String assetPath = "assets/";
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();

                // Check if entry is inside the assets folder and is not a directory
                if (entry.getName().startsWith(assetPath) && !entry.isDirectory()) {
                    String relativePath = entry.getName().substring(assetPath.length());
                    File outputFile = new File(dest.toString(), relativePath);

                    if (!outputFile.getParentFile().exists()) {
                        outputFile.getParentFile().mkdirs();
                    }

                    try (InputStream inputStream = zipFile.getInputStream(entry);
                         OutputStream outputStream = new FileOutputStream(outputFile)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;

                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }
                }

                // Check if entry is inside the assets folder and is a directory
                if (entry.getName().startsWith(assetPath) && entry.isDirectory()) {
                    String relativePath = entry.getName().substring(assetPath.length());
                    File outputDir = new File(dest.toString(), relativePath);

                    if (!outputDir.exists()) {
                        outputDir.mkdirs();
                    }
                }
            }
        } catch (IOException e) {
            ContentWork.logger.error("Unable to combine plugins assets for {}", plugin.getName(), e);
        }
    }

    public void packEverything() {
        File resourcePack = FileManager.getRootDirectoryFile("temp/resource-pack");
        File dataPack = FileManager.getRootDirectoryFile("temp/data-pack");

        FileUtils.zip(resourcePack.getPath(), FileManager.getCacheDirectoryFile(RESOURCE_PACK_CACHE).getPath());
        FileUtils.zip(dataPack.getPath(), FileManager.getCacheDirectoryFile(DATA_PACK_CACHE).getPath());

        JsonFileLoader cacheEntryLoader = new JsonFileLoader(FileManager.getCacheDirectoryFile("cache.json"));
        if (!cacheEntryLoader.fileExists()) {
            cacheEntryLoader.createFile();
        }

        JsonObject cacheJson = new JsonObject();
        File[] plugins = FileManager.getPluginDirectory().listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));
        for (File plugin : plugins) {
            cacheJson.addProperty(plugin.getName(), FileUtils.getFileSha1(plugin));
        }

        cacheEntryLoader.save(cacheJson);
    }

    public void cleanUp() {
        FileUtils.deleteDirectory(FileManager.getRootDirectoryFile("temp"));
    }
}
