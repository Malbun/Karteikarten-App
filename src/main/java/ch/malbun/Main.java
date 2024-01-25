package ch.malbun;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.StringTemplate.STR;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        LearnArray array = new LearnArray();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Modus waehlen");
            System.out.println("N: neues Lernset erstellen");
            System.out.println("L: Lernset lernen");
            System.out.println("D: Lernset loeschen");
            System.out.println("E: Lernset editieren");
            System.out.println(":!exit beendet das Programm!");

            switch (scanner.nextLine()) {
                case "N" -> {
                    System.out.println(":!exit um das Lernset zu speichern und zur Hauptseite zurueckzukehren.");
                    while (true) {
                        System.out.println("1. Wert eingeben:");
                        String input1 = scanner.nextLine();
                        if (Objects.equals(input1, ":!exit")) {
                           break;
                        }

                        System.out.println("2. Wert eingeben:");
                        String input2 = scanner.nextLine();
                        if (Objects.equals(input2, ":!exit")) {
                            break;
                        }

                        array.addElement(input1, input2);
                        System.out.println(STR."Karte erfolgreich hinfugefuegt: \{input1}, \{input2}\n");

                    }
                    String name = " ";
                    do {
                        System.out.println("Name fuer das Lernset eingeben.");
                        System.out.println("Darf keine Leerzeichen enthalten!");

                        name = scanner.nextLine();
                    } while (name.contains(" ") || name.isBlank() || name.isEmpty());

                    array.safe(STR."\{name}.lob");
                    System.out.println(STR."Lernset \{name} erfolgreich gespeichert");
                }

                case "L" -> {
                    // Lernset auswaehlen
                    System.out.println("Alle verfuegbaren Sets:\n");
                    ArrayList<String> files = new ArrayList<>();
                    FileUtils.getAllSets().forEach(i -> {
                        System.out.println(STR."Lernset: \{i}");
                        files.add(i);
                    });
                    System.out.println("\nLernset auswaehlen");
                    System.out.print("Name eingeben: ");
                    String setName = scanner.nextLine();
                    if (setName.isBlank() || setName.isEmpty()) {
                        System.out.println("Ungueltige Auswahl!\n");
                        continue;
                    }
                    if (Objects.equals(setName, ":!exit")) {
                        continue;
                    }
                    if (!files.contains(setName)) {
                        System.out.println("Ungueltige Auswahl!");
                        continue;
                    }

                    //Lernset laden
                    array.load(STR."\{setName}.lob");
                    System.out.println(STR."Lernset \{setName} erfolgreich geladen!");

                    //Lernset lernen
                    array.learn();
                }

                case "D" -> {
                    System.out.println("Alle verfuegbaren Sets:\n");
                    ArrayList<String> files = new ArrayList<>();
                    FileUtils.getAllSets().forEach(i -> {
                        System.out.println(STR."Lernset: \{i}");
                        files.add(i);
                    });
                    System.out.println("\nLernset auswaehlen");
                    System.out.print("Name eingeben: ");
                    String setName = scanner.nextLine();
                    if (setName.isBlank() || setName.isEmpty()) {
                        System.out.println("Ungueltige Auswahl!\n");
                        continue;
                    }
                    if (Objects.equals(setName, ":!exit")) {
                        continue;
                    }
                    if (!files.contains(setName)) {
                        System.out.println("Ungueltige Auswahl!");
                        continue;
                    }

                    System.out.print(STR."Lernset \{setName} wirklich loeschen? (y/n): ");
                    if (Objects.equals(scanner.nextLine(), "y")) {
                        Files.delete(Path.of(STR."\{setName}.lob"));
                        System.out.println(STR."Lernset \{setName} erfolgreich geloescht!");
                    }
                }

                case "E" -> {
                    //Lernset editieren
                    System.out.println("Alle verfuegbaren Sets:\n");
                    ArrayList<String> files = new ArrayList<>();
                    FileUtils.getAllSets().forEach(i -> {
                        System.out.println(STR."Lernset: \{i}");
                        files.add(i);
                    });
                    System.out.println("\nLernset auswaehlen");
                    System.out.print("Name eingeben: ");
                    String setName = scanner.nextLine();
                    if (setName.isBlank() || setName.isEmpty()) {
                        System.out.println("Ungueltige Auswahl!\n");
                        continue;
                    }
                    if (Objects.equals(setName, ":!exit")) {
                        continue;
                    }
                    if (!files.contains(setName)) {
                        System.out.println("Ungueltige Auswahl!");
                        continue;
                    }

                    array.load(STR."\{setName}.lob");
                    System.out.println(STR."Lernset \{setName} erfolgreich geladen.");

                    array.edit(setName);

                }

                case ":!exit" -> System.exit(0);
                case null, default -> System.out.println("Ungueltige Auswahl");
            }
        }
    }
}