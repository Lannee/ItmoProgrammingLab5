package main.java.src;

import main.java.src.commands.Invoker;
import main.java.src.logic.data.Receiver;
import main.java.src.logic.streams.ConsoleInputManager;
import main.java.src.logic.streams.ConsoleOutputManager;
import main.java.src.logic.streams.InputManager;
import main.java.src.logic.streams.OutputManager;


/**
 * Client class doing reading commands from console
 */
public class Client {

    private final Invoker invoker;

    /**
     * Invitation to input string
     */
    public final static String invite = ">>>";

    private final static String logo = """
            ╔╗   ╔═══╗╔══╗  ╔═══╗     ╔══╗╔════╗╔═╗╔═╗╔═══╗
            ║║   ║╔═╗║║╔╗║  ║╔══╝     ╚╣─╝║╔╗╔╗║║║╚╝║║║╔═╗║
            ║║   ║║ ║║║╚╝╚╗ ║╚══╗      ║║ ╚╝║║╚╝║╔╗╔╗║║║ ║║  ╔╗       ╔╗
            ║║ ╔╗║╚═╝║║╔═╗║ ╚══╗║      ║║   ║║  ║║║║║║║║ ║║  ║╚╗╔╦╗╔╗ ║╠╗╔═╗
            ║╚═╝║║╔═╗║║╚═╝║ ╔══╝║     ╔╣─╗  ║║  ║║║║║║║╚═╝║  ║╬║║║║║╚╗║═╣║╬║
            ╚═══╝╚╝ ╚╝╚═══╝ ╚═══╝     ╚══╝  ╚╝  ╚╝╚╝╚╝╚═══╝  ╚═╝╚═╝╚═╝╚╩╝╚═╝
            """;

    /**
     * output stream ot send data to
     */
    public static final OutputManager out = new ConsoleOutputManager();

    /**
     * input stream to get data from
     */
    public static final InputManager in = new ConsoleInputManager();

    /**
     * Client constructor
     * @param args name of file containing collection
     */
    public Client(String[] args) {
        if(args.length == 0) {
            out.print("Incorrect number of arguments\n");
            System.exit(2);
        }
        String fileName = args[0];
//        String fileName = "FileJ";
        String filePath = System.getenv().get(fileName);
        if(filePath == null) {
            out.print("Environment variable \"" + fileName + "\" does not exist\n");
            System.exit(1);
        }

        invoker = new Invoker(
                new Receiver(filePath)
            );
    }

    /**
     * method, starting the client and reading data from user
     */
    public void runClient() {
        out.print("Hello, Welcome to\n");
        out.print(logo);
        out.print("Type \"help\" to get the information about all commands\n");

        String line;
        while (true) {
            try {
                if(in.isBufferEmpty()) {
                    if(invoker.getRecursionSize() != 0)
                        invoker.clearRecursion();
                    out.print(invite + " ");
                }
                line = in.readLine();
                invoker.parseCommand(line);
            } catch (IllegalArgumentException iae) {
                out.print(iae.getMessage() + "\n");
            }
        }
    }
}
