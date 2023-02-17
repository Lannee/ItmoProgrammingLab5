package src.commands;

import src.Program;

public class Add implements Command {

    public final static String[] args = new String[0];

    @Override
    public void execute(Program program, String[] args) {

    }

    @Override
    public String getDescription() {
        return "Добавляет новый элемент в коллекцию";
    }

    @Override
    public String[] args() {
        return args;
    }
}
