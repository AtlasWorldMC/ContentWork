package fr.atlasworld.contentwork.file;

import fr.atlasworld.contentwork.ContentWork;

import javax.annotation.Nullable;
import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class FileUtils {
    public static String getFileSha1(File file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            try (FileInputStream fileInput = new FileInputStream(file);
                 DigestInputStream digestInput = new DigestInputStream(fileInput, digest)) {

                byte[] buffer = new byte[8192];
                while (digestInput.read(buffer) != -1) {
                }

                byte[] hashBytes = digest.digest();

                StringBuilder builder = new StringBuilder();
                for (byte hashByte : hashBytes) {
                    builder.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
                }
                return builder.toString();
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            ContentWork.logger.error("Unable to get file hash(SHA-1) for {}", file, e);
            return "";
        }
    }

    public static boolean validateFileSha1(File file, String sha1) {
        return getFileSha1(file).equals(sha1);
    }

    public static boolean archiveContainsFile(File archive, String file) {
        if (!archive.isFile()) {
            return false;
        }

        try (ZipFile zipFile = new ZipFile(archive)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.getName().equalsIgnoreCase(file) && !entry.isDirectory()) {
                    return true;
                }
            }
        } catch (IOException ignored) {}
        return false;
    }

    public static boolean archiveContainsDirectory(File archive, String directory) {
        if (!archive.isFile()) {
            return false;
        }

        try (ZipFile zipFile = new ZipFile(archive)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.getName().equalsIgnoreCase(directory) && entry.isDirectory()) {
                    return true;
                }
            }
        } catch (IOException ignored) {}
        return false;
    }

    public static void extractArchive(File archive, File dest, String root) throws IOException {
        try (ZipFile zipFile = new ZipFile(archive)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();

                if (entry.getName().startsWith(root) && !entry.isDirectory()) {
                    String relativePath = entry.getName().substring(root.length());
                    File outputFile = new File(dest.toString(), relativePath);

                    if (!outputFile.getParentFile().exists()) {
                        outputFile.getParentFile().mkdirs();
                    }

                    try (InputStream inputStream = zipFile.getInputStream(entry);
                         OutputStream outputStream = new FileOutputStream(outputFile)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;

                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }
                }

                if (entry.getName().startsWith(root) && entry.isDirectory()) {
                    String relativePath = entry.getName().substring(root.length());
                    File outputDir = new File(dest.toString(), relativePath);

                    if (!outputDir.exists()) {
                        outputDir.mkdirs();
                    }
                }
            }
        }
    }

    //Big thanks to Malfrador#0923 (https://github.com/Malfrador) for the working zip code!
    public static void zip(String srcFolder, String out) {
        try {
            File file = new File(srcFolder);
            FileOutputStream fileOutputStream = new FileOutputStream(out);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            File[] arrayOfFile = file.listFiles();
            if (arrayOfFile == null)
                return;
            for (File file1 : arrayOfFile)
                zipFile(file1, file1.getName(), zipOutputStream);
            zipOutputStream.close();
            fileOutputStream.close();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    //Big thanks to Malfrador#0923 (https://github.com/Malfrador) for the working zip code!
    private static void zipFile(File paramFile, String paramString, ZipOutputStream paramZipOutputStream) throws IOException {
        if (paramFile.isHidden())
            return;
        if (paramFile.isDirectory()) {
            if (paramString.endsWith("/")) {
                paramZipOutputStream.putNextEntry(new ZipEntry(paramString));
            } else {
                paramZipOutputStream.putNextEntry(new ZipEntry(paramString + "/"));
            }
            paramZipOutputStream.closeEntry();
            File[] arrayOfFile = paramFile.listFiles();
            if (arrayOfFile != null)
                for (File file : arrayOfFile) {
                    zipFile(file, paramString + "/" + file.getName(), paramZipOutputStream);
                }
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(paramFile);
        ZipEntry zipEntry = new ZipEntry(paramString);
        paramZipOutputStream.putNextEntry(zipEntry);
        byte[] arrayOfByte = new byte[1024];
        int i;
        while ((i = fileInputStream.read(arrayOfByte)) >= 0)
            paramZipOutputStream.write(arrayOfByte, 0, i);
        fileInputStream.close();
    }

    public static void deleteDirectory(File directory) {
        File[] files = directory.listFiles();

        if (files == null || files.length < 1) {
            directory.delete();
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                deleteDirectory(file);
            }

            if (file.isFile()) {
                file.delete();
            }
        }

        directory.delete();
    }
}
