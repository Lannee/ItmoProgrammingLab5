package main.java.src;

import main.java.src.commands.Invoker;
import main.java.src.logic.data.Receiver;
import main.java.src.logic.streams.ConsoleInputManager;
import main.java.src.logic.streams.ConsoleOutputManager;
import main.java.src.logic.streams.InputManager;
import main.java.src.logic.streams.OutputManager;

public class Client {

    private final Invoker invoker;
    public final static String invite = ">>>";

    public static final OutputManager out = new ConsoleOutputManager();
    public static final InputManager in = new ConsoleInputManager();

    public Client(String[] args) {
        String fileName = args.length > 0 ? args[0] : "";
//        String fileName = "FileJ";
        String filePath = System.getenv().get(fileName);

        invoker = new Invoker(
                new Receiver(
                        filePath != null ? filePath : ""
                ));
    }

    public void runClient() {
        String line;
        while (true) {
            try {
                out.print(invite + " ");
                line = in.readLine();
                invoker.parseCommand(line);
            } catch (IllegalArgumentException iae) {
                out.print(iae.getMessage() + "\n");
            }
        }
    }
}
