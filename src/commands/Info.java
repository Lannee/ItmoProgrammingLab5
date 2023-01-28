package src.commands;

import src.Program;

public class Info extends Command {

//    public static final String name = "info";
    public static final String[] args = new String[0];

    public Info() {}

    @Override
    public void execute(Program program, String[] args) {

    }

    @Override
    public String getDescription() {
        return " " + String.join(", ", args) + " (выводит в стандартный поток вывода информацию о коллекции)";
    }
}
