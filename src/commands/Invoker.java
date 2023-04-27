package main.java.src.commands;

import main.java.src.Client;
import main.java.src.logic.data.Receiver;
import main.java.src.logic.streams.InputManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains the information about all command and executes them
 */
public class Invoker {

    private final Map<String, Command> declaredCommands = new TreeMap<>();

    private final Receiver receiver;

    private final Map<String, Integer> files = new HashMap<>();

    private Integer recursionDepth = 1;

    private static final Pattern ARG_PAT = Pattern.compile("\"[^\"]+\"|\\S+");


    /**
     * Invoker constructor
     * @param receiver gets receiver for commands to work with
     */
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

    /**
     * @return returns amount of files that were executed
     */
    public int getRecursionSize() {
        return files.size();
    }

    /**
     * Clears the information about executed_files
     */
    public void clearRecursion() {
        files.clear();
    }

    /**
     * Loads data form file to users InputManager buffer
     * @param file file to execute
     */
    public void execute_script(String file) {
        File f = new File(file);
        if(!f.exists()) {
            Client.out.print("File \"" + file + "\" does not exist\n");
            return;
        }

        if(!f.canRead()) {
            Client.out.print("Cannot read file \"" + file + "\"\n");
            return;
        }

        if(files.containsKey(file)) {
            Integer value = files.get(file);
            if(value >= recursionDepth) {
                Client.out.print("Recursion was cached. After executing file " + file + " " + recursionDepth + " times\n");
                files.clear();
                return;
            }
            files.put(file, ++value);
        } else {
            files.put(file, 1);
            if(files.size() == 1) {
                int input = 0;
                do {
                    try {
                        Client.out.print("Please enter recursion depth (1, 50) : ");
                        input = Integer.parseInt(Client.in.readLine());
                    } catch (NumberFormatException ignored) {}
                } while (input < 1 || input > 50);
                recursionDepth = input;
            }
        }

        try(InputStream fileInputStream = new FileInputStream(file);
            Reader decoder = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader lineReader = new BufferedReader(decoder)) {

            List<String> lines = new LinkedList<>();
            String line;
            while((line = lineReader.readLine()) != null) {
                lines.add(line);
            }
            InputManager inputManager = Client.in;
            ListIterator<String> iterator = lines.listIterator(lines.size());
            while(iterator.hasPrevious()) {
                Client.in.write(iterator.previous());
            }

        } catch (IOException e) {
            Client.out.print("Command cannot be executed: file " + file + " does not exist.\n");
        }
    }

    /**
     * @return returns the information about all commands
     */
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

    /**
     * @param request users request to parse and execute needed command
     */
    public void parseCommand(String request) {
        request = request.trim();
        if(request.equals("")) return;

        String[] words = request.split("( )+", 2);

        String command = words[0].toLowerCase();
        String[] args;
        if(words.length == 1)
            args = new String[0];
        else
            args = parseArgs(words[1]);

        if(declaredCommands.containsKey(command)) {
            declaredCommands.get(command).execute(args);
        } else {
            Client.out.print("Unknown command " + command + ". Type help to get information about all commands.\n");
        }
    }
    
    private String[] parseArgs(String line) {
        return ARG_PAT.matcher(line)
                    .results()
                    .map(MatchResult::group)
                    .map(e -> e.replaceAll("\"", ""))
                    .toArray(String[]::new);
    }
}
