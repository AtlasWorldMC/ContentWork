package fr.atlasworld.contentwork.api.common.nbt;

import fr.atlasworld.contentwork.api.utils.Duplicable;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

public interface NbtCompound extends NbtTag, Duplicable<NbtCompound> {
    //put
    NbtCompound put(String key, String value);
    NbtCompound put(String key, UUID value);
    NbtCompound put(String key, byte value);
    NbtCompound put(String key, boolean value);
    NbtCompound put(String key, short value);
    NbtCompound put(String key, int value);
    NbtCompound put(String key, long value);
    NbtCompound put(String key, float value);
    NbtCompound put(String key, double value);
    NbtCompound put(String key, byte[] value);
    NbtCompound put(String key, int[] value);
    NbtCompound put(String key, long[] value);
    NbtCompound put(String key, NbtTag value);

    //get
    String getString(String key);
    UUID getUuid(String key);
    byte getByte(String key);
    boolean getBoolean(String key);
    short getShort(String key);
    int getInt(String key);
    long getLong(String key);
    float getFloat(String key);
    double getDouble(String key);
    byte[] getByteArray(String key);
    int[] getIntArray(String key);
    long[] getLongArray(String key);
    ListNbtCompound getList(String key);

    Set<String> getKeys();
}
