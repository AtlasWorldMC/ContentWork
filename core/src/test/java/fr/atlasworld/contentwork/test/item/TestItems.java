package fr.atlasworld.contentwork.test.item;

import fr.atlasworld.contentwork.api.common.item.Item;
import fr.atlasworld.contentwork.api.registering.RegistryObject;
import fr.atlasworld.contentwork.api.registering.event.RegisterItemEvent;
import fr.atlasworld.contentwork.api.registering.Register;
import fr.atlasworld.contentwork.test.ContentWorkTest;
import io.papermc.paper.inventory.ItemRarity;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TestItems implements Listener {
    private static final Register<Item> ITEMS = new Register<>(ContentWorkTest.plugin);

    public static final RegistryObject<Item> CUSTOM_ITEM = ITEMS.register("custom_item", () ->
            new Item(Material.STICK, new Item.Properties().rarity(ItemRarity.RARE)));


    @EventHandler
    public void register(RegisterItemEvent event) {
        ITEMS.register(event);
        ContentWorkTest.plugin.getSLF4JLogger().info("Item registered!");
    }
}
