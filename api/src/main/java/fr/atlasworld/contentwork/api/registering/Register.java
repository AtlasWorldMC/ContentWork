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

/**
 * Register Class, handles all the registering, adding elements to a registry should only happen through a Register
 * @param <T> The Value type of the registry
 */
public class Register<T> {
    private final Plugin plugin;
    private final Map<NamespacedKey, T> entries;
    private boolean hasSeenRegistry = false;

    public Register(Plugin plugin) {
        this.plugin = plugin;
        this.entries = new HashMap<>();
    }

    /**
     * Registers a new element to add to the registry,
     * throws a registering exception if the register is already registered
     * @param key element key will be combined with the plugin namespace later
     * @param supplier Supplier that supplies the element to register
     * @return a RegistryObject containing the key and the element
     */
    public RegistryObject<T> register(String key, Supplier<T> supplier) {
        if (this.hasSeenRegistry) {
            throw new RegisteringException("Cannot register new entries after the register event!");
        }

        NamespacedKey nameKey = new NamespacedKey(this.plugin.getName().toLowerCase(Locale.ROOT).replace("-", "_"), key);
        T entry = supplier.get();

        this.entries.put(nameKey, entry);
        return new RegistryObject<>(entry, nameKey);
    }

    /**
     * Registers all the register's saved elements to the registry
     * @param event registry event
     */
    public void register(RegisterEvent<T> event) {
        this.hasSeenRegistry = true;
        this.entries.forEach((key, value) -> event.getRegistry().register(key, value, event));
    }
}
