package fr.atlasworld.contentwork.api.registering.registry;

import org.bukkit.NamespacedKey;

public interface RegistryManager {
    <T> Registry<T> getRegistry(NamespacedKey registry);
}
