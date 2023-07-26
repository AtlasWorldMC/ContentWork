package fr.atlasworld.contentwork.data.generator;

import fr.atlasworld.contentwork.api.data.generator.DataGenerator;
import fr.atlasworld.contentwork.api.data.generator.provider.DataProvider;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class DataGeneratorImpl implements DataGenerator {
    private final File root;

    public DataGeneratorImpl(File root) {
        this.root = root;
    }

    @Override
    public void addProvider(Plugin plugin, DataProvider provider) {
        File pipelineDirectory = new File(this.root,
                plugin.getName().toLowerCase().replace("-", "_") + "/" + provider.getDirectory());
        provider.run(new FilePipelineImpl(plugin, pipelineDirectory));
    }
}
