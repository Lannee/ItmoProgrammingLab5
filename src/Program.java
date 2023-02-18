package src;

import src.commands.*;
import src.logic.data.CSVFileDataManager;
import src.logic.data.DataManager;
import src.logic.streams.ConsoleInputManager;
import src.logic.streams.ConsoleOutputManager;
import src.logic.streams.InputManager;
import src.logic.streams.OutputManager;
import src.stored.Dragon;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;



public class Program {

    public final static String invite = ">>>";
    public final static String indent = "\t";

    private static Program singleton = null;

    private final Map<String, Command> commands = Config.declaredCommands;
    private final DataManager<?> collection = new CSVFileDataManager<Dragon>(Dragon.class);
    public final OutputManager out = new ConsoleOutputManager();
    public final InputManager in = new ConsoleInputManager();

    private Program() {

    }

    public static Program getInstance() {
        return singleton == null ? singleton = new Program() : singleton;
    }

//    private static void initializeCollection()

    public void parseCommand(String line) {
        line = line.trim();
        if(line.equals("")) return;

        String[] words = line.split(" ");

        String command = words[0];
        String[] args;
        if(words.length == 1) { args = new String[0]; }
        else { args = Arrays.copyOfRange(words, 1, words.length); }

        if(commands.containsKey(command)) {
            commands.get(command).execute(this, args);
        } else {
            out.print("Unknown command " + command + ". Type help to get information about all commands.\n");
        }
    }

    public DataManager<?> collection() {
        return collection;
    }

    public static void main(String[] args) {
        Program program = Program.getInstance();
        String line;
        while(true) {
            try {
                program.out.print(invite + " ");
                try {
                    line = program.in.readLine();
                    program.parseCommand(line);
                } catch (IOException e) {
                    System.out.println("Exception!!!!!111!!1!");
                }
            } catch (IllegalArgumentException iae) {
                program.out.print(iae.getMessage() + "\n");
            }
        }

    }
}
