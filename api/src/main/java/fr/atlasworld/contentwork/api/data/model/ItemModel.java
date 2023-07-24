package fr.atlasworld.contentwork.api.data.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.atlasworld.contentwork.api.data.model.override.ItemModelOverride;
import fr.atlasworld.contentwork.api.data.model.override.OverridePredicates;
import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.List;

public class ItemModel extends Model {

    public void addOverride(ItemModelOverride override) {
        if (!this.modelJson.has("overrides")) {
            this.modelJson.add("overrides", new JsonArray());
        }
        this.modelJson.get("overrides").getAsJsonArray().add(override.build);
    }

    public void addOverrides(ItemModelOverride... overrides) {
        if (this.overrides == null) {
            this.setOverrides(List.of(overrides));
            return;
        }
        this.overrides.addAll(List.of(overrides));
    }

    public void setOverrides(List<ItemModelOverride> overrides) {
        this.overrides = overrides;
    }

    public void clearOverrides() {
        this.overrides.clear();
    }

    public List<ItemModelOverride> getOverrides() {
        return overrides;
    }

    //Static field
    public static ItemModel fromJson(JsonObject json) {
        ItemModel model = new ItemModel();

        if (json.has("parent")) {
            model.setParent(NamespacedKey.fromString(json.get("parent").getAsString()));
        }
        if (json.has("textures")) {
            json.get("textures").getAsJsonObject().entrySet().forEach(entry ->
                    model.addTexture(entry.getKey(), NamespacedKey.fromString(entry.getValue().getAsString())));
        }
        if (json.has("overrides")) {
            json.get("overrides").getAsJsonArray().forEach(element -> {
                JsonObject override = element.getAsJsonObject();
                ItemModelOverride modelOverride = new ItemModelOverride(NamespacedKey.fromString(override.get("model").getAsString()));

                override.get("predicate").getAsJsonObject().entrySet().forEach(entry -> {
                    OverridePredicates overridePredicate = OverridePredicates.valueOf(entry.getKey().toUpperCase());
                    modelOverride.addPredicate(overridePredicate, entry.getValue().getAsFloat());
                });

                model.addOverride(modelOverride);
            });
        }

        return model;
    }
}
