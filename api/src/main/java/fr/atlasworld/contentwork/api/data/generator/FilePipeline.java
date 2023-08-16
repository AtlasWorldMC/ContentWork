package fr.atlasworld.contentwork.api.data.generator;

import fr.atlasworld.contentwork.api.data.model.builder.ModelBuilder;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import java.util.Map;

/**
 * Helper class for data generation
 */
public interface FilePipeline {
    /**
     * Gets the plugin which the pipeline is attached to
     * @return Attached Plugin
     */
    Plugin getPlugin();

    /**
     * Checks if file exists
     * @param location location of the file
     * @return true if exists
     */
    boolean fileExists(NamespacedKey location);

    /**
     * Saves models files
     * @param models Model files
     */
    void saveModels(Map<NamespacedKey, ModelBuilder<?>> models);
}
