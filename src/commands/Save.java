package main.java.src.commands;

import main.java.src.Program;

/**
 * Saves the collection to the file
 */
public class Save implements Command {

    private static final String[] args = new String[0];
    @Override
    public void execute(String[] args) {
        Program program = Program.getInstance();
        Command.checkArgsConformity(args, args());
        program.collection.save();
    }

    @Override
    public String getDescription() {
        return "Saves the collection to the file";
    }

    @Override
    public String[] args() {
        return args;
    }
}
