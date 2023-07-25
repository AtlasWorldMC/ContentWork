package fr.atlasworld.contentwork.api.data.model.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.atlasworld.contentwork.api.data.generator.FilePipeline;
import org.bukkit.NamespacedKey;

public class ItemModelBuilder extends ModelBuilder<ItemModelBuilder> {
    public ItemModelBuilder(NamespacedKey location, FilePipeline pipeline) {
        super(location, pipeline);
    }

    public ItemModelBuilder(NamespacedKey location, FilePipeline pipeline, JsonObject json) {
        super(location, pipeline, json);
    }

    public ItemModelBuilder override(OverrideBuilder override) {
        if (!this.json.has("overrides")) {
            this.json.add("overrides", new JsonArray());
        }

        this.json.get("overrides").getAsJsonArray().add(override.toJson());
        return this;
    }
}
