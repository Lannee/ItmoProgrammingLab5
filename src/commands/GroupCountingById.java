package main.java.src.commands;

import main.java.src.Program;

public class GroupCountingById implements Command {
    @Override
    public void execute(String[] args) {
        Program program = Program.getInstance();
        Command.checkArgsConformity(args, args());
    }

    @Override
    public String getDescription() {
        return "Группирует элементы коллекции по значению поля id, выводит количество элементов в каждой группе";
    }

    @Override
    public String[] args() {
        return new String[0];
    }
}
