package fr.atlasworld.contentwork.api.data.model.builder;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import fr.atlasworld.contentwork.api.data.generator.FilePipeline;
import fr.atlasworld.contentwork.api.data.model.ModelFile;
import fr.atlasworld.contentwork.api.data.model.core.GuiLight;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

/**
 * Builder for minecraft's json model format
 * @param <T> Simply for returning the correct inheritance
 */
public class ModelBuilder<T extends ModelBuilder<T>> extends ModelFile {
    protected final FilePipeline pipeline;
    protected final JsonObject json;

    public ModelBuilder(NamespacedKey location, FilePipeline pipeline) {
        super(location);
        this.pipeline = pipeline;
        this.json = new JsonObject();
    }

    public ModelBuilder(NamespacedKey location, FilePipeline pipeline, JsonObject json) {
        super(location);
        this.pipeline = pipeline;
        this.json = json;
    }

    @Override
    public boolean exists() {
        return true;
    }

    public T parent(ModelFile parent) {
        this.json.addProperty("parent", parent.getLocation().asString());
        return self();
    }

    //Used to create the texture block
    private void checkTexture() {
        if (!this.json.has("textures")) {
            this.json.add("textures", new JsonObject());
        }
    }

    public T texture(@NotNull String key, @NotNull String texture) {
        Preconditions.checkNotNull(key, "Key must not be null");
        Preconditions.checkNotNull(texture, "Texture must not be null");

        this.checkTexture();

        if (texture.charAt(0) == '#') {
            this.json.get("textures").getAsJsonObject().addProperty(key, texture);
            return self();
        }

        NamespacedKey asLocation;
        if (texture.contains(":")) {
            asLocation = NamespacedKey.fromString(texture);
        } else {
            asLocation = new NamespacedKey(this.location.getNamespace(), texture);
        }
        return this.texture(key, asLocation);
    }

    public T texture(String key, NamespacedKey texture) {
        Preconditions.checkNotNull(key, "Key must not be null");
        Preconditions.checkNotNull(texture, "Texture must not be null");

        this.checkTexture();

        this.json.get("textures").getAsJsonObject().addProperty(key, texture.asString());
        return self();
    }

    public T ambientOcclusion(boolean ambientOcclusion) {
        this.json.addProperty("ambientocclusion", ambientOcclusion);
        return self();
    }

    public T guiLight(GuiLight light) {
        this.json.addProperty("gui_light", light.getSerializedName());
        return self();
    }

    public JsonObject build() {
        return this.json;
    }

    @SuppressWarnings("unchecked")
    private T self() {
        return (T) this;
    }
}
