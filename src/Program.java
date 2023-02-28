package main.java.src;

import main.java.src.commands.*;
import main.java.src.logic.data.CSVFileDataManager;
import main.java.src.logic.data.DataManager;
import main.java.src.logic.streams.ConsoleInputManager;
import main.java.src.logic.streams.ConsoleOutputManager;
import main.java.src.logic.streams.InputManager;
import main.java.src.logic.streams.OutputManager;
import main.java.src.stored.Dragon;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;



public class Program {

    public final static String invite = ">>>";
    public final static String indent = "\t";

    private static Program singleton = null;

    private final Map<String, Command> commands = Config.declaredCommands;
    public final DataManager<Dragon> collection = new CSVFileDataManager<>(Dragon.class);
    public final OutputManager out = new ConsoleOutputManager();
    public final InputManager in = new ConsoleInputManager();

    private Program() {
    }

    public static Program getInstance() {
        return singleton == null ? singleton = new Program() : singleton;
    }

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

//        String filePath = args.length > 0 ? args[0] : "";
        String filePath = "test2.csv";
        program.collection.initialize(filePath);

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
