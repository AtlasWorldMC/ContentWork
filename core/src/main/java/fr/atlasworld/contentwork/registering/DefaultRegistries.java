package fr.atlasworld.contentwork.registering;

import fr.atlasworld.contentwork.api.common.item.Item;
import fr.atlasworld.contentwork.api.registering.event.RegisterItemEvent;
import fr.atlasworld.contentwork.api.registering.registry.Registry;
import fr.atlasworld.contentwork.registering.registries.ItemRegistry;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

public class DefaultRegistries {
    private static final List<Registry<?>> REGISTRIES = new ArrayList<>();

    public static Registry<Item> ITEM = register(new ItemRegistry());

    private static <T> Registry<T> register(Registry<T> registry) {
        REGISTRIES.add(registry);
        return registry;
    }

    public static void register(PluginManager manager) {
        manager.callEvent(new RegisterItemEvent(ITEM));
    }
    public static Registry<?> getRegistry(NamespacedKey key) {
        return REGISTRIES.stream()
                .filter(registry -> registry.getRegistryKey().equals(key))
                .findFirst().orElse(null);
    }
}
