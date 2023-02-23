package main.java.src.commands;

import main.java.src.Program;

public class Update implements Command {
    public final static String[] args = {"id"};

    @Override
    public void execute(String[] args) {
        Program program = Program.getInstance();
        Command.checkArgsConformity(Update.args, args);

    }

    @Override
    public String getDescription() {
        return  "Обновляет значение элемента коллекции, " + args[0] + " которого равен заданному";
    }

    @Override
    public String[] args() {
        return args;
    }
}
