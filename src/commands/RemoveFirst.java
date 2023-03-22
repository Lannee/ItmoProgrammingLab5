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
        if(receiver.collectionSize() == 0) {
            Client.out.print("Cannot remove since the collection is empty\n");
        } else {
            if(receiver.removeOn(e -> receiver.getElementByIndex(0) == e)) {
                Client.out.print("First object was successfully removed\n");
            } else {
                Client.out.print("Unable to remove element from the collection\n");
            }
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
