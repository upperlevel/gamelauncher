package xyz.upperlevel.graphicengine.gamelauncher.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@NoArgsConstructor
public class Extractor {

    @RequiredArgsConstructor
    public static class Resource {
        @Getter
        public final String path;

        @Getter
        public final File file;
    }

    private static final Yaml YAML = new Yaml();

    @Getter
    public final List<Resource> resources = new ArrayList<>();

    @Getter
    public final Map<String, Resource> idResources = new HashMap<>(); // these resources has an index

    private String formatSep(String str) {
        return str
                .replace("/", File.separator);
    }

    private String formatPath(String str) {
        return formatSep(str
                .replace("$user", FileUtils.getUserDirectory().getPath()));
    }

    @SuppressWarnings("unchecked")
    public void load(InputStream extractorData) {
        Map<String, Object> data = (Map<String, Object>) YAML.load(extractorData);
        // gets general path
        File root = new File(
                formatPath((String) data.get("path")));
        // reads resources
        List<Map<String, Object>> resources = (List<Map<String, Object>>) data.get("resources");
        for (Map<String, Object> resData : resources) {
            String id = (String) resData.get("id");
            // foreach resources create a new res object
            Resource res = new Resource(
                    formatSep((String) resData.get("where")),
                    new File(root, formatPath((String) resData.get("where")))
            );
            this.resources.add(res);
            if (id != null)
                idResources.put(id, res);
        }
    }

    public Optional<Resource> getResource(String id) {
        return Optional.ofNullable(idResources.get(id));
    }

    public void extract() throws IOException {
        extract(getClass().getClassLoader());
    }

    public void extract(ClassLoader classLoader) throws IOException {
        extract(classLoader, false);
    }

    public void extract(ClassLoader classLoader, boolean forced) throws IOException {
        for (Resource res : resources) {
            InputStream in = classLoader.getResourceAsStream(res.path);
            if (in == null)
                continue;
            File f = res.file;
            if (f.getParentFile().mkdirs() || f.createNewFile() || forced)
                FileUtils.copyInputStreamToFile(in, f);
        }
    }
}