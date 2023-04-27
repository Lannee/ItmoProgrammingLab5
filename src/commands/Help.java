package main.java.src.commands;

import main.java.src.Client;

/**
 * Displays a list of all available commands
 */
public class Help implements Command {

    /**
     * array of command arguments
     */
    private static final String[] args = new String[0];

    private final Invoker invoker;

    /**
     * Command constructor
     * @param invoker invoker to get commands from
     */
    public Help(Invoker invoker) {
        this.invoker = invoker;
    }

    /**
     * Method executing the command
     * @param args command arguments to execute with
     */
    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        Client.out.print(invoker.commandsInfo());
    }

    /**
     * returns command args
     * @return returns command args
     */
    @Override
    public String[] args() {
        return args;
    }

    /**
     * returns command description
     * @return returns command description
     */
    @Override
    public String getDescription() {
        return "Displays a list of all available commands";
    }
}
