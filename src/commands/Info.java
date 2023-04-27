package main.java.src.commands;

import main.java.src.Client;
import main.java.src.logic.data.Receiver;

/**
 * Outputs information about the collection to the standard output stream
 */
public class Info implements Command {

    /**
     * array of command arguments
     */
    public static final String[] args = new String[0];

    private final Receiver receiver;

    /**
     * Command constructor
     * @param receiver receiver, containing collection to get the information about
     */
    public Info(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Method executing the command
     * @param args command arguments to execute with
     */
    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        Client.out.print(receiver.getInfo() + "\n");
    }

    /**
     * returns command description
     * @return returns command description
     */
    @Override
    public String getDescription() {
        return "Outputs information about the collection to the standard output stream";
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
