package fr.atlasworld.contentwork.api.datagen.event;

import fr.atlasworld.contentwork.api.datagen.DataGenerationHandler;
import fr.atlasworld.contentwork.api.datagen.data.DataProvider;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Triggered when ContentWork combines all data from the plugins
 */
public class BuildDataEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final DataGenerationHandler handler;

    public BuildDataEvent(DataGenerationHandler handler) {
        this.handler = handler;
    }

    public BuildDataEvent(boolean isAsync, DataGenerationHandler handler) {
        super(isAsync);
        this.handler = handler;
    }

    public DataProvider getProvider(Plugin plugin) {
        return this.handler.buildDataProvider(plugin);
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
