package fr.atlasworld.contentwork.api.registering.registry;

import com.google.common.collect.BiMap;
import fr.atlasworld.contentwork.api.registering.event.RegisterEvent;
import org.bukkit.NamespacedKey;

import java.util.Map;
import java.util.stream.Stream;

public abstract class SimpleRegistry<T> implements Registry<T> {
    protected final BiMap<NamespacedKey, T> registryItems;

    public SimpleRegistry(BiMap<NamespacedKey, T> registryItems) {
        this.registryItems = registryItems;
    }

    @Override
    public T register(NamespacedKey key, T obj, RegisterEvent<T> event) {
        return this.registryItems.putIfAbsent(key, obj);
    }

    @Override
    public NamespacedKey getKey(T obj) {
        return this.registryItems.inverse().get(obj);
    }

    @Override
    public T get(NamespacedKey key) {
        return this.registryItems.get(key);
    }

    @Override
    public Stream<Map.Entry<NamespacedKey, T>> getEntries() {
        return registryItems.entrySet().stream();
    }
}
