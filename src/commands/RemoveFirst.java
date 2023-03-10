package main.java.src.commands;

import main.java.src.Program;
import main.java.src.logic.data.DataManager;

/**
 * Removes the first element from the collection
 */
public class RemoveFirst implements Command {

    private final static String[] args = new String[0];

    @Override
    public void execute(String[] args) {
        Program program = Program.getInstance();
        Command.checkArgsConformity(args, args());
        DataManager<?> collection = program.collection;
        if(collection.size() == 0) {
            program.out.print("Cannot remove as collection is empty\n");
        } else {
            collection.remove(collection.get(0));
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
