package fr.atlasworld.contentwork.api.data.model;

import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Model {
    protected final JsonObject modelJson;

    public Model() {
        this.modelJson = new JsonObject();
    }

    public Model(JsonObject json) {
        this.modelJson = json;
    }

    public void setParent(NamespacedKey parent) {
        this.modelJson.addProperty("parent", parent.asString());
    }

    public NamespacedKey getParent() {
        return NamespacedKey.fromString(this.modelJson.getAsString());
    }

    public void addTexture(String variable, NamespacedKey texture) {
        if (!this.modelJson.has("textures")) {
            this.modelJson.add("textures", new JsonObject());
        }
        this.modelJson.get("textures").getAsJsonObject().addProperty(variable, texture.asString());
    }

    public void addTextures(Map<String, NamespacedKey> textures) {
        textures.forEach(this::addTexture);
    }

    public void clearTextures() {
        this.modelJson.remove("textures");
    }

    public Set<Map.Entry<String, NamespacedKey>> getTextures() {
        if (!this.modelJson.has("textures")) {
            return new HashSet<>();
        }
        return this.modelJson.get("textures").getAsJsonObject().entrySet()
                .stream()
                .map(entry -> Map.entry(entry.getKey(), NamespacedKey.fromString(entry.getValue().getAsString())))
                .collect(Collectors.toSet());
    }

    public JsonObject build() {
        return this.modelJson;
    }
}
