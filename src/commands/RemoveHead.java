package main.java.src.commands;

import main.java.src.Client;
import main.java.src.logic.data.Receiver;

/**
 * Prints the first element of the collection and deletes it
 */
public class RemoveHead implements Command {

    private static final String[] args = new String[0];

    private final Receiver receiver;

    public RemoveHead(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        Object firstElement = receiver.getElementByIndex(0);
        receiver.removeOn(e -> firstElement == e, true);
    }

    @Override
    public String getDescription() {
        return "Prints the first element of the collection and deletes it";
    }

    @Override
    public String[] args() {
        return args;
    }
}
