package src.commands;

import src.Program;

import java.util.List;

public class RemoveFirst implements Command {

    private final static String[] args = new String[0];

    @Override
    public void execute(Program program, String[] args) {
        List<?> collection = program.collection().getElements();
        collection.remove(collection.get(0));
    }

    @Override
    public String getDescription() {
        return "Удаляет первый элемент из коллекции";
    }

    @Override
    public String[] args() {
        return args;
    }
}
