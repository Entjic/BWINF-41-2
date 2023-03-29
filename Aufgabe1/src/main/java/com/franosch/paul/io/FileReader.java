package com.franosch.paul.io;

import com.franosch.paul.Main;
import com.franosch.paul.model.Point;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.util.*;

@RequiredArgsConstructor
public class FileReader {
    private final boolean useTestResources;

    @SneakyThrows
    public List<Point> read(String name) {
        File file = new File(getCurrentPath(useTestResources) + name + ".txt");
        Scanner scanner = new Scanner(file);
        List<Point> points = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            final String[] split = data.split(" ");
            Point point = new Point(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
            points.add(point);
        }
        scanner.close();
        return points;
    }

    private String getCurrentPath(boolean useTestResources) {
        return useTestResources ? getCurrentPathDev() : getCurrentPathProd();
    }

    @SneakyThrows
    private String getCurrentPathProd() {
        String path = new File(Main.class.getProtectionDomain()
                .getCodeSource().getLocation().toURI()).getPath();
        String[] splits = path.split("[/\\\\]");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < splits.length - 1; i++) {
            stringBuilder.append(splits[i]).append("/");
        }
        return stringBuilder.append("resources/").toString();
    }

    private String getCurrentPathDev() {
        return new File("").getAbsolutePath()
                + "/src/test/resources/";
    }

}
