package com.franosch.paul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Bitte wähle den gewünschten Modus (1) Pfannkuchen Stapel" +
                " aus Datei lösen oder (2) PWUE-Zahl ermitteln.");
        String modeInput = br.readLine();
        if (! isInt(modeInput)) {
            System.out.println("Expected integer value: " + modeInput + " is not an integer!");
            System.exit(0);
        }
        int mode = Integer.parseInt(modeInput);
        while (true) {
            Solver solver = new Solver();
            switch (mode) {
                case 1 -> {
                    System.out.println("Du befindest dich nun im Pfannkuchenstapellöser Modus.");
                    System.out.println("Bitte gib die nr der Datei mit dem gewünschten Pfannkuchenstapel an!");
                    String fileInput = br.readLine();
                    if (! isInt(fileInput)) {
                        System.out.println("Expected integer value: " + fileInput + " is not an integer!");
                        System.exit(0);
                    }
                    solver.solveFile(Integer.parseInt(fileInput), true, true);
                    // System.exit(0);
                }
                case 2 -> {
                    System.out.println("Du befindest dich nun im PWUE-Zahl Modus.");
                    System.out.println("Bitte gib die Höhe des Pfannkuchenstapels an für die die PWUE-Zahl ermittelt werden soll!");
                    String heightInput = br.readLine();
                    if (! isInt(heightInput)) {
                        System.out.println("Expected integer value: " + heightInput + " is not an integer!");
                        System.exit(0);
                    }
                    int pwue = solver.findPWUE(Integer.parseInt(heightInput), 10);
                    // System.exit(0);
                }
                default -> System.out.println("Es stehen nur die Modi 1 und 2 zur verfügung!");
            }

        }

    }

    private static boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
