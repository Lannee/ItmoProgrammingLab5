package main.java.src;

import main.java.src.commands.*;
import main.java.src.logic.data.CSVFileDataManager;
import main.java.src.logic.data.DataManager;
import main.java.src.logic.streams.ConsoleInputManager;
import main.java.src.logic.streams.ConsoleOutputManager;
import main.java.src.logic.streams.InputManager;
import main.java.src.logic.streams.OutputManager;
import main.java.src.stored.Dragon;

import java.util.Arrays;
import java.util.Map;


/**
 * The main file is the link between all systems in the project
 * Is a singleton
 */
public class Program {

    public final static String invite = ">>>";

    private static Program singleton = null;

    private final Map<String, Command> commands = Config.declaredCommands;
    public final DataManager<Dragon> collection = new CSVFileDataManager<>(Dragon.class);
    public final OutputManager out = new ConsoleOutputManager();
    public final InputManager in = new ConsoleInputManager();

    private Program() {
    }

    /**
     * @return Program unique instance
     */
    public static Program getInstance() {
        return singleton == null ? singleton = new Program() : singleton;
    }

    /**
     * Parses the given String line as a command and if it is correct executes it
     * @param line
     */
    public void parseCommand(String line) {
        line = line.trim();
        if(line.equals("")) return;

        String[] words = line.split(" ");

        String command = words[0];
        String[] args;
        if(words.length == 1) { args = new String[0]; }
        else { args = Arrays.copyOfRange(words, 1, words.length); }

        if(commands.containsKey(command)) {
            commands.get(command).execute(args);
        } else {
            out.print("Unknown command " + command + ". Type help to get information about all commands.\n");
        }
    }

    public static void main(String[] args){
        Program program = Program.getInstance();

        String fileName = args.length > 0 ? args[0] : "";
//        String fileName = "FileJ";
        Map<String, String> env = System.getenv();
        String filePath = env.get(fileName);
//        String filePath = "test2.csv";
        program.collection.initialize(filePath != null ? filePath : "");

        String line;
        while(true) {
            try {
                program.out.print(invite + " ");
                line = program.in.readLine();
                program.parseCommand(line);
            } catch (IllegalArgumentException iae) {
                program.out.print(iae.getMessage() + "\n");
            }
        }

    }
}
