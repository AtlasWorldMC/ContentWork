package fr.atlasworld.contentwork.file;

import java.io.File;

public class FileManager {
    private static final String ROOT_PLUGIN_DIRECTORY = "plugins/content-work";

    public static File getPluginDirectory() {
        return new File("plugins/");
    }

    public static File getPluginFile(String filename) {
        return new File(getPluginDirectory(), filename);
    }

    public static File getRootDirectory() {
        return new File(ROOT_PLUGIN_DIRECTORY);
    }

    public static File getRootDirectoryFile(String filename) {
        return new File(getRootDirectory(), filename);
    }

    public static File getCacheDirectory() {
        return new File(getRootDirectory(), "cache");
    }

    public static File getCacheDirectoryFile(String filename) {
        return new File(getCacheDirectory(), filename);
    }

    public static File getConfigDirectory() {
        return new File(getRootDirectory(), "config");
    }

    public static File getConfigDirectory(String filename) {
        return new File(getConfigDirectory(), filename);
    }
}
