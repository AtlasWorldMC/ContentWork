package fr.atlasworld.contentwork.common.item;

import fr.atlasworld.contentwork.api.common.item.CustomItemHelper;
import fr.atlasworld.contentwork.api.common.item.Item;
import fr.atlasworld.contentwork.registering.registries.ItemRegistry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItemHelperImpl implements CustomItemHelper {
    private final ItemRegistry registry;

    public CustomItemHelperImpl(ItemRegistry registry) {
        this.registry = registry;
    }

    @Override
    public ItemStack createItemStack(Item item, int count) {
        ItemStack stack = new ItemStack(item.getParent(), count);
        ItemMeta meta = stack.getItemMeta();

        NamespacedKey itemKey = this.registry.getKey(item);

        meta.displayName(Component.translatable("item." + itemKey.getNamespace() + "." + itemKey.getKey())
                .decoration(TextDecoration.ITALIC, false).color(item.getRarity().getColor()));
        meta.setCustomModelData((int) this.registry.getCustomModel(itemKey));
        item.appendTooltip(meta.lore());

        stack.setItemMeta(meta);

        net.minecraft.world.item.ItemStack internalStack = CraftItemStack.asNMSCopy(stack);

        CompoundTag tag = internalStack.getOrCreateTag();
        tag.putString("customItem", itemKey.asString());
        internalStack.setTag(tag);

        return internalStack.getBukkitStack();
    }

    @Override
    public boolean isCustomItem(ItemStack stack) {
        net.minecraft.world.item.ItemStack internalStack = CraftItemStack.asNMSCopy(stack);
        CompoundTag tag = internalStack.getTag();
        if (tag == null) {
            return false;
        }

        return !tag.getString("customItem").isEmpty();
    }
}
