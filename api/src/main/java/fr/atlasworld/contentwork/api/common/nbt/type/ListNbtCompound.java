package fr.atlasworld.contentwork.api.common.nbt.type;

import fr.atlasworld.contentwork.api.common.nbt.NbtTag;
import fr.atlasworld.contentwork.api.utils.Duplicable;

import java.util.UUID;

public interface ListNbtCompound extends NbtTag, Duplicable<ListNbtCompound> {
    void add(String value);
    void add(UUID value);
    void add(byte value);
    void add(boolean value);
    void add(short value);
    void add(int value);
    void add(long value);
    void add(float value);
    void add(double value);
    void add(byte[] value);
    void add(int[] value);
    void add(long[] value);
    void add(NbtTag value);

    void set(int i, String value);
    void set(int i, UUID value);
    void set(int i, byte value);
    void set(int i, boolean value);
    void set(int i, short value);
    void set(int i, int value);
    void set(int i, long value);
    void set(int i, float value);
    void set(int i, double value);
    void set(int i, byte[] value);
    void set(int i, int[] value);
    void set(int i, long[] value);
    void set(int i, NbtTag value);

    NbtTag get(int index);

    String getString(int index);
    UUID getUUID(int index);
    byte getByte(int index);
    boolean getBoolean(int index);
    short getShort(int index);
    int getInt(int index);
    long getLong(int index);
    float getFloat(int index);
    double getDouble(int index);
    byte[] getByteArray(int index);
    int[] getIntArray(int index);
    long[] getLongArray(int index);

    void remove(int index);

    int size();
    boolean isEmpty();
}
