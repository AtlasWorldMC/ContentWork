package fr.atlasworld.contentwork.api.data.generator;

import fr.atlasworld.contentwork.api.data.generator.provider.DataProvider;
import org.bukkit.plugin.Plugin;

/**
 * Helper class for data generation
 */
public interface DataGenerator {

    /**
     * Adds a data provider to the data generator
     * @param plugin Plugin that adds the provider
     * @param provider The data provider
     */
    void addProvider(Plugin plugin, DataProvider provider);
}
