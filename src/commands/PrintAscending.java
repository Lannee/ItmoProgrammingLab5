package main.java.src.commands;

import main.java.src.Program;

public class PrintAscending implements Command {

    private static final String[] args = new String[0];
    @Override
    public void execute(String[] args) {
        Program program = Program.getInstance();
        Command.checkArgsConformity(args, args());
    }

    @Override
    public String getDescription() {
        return "Выводит элементы коллекции в порядке возрастания";
    }

    @Override
    public String[] args() {
        return args;
    }
}
