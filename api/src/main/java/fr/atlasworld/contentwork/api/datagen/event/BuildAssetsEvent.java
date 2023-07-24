package fr.atlasworld.contentwork.api.datagen.event;

import fr.atlasworld.contentwork.api.datagen.DataGenerationHandler;
import fr.atlasworld.contentwork.api.datagen.assets.AssetProvider;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Triggered when ContentWork combines all assets from the plugins
 */
public class BuildAssetsEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final DataGenerationHandler handler;

    public BuildAssetsEvent(DataGenerationHandler handler) {
        this.handler = handler;
    }

    public BuildAssetsEvent(boolean isAsync, DataGenerationHandler handler) {
        super(isAsync);
        this.handler = handler;
    }

    public AssetProvider getProvider(Plugin plugin) {
        return this.handler.buildAssetProvider(plugin);
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
