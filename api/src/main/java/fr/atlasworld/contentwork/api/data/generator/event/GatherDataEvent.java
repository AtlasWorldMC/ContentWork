package fr.atlasworld.contentwork.api.data.generator.event;

import fr.atlasworld.contentwork.api.data.generator.DataGenerator;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

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

    public DataGenerator getDataGenerator() {
        return dataGenerator;
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
