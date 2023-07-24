package fr.atlasworld.contentwork.test.datagen.assets;

import fr.atlasworld.contentwork.api.datagen.FilePipeline;
import fr.atlasworld.contentwork.api.datagen.assets.ItemModelProvider;
import fr.atlasworld.contentwork.test.item.TestItems;

public class TestItemModelProvider extends ItemModelProvider {
    @Override
    public void buildModels(FilePipeline pipeline) {
        simpleItem(TestItems.CUSTOM_ITEM, pipeline);
    }
}
