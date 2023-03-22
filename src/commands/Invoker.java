package main.java.src.commands;

import main.java.src.Client;
import main.java.src.logic.data.Receiver;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Invoker {

    private final Map<String, Command> declaredCommands = new HashMap<>();

    private final Receiver receiver;

    private final Map<String, Integer> files = new HashMap<>();

    public Invoker(Receiver receiver) {
        this.receiver = receiver;
        declaredCommands.put("help", new Help(this));
        declaredCommands.put("info", new Info(receiver));
        declaredCommands.put("update", new Update(receiver));
        declaredCommands.put("execute_script", new ExecuteScript(this));
        declaredCommands.put("add", new Add(receiver));
        declaredCommands.put("clear", new Clear(receiver));
        declaredCommands.put("exit", new Exit());
        declaredCommands.put("save", new Save(receiver));
        declaredCommands.put("show", new Show(receiver));
        declaredCommands.put("remove_first", new RemoveFirst(receiver));
        declaredCommands.put("remove_head", new RemoveHead(receiver));
        declaredCommands.put("remove_by_id", new RemoveById(receiver));
        declaredCommands.put("print_ascending", new PrintAscending(receiver));
        declaredCommands.put("remove_greater", new RemoveGreater(receiver));
        declaredCommands.put("count_greater_than_weight", new CountGreaterThanWeight(receiver));
        declaredCommands.put("group_counting_by_id", new GroupCountingById(receiver));
    }

    public void execute_script(String file) {
        if(files.containsKey(file)) {
            Integer value = files.get(file);
            if(value >= 5) {
                Client.out.print("Recursion was cached. While executing file " + file + "\n");
                return;
            }
            files.put(file, ++value);
        } else {
            files.put(file, 1);
        }

        try(InputStream fileInputStream = new FileInputStream(file);
            Reader decoder = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader lineReader = new BufferedReader(decoder)) {

            String line;
            while((line = lineReader.readLine()) != null) {
                parseCommand(line);
            }
        } catch (IOException e) {
            Client.out.print("Command cannot be executed: file " + file + " does not exist.\n");
        }
    }

    public String commandsInfo() {
        StringBuilder out = new StringBuilder();
        declaredCommands.forEach((key, value) -> {
            out.append(key +
                    (value.args().length == 0 ?
                            "" :
                            " [" + String.join(", ", value.args()) + "]") +
                    " (" + value.getDescription() + ")\n");
        });
        return out.toString();
    }

    public void parseCommand(String request) {
        request = request.trim();
        if(request.equals("")) return;

        String[] words = request.split(" ");

        String command = words[0];
        String[] args;
        if(words.length == 1) { args = new String[0]; }
        else { args = Arrays.copyOfRange(words, 1, words.length); }

        if(declaredCommands.containsKey(command)) {
            declaredCommands.get(command).execute(args);
        } else {
            Client.out.print("Unknown command " + command + ". Type help to get information about all commands.\n");
        }
    }
}
