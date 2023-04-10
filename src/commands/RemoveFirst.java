package main.java.src.commands;

import main.java.src.Client;
import main.java.src.logic.data.Receiver;

/**
 * Removes the first element from the collection
 */
public class RemoveFirst implements Command {

    private final static String[] args = new String[0];

    private final Receiver receiver;

    public RemoveFirst(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        Object firstElement = receiver.getElementByIndex(0);
        if(receiver.removeOn(e -> firstElement == e, false)) {
            Client.out.print("First object was successfully removed\n");
        }
    }

    @Override
    public String getDescription() {
        return "Removes the first element from the collection";
    }

    @Override
    public String[] args() {
        return args;
    }
}
