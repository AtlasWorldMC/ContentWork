package fr.atlasworld.contentwork;

import fr.atlasworld.contentwork.api.common.item.CustomItemHelper;
import fr.atlasworld.contentwork.api.registering.registry.RegistryManager;
import fr.atlasworld.contentwork.common.item.CustomItemHelperImpl;
import fr.atlasworld.contentwork.listener.PlayerEventListener;
import fr.atlasworld.contentwork.listener.ServerEventListener;
import fr.atlasworld.contentwork.registering.DefaultRegistries;
import fr.atlasworld.contentwork.registering.registries.ItemRegistry;
import fr.atlasworld.contentwork.registering.registry.RegistryManagerImpl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

public class ContentWork extends JavaPlugin {
    public static Logger logger;

    @Override
    public void onEnable() {
        logger = this.getSLF4JLogger();
        ContentWork.logger.info("Initializing ContentWork...");

        //Register Services
        Bukkit.getServicesManager().register(RegistryManager.class, new RegistryManagerImpl(), this, ServicePriority.Normal);
        Bukkit.getServicesManager().register(CustomItemHelper.class, new CustomItemHelperImpl((ItemRegistry) DefaultRegistries.ITEM),
                this, ServicePriority.Normal);

        //Register Events
        Bukkit.getPluginManager().registerEvents(new ServerEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerEventListener(), this);
    }

    @Override
    public void onDisable() {

    }
}
