package fr.atlasworld.contentwork.api.data.model.builder;

import com.google.gson.JsonObject;
import fr.atlasworld.contentwork.api.data.generator.FilePipeline;
import org.bukkit.NamespacedKey;

public class BlockModelBuilder extends ModelBuilder<BlockModelBuilder> {
    public BlockModelBuilder(NamespacedKey location, FilePipeline pipeline) {
        super(location, pipeline);
    }

    public BlockModelBuilder(NamespacedKey location, FilePipeline pipeline, JsonObject json) {
        super(location, pipeline, json);
    }
}
