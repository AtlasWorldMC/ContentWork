package fr.atlasworld.contentwork.test.datagen;

import fr.atlasworld.contentwork.api.data.generator.event.GatherDataEvent;
import fr.atlasworld.contentwork.test.ContentWorkTest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DataGenerationListener implements Listener {
    @EventHandler
    public void onGatherData(GatherDataEvent event) {
        event.getDataGenerator().addProvider(ContentWorkTest.plugin, new TestItemModelProvider(ContentWorkTest.plugin));
    }
}
