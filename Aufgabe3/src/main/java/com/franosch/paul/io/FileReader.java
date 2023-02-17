package com.franosch.paul.io;

import com.franosch.paul.Main;
import com.franosch.paul.model.PancakeStack;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class FileReader {
    private final boolean useTestResources;

    @SneakyThrows
    public PancakeStack read(String name) {
        File file = new File(getCurrentPath(useTestResources) + name + ".txt");
        Scanner scanner = new Scanner(file);
        boolean skip = true;
        List<Integer> pancakes = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            if (skip) {
                skip = false;
                continue;
            }
            pancakes.add(Integer.parseInt(data));

        }
        scanner.close();
        Collections.reverse(pancakes);
        Integer[] array = pancakes.toArray(new Integer[0]);
        return new PancakeStack(array);
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
