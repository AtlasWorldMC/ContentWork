package fr.atlasworld.contentwork.datagen;

import fr.atlasworld.contentwork.ContentWork;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class DataGenManager {
    private Set<File> dataGenPlugins;
    public void collectPlugins() {
        File[] pluginFiles = new File("plugins").listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));
        dataGenPlugins = Arrays.stream(pluginFiles)
                .filter(plugin -> hasAssetsDirectory(plugin) || hasDataDirectory(plugin))
                .collect(Collectors.toSet());
    }

    public Set<File> getDataGenPlugins() {
        return dataGenPlugins;
    }

    private boolean hasAssetsDirectory(File file) {
        try (JarFile jarFile = new JarFile(file)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().startsWith("assets/") && entry.isDirectory()) {
                    return true;
                }
            }
        } catch (IOException e) {
            ContentWork.logger.error("Unable to verify {}!", file.getName(), e);
        }
        return false;
    }

    private boolean hasDataDirectory(File file) {
        try (JarFile jarFile = new JarFile(file)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().startsWith("data/") && entry.isDirectory()) {
                    return true;
                }
            }
        } catch (IOException e) {
            ContentWork.logger.error("Unable to verify {}!", file.getName(), e);
        }
        return false;
    }
}
