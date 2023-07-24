package fr.atlasworld.contentwork.api.datagen.assets;

import fr.atlasworld.contentwork.api.common.item.Item;
import fr.atlasworld.contentwork.api.data.model.ItemModel;
import fr.atlasworld.contentwork.api.datagen.FilePipeline;
import fr.atlasworld.contentwork.api.registering.RegistryObject;
import org.bukkit.NamespacedKey;

public abstract class ItemModelProvider {
    public abstract void buildModels(FilePipeline filePipeline);

    protected void simpleItem(RegistryObject<Item> item, FilePipeline pipeline) {
        ItemModel model = new ItemModel();
        model.setParent(NamespacedKey.minecraft("item/generated"));
        model.addTexture("layer0", new NamespacedKey(item.getKey().getNamespace(), "item/" + item.getKey().getKey()));

        pipeline.save(model, item.getKey().getKey());
    }

    protected void simpleItemWithCustomParent(RegistryObject<Item> item, FilePipeline pipeline, NamespacedKey parent) {
        ItemModel model = new ItemModel();
        model.setParent(parent);
        model.addTexture("layer0", new NamespacedKey(item.getKey().getNamespace(), "item/" + item.getKey().getKey()));

        pipeline.save(model, item.getKey().getKey());
    }

    protected void simpleItemWithCustomParent(RegistryObject<Item> item, FilePipeline pipeline, RegistryObject<Item> parent) {
        this.simpleItemWithCustomParent(item, pipeline, new NamespacedKey(parent.getKey().getNamespace(), "item/" + parent.getKey().getKey()));
    }
}
