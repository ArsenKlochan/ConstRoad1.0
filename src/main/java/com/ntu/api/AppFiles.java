package com.ntu.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public final class AppFiles {
    /** Тека, де лежить JAR (або classes у IDE). */
    public static Path appDir(Class<?> anchor) {
        try {
            Path loc = Paths.get(anchor.getProtectionDomain().getCodeSource().getLocation().toURI());
            return Files.isRegularFile(loc) ? loc.getParent() : loc;
        } catch (URISyntaxException e) {
            return Paths.get(System.getProperty("user.dir"));
        }
    }

    /** Гарантує наявність файла 'root'; якщо відсутній — створює з вмістом "/base". */
    public static Path ensureRootFile(Class<?> anchor) throws IOException {
        Path rootFile = appDir(anchor).resolve("root");

        if (Files.exists(rootFile) && Files.isDirectory(rootFile)) {
            throw new IOException("'root' уже існує як ТЕКА: " + rootFile);
        }

        if (Files.notExists(rootFile)) {
            // створити файл лише якщо його дійсно немає
            Files.writeString(rootFile, "/base", StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
        }
        return rootFile;
    }
}
