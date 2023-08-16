package fr.atlasworld.contentwork.api.data.model;

import com.google.common.base.Preconditions;
import fr.atlasworld.contentwork.api.data.generator.FilePipeline;
import org.bukkit.NamespacedKey;

/**
 * Represents a minecraft model file
 */
public abstract class ModelFile {
    protected final NamespacedKey location;

    public ModelFile(NamespacedKey location) {
        this.location = location;
    }

    /**
     * Returns if the file exists
     * @return file exists
     */
    public abstract boolean exists();

    /**
     * Returns the model file location
     * @return model file location
     */
    public NamespacedKey getLocation() {
        assertExistence();
        return location;
    }

    /**
     * Asserts file that the model file exists
     */
    public void assertExistence() {
        Preconditions.checkState(exists(), "Model at %s does not exist", location);
    }

    /**
     * Basically a mock model file, it always returns true when exists function is triggered
     */
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

    /**
     * Represents an existing model file
     */
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
