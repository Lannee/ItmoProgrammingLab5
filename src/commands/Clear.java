package main.java.src.commands;

import main.java.src.Program;

/**
 * Clears the collection
 */
public class Clear implements Command {
    private final static String[] args = new String[0];

    @Override
    public void execute(String[] args) {
        Program program = Program.getInstance();
        Command.checkArgsConformity(args, args());
        program.out.print("Are you sure you want to clear the collection (y/n) : ");
        if(program.in.readLine().equals("y"))
            program.collection.clear();
    }

    @Override
    public String[] args() {
        return args;
    }

    @Override
    public String getDescription() {
        return "Clears the collection";
    }
}
