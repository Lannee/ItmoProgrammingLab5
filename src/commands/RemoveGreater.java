package main.java.src.commands;

import main.java.src.Program;
import main.java.src.utils.ObjectFactory;

/**
 * Removes all items from the collection that exceed the specified
 */
public class RemoveGreater implements Command {

    private static final String[] args = new String[0];
    @Override
    public void execute(String[] args) {
        Program program = Program.getInstance();
        Command.checkArgsConformity(args, args());

        Object obj = ObjectFactory.createObjectInteractively(program.collection.getClT());

        program.collection.forEach(e -> {
            if(e.compareTo(program.collection.getClT().cast(obj)) > 0) {
                program.collection.remove(e);
            }
        });
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
