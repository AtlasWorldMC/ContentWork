package fr.atlasworld.contentwork.api.registering;

import org.bukkit.NamespacedKey;

import javax.annotation.Nullable;

public class RegistryObject<T> {
    private final T data;
    private final NamespacedKey key;

    public RegistryObject(T data, NamespacedKey key) {
        this.data = data;
        this.key = key;
    }

    @Nullable
    public T get() {
        return data;
    }

    public NamespacedKey getKey() {
        return key;
    }
}
