package fr.atlasworld.contentwork.registering;

import fr.atlasworld.contentwork.api.common.item.Item;
import fr.atlasworld.contentwork.api.registering.event.RegisterItemEvent;
import fr.atlasworld.contentwork.api.registering.registry.Registry;
import fr.atlasworld.contentwork.registering.registries.ItemRegistry;
import org.bukkit.plugin.PluginManager;

public class DefaultRegistries {
    public static Registry<Item> ITEM = new ItemRegistry();

    public static void register(PluginManager manager) {
        manager.callEvent(new RegisterItemEvent(ITEM));
    }
}
