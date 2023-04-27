package main.java.src.commands;

import main.java.src.logic.data.Receiver;

/**
 * Saves the collection to the file
 */
public class Save implements Command {

    /**
     * array of command arguments
     */
    private static final String[] args = new String[0];

    private final Receiver receiver;

    /**
     * Command constructor
     * @param receiver receiver to save data in
     */
    public Save(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Method executing the command
     * @param args command arguments to execute with
     */
    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        receiver.saveCollection();
    }

    /**
     * returns command description
     * @return returns command description
     */
    @Override
    public String getDescription() {
        return "Saves the collection to the file";
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
