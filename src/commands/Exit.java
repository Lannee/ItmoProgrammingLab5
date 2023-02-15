package src.commands;

import src.Program;

public class Exit implements Command {

    @Override
    public void execute(Program program, String[] args) {
        System.exit(0);
    }

    @Override
    public String getDescription() {
        return null;
    }
}
