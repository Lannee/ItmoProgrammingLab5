package main.java.src.commands;

import main.java.src.Program;

public class Show implements Command {

    private static final String[] args = new String[0];
    @Override
    public void execute(String[] args) {
        Program program = Program.getInstance();
        Command.checkArgsConformity(args, args());
        program.collection.forEach(e -> program.out.print(e + "\n"));
    }

    @Override
    public String getDescription() {
        return "Prints all the elements of the collection in a string representation";
    }

    @Override
    public String[] args() {
        return args;
    }
}
