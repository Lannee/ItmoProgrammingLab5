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
    }

    static {
        /*

         */
        Config.declaredCommands.keySet().forEach(e -> {if(e.contains(" ")) throw new RuntimeException("Unsupported command name (" + e + ")");});
    }
}
