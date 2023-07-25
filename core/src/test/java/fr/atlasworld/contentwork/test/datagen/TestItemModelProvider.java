package fr.atlasworld.contentwork.test.datagen;

import fr.atlasworld.contentwork.api.data.generator.FilePipeline;
import fr.atlasworld.contentwork.api.data.generator.provider.ItemModelProvider;
import fr.atlasworld.contentwork.test.item.TestItems;
import org.bukkit.plugin.Plugin;

public class TestItemModelProvider extends ItemModelProvider {
    public TestItemModelProvider(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void generate(FilePipeline pipeline) {
        basicItem(TestItems.CUSTOM_ITEM, pipeline);
    }
}
