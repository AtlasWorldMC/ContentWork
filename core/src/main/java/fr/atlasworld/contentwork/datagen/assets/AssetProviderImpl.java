package fr.atlasworld.contentwork.datagen.assets;

import fr.atlasworld.contentwork.api.datagen.FilePipeline;
import fr.atlasworld.contentwork.api.datagen.assets.AssetProvider;
import fr.atlasworld.contentwork.api.datagen.assets.ItemModelProvider;
import fr.atlasworld.contentwork.datagen.FilePipelineImpl;
import fr.atlasworld.contentwork.file.FileManager;

public class AssetProviderImpl implements AssetProvider {
    private final String namespace;

    public AssetProviderImpl(String namespace) {
        this.namespace = namespace;
    }

    @Override
    public void provider(ItemModelProvider provider) {
        provider.buildModels(this.getItemModelPipeline());
    }

    private FilePipeline getItemModelPipeline() {
        return new FilePipelineImpl(FileManager.getRootDirectoryFile("temp/resource-pack/assets").getPath(), this.namespace, "model");
    }
}
