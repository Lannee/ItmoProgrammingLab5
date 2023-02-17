package src.commands;

import src.Program;

public class PrintAscending implements Command {

    private static final String[] args = new String[0];
    @Override
    public void execute(Program program, String[] args) {

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
