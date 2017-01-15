package xyz.upperlevel.graphicengine.gamelauncher.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarExtractor {

    public static void extractFile(ClassLoader classLoader, String pathToFile, File destFile) throws IOException {
        destFile.getParentFile().mkdirs();
        destFile.createNewFile();
        InputStream in = classLoader.getResourceAsStream(pathToFile);
        if (in == null)
            throw new IOException("Cannot read input stream");
        FileUtils.copyInputStreamToFile(in, destFile);
    }

    public static void extractDirectory(ClassLoader classLoader, String pathToDir, File destFold) {
        destFold.mkdirs();

    }
}
