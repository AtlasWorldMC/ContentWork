package fr.atlasworld.contentwork.data.generator;

import com.google.gson.JsonObject;
import fr.atlasworld.contentwork.ContentWork;
import fr.atlasworld.contentwork.api.data.generator.event.GatherDataEvent;
import fr.atlasworld.contentwork.api.data.model.ModelFile;
import fr.atlasworld.contentwork.api.data.model.builder.ItemModelBuilder;
import fr.atlasworld.contentwork.api.data.model.builder.OverrideBuilder;
import fr.atlasworld.contentwork.file.FileManager;
import fr.atlasworld.contentwork.file.FileUtils;
import fr.atlasworld.contentwork.file.loader.JsonFileLoader;
import fr.atlasworld.contentwork.registering.DefaultRegistries;
import fr.atlasworld.contentwork.registering.registries.ItemRegistry;
import fr.atlasworld.contentwork.web.Request;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DataManager {
    private static final String CACHE_FILE = "cache.json";
    public static final String RESOURCE_PACK_CACHE = "resource-pack.zip";
    public static final String DATA_PACK_CACHE = "data-pack.zip";
    private static final String TEMP_RESOURCE_PACK = "temp/resource-pack";
    private static final String TEMP_DATA_PACK = "temp/data-pack";
    private static final int RESOURCE_PACK_VERSION = 15; // Pack version for 1.20.1
    private static final int DATA_PACK_VERSION = 15; // Pack version for 1.20.1
    private static final String CLIENT_JAR_URL = "https://piston-data.mojang.com/v1/objects/0c3ec587af28e5a785c0b4a7b8a30f9a8f78f838/client.jar"; //Client Jar for 1.20.1


    private File[] dataGenerationPlugins;

    public void initialize() {
        ContentWork.logger.info("[DataGen] Checking for cache...");
        if (!this.cacheAvailable()) {
            ContentWork.logger.info("[DataGen] No cache available.");
            ContentWork.logger.info("[DataGen] Collecting Plugins..");
            this.collectPlugins();
            ContentWork.logger.info("[DataGen] Found {} Plugin(s) to load.", this.dataGenerationPlugins.length);
            ContentWork.logger.info("[DataGen] Generating Resource Pack & Data Pack..");
            this.generatePackBase();
            ContentWork.logger.info("[DataGen] Successfully generated packs.");
            ContentWork.logger.info("[DataGen] Downloading Minecraft assets..");
            this.downloadVanillaAssets();
            ContentWork.logger.info("[DataGen] Successfully downloaded Minecraft assets.");
            ContentWork.logger.info("[DataGen] Combining plugins assets..");
            this.combinePluginAssets();
            ContentWork.logger.info("[DataGen] Successfully combined plugins assets.");
            ContentWork.logger.info("[DataGen] Combining plugins data..");
            this.combinePluginData();
            ContentWork.logger.info("[DataGen] Successfully combined plugins data.");
            ContentWork.logger.info("[DataGen] Running plugins Data Generators..");
            this.launchPluginDataGeneration();
            ContentWork.logger.info("[DataGen] Successfully ran plugin's Data Generators.");
            ContentWork.logger.info("[DataGen] Modifying model files..");
            this.modifyModels();
            ContentWork.logger.info("[DataGen] Successfully modified model files.");
            ContentWork.logger.info("[DataGen] Package packs..");
            this.packEverything();
            ContentWork.logger.info("[DataGen] Successfully packaged packs.");
            ContentWork.logger.info("[DataGen] Cleaning up..");
            this.cleanUp();
            ContentWork.logger.info("[DataGen] Successfully cleaned up.");
        }
        ContentWork.logger.info("[DataGen] Data Generation finished!");
    }

    public boolean cacheAvailable() {
        File cacheFile = FileManager.getCacheDirectoryFile(CACHE_FILE);
        File resourcePackCache = FileManager.getCacheDirectoryFile(RESOURCE_PACK_CACHE);
        File dataPackCache = FileManager.getCacheDirectoryFile(DATA_PACK_CACHE);

        if (!cacheFile.isFile() || !resourcePackCache.isFile() || !dataPackCache.isFile()) {
            return false;
        }

        JsonFileLoader loader = new JsonFileLoader(cacheFile);
        JsonObject json = loader.load().getAsJsonObject();

        String resourcePackSha = json.get("resource-pack").getAsString();
        String dataPackSha = json.get("data-pack").getAsString();
        if (!FileUtils.validateFileSha1(resourcePackCache, resourcePackSha) || !FileUtils.validateFileSha1(dataPackCache, dataPackSha)) {
            return false;
        }

        Set<Map.Entry<String, String>> pluginsSha = json.get("plugins")
                .getAsJsonObject()
                .entrySet()
                .stream()
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().getAsString()))
                .collect(Collectors.toSet());

        for (Map.Entry<String, String> entry : pluginsSha) {
            File pluginFile = FileManager.getPluginFile(entry.getKey());
            String pluginFileSha = entry.getValue();

            if (!pluginFile.isFile()) {
                return false; // Plugin file does not exist
            }

            if (!FileUtils.validateFileSha1(pluginFile, pluginFileSha)) {
                return false; // Saved plugin sha and actual sha doesn't match
            }
        }

        return true;
    }

    public void collectPlugins() {
        File[] files = FileManager.getPluginDirectory().listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));

        this.dataGenerationPlugins = Arrays.stream(files)
                .filter(file -> FileUtils.archiveContainsDirectory(file, "assets/") ||
                        FileUtils.archiveContainsDirectory(file, "data/"))
                .toArray(File[]::new);
    }

    public void generatePackBase() {
        File resourcePackDirectory = FileManager.getRootDirectoryFile(TEMP_RESOURCE_PACK);
        File dataPackDirectory = FileManager.getRootDirectoryFile(TEMP_DATA_PACK);

        if (resourcePackDirectory.exists()) {
            FileUtils.deleteDirectory(resourcePackDirectory); //Delete directory to clear all files
        }
        if (dataPackDirectory.exists()) {
            FileUtils.deleteDirectory(dataPackDirectory); //Delete directory to clear all files
        }

        resourcePackDirectory.mkdirs();
        dataPackDirectory.mkdirs();

        JsonFileLoader resourceLoader = new JsonFileLoader(new File(resourcePackDirectory, "pack.mcmeta"), true);
        JsonFileLoader dataLoader = new JsonFileLoader(new File(dataPackDirectory, "pack.mcmeta"), true);
        resourceLoader.createFile();;
        dataLoader.createFile();

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


        resourceLoader.save(parentResourceMeta);
        dataLoader.save(parentDataMeta);
    }

    public void downloadVanillaAssets() {
        File clientJarCache = FileManager.getCacheDirectoryFile("download/client.jar");
        if (!clientJarCache.isFile()) {
            new Request<>()
                    .url(CLIENT_JAR_URL)
                    .download(clientJarCache).join(); //Wait till download is finished
        }
    }

    public void combinePluginAssets() {
        File clientJarCache = FileManager.getCacheDirectoryFile("download/client.jar");
        File resourcePack = FileManager.getRootDirectoryFile(TEMP_RESOURCE_PACK + "/assets");
        try {
            FileUtils.extractArchive(clientJarCache, resourcePack, "assets/");
            for (File plugin : this.dataGenerationPlugins) {
                FileUtils.extractArchive(plugin, resourcePack, "assets/");
            }
        } catch (IOException e) {
            ContentWork.logger.error("Unable to combine plugin assets!", e);
        }
    }

    public void combinePluginData() {
        File clientJarCache = FileManager.getCacheDirectoryFile("download/client.jar");
        File resourcePack = FileManager.getRootDirectoryFile(TEMP_DATA_PACK + "/data");
        try {
            FileUtils.extractArchive(clientJarCache, resourcePack, "data/");
            for (File plugin : this.dataGenerationPlugins) {
                FileUtils.extractArchive(plugin, resourcePack, "data/");
            }
        } catch (IOException e) {
            ContentWork.logger.error("Unable to combine plugin assets!", e);
        }
    }

    public void launchPluginDataGeneration() {
        File root = FileManager.getRootDirectoryFile(TEMP_RESOURCE_PACK + "/assets");
        Bukkit.getPluginManager().callEvent(new GatherDataEvent(new DataGeneratorImpl(root)));
    }

    public void modifyModels() {
        ItemRegistry itemRegistry = (ItemRegistry) DefaultRegistries.ITEM;
        File minecraftModelRoot = FileManager.getRootDirectoryFile(TEMP_RESOURCE_PACK +
                "/assets/minecraft/models/item");

        itemRegistry.getEntries()
                .forEach(entry -> {
                    Material material = entry.getValue().getParent();

                    JsonFileLoader modelLoader = new JsonFileLoader(new File(minecraftModelRoot, material.getKey().getKey() + ".json"), true);
                    if (!modelLoader.fileExists()) {
                        ContentWork.logger.error("Unable to find {} model file!", modelLoader.getFile().getName());
                        return;
                    }

                    ItemModelBuilder modelBuilder = new ItemModelBuilder(material.getKey(), null, modelLoader.load().getAsJsonObject());
                    OverrideBuilder overrideBuilder = new OverrideBuilder();
                    overrideBuilder.model(new ModelFile.UncheckedModelFile(new NamespacedKey(entry.getKey().getNamespace(), "item/" + entry.getKey().getKey())));
                    overrideBuilder.predicate(OverrideBuilder.Predicates.CUSTOM_MODEL_DATA, itemRegistry.getCustomModel(entry.getKey()));
                    modelBuilder.override(overrideBuilder);

                    modelLoader.save(modelBuilder.build());
                });
    }

    public void packEverything() {
        File resourcePackDirectory = FileManager.getRootDirectoryFile(TEMP_RESOURCE_PACK);
        File dataPackDirectory = FileManager.getRootDirectoryFile(TEMP_DATA_PACK);
        File resourcePackCache = FileManager.getCacheDirectoryFile(RESOURCE_PACK_CACHE);
        File dataPackCache = FileManager.getCacheDirectoryFile(DATA_PACK_CACHE);

        FileUtils.zip(resourcePackDirectory.toString(), resourcePackCache.toString());
        FileUtils.zip(dataPackDirectory.toString(), dataPackCache.toString());

        String resourcePackSha = FileUtils.getFileSha1(resourcePackCache);
        String dataPackSha = FileUtils.getFileSha1(dataPackCache);

        JsonObject cacheJson = new JsonObject();

        cacheJson.addProperty("resource-pack", resourcePackSha);
        cacheJson.addProperty("data-pack", dataPackSha);
        cacheJson.add("plugins", new JsonObject());

        File[] plugins = FileManager.getPluginDirectory().listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));
        for (File plugin : plugins) {
            cacheJson.getAsJsonObject("plugins").addProperty(plugin.getName(), FileUtils.getFileSha1(plugin));
        }

        JsonFileLoader cacheLoader = new JsonFileLoader(FileManager.getCacheDirectoryFile(CACHE_FILE), true);

        if (!cacheLoader.fileExists()) {
            cacheLoader.createFile();
        }
        cacheLoader.save(cacheJson);
    }

    public void cleanUp() {
        FileUtils.deleteDirectory(FileManager.getRootDirectoryFile("temp"));
    }
}
