package fr.atlasworld.contentwork.api.registering;

import fr.atlasworld.contentwork.api.exception.RegisteringException;
import fr.atlasworld.contentwork.api.registering.event.RegisterEvent;
import fr.atlasworld.contentwork.api.registering.registry.Registry;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class Register<T> {
    private final Plugin plugin;
    private final Map<NamespacedKey, T> entries;
    private boolean hasSeenRegistry = false;

    public Register(Plugin plugin) {
        this.plugin = plugin;
        this.entries = new HashMap<>();
    }

    public RegistryObject<T> register(String key, Supplier<T> supplier) {
        if (hasSeenRegistry) {
            throw new RegisteringException("Cannot register new entries after the register event!");
        }

        NamespacedKey nameKey = new NamespacedKey(this.plugin.getName().toLowerCase(Locale.ROOT).replace("-", "_"), key);
        T entry = supplier.get();

        this.entries.put(nameKey, entry);
        return new RegistryObject<>(entry, nameKey);
    }

    public void register(RegisterEvent<T> event) {
        this.hasSeenRegistry = true;
        this.entries.forEach((key, value) -> event.getRegistry().register(key, value, event));
    }
}
