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
        if(receiver.collectionSize() == 0) {
            Client.out.print("Cannot remove since the collection is empty\n");
        } else {
            Object head = receiver.getElementByIndex(0);
            if(receiver.removeFromCollection(head)) {
                Client.out.print(head + "\n");
            } else {
                Client.out.print("Unable to remove element from the collection\n");
            }
        }
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
