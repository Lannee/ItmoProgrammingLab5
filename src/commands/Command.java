package src.commands;

import src.Program;

public abstract class Command {

    public abstract void execute(Program program, String[] args);

    public abstract String getDescription();
}
