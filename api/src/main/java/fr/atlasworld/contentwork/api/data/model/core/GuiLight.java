package fr.atlasworld.contentwork.api.data.model.core;

import fr.atlasworld.contentwork.api.utils.StringRepresentable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum GuiLight implements StringRepresentable {
    FRONT, SIDE;

    public boolean lightLikeBlock() {
        return this == SIDE;
    }

    public static @Nullable GuiLight fromString(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name().toLowerCase();
    }
}
