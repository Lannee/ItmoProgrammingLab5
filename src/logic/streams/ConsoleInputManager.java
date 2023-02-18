package src.logic.streams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInputManager implements InputManager {

    private final BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));

    @Override
    public String readLine() throws IOException {
        return reader.readLine();
    }
}
