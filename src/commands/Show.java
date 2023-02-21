package main.java.src.commands;

import main.java.src.Program;

public class Show implements Command {

    private static final String[] args = new String[0];
    @Override
    public void execute(Program program, String[] args) {
        program.collection.getElements().forEach(e -> program.out.print(e + "\n"));
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
