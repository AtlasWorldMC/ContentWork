package fr.atlasworld.contentwork.test;

import fr.atlasworld.contentwork.api.common.item.Item;
import fr.atlasworld.contentwork.api.registering.registry.Registry;
import fr.atlasworld.contentwork.api.registering.registry.RegistryManager;
import fr.atlasworld.contentwork.test.datagen.DataGenListener;
import fr.atlasworld.contentwork.test.item.TestItems;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ContentWorkTest extends JavaPlugin {
    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        Bukkit.getPluginManager().registerEvents(new TestItems(), this);
        Bukkit.getPluginManager().registerEvents(new DataGenListener(), this);
    }

    @Override
    public void onDisable() {

    }
}
