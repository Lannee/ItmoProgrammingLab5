package main.java.src.logic.streams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Gets data from standard console input stream
 */
public class ConsoleInputManager extends InputManager {

    private final BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));

    /**
     * @return returns line from user
     */
    @Override
    public String readLine() {
        String line;
        if((line = getNext()) == null) {
            try {
                line = reader.readLine();
            } catch (IOException e) {}

            if(line == null) {
                System.exit(1);
            }
        }
        return line;
    }
}
