package fr.atlasworld.contentwork.api.data.model.override;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Map;

public class ItemModelOverride {
    protected final JsonObject jsonOverride;
    //private final NamespacedKey model;
    //private final Map<OverridePredicates, Float> predicates;

    public ItemModelOverride(JsonObject json) {
        this.jsonOverride = json;
    }

    public ItemModelOverride(NamespacedKey model) {
        this.jsonOverride = new JsonObject();
        this.jsonOverride.addProperty("model", model.asString());
    }

    public void addPredicate(OverridePredicates predicate, Float value) {
        if (!this.jsonOverride.has("predicate")) {
            this.jsonOverride.add("predicate", new JsonObject());
        }
        this.jsonOverride.get("predicate").getAsJsonObject().addProperty(predicate.name().toLowerCase(), value);
    }

    public NamespacedKey getModel() {
        return NamespacedKey.fromString(this.jsonOverride.get("model").getAsString());
    }

    public Map<OverridePredicates, Float> getPredicates() {
        if (!this.jsonOverride.has("pre"));
        //TODO: FINISH HERE
        return null;
    }

    public JsonElement build() {
        //TODO: FINISh HERE
        return null;
    }
}
