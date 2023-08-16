package fr.atlasworld.contentwork.api.data.model.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.atlasworld.contentwork.api.data.generator.FilePipeline;
import org.bukkit.NamespacedKey;

/**
 * Item Model File Builder
 */
public class ItemModelBuilder extends ModelBuilder<ItemModelBuilder> {
    public ItemModelBuilder(NamespacedKey location, FilePipeline pipeline) {
        super(location, pipeline);
    }

    public ItemModelBuilder(NamespacedKey location, FilePipeline pipeline, JsonObject json) {
        super(location, pipeline, json);
    }

    /**
     * Adds an override to the model file
     * @param override model override
     * @return model builder
     */
    public ItemModelBuilder override(OverrideBuilder override) {
        if (!this.json.has("overrides")) {
            this.json.add("overrides", new JsonArray());
        }

        this.json.get("overrides").getAsJsonArray().add(override.toJson());
        return this;
    }
}
