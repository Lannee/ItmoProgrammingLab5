package src.commands;

import src.Program;

public class Exit implements Command {

    private static final String[] args = new String[0];

    @Override
    public void execute(Program program, String[] args) {
        System.exit(0);
    }

    @Override
    public String getDescription() {
        return "Завершает программу без сохранения в файл";
    }

    @Override
    public String[] args() {
        return args;
    }
}
