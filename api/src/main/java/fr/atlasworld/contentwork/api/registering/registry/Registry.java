package fr.atlasworld.contentwork.api.registering.registry;

import fr.atlasworld.contentwork.api.registering.event.RegisterEvent;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Stream;

/**
 * The Registry is an immutable system (If implemented correctly),
 * That allows registering specific instance or values and then becoming immutable for the rest of it's life-cycle
 * @param <T> Object Type for the registry
 */
public interface Registry<T> {

    /**
     * Get the key of the Registry
     * @return key
     */
    @NotNull
    NamespacedKey getRegistryKey();

    /**
     * Register a new element or value into the registry, this should only be triggered from the Register Class
     * @param key registry key of the element
     * @param obj the object/value to register
     * @param event the registering event
     * @see fr.atlasworld.contentwork.api.registering.Register
     * @return the registered object
     */
    T register(NamespacedKey key, T obj, RegisterEvent<T> event);

    /**
     * Get the key of a specific element in the registry
     * @param obj element/value
     * @return key
     */
    NamespacedKey getKey(T obj);

    /**
     * Get the element from its registry key
     * @param key element's registry key
     * @return element/value
     */
    T get(NamespacedKey key);

    /**
     * Get all entries of the registry
     * @return all entries
     */
    Stream<Map.Entry<NamespacedKey, T>> getEntries();
}
