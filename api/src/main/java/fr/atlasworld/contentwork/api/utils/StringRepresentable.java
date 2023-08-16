package fr.atlasworld.contentwork.api.utils;

import org.jetbrains.annotations.NotNull;

/**
 * Interface that returns an enum as a string
 */
public interface StringRepresentable {
    @NotNull String getSerializedName();
}
