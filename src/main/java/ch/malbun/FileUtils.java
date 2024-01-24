package ch.malbun;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {
    public static ArrayList<String> getAllSets() throws IOException {
       ArrayList<String> toReturn = new ArrayList<>();

       List<Path> files;
       try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(System.getProperty("user.dir")))) {
           for (Path path : stream) {
               if (path.toFile().getName().endsWith(".lob")) {
                   toReturn.add(path.toFile().getName().replace(".lob", ""));
               }
           }
       }

       return toReturn;
    }
}
