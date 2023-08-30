package fr.atlasworld.contentwork.common.nbt;

import fr.atlasworld.contentwork.api.common.nbt.ListNbtCompound;
import fr.atlasworld.contentwork.api.common.nbt.NbtTag;
import net.minecraft.nbt.*;

import java.util.UUID;

public class ListNbtCompoundImpl implements ListNbtCompound {
    final ListTag nbtTag;

    public ListNbtCompoundImpl(ListTag nbtTag) {
        this.nbtTag = nbtTag;
    }

    @Override
    public void add(String value) {
        this.nbtTag.add(StringTag.valueOf(value));
    }

    @Override
    public void add(UUID value) {
        this.nbtTag.add(NbtUtils.createUUID(value));
    }

    @Override
    public void add(byte value) {
        this.nbtTag.add(ByteTag.valueOf(value));
    }

    @Override
    public void add(boolean value) {
        this.nbtTag.add(ByteTag.valueOf(value));
    }

    @Override
    public void add(short value) {
        this.nbtTag.add(ShortTag.valueOf(value));
    }

    @Override
    public void add(int value) {
        this.nbtTag.add(IntTag.valueOf(value));
    }

    @Override
    public void add(long value) {
        this.nbtTag.add(LongTag.valueOf(value));
    }

    @Override
    public void add(float value) {
        this.nbtTag.add(FloatTag.valueOf(value));
    }

    @Override
    public void add(double value) {
        this.nbtTag.add(DoubleTag.valueOf(value));
    }

    @Override
    public void add(byte[] value) {
        this.nbtTag.add(new ByteArrayTag(value));
    }

    @Override
    public void add(int[] value) {
        this.nbtTag.add(new IntArrayTag(value));
    }

    @Override
    public void add(long[] value) {
        this.nbtTag.add(new LongArrayTag(value));
    }

    @Override
    public void add(NbtTag value) {
        if (value instanceof ListNbtCompoundImpl listCompound) this.nbtTag.add(listCompound.nbtTag);
        if (value instanceof NbtCompoundImpl compound) this.nbtTag.add(compound.nbtTag);
    }

    @Override
    public void set(int i, String value) {
        this.nbtTag.set(i, StringTag.valueOf(value));
    }

    @Override
    public void set(int i, UUID value) {
        this.nbtTag.set(i, NbtUtils.createUUID(value));
    }

    @Override
    public void set(int i, byte value) {
        this.nbtTag.set(i, ByteTag.valueOf(value));
    }

    @Override
    public void set(int i, boolean value) {
        this.nbtTag.set(i, ByteTag.valueOf(value));
    }

    @Override
    public void set(int i, short value) {
        this.nbtTag.set(i, ShortTag.valueOf(value));
    }

    @Override
    public void set(int i, int value) {
        this.nbtTag.set(i, IntTag.valueOf(value));
    }

    @Override
    public void set(int i, long value) {
        this.nbtTag.set(i, LongTag.valueOf(value));
    }

    @Override
    public void set(int i, float value) {
        this.nbtTag.set(i, FloatTag.valueOf(value));
    }

    @Override
    public void set(int i, double value) {
        this.nbtTag.set(i, DoubleTag.valueOf(value));
    }

    @Override
    public void set(int i, byte[] value) {
        this.nbtTag.set(i, new ByteArrayTag(value));
    }

    @Override
    public void set(int i, int[] value) {
        this.nbtTag.set(i, new IntArrayTag(value));
    }

    @Override
    public void set(int i, long[] value) {
        this.nbtTag.set(i, new LongArrayTag(value));
    }

    @Override
    public void set(int i, NbtTag value) {
        if (value instanceof ListNbtCompoundImpl listCompound) this.nbtTag.set(i, listCompound.nbtTag);
        if (value instanceof NbtCompoundImpl compound) this.nbtTag.set(i, compound.nbtTag);
    }

    @Override
    public String getString(int index) {
        return this.nbtTag.getString(index);
    }

    @Override
    public UUID getUUID(int index) {
        return NbtUtils.loadUUID(this.nbtTag.get(index));
    }

    @Override
    public byte getByte(int index) {
        try {
            return ((NumericTag)this.nbtTag.get(index)).getAsByte();
        } catch (ClassCastException e) {
            return 0;
        }
    }

    @Override
    public boolean getBoolean(int index) {
        return this.getByte(index) != 0;
    }

    @Override
    public short getShort(int index) {
        return this.nbtTag.getShort(index);
    }

    @Override
    public int getInt(int index) {
        return this.nbtTag.getInt(index);
    }

    @Override
    public long getLong(int index) {
        try {
            return ((NumericTag)this.nbtTag.get(index)).getAsLong();
        } catch (ClassCastException e) {
            return 0;
        }
    }

    @Override
    public float getFloat(int index) {
        return this.nbtTag.getFloat(index);
    }

    @Override
    public double getDouble(int index) {
        return this.nbtTag.getDouble(index);
    }

    @Override
    public byte[] getByteArray(int index) {
        try {
            return ((ByteArrayTag)this.nbtTag.get(index)).getAsByteArray();
        } catch (ClassCastException var3) {
            return new byte[0];
        }
    }

    @Override
    public int[] getIntArray(int index) {
        return this.nbtTag.getIntArray(index);
    }

    @Override
    public long[] getLongArray(int index) {
        return this.nbtTag.getLongArray(index);
    }

    @Override
    public void remove(int index) {
        this.nbtTag.remove(index);
    }

    @Override
    public int size() {
        return this.nbtTag.size();
    }

    @Override
    public boolean isEmpty() {
        return this.nbtTag.isEmpty();
    }

    @Override
    public ListNbtCompound duplicate() {
        return new ListNbtCompoundImpl(this.nbtTag.copy());
    }
}
