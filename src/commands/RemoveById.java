package main.java.src.commands;

import main.java.src.Program;

public class RemoveById implements Command {

    private static final String[] args = {"id"};
    @Override
    public void execute(Program program, String[] args) {

    }

    @Override
    public String getDescription() {
        return "Удаляет элемент из коллекции по его " + args[0];
    }

    @Override
    public String[] args() {
        return args;
    }
}
