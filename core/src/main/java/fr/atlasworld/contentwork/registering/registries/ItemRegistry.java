package fr.atlasworld.contentwork.registering.registries;

import com.google.common.collect.HashBiMap;
import fr.atlasworld.contentwork.api.common.item.Item;
import fr.atlasworld.contentwork.api.registering.event.RegisterEvent;
import fr.atlasworld.contentwork.api.registering.registry.SimpleRegistry;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class ItemRegistry extends SimpleRegistry<Item> {
    private final Map<NamespacedKey, AbstractMap.SimpleEntry<Material, Float>> customModelIdHolder;
    private final Map<Material, Float> materialModelHolder;

    public ItemRegistry() {
        super(HashBiMap.create());
        this.customModelIdHolder = new HashMap<>();
        this.materialModelHolder = new HashMap<>();
    }

    @Override
    public @NotNull NamespacedKey getRegistryKey() {
        return new NamespacedKey("content_work", "item");
    }

    @Override
    public Item register(NamespacedKey key, Item obj, RegisterEvent<Item> event) {
        if (this.materialModelHolder.containsKey(obj.getParent())) {
            this.materialModelHolder.put(obj.getParent(), this.materialModelHolder.get(obj.getParent()) + 1);
        } else {
            this.materialModelHolder.put(obj.getParent(), 1F);
        }

        this.customModelIdHolder.put(key, new AbstractMap.SimpleEntry<>(obj.getParent(), this.materialModelHolder.get(obj.getParent())));
        return super.register(key, obj, event);
    }

    public float getCustomModel(NamespacedKey key) {
        return this.customModelIdHolder.get(key).getValue();
    }
}
