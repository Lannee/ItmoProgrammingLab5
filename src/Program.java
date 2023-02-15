package src;

import src.commands.*;
import src.logic.CollectionManager;
import src.logic.ConsolOutputManager;
import src.logic.OutputManager;
import src.stored.Dragon;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Program {

    private static Program singleton = null;

    private final Map<String, Command> commands = Config.declaredCommands;
    private CollectionManager<?> collection = new CollectionManager<Dragon>();
    public final OutputManager out = new ConsolOutputManager();
//    public final InputManager in;

    private Program() {

    }

    public static Program getInstance() {
        return singleton != null ? singleton = new Program() : singleton;
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
            out.print("Unknown command " + command + ". Type help to get information about all commands.");
        }
    }

    public List<?> collection() {
        return collection.getElements();
    }

    public static void main(String[] args) {
        Program program = Program.getInstance();
        program.parseCommand("execute_script zhopa.txt");
    }
}
