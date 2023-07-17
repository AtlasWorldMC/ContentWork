package fr.atlasworld.contentwork.api.registering.event;

import fr.atlasworld.contentwork.api.registering.registry.Registry;
import org.bukkit.event.Event;

public abstract class RegisterEvent<T> extends Event {
    protected final Registry<T> registry;

    public RegisterEvent(Registry<T> registry) {
        this.registry = registry;
    }

    public RegisterEvent(boolean isAsync, Registry<T> registry) {
        super(isAsync);
        this.registry = registry;
    }

    public Registry<T> getRegistry() {
        return registry;
    }
}
