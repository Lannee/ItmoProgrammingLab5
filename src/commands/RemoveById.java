package main.java.src.commands;

import main.java.src.Client;
import main.java.src.logic.data.Receiver;
import main.java.src.utils.StringConverter;

/**
 * Removes an item from the collection by its id
 */
public class RemoveById implements Command {

    /**
     * array of command arguments
     */
    private static final String[] args = {"id"};

    private final Receiver receiver;

    /**
     * Command constructor
     * @param receiver receiver to remove from
     */
    public RemoveById(Receiver receiver) {
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
            Long id = Long.parseLong(args[0]);
            Object obj = receiver.getElementByFieldValue(args()[0], id);
            if(receiver.removeOn(e -> e == obj, false)) {
                Client.out.print("Object with " + args()[0] + " " + id + " was successfully removed\n");
            } else {
                Client.out.print("Unable to remove element from the collection. No element with such " + args()[0] + "\n");
            }
        } catch (NoSuchFieldException e) {
            Client.out.print("Stored type does not support this command\n");
        } catch (NumberFormatException e) {
            Client.out.print("Invalid command argument\n");
        }
    }

    /**
     * returns command description
     * @return returns command description
     */
    @Override
    public String getDescription() {
        return "Removes an item from the collection by its " + args[0];
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
