package src;


import src.commands.*;

import java.util.Arrays;
import java.util.Map;

public class Program {

//    private final ArrayList<Command> commands = new ArrayList<>();
    private final Map<String, Command> commands = Config.declaredCommands;
    private CollectionManager collection = new CollectionManager();

    public Program() {
    }

    public void parsCommand(String line) {
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
            System.err.println("Incorrect command. Type info command to get the information about all commands.");
        }
    }

    public CollectionManager getCollection() {
        return collection;
    }
}
