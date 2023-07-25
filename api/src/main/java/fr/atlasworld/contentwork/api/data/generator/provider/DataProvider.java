package fr.atlasworld.contentwork.api.data.generator.provider;

import fr.atlasworld.contentwork.api.data.generator.FilePipeline;

public interface DataProvider {
    void generate(FilePipeline pipeline);
    void run(FilePipeline pipeline);
    String getDirectory();
}
