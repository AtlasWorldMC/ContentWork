package fr.atlasworld.contentwork.api.registering.registry;

import fr.atlasworld.contentwork.api.common.item.Item;
import org.bukkit.NamespacedKey;

public interface IItemRegistry extends Registry<Item> {
    int getCustomModel(NamespacedKey key);
}
