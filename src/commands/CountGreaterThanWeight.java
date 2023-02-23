package main.java.src.commands;

import main.java.src.Program;

public class CountGreaterThanWeight implements Command {

    private final static String[] args = {"weight"};

    @Override
    public void execute(String[] args) {
        Program program = Program.getInstance();
        Command.checkArgsConformity(args, args());

    }

    @Override
    public String[] args() {
        return args;
    }

    @Override
    public String getDescription() {
        return "Выводит количество элементов, значение поля " + args[0] + " которых больше заданного";
    }
}
