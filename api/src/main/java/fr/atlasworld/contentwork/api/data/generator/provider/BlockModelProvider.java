package fr.atlasworld.contentwork.api.data.generator.provider;

import fr.atlasworld.contentwork.api.data.model.builder.BlockModelBuilder;
import org.bukkit.plugin.Plugin;

public abstract class BlockModelProvider extends ModelProvider<BlockModelBuilder> {
    public BlockModelProvider(Plugin plugin) {
        super(plugin, BlockModelBuilder::new);
    }

    @Override
    public String getDirectory() {
        return "models/block/";
    }
}
