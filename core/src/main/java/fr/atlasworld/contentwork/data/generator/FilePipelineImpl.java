package fr.atlasworld.contentwork.data.generator;

import fr.atlasworld.contentwork.api.data.generator.FilePipeline;
import fr.atlasworld.contentwork.api.data.model.builder.ModelBuilder;
import fr.atlasworld.contentwork.file.loader.JsonFileLoader;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Map;

public class FilePipelineImpl implements FilePipeline {
    private final Plugin plugin;
    private final File directory;

    public FilePipelineImpl(Plugin plugin, File directory) {
        this.plugin = plugin;
        this.directory = directory;
    }

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public boolean fileExists(NamespacedKey location) {
        return new File(this.directory, location.getKey()).exists();
    }

    @Override
    public void saveModels(Map<NamespacedKey, ModelBuilder<?>> models) {
        models.forEach((key, modelBuilder) -> {
            File file = new File(this.directory, key.getKey() + ".json");
            JsonFileLoader loader = new JsonFileLoader(file, true);

            if (!loader.fileExists()) {
                loader.createFile();
            }

            loader.save(modelBuilder.build());
        });
    }
}
