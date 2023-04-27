package main.java.src.commands;

import main.java.src.logic.data.Receiver;

/**
 * Add new element into collection
 */
public class Add implements Command {

    /**
     * array of command arguments
     */
    public final static String[] args = new String[0];

    private final Receiver receiver;

    /**
     * Command constructor
     * @param receiver receiver to add to
     */
    public Add(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Method executing the command
     * @param args command arguments to execute with
     */
    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        receiver.interactiveAdd();
    }

    /**
     * returns command description
     * @return returns command description
     */
    @Override
    public String getDescription() {
        return "Add new element into collection";
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
