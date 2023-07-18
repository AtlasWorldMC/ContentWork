package fr.atlasworld.contentwork.registering.registry;

import fr.atlasworld.contentwork.api.registering.registry.Registry;
import fr.atlasworld.contentwork.api.registering.registry.RegistryManager;
import fr.atlasworld.contentwork.registering.DefaultRegistries;
import org.bukkit.NamespacedKey;

@SuppressWarnings("unchecked")
public class RegistryManagerImpl implements RegistryManager {
    @Override
    public <T> Registry<T> getRegistry(NamespacedKey registry) {
        return (Registry<T>) DefaultRegistries.getRegistry(registry);
    }
}
