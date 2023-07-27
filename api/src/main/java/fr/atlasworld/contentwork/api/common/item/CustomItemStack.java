package fr.atlasworld.contentwork.api.common.item;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public class CustomItemStack {
    private static final CustomItemHelper helper;

    static {
        helper = Bukkit.getServicesManager().load(CustomItemHelper.class);
    }

    public static ItemStack create(Item item) {
        return helper.createItemStack(item, 1);
    }

    public static ItemStack create(Item item, int count) {
        return helper.createItemStack(item, count);
    }
}
