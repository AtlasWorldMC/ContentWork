package fr.atlasworld.contentwork.api.common.item;

import org.bukkit.inventory.ItemStack;

public interface CustomItemHelper {
    ItemStack createItemStack(Item item, int count);
}
