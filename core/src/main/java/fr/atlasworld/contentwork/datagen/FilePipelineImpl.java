package fr.atlasworld.contentwork.datagen;

import fr.atlasworld.contentwork.api.data.model.Model;
import fr.atlasworld.contentwork.api.datagen.FilePipeline;
import fr.atlasworld.contentwork.file.loader.JsonFileLoader;

import java.io.File;

public class FilePipelineImpl implements FilePipeline {
    private final File directory;
    private final String pluginKey;
    private final String subDirectory;

    public FilePipelineImpl(String rootDirectory, String pluginKey, String subDirectory) {
        this.directory = new File(rootDirectory + "/" + pluginKey + "/" + subDirectory);
        this.pluginKey = pluginKey;
        this.subDirectory = subDirectory;
    }

    @Override
    public void save(Model model, String filename) {
        File file = new File(this.directory, filename + ".json");
        JsonFileLoader loader = new JsonFileLoader(file);

        if (!loader.fileExists()) {
            loader.createFile();
        }

        loader.save(model.build());
    }

    public String getPluginKey() {
        return pluginKey;
    }

    public String getSubDirectory() {
        return subDirectory;
    }
}
