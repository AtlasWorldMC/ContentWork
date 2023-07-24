package fr.atlasworld.contentwork.datagen;

import fr.atlasworld.contentwork.api.datagen.DataGenerationHandler;
import fr.atlasworld.contentwork.api.datagen.assets.AssetProvider;
import fr.atlasworld.contentwork.api.datagen.data.DataProvider;
import fr.atlasworld.contentwork.datagen.assets.AssetProviderImpl;
import org.bukkit.plugin.Plugin;

import java.util.Locale;

public class DataGenerationHandlerImpl implements DataGenerationHandler {
    @Override
    public AssetProvider buildAssetProvider(Plugin plugin) {
        return new AssetProviderImpl(plugin.getName().toLowerCase(Locale.ROOT).replace("-", "_"));
    }

    @Override
    public DataProvider buildDataProvider(Plugin plugin) {
        return null;
    }
}
