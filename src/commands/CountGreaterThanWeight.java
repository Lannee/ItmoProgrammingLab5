package src.commands;

import src.Program;

public class CountGreaterThanWeight implements Command {

    private final static String[] args = {"weight"};

    @Override
    public void execute(Program program, String[] args) {

    }

    @Override
    public String[] args() {
        return args;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
