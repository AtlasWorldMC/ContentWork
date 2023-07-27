package fr.atlasworld.contentwork.api.common.item;

import fr.atlasworld.contentwork.api.registering.registry.Registry;
import fr.atlasworld.contentwork.api.registering.registry.RegistryManager;
import io.papermc.paper.inventory.ItemRarity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {
    protected final Material parent;
    protected final Properties properties;

    public Item(Material parent, Properties properties) {
        this.parent = parent;
        this.properties = properties;
    }

    public Material getParent() {
        return parent;
    }

    public static class Properties {
        ItemRarity rarity = ItemRarity.COMMON;

        public Item.Properties rarity(ItemRarity rarity) {
            this.rarity = rarity;
            return this;
        }
    }
}
