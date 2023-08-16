package fr.atlasworld.contentwork.api.registering.registry;

import org.bukkit.NamespacedKey;

/**
 * Registry Helper Class
 */
public interface RegistryManager {
    /**
     * Get the registry from its key
     * @param registry registry key
     * @return the registry
     * @param <T> type of the registry
     */
    <T> Registry<T> getRegistry(NamespacedKey registry);
}
