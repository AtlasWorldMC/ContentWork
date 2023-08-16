package fr.atlasworld.contentwork.api.data.generator.provider;

import fr.atlasworld.contentwork.api.data.generator.FilePipeline;
import org.jetbrains.annotations.ApiStatus;

/**
 * Handles the generation of the files
 */
public interface DataProvider {
    /**
     * Triggered when the data generation begins for that specific data provider
     * @param pipeline FilePipeline
     */
    void generate(FilePipeline pipeline);

    @ApiStatus.Internal
    void run(FilePipeline pipeline);

    /**
     * Gets the directory where the data provider creates its files.
     * @return Directory
     */
    String getDirectory();
}
