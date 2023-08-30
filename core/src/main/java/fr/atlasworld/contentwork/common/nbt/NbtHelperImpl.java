package fr.atlasworld.contentwork.common.nbt;

import fr.atlasworld.contentwork.api.common.nbt.ListNbtCompound;
import fr.atlasworld.contentwork.api.common.nbt.NbtCompound;
import fr.atlasworld.contentwork.api.common.nbt.internal.NbtHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NbtHelperImpl implements NbtHelper {
    @Override
    public NbtCompound create() {
        return new NbtCompoundImpl(new CompoundTag());
    }

    @Override
    public NbtCompound create(ItemStack item) {
        return new NbtCompoundImpl(CraftItemStack.asNMSCopy(item).getTag());
    }

    @Override
    public ListNbtCompound createList() {
        return new ListNbtCompoundImpl(new ListTag());
    }
}
