package src.commands;

import src.Program;

public class Update extends Command {
//    public final static String name = "update";
    public final static String[] args = {"id", "element"};

    @Override
    public void execute(Program program, String[] args) {

    }

    @Override
    public String getDescription() {
        return  " [" + String.join(", ", args) + "]  (обновляет значение элемента коллекции, " + args[0] + " которого равен заданному)";
    }
}
