package fr.atlasworld.contentwork.file.loader;

import java.io.File;
import java.io.IOException;

//Author: RaftDev
//Find the original file here : https://github.com/Raft08/Crafted-Launcher/blob/main/src/main/java/be/raft/launcher/file/loader/FileLoader.java
public abstract class FileLoader<T> {
    protected final File file;

    public FileLoader(File file) {
        this.file = file;
    }

    public abstract T load();

    public abstract void save(T value);

    public boolean fileExists() {
        return file.isFile();
    }

    public void createFile() {
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File getFile() {
        return file;
    }
}
