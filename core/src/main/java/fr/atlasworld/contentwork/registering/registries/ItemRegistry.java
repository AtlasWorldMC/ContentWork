package fr.atlasworld.contentwork.registering.registries;

import com.google.common.collect.HashBiMap;
import fr.atlasworld.contentwork.api.common.item.Item;
import fr.atlasworld.contentwork.api.registering.registry.IItemRegistry;
import fr.atlasworld.contentwork.api.registering.registry.SimpleRegistry;
import org.bukkit.NamespacedKey;

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
    public Item register(NamespacedKey key, Item obj) {
        this.customModelIdHolder.put(key, this.customModelIndex);
        this.customModelIndex++;
        return super.register(key, obj);
    }

    public int getCustomModel(NamespacedKey key) {
        return this.customModelIdHolder.get(key);
    }
}
