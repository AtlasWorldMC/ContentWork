package fr.atlasworld.contentwork.api.datagen;

import fr.atlasworld.contentwork.api.data.model.Model;

public interface FilePipeline {
    void save(Model file, String filename);
}
