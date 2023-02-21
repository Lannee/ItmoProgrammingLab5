package main.java.src.logic.streams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInputManager implements InputManager {

    private final BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));

    @Override
    public String readLine() {
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            line = "НЕЕЕЕЕЕЕЕЕЕЕТТТТТТТТ";
            System.out.println("НЕЕЕЕЕЕЕЕЕЕЕТТТТТТТТ");
        }
        return line;
    }
}
