package fr.atlasworld.contentwork.api.data.model;

import com.google.common.base.Preconditions;
import fr.atlasworld.contentwork.api.data.generator.FilePipeline;
import org.bukkit.NamespacedKey;

public abstract class ModelFile {
    protected final NamespacedKey location;

    public ModelFile(NamespacedKey location) {
        this.location = location;
    }

    public abstract boolean exists();

    public NamespacedKey getLocation() {
        assertExistence();
        return location;
    }

    public void assertExistence() {
        Preconditions.checkState(exists(), "Model at %s does not exist", location);
    }

    public static class UncheckedModelFile extends ModelFile {

        public UncheckedModelFile(String location) {
            this(location.contains(":") ? NamespacedKey.fromString(location) : NamespacedKey.minecraft(location));
        }

        public UncheckedModelFile(NamespacedKey location) {
            super(location);
        }

        @Override
        public boolean exists() {
            return true;
        }
    }

    public static class ExistingModelFile extends ModelFile {
        private final FilePipeline pipeline;

        public ExistingModelFile(NamespacedKey location, FilePipeline pipeline) {
            super(location);
            this.pipeline = pipeline;
        }

        @Override
        public boolean exists() {
            return this.pipeline.fileExists(this.location);
        }
    }
}
