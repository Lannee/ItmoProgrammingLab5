package main.java.src.logic.streams;

public class ConsoleOutputManager implements OutputManager {
    @Override
    public void print(String message) {
        System.out.print(message);
    }
}
