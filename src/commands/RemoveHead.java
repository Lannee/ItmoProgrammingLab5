package main.java.src.commands;

import main.java.src.Client;
import main.java.src.logic.data.Receiver;

/**
 * Prints the first element of the collection and deletes it
 */
public class RemoveHead implements Command {

    /**
     * array of command arguments
     */
    private static final String[] args = new String[0];

    private final Receiver receiver;

    /**
     * Command constructor
     * @param receiver receiver to remove from
     */
    public RemoveHead(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Method executing the command
     * @param args command arguments to execute with
     */
    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        receiver.removeByIndex(0, true);
    }

    /**
     * returns command description
     * @return returns command description
     */
    @Override
    public String getDescription() {
        return "Prints the first element of the collection and deletes it";
    }

    /**
     * returns command args
     * @return returns command args
     */
    @Override
    public String[] args() {
        return args;
    }
}
