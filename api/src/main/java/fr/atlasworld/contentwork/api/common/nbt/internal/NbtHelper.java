package fr.atlasworld.contentwork.api.common.nbt.internal;

import fr.atlasworld.contentwork.api.common.nbt.NbtCompound;
import fr.atlasworld.contentwork.api.common.nbt.ListNbtCompound;
import org.bukkit.inventory.ItemStack;

public interface NbtHelper {
    NbtCompound create();
    NbtCompound create(ItemStack item);

    ListNbtCompound createList();
}
