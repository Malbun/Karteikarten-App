package ch.malbun;

import java.io.*;
import java.util.*;
import java.util.logging.ConsoleHandler;

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

}
