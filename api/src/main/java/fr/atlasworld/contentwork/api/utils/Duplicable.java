package fr.atlasworld.contentwork.api.utils;

public interface Duplicable<T extends Duplicable<T>> {
    T duplicate();
}
