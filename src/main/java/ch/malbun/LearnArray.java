package ch.malbun;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LearnArray implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private ArrayList<LearnObject> learnObjects = new ArrayList<>();

    private final Scanner scanner = new Scanner(System.in);

    public void addElement(String a, String b) {
        LearnObject c = new LearnObject(a, b);
        learnObjects.add(c);
    }

    public void safe(String file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(learnObjects);
            oos.flush();
            oos.close();
        }
    }

    public void load(String file) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(file)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
          this.learnObjects = (ArrayList<LearnObject>) ois.readObject();
        }
    }

    public void learn() {
        ArrayList<LearnObject> todoLearn = new ArrayList<>(learnObjects);
        Collections.shuffle(todoLearn);

        ArrayList<Chunk> chunks = new ArrayList<>();

        List<List<LearnObject>> batches = batches(todoLearn, 7).toList();

        for (List<LearnObject> list : batches) {
            ArrayList<LearnObject> currentObjects = new ArrayList<>(list);
            Chunk chunk = new Chunk(currentObjects);
            chunks.add(chunk);
        }


        chunks.forEach(Chunk::learn);

    }

    public <T> Stream<List<T>> batches(List<T> source, int length) {
        if (length <= 0)
            throw new IllegalArgumentException("length = " + length);
        int size = source.size();
        if (size <= 0)
            return Stream.empty();
        int fullChunks = (size - 1) / length;
        return IntStream.range(0, fullChunks + 1).mapToObj(
                n -> source.subList(n * length, n == fullChunks ? size : (n + 1) * length));
    }

    public void edit(String filename) throws IOException {
        String choise;
        do {
            System.out.println("Verfügbare Aktionen:");
            System.out.println("N: neue Karte hinzufügen");
            System.out.println("E: Karte editieren");
            System.out.println("D: Karte löschen");
            System.out.print("Auswahl: ");
            choise = scanner.nextLine();
            if (Objects.equals(choise, ":!exit")) {
                return;
            }
            if (!(Objects.equals(choise, "N") || Objects.equals(choise, "E") || Objects.equals(choise, "D"))) {
                System.out.println("Ungültige Auswahl!\n");
            }
        } while (!(Objects.equals(choise, "N") || Objects.equals(choise, "E") || Objects.equals(choise, "D")));

      switch (choise) {
        case "E" -> {
          System.out.println("Alle Karten: ");
          for (int i = 0; i < learnObjects.size(); i++) {
            System.out.println("Karte " + i + ": " + learnObjects.get(i).getA() + ", " + learnObjects.get(i).getB());

          }

          int card = 0;

          boolean correctInput = false;
          while (!correctInput) {
            try {
              System.out.print("Zu bearbeitende Karte wählen: ");
              String cardString = scanner.nextLine();
              if (Objects.equals(cardString, ":!exit")) {
                return;
              }
              card = Integer.parseInt(cardString);
              if ((card >= 0) && (card <= learnObjects.size() - 1)) {
                correctInput = true;
              } else {
                System.out.println("Ungueltige Eingabe");
              }
            } catch (NumberFormatException e) {
              System.out.println("Ungueltige Eingabe!");
            }
          }

          LearnObject currentLearnObject = learnObjects.get(card);
          System.out.println("Karte bisher:");
          System.out.println("1. Wert: " + currentLearnObject.getA() + ", 2. Wert: " + currentLearnObject.getB() + "\n");

          System.out.print("1. Wert eingeben: ");
          String firstValue = scanner.nextLine();
          currentLearnObject.setA(firstValue);

          System.out.print("2. Wert eingeben: ");
          String secondValue = scanner.nextLine();
          currentLearnObject.setB(secondValue);

          learnObjects.set(card, currentLearnObject);

          System.out.println("Karte editiert: 1. Wert: " + currentLearnObject.getA() + ", 2. Wert: " + currentLearnObject.getB());

          String remakeChoise = "n";
          do {
            System.out.print("Andere Karte editieren/anlegen (y/n): ");
            remakeChoise = scanner.nextLine();
            if (!(Objects.equals(remakeChoise, "y") || Objects.equals(remakeChoise, "n"))) {
              System.out.println("Ungueltige Eingabe!\n");
            }
          } while (!(Objects.equals(remakeChoise, "y") || Objects.equals(remakeChoise, "n")));

          if (Objects.equals(remakeChoise, "y")) {
            edit(filename);
          }
        }
        case "N" -> {
          while (true) {
            System.out.println("Dieser Dialog kann mit :!exit verlassen werden.");

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

            addElement(input1, input2);
            System.out.println("Karte erfolgreich hinfugefuegt: " + input1 + ", " + input2 + "\n");
            safe(filename + ".lob");
          }
        }
        case "D" -> delete(filename);
      }

    }

    private void delete(String filename) throws IOException {
        System.out.println("Alle Karten: ");
        for (int i = 0; i < learnObjects.size(); i++) {
            System.out.println("Karte " + i + ": " + learnObjects.get(i).getA() + ", " + learnObjects.get(i).getB());

        }

        int card = 0;

        boolean correctInput = false;
        while (!correctInput) {
            try {
                System.out.print("Zu loeschende Karte wählen: ");
                String cardString = scanner.nextLine();
                if (Objects.equals(cardString, ":!exit")) {
                    return;
                }
                card = Integer.parseInt(cardString);
                if ((card >= 0) && (card <= learnObjects.size() - 1)) {
                    correctInput = true;
                } else {
                    System.out.println("Ungueltige Eingabe");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ungueltige Eingabe!");
            }
        }

        String deleteChoise = null;

        while (true) {
            System.out.print("Karte " + card + " wirklich loeschen? (y/n): ");
            deleteChoise = scanner.nextLine();
            if (Objects.equals(deleteChoise, "y") || Objects.equals(deleteChoise, "n") || Objects.equals(deleteChoise, ":!exit")) {
                break;
            } else {
                System.out.println("Ungueltige Auswahl!");
            }
        }

        switch (deleteChoise) {
            case "y" -> {
                learnObjects.remove(card);
                safe(filename + ".lob");
                System.out.println("Karte " + card + " gelöscht!\n");
                boolean continueBoolean = false;
                String continueChoise = null;
                while (!continueBoolean) {
                    System.out.print("Eine weitere Karte loeschen? (y/n): ");
                    continueChoise = scanner.nextLine();

                    if (Objects.equals(continueChoise, "y") || Objects.equals(continueChoise, "n")) {
                        continueBoolean = true;
                    } else {
                        System.out.println("Ungueltige Auswahl!\n");
                    }
                }

                if (Objects.equals(continueChoise, "y")) {
                    delete(filename);
                }

            }

            case "n" -> delete(filename);
            case ":!exit" -> {}
          default -> System.out.println("Ungueltige Auswahl!");
        }
    }
}
