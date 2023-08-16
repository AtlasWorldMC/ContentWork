package fr.atlasworld.contentwork.api.common.item;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

/**
 * Helper class for custom items
 */
public class CustomItemStack {
    private static final CustomItemHelper helper;

    static {
        helper = Bukkit.getServicesManager().load(CustomItemHelper.class);
    }

    /**
     * Creates an item stack for a give item
     * @param item custom item
     * @return the ItemStack containing the custom item
     */
    public static ItemStack create(Item item) {
        return helper.createItemStack(item, 1);
    }

    /**
     * Creates an item stack for a give item with a set amount
     * @param count item stack size
     * @param item custom item
     * @return the ItemStack containing the custom item
     */
    public static ItemStack create(Item item, int count) {
        return helper.createItemStack(item, count);
    }
}
