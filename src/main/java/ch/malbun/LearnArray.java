package ch.malbun;

import java.io.*;
import java.util.*;

public class LearnArray implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private ArrayList<LearnObject> learnObjects = new ArrayList<>();

    private Scanner scanner = new Scanner(System.in);

    public void addElement(String a, String b) {
        LearnObject c = new LearnObject(a, b);
        learnObjects.add(c);
    }

    public LearnObject getRandom() {
        Random r = new Random();
        int randomNumber = r.nextInt(learnObjects.size());
        return learnObjects.get(randomNumber);
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
            ArrayList<LearnObject> loaded = (ArrayList<LearnObject>) ois.readObject();
            this.learnObjects = loaded;
        }
    }

    public void learn() {
        ArrayList<LearnObject> progress = new ArrayList<>();

        int mistakes = 0;

        do {
            LearnObject current;
            do {
                current = getRandom();
            } while (progress.contains(current));

            System.out.println("\n");
            System.out.println(STR."\{progress.size()} von \{learnObjects.size()}");
            System.out.print(STR."\{current.getA()}: ");

            String answer = scanner.nextLine();

            if (Objects.equals(answer, current.getB())) {
                System.out.println("Richtig!");
                progress.add(current);
            } else if (Objects.equals(answer, ":!exit")) {
                break;
            } else {
                System.out.println("Falsch!");
                System.out.println(STR."Richtige Antwort: \{current.getB()}");
                mistakes++;
            }


        } while (progress.size() != learnObjects.size());

        System.out.println("Lernen beendet!");
        System.out.println(STR."Anzahl Fehler: \{mistakes}");

        System.out.println("R um nochmals dieses Set zu lernen.");

        if (Objects.equals(scanner.nextLine(), "R")) {
            learn();
        }

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

        if (Objects.equals(choise, "E")) {
            System.out.println("Alle Karten: ");
            for (int i = 0; i < learnObjects.size(); i++) {
                System.out.println(STR."Karte \{i}: \{learnObjects.get(i).getA()}, \{learnObjects.get(i).getB()}");

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
            System.out.println(STR."1. Wert: \{currentLearnObject.getA()}, 2. Wert: \{currentLearnObject.getB()}\n");

            System.out.print("1. Wert eingeben: ");
            String firstValue = scanner.nextLine();
            currentLearnObject.setA(firstValue);

            System.out.print("2. Wert eingeben: ");
            String secondValue = scanner.nextLine();
            currentLearnObject.setB(secondValue);

            learnObjects.set(card, currentLearnObject);

            System.out.println(STR."Karte editiert: 1. Wert: \{currentLearnObject.getA()}, 2. Wert: \{currentLearnObject.getB()}");

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

        } else if (Objects.equals(choise, "N")) {
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
                System.out.println(STR."Karte erfolgreich hinfugefuegt: \{input1}, \{input2}\n");
                safe(STR."\{filename}.lob");
            }
        } else if (Objects.equals(choise, "D")) {
            delete(filename);
        }

    }

    private void delete(String filename) throws IOException {
        System.out.println("Alle Karten: ");
        for (int i = 0; i < learnObjects.size(); i++) {
            System.out.println(STR."Karte \{i}: \{learnObjects.get(i).getA()}, \{learnObjects.get(i).getB()}");

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
            System.out.print(STR."Karte \{card} wirklich loeschen? (y/n): ");
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
                safe(STR."\{filename}.lob");
                System.out.println(STR."Karte \{card} gelöscht!\n");
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
            case null, default -> System.out.println("Ungueltige Auswahl!");
        }
    }
}
