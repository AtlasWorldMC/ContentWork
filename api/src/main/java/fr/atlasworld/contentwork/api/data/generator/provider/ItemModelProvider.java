package fr.atlasworld.contentwork.api.data.generator.provider;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import fr.atlasworld.contentwork.api.common.item.Item;
import fr.atlasworld.contentwork.api.data.generator.FilePipeline;
import fr.atlasworld.contentwork.api.data.model.ModelFile;
import fr.atlasworld.contentwork.api.data.model.builder.ItemModelBuilder;
import fr.atlasworld.contentwork.api.registering.RegistryObject;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

/**
 * Item Model Generator
 */
public abstract class ItemModelProvider extends ModelProvider<ItemModelBuilder> {
    public ItemModelProvider(Plugin plugin) {
        super(plugin, ItemModelBuilder::new);
    }

    /**
     * Create a basic item model
     * @param item item
     * @param pipeline the file pipeline
     * @return model builder;
     */
    @CanIgnoreReturnValue
    public ItemModelBuilder basicItem(RegistryObject<Item> item, FilePipeline pipeline) {
        return basicItem(item.getKey(), pipeline);
    }

    /**
     * Create a basic item model
     * @param item item
     * @param pipeline the file pipeline
     * @return model builder;
     */
    @CanIgnoreReturnValue
    public ItemModelBuilder basicItem(NamespacedKey item, FilePipeline pipeline) {
        return getBuilder(item.asString(), pipeline)
                .parent(new ModelFile.UncheckedModelFile(ModelProvider.ITEM_FOLDER + "/generated"))
                .texture("layer0", new NamespacedKey(item.getNamespace(), "item/" + item.getKey()));
    }

    @Override
    public String getDirectory() {
        return "models/item/";
    }
}
