package main.java.src.commands;

import main.java.src.Program;
import main.java.src.utils.ObjectFactory;

public class Add implements Command {

    public final static String[] args = new String[0];

    @Override
    public void execute(String[] args) {
        Program program = Program.getInstance();
        Command.checkArgsConformity(args, args());
        Class<?> collectionCl = program.collection.getClT();
        program.collection.add(
                program.collection.getClT().cast(
                        ObjectFactory.createObjectInteractively(
                                program.collection.getClT()
                        )));
        program.out.print(collectionCl.getSimpleName() + " was successfully created\n");
    }

    @Override
    public String getDescription() {
        return "Add new element into collection";
    }

    @Override
    public String[] args() {
        return args;
    }
}
