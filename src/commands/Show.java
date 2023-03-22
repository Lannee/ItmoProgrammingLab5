package main.java.src.commands;

import main.java.src.Client;
import main.java.src.logic.data.Receiver;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Prints all the elements of the collection in a string representation
 */
public class Show implements Command {

    private static final String[] args = new String[0];

    private final Receiver receiver;

    public Show(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        checkArgsConformity(args);
        Arrays.stream(
                        receiver.
                                getStringElements(Comparator.reverseOrder())
                ).
                forEach(e -> Client.out.print(e + "\n"));
    }

    @Override
    public String getDescription() {
        return "Prints all the elements of the collection in a string representation";
    }

    @Override
    public String[] args() {
        return args;
    }
}
