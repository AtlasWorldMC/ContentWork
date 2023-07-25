package fr.atlasworld.contentwork.api.data.generator;

import fr.atlasworld.contentwork.api.data.model.builder.ModelBuilder;
import org.bukkit.NamespacedKey;

import java.util.Map;

public interface FilePipeline {
    boolean fileExists(NamespacedKey location);
    void saveModels(Map<NamespacedKey, ModelBuilder<?>> models);
}
