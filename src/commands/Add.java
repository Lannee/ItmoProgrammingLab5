package src.commands;

import src.Program;

public class Add implements Command {

    public final static String[] args = {"element"};

    @Override
    public void execute(Program program, String[] args) {

    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String[] args() {
        return args;
    }
}
