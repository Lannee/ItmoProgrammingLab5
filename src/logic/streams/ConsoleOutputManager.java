package main.java.src.logic.streams;

/**
 * Sends data to standard console output stream
 */
public class ConsoleOutputManager implements OutputManager {
    /**
     * @param message message to send to user
     */
    @Override
    public void print(String message) {
        System.out.print(message);
    }
}
