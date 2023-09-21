package fr.atlasworld.contentwork.api.common.item;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface CustomItemHelper {
    ItemStack createItemStack(Item item, int count);
    boolean isCustomItem(ItemStack stack);
}
