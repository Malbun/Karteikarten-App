package ch.malbun;

import java.util.*;

public class Chunk {
  ArrayList<LearnObject> objectsOriginal;
  public Chunk(ArrayList<LearnObject> objectList) {
    objectsOriginal = objectList;
  }

  public void learn() {
    Scanner scanner = new Scanner(System.in);

    ArrayList<LearnObject> todoList = new ArrayList<>(objectsOriginal);
    Collections.shuffle(todoList);

    Iterator<LearnObject> objectIterator = todoList.iterator();

    while (objectIterator.hasNext()) {
      LearnObject currentObject = objectIterator.next();
      System.out.println(currentObject.getA() + ": ");
      String response = scanner.nextLine();

      if (Objects.equals(response, currentObject.getB())) {
        System.out.println("Richtig!\n");
        todoList.remove(currentObject);
      } else {
        System.out.println("Falsch!");
        System.out.println("Richtige Antwort: " + currentObject.getB() + "\n");
        todoList.add(currentObject);
      }

      Collections.shuffle(todoList);
      objectIterator = todoList.iterator();

    }

    learnB();

  }

  private void learnB() {
    Scanner scanner = new Scanner(System.in);

    ArrayList<LearnObject> todoList = new ArrayList<>(objectsOriginal);
    Collections.shuffle(todoList);

    Iterator<LearnObject> objectIterator = todoList.iterator();

    while (objectIterator.hasNext()) {
      LearnObject currentObject = objectIterator.next();
      System.out.println(currentObject.getB() + ": ");
      String response = scanner.nextLine();

      if (Objects.equals(response, currentObject.getA())) {
        System.out.println("Richtig!\n");
        todoList.remove(currentObject);
      } else {
        System.out.println("Falsch!");
        System.out.println("Richtige Antwort: " + currentObject.getA() + "\n");
        todoList.add(currentObject);
      }

      Collections.shuffle(todoList);
      objectIterator = todoList.iterator();

    }
  }

}
