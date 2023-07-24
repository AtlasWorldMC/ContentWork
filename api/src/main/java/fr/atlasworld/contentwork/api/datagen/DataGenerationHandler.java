package fr.atlasworld.contentwork.api.datagen;

import fr.atlasworld.contentwork.api.datagen.assets.AssetProvider;
import fr.atlasworld.contentwork.api.datagen.data.DataProvider;
import org.bukkit.plugin.Plugin;

public interface DataGenerationHandler {
    AssetProvider buildAssetProvider(Plugin plugin);
    DataProvider buildDataProvider(Plugin plugin);
}
