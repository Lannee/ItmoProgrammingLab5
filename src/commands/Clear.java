package main.java.src.commands;

import main.java.src.logic.data.Receiver;

/**
 * Clears the collection
 */
public class Clear implements Command {
    /**
     * array of command arguments
     */
    private final static String[] args = new String[0];

    private final Receiver receiver;

    /**
     * Command constructor
     * @param receiver receiver to clear collection in
     */
    public Clear(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Method executing the command
     * @param args command arguments to execute with
     */
    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        receiver.clear();
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
        return "Clears the collection";
    }
}
