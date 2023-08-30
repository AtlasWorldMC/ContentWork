package fr.atlasworld.contentwork.common.nbt;

import fr.atlasworld.contentwork.api.common.nbt.NbtCompound;
import fr.atlasworld.contentwork.api.common.nbt.NbtTag;
import fr.atlasworld.contentwork.api.common.nbt.ListNbtCompound;
import net.minecraft.nbt.CompoundTag;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

public class NbtCompoundImpl implements NbtCompound {
    final CompoundTag nbtTag;

    public NbtCompoundImpl(CompoundTag nbtTag) {
        this.nbtTag = nbtTag;
    }

    @Override
    public NbtCompound put(String key, String value) {
        this.nbtTag.putString(key, value);
        return this;
    }

    @Override
    public NbtCompound put(String key, UUID value) {
        this.nbtTag.putUUID(key, value);
        return this;
    }

    @Override
    public NbtCompound put(String key, byte value) {
        this.nbtTag.putByte(key, value);
        return this;
    }

    @Override
    public NbtCompound put(String key, boolean value) {
        this.nbtTag.putBoolean(key, value);
        return this;
    }

    @Override
    public NbtCompound put(String key, short value) {
        this.nbtTag.putShort(key, value);
        return this;
    }

    @Override
    public NbtCompound put(String key, int value) {
        this.nbtTag.putInt(key, value);
        return this;
    }

    @Override
    public NbtCompound put(String key, long value) {
        this.nbtTag.putLong(key, value);
        return this;
    }

    @Override
    public NbtCompound put(String key, float value) {
        this.nbtTag.putFloat(key, value);
        return this;
    }

    @Override
    public NbtCompound put(String key, double value) {
        this.nbtTag.putDouble(key, value);
        return this;
    }

    @Override
    public NbtCompound put(String key, byte[] value) {
        this.nbtTag.putByteArray(key, value);
        return this;
    }

    @Override
    public NbtCompound put(String key, int[] value) {
        this.nbtTag.putIntArray(key, value);
        return this;
    }

    @Override
    public NbtCompound put(String key, long[] value) {
        this.nbtTag.putLongArray(key, value);
        return this;
    }

    @Override
    public NbtCompound put(String key, NbtTag value) {
        if (value instanceof NbtCompoundImpl compound) this.nbtTag.put(key, compound.nbtTag);
        if (value instanceof ListNbtCompoundImpl compound) this.nbtTag.put(key, compound.nbtTag);
        return this;
    }

    @Override
    public String getString(String key) {
        return this.nbtTag.getString(key);
    }

    @Override
    public UUID getUuid(String key) {
        return this.nbtTag.getUUID(key);
    }

    @Override
    public byte getByte(String key) {
        return this.nbtTag.getByte(key);
    }

    @Override
    public boolean getBoolean(String key) {
        return this.nbtTag.getBoolean(key);
    }

    @Override
    public short getShort(String key) {
        return this.nbtTag.getShort(key);
    }

    @Override
    public int getInt(String key) {
        return this.nbtTag.getInt(key);
    }

    @Override
    public long getLong(String key) {
        return this.nbtTag.getLong(key);
    }

    @Override
    public float getFloat(String key) {
        return this.nbtTag.getFloat(key);
    }

    @Override
    public double getDouble(String key) {
        return this.nbtTag.getDouble(key);
    }

    @Override
    public byte[] getByteArray(String key) {
        return this.nbtTag.getByteArray(key);
    }

    @Override
    public int[] getIntArray(String key) {
        return this.nbtTag.getIntArray(key);
    }

    @Override
    public long[] getLongArray(String key) {
        return this.nbtTag.getLongArray(key);
    }

    @Override
    public ListNbtCompound getList(String key) {
        return new ListNbtCompoundImpl(this.nbtTag.getList(key, 0));
    }

    @Override
    public Set<String> getKeys() {
        return this.nbtTag.getAllKeys();
    }

    @Override
    public NbtCompound duplicate() {
        return new NbtCompoundImpl(this.nbtTag.copy());
    }
}
