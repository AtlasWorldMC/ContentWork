package fr.atlasworld.contentwork.api.registering.event;

import fr.atlasworld.contentwork.api.common.item.Item;
import fr.atlasworld.contentwork.api.registering.registry.Registry;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RegisterItemEvent extends RegisterEvent<Item> {
    private static final HandlerList handlers = new HandlerList();

    public RegisterItemEvent(Registry<Item> registry) {
        super(registry);
    }

    public RegisterItemEvent(boolean isAsync, Registry<Item> registry) {
        super(isAsync, registry);
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
