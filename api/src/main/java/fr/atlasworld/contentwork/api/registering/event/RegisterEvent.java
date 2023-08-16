package fr.atlasworld.contentwork.api.registering.event;

import fr.atlasworld.contentwork.api.registering.registry.Registry;
import org.bukkit.event.Event;

/**
 * This event is triggered when the registering for that specific registry types begins.
 * This can be extended for your own custom registry
 * @param <T> Registry Type
 * @see RegisterItemEvent
 */
public abstract class RegisterEvent<T> extends Event {
    protected final Registry<T> registry;

    public RegisterEvent(Registry<T> registry) {
        this.registry = registry;
    }

    public RegisterEvent(boolean isAsync, Registry<T> registry) {
        super(isAsync);
        this.registry = registry;
    }

    /**
     * Gets the Registry Attached to the event
     * @return registry
     */
    public Registry<T> getRegistry() {
        return registry;
    }
}
