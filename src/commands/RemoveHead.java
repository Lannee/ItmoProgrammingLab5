package main.java.src.commands;

import main.java.src.Program;
import main.java.src.logic.data.DataManager;

public class RemoveHead implements Command {

    private static final String[] args = new String[0];
    @Override
    public void execute(Program program, String[] args) {
        DataManager<?> collection = program.collection;
        if(collection.size() == 0) {
            program.out.print("Cannot remove as collection is empty\n");
        } else {
            Object first = collection.get(0);
            program.out.print(first + "\n");
            collection.remove(first);
        }
    }

    @Override
    public String getDescription() {
        return "Выводит первый элемент коллекции и удаляет его";
    }

    @Override
    public String[] args() {
        return args;
    }
}
