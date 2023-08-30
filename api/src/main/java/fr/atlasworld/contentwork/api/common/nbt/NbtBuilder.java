package fr.atlasworld.contentwork.api.common.nbt;

import fr.atlasworld.contentwork.api.common.nbt.internal.NbtHelper;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public class NbtBuilder {
    private static final NbtHelper helper;

    static {
        helper = Bukkit.getServicesManager().load(NbtHelper.class);
    }

    public static NbtCompound create(ItemStack stack) {
        return helper.create(stack);
    }

    private static NbtCompound createEmpty() {
        return helper.create();
    }

    private static ListNbtCompound createEmptyList() {
        return helper.createList();
    }
}
