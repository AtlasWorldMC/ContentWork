package fr.atlasworld.contentwork.api.data.generator.event;

import fr.atlasworld.contentwork.api.data.generator.DataGenerator;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Triggered when the data generation begins, allows adding providers for assets or data generation
 */
public class GatherDataEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final DataGenerator dataGenerator;

    public GatherDataEvent(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    public GatherDataEvent(boolean isAsync, DataGenerator dataGenerator) {
        super(isAsync);
        this.dataGenerator = dataGenerator;
    }

    /**
     * Gets the Data Generator
     * @return Data Generator of the event
     */
    public DataGenerator getDataGenerator() {
        return dataGenerator;
    }

    //Bukkit
    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
