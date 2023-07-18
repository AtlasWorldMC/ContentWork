package fr.atlasworld.contentwork.api.registering.registry;

import fr.atlasworld.contentwork.api.registering.event.RegisterEvent;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Stream;

public interface Registry<T> {
    @NotNull
    NamespacedKey getRegistryKey();
    T register(NamespacedKey key, T obj, RegisterEvent<T> event);
    NamespacedKey getKey(T obj);
    T get(NamespacedKey key);
    Stream<Map.Entry<NamespacedKey, T>> getEntries();
}
