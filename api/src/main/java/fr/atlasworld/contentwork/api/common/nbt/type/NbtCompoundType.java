package fr.atlasworld.contentwork.api.common.nbt.type;

import fr.atlasworld.contentwork.api.common.nbt.NbtTag;
import fr.atlasworld.contentwork.api.utils.Duplicable;

public interface NbtCompoundType<T> extends NbtTag {
    T get();
    void set(T value);
}
