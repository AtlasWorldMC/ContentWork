package fr.atlasworld.contentwork.api.registering.registry;

import fr.atlasworld.contentwork.api.exception.RegisteringException;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Register<T> {
    private final Plugin plugin;
    private final Map<NamespacedKey, T> entries;
    private boolean hasSeenRegister = false;

    public Register(Plugin plugin) {
        this.plugin = plugin;
        this.entries = new HashMap<>();
    }

    public T register(String key, Supplier<T> supplier) {
        if (hasSeenRegister) {
            throw new RegisteringException("Cannot register new entries after the register event!");
        }

        NamespacedKey nameKey = new NamespacedKey(plugin, key);
        T entry = supplier.get();

        this.entries.put(nameKey, entry);
        return entry;
    }

    public void register(Registry<T> registry) {
        this.hasSeenRegister = true;
        this.entries.forEach(registry::register);
    }
}
