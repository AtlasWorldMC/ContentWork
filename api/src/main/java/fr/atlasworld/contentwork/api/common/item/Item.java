package fr.atlasworld.contentwork.api.common.item;

import io.papermc.paper.inventory.ItemRarity;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Item {
    protected final Material parent;
    protected final Properties properties;

    public Item(Material parent, Properties properties) {
        this.parent = parent;
        this.properties = properties;
    }

    /**
     * Gets the parent material/item
     * @return the parent material/item
     */
    public Material getParent() {
        return parent;
    }

    /**
     * Allows adding default tooltips to the item in the inventory
     * @param tooltips tooltips list
     */
    public void appendTooltip(List<Component> tooltips) {
    }

    /**
     * Get the item rarity
     * @return item rarity
     */
    public ItemRarity getRarity() {
        return this.properties.rarity;
    }

    /**
     * Check if the item is allowed to damage specific entities
     * @param item stack
     * @param source entity trying to hurt entity
     * @param target target entity
     * @return true if the source entity is allowed to hurt the target entity
     */
    public boolean canHurtEntity(ItemStack item, LivingEntity source, LivingEntity target) {
        return true;
    }

    /**
     * Check if the item is allowed to damage specific blocks
     * @param state the block's state
     * @param world block's world/level
     * @param location block's location in the world
     * @param player player trying to damage the block
     * @return true if the player is allowed to damage the block
     */
    public boolean canAttackBlock(BlockState state, World world, Location location, Player player){
        return true;
    }

    /**
     * Check if the item is allowed to mine specific blocks
     * @param item item stack
     * @param world block's world/level
     * @param state the block's states
     * @param location block's location in the world
     * @param entity entity trying to mine the block
     * @return return true if the entity is allowed to mine the block
     */
    public boolean canMineBlock(ItemStack item, World world, BlockState state, Location location, LivingEntity entity){
        return true;
    }

    /**
     * Called when item is crafted
     * @param item crafted item stack
     * @param world world
     * @param player crafter
     */
    public void onCraftedBy(ItemStack item, World world, Player player) {
    }

    /**
     * Called when item is destroyed
     * @param location location where the item is destroyed
     * @param world where the item is destroyed
     */
    public void onDestroyed(Location location, World world) {
    }

    /**
     * Called when the item is used
     * @param item item stack
     * @param world world
     * @param entity entity
     * @return cancel event
     */
    public boolean use(ItemStack item, World world, LivingEntity entity) {
        return false;
    }

    /**
     * Called when the item is stopped being used
     * @param item item stack
     * @param ticks amount of time that the item was being used in ticks
     * @param player user of the item
     */
    public void onStopUsing(ItemStack item, int ticks, Player player) {
    }

    public static class Properties {
        ItemRarity rarity = ItemRarity.COMMON;

        /**
         * Sets the rarity for the item
         * @param rarity item rarity
         * @return Instance of the properties
         * @see ItemRarity
         */
        public Item.Properties rarity(ItemRarity rarity) {
            this.rarity = rarity;
            return this;
        }
    }
}
