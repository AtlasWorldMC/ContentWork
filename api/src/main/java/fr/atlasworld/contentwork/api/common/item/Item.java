package fr.atlasworld.contentwork.api.common.item;

import io.papermc.paper.inventory.ItemRarity;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Item {
    protected final Material material;
    protected final Properties properties;

    public Item(Material material, Properties properties) {
        this.material = material;
        this.properties = properties;
    }

    public ItemStack getItem(int count) {
        ItemStack item = new ItemStack(this.material, count);


        return item;
    }

    public static class Properties {
        ItemRarity rarity = ItemRarity.COMMON;

        public Item.Properties rarity(ItemRarity rarity) {
            this.rarity = rarity;
            return this;
        }
    }
}
