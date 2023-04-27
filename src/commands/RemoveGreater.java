package main.java.src.commands;

import main.java.src.Client;
import main.java.src.logic.data.Receiver;
import main.java.src.logic.exceptions.CannotCreateObjectException;
import main.java.src.utils.ObjectUtils;

/**
 * Removes all items from the collection that exceed the specified
 */
public class RemoveGreater implements Command {

    /**
     * array of command arguments
     */
    private static final String[] args = new String[0];

    private final Receiver receiver;

    /**
     * Command constructor
     * @param receiver receiver to remove from
     */
    public RemoveGreater(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Method executing the command
     * @param args command arguments to execute with
     */
    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        try {
            Object obj = ObjectUtils.createObjectInteractively(receiver.getStoredType());
            receiver.removeOn(e -> e.compareTo(receiver.getStoredType().cast(obj)) > 0, false);
        } catch (CannotCreateObjectException e) {
            Client.out.print("Unable to create object: " + e.getMessage() + "\n");
        }
    }

    /**
     * returns command description
     * @return returns command description
     */
    @Override
    public String getDescription() {
        return "Removes all items from the collection that exceed the specified";
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
