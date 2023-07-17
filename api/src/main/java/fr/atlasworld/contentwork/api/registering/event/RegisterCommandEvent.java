package fr.atlasworld.contentwork.api.registering.event;

import com.mojang.brigadier.CommandDispatcher;
import fr.atlasworld.contentwork.api.common.item.Item;
import fr.atlasworld.contentwork.api.registering.registry.Registry;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RegisterCommandEvent extends RegisterEvent<CommandDispatcher<CommandSender>> {
    private static final HandlerList handlers = new HandlerList();

    public RegisterCommandEvent(Registry<CommandDispatcher<CommandSender>> registry) {
        super(registry);
    }

    public RegisterCommandEvent(boolean isAsync, Registry<CommandDispatcher<CommandSender>> registry) {
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
