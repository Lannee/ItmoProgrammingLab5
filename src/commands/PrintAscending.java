package main.java.src.commands;

import main.java.src.Program;

import java.util.Comparator;
import java.util.List;

public class PrintAscending implements Command {

    private static final String[] args = new String[0];
    @Override
    public void execute(String[] args) {
        Program program = Program.getInstance();
        Command.checkArgsConformity(args, args());
        List<?> collection = program.collection.getElements(Comparator.naturalOrder());
        collection.forEach(e -> program.out.print(e + "\n"));
    }

    @Override
    public String getDescription() {
        return "Prints the elements of the collection in ascending order";
    }

    @Override
    public String[] args() {
        return args;
    }
}
