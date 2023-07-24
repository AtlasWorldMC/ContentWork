package fr.atlasworld.contentwork.test.datagen;

import fr.atlasworld.contentwork.api.datagen.assets.AssetProvider;
import fr.atlasworld.contentwork.api.datagen.event.BuildAssetsEvent;
import fr.atlasworld.contentwork.api.datagen.event.BuildDataEvent;
import fr.atlasworld.contentwork.test.ContentWorkTest;
import fr.atlasworld.contentwork.test.datagen.assets.TestItemModelProvider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DataGenListener implements Listener {
    @EventHandler
    public void onAssetGeneration(BuildAssetsEvent event) {
        AssetProvider provider = event.getProvider(ContentWorkTest.plugin);
        provider.provider(new TestItemModelProvider());
    }

    @EventHandler
    public void onDataGeneration(BuildDataEvent event) {

    }
}
