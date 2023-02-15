package src;

import src.commands.*;

import java.util.HashMap;
import java.util.Map;

public final class Config {
    public static final Map<String, Command> declaredCommands = new HashMap<>();
    /*
    declaredCommands - container for all available commands.
    First argument - name of the command (it must not contain whitespace characters)
    Second argument - Command object itself
     */

    static {
        declaredCommands.put("help", new Help());
        declaredCommands.put("info", new Info());
        declaredCommands.put("update", new Update());
        declaredCommands.put("execute_script", new ExecuteScript());
        declaredCommands.put("add", new Add());
        declaredCommands.put("clear", new Clear());
        declaredCommands.put("exit", new Exit());
        declaredCommands.put("save", new Save());
        declaredCommands.put("show", new Show());
        declaredCommands.put("update_id", new UpdateId());
        declaredCommands.put("remove_first", new RemoveFirst());
        declaredCommands.put("remove_head", new RemoveHead());
        declaredCommands.put("remove_by_id", new RemoveById());
        declaredCommands.put("print_ascending", new PrintAscending());
        declaredCommands.put("remove_greater", new RemoveGreater());
        declaredCommands.put("count_greater_than_weight", new CountGreaterThanWeight());
        declaredCommands.put("group_counting_by_id", new GroupCountingById());
    }

    static {
        /*

         */
        Config.declaredCommands.keySet().forEach(e -> {if(e.contains(" ")) throw new RuntimeException("Unsupported command name (" + e + ")");});
    }

    private Config() {}
}
