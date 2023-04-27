package main.java.src.commands;

import main.java.src.Client;
import main.java.src.logic.data.Receiver;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Prints all the elements of the collection in a string representation
 */
public class Show implements Command {

    /**
     * array of command arguments
     */
    private static final String[] args = new String[0];

    private final Receiver receiver;

    /**
     * Command constructor
     * @param receiver receiver to get data from
     */
    public Show(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Method executing the command
     * @param args command arguments to execute with
     */
    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        Client.out.print(receiver.getFormattedCollection() + "\n");
    }

    /**
     * returns command description
     * @return returns command description
     */
    @Override
    public String getDescription() {
        return "Prints all the elements of the collection in a string representation";
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
