package src.commands;

import src.Program;

public class Save implements Command {

    private static final String[] args = new String[0];
    @Override
    public void execute(Program program, String[] args) {

    }

    @Override
    public String getDescription() {
        return "Сохраняет коллекцию в файл";
    }

    @Override
    public String[] args() {
        return args;
    }
}
