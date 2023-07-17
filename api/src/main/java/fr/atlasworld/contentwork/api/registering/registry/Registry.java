package fr.atlasworld.contentwork.api.registering.registry;

import org.bukkit.NamespacedKey;

import java.util.Map;
import java.util.stream.Stream;

public interface Registry<T> {
    T register(NamespacedKey key, T obj);
    NamespacedKey getKey(T obj);
    T get(NamespacedKey key);
    Stream<Map.Entry<NamespacedKey, T>> getEntries();
}
