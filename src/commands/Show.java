package src.commands;

import src.Program;

public class Show implements Command {

    private static final String[] args = new String[0];
    @Override
    public void execute(Program program, String[] args) {

    }

    @Override
    public String getDescription() {
        return "Выводит все элементы коллекции в строковом представлении";
    }

    @Override
    public String[] args() {
        return args;
    }
}
