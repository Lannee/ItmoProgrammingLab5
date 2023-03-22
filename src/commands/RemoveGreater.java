package main.java.src.commands;

import main.java.src.logic.data.Receiver;
import main.java.src.utils.ObjectUtils;

/**
 * Removes all items from the collection that exceed the specified
 */
public class RemoveGreater implements Command {

    private static final String[] args = new String[0];

    private final Receiver receiver;

    public RemoveGreater(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        Object obj = ObjectUtils.createObjectInteractively(receiver.getStoredType());
        receiver.removeOn(e -> e.compareTo(receiver.getStoredType().cast(obj)) > 0);
    }

    @Override
    public String getDescription() {
        return "Removes all items from the collection that exceed the specified";
    }

    @Override
    public String[] args() {
        return args;
    }
}
