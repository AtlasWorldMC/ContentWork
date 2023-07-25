package fr.atlasworld.contentwork.api.data.generator;

import fr.atlasworld.contentwork.api.data.generator.provider.DataProvider;
import org.bukkit.plugin.Plugin;

public interface DataGenerator {
    void addProvider(Plugin plugin, DataProvider provider);
}
