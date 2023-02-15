package src.logic;

public class ConsolOutputManager implements OutputManager {
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}
