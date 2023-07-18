package fr.atlasworld.contentwork.registering.registries;

import com.google.common.collect.HashBiMap;
import fr.atlasworld.contentwork.api.common.item.Item;
import fr.atlasworld.contentwork.api.registering.event.RegisterEvent;
import fr.atlasworld.contentwork.api.registering.registry.IItemRegistry;
import fr.atlasworld.contentwork.api.registering.registry.SimpleRegistry;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ItemRegistry extends SimpleRegistry<Item> implements IItemRegistry {
    private final Map<NamespacedKey, Integer> customModelIdHolder;
    private int customModelIndex = 0;

    public ItemRegistry() {
        super(HashBiMap.create());
        this.customModelIdHolder = new HashMap<>();
    }

    @Override
    public @NotNull NamespacedKey getRegistryKey() {
        return new NamespacedKey("content_work", "item");
    }

    @Override
    public Item register(NamespacedKey key, Item obj, RegisterEvent<Item> event) {
        this.customModelIdHolder.put(key, this.customModelIndex);
        this.customModelIndex++;
        return super.register(key, obj, event);
    }

    public int getCustomModel(NamespacedKey key) {
        return this.customModelIdHolder.get(key);
    }
}
