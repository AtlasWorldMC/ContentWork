package fr.atlasworld.contentwork.api.data.model.builder;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import fr.atlasworld.contentwork.api.data.generator.FilePipeline;
import fr.atlasworld.contentwork.api.data.model.ModelFile;
import fr.atlasworld.contentwork.api.utils.StringRepresentable;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class OverrideBuilder {
    private ModelFile model;
    private final Map<Predicates, Float> predicates = new LinkedHashMap<>();

    public OverrideBuilder() {
    }

    public OverrideBuilder(@NotNull JsonObject json, @NotNull FilePipeline pipeline) {
        Preconditions.checkNotNull(json, "Json cannot be null!");
        Preconditions.checkNotNull(pipeline, "FilePipeline cannot be null!");
        this.model = new ItemModelBuilder(NamespacedKey.fromString(json.get("model").getAsString()), pipeline);
        json.get("predicate").getAsJsonObject().entrySet().forEach(entry ->
                predicate(Predicates.fromString(entry.getKey()), entry.getValue().getAsFloat()));
    }

    public OverrideBuilder model(ModelFile model) {
        this.model = model;
        model.assertExistence();
        return this;
    }

    public OverrideBuilder predicate(@NotNull Predicates predicate, float value) {
        Preconditions.checkNotNull(predicate, "Predicate cannot be null!");
        this.predicates.put(predicate, value);
        return this;
    }

    public JsonObject toJson() {
        JsonObject ret = new JsonObject();
        JsonObject predicatesJson = new JsonObject();
        predicates.forEach((key, val) -> predicatesJson.addProperty(key.getSerializedName(), val));
        ret.add("predicate", predicatesJson);
        ret.addProperty("model", model.getLocation().asString());
        return ret;
    }


    public enum Predicates implements StringRepresentable {
        ANGLE,
        BLOCKING,
        BROKEN,
        CAST,
        COOLDOWN,
        DAMAGE,
        DAMAGED,
        LEFTHANDED,
        PULL,
        PULLING,
        CHARGED,
        FIREWORK,
        THROWING,
        TIME,
        CUSTOM_MODEL_DATA,
        LEVEL,
        FILLED,
        TOOTING,
        TRIM_TYPE,
        BRUSHING;

        @Override
        public @NotNull String getSerializedName() {
            return this.name().toLowerCase();
        }

        public static @Nullable Predicates fromString(String name) {
            try {
                return valueOf(name.toUpperCase());
            } catch (Exception e) {
                return null;
            }
        }
    }
}
