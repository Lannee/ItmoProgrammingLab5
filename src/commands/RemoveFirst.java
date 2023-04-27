package main.java.src.commands;

import main.java.src.Client;
import main.java.src.logic.data.Receiver;

/**
 * Removes the first element from the collection
 */
public class RemoveFirst implements Command {

    /**
     * array of command arguments
     */
    private final static String[] args = new String[0];

    private final Receiver receiver;

    /**
     * Command constructor
     * @param receiver receiver to remove from
     */
    public RemoveFirst(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Method executing the command
     * @param args command arguments to execute with
     */
    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        if(receiver.removeByIndex(0, false)) {
            Client.out.print("First object was successfully removed\n");
        }
    }

    /**
     * returns command description
     * @return returns command description
     */
    @Override
    public String getDescription() {
        return "Removes the first element from the collection";
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
