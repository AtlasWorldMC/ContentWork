package fr.atlasworld.contentwork.api.data.generator;

import fr.atlasworld.contentwork.api.data.model.builder.ModelBuilder;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public interface FilePipeline {
    Plugin getPlugin();
    boolean fileExists(NamespacedKey location);
    void saveModels(Map<NamespacedKey, ModelBuilder<?>> models);
}
