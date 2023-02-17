package src.commands;

import src.Program;

public class RemoveHead implements Command {

    private static final String[] args = new String[0];
    @Override
    public void execute(Program program, String[] args) {

    }

    @Override
    public String getDescription() {
        return "Выводит первый элемент коллекции и удаляет его";
    }

    @Override
    public String[] args() {
        return args;
    }
}
