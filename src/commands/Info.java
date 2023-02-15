package src.commands;

import src.Program;

public class Info implements Command {

//    public static final String name = "info";
    public static final String[] args = new String[0];

    public Info() {}

    @Override
    public void execute(Program program, String[] args) {
        Command.checkArgsConformity(Info.args, args);

    }

    @Override
    public String getDescription() {
        return " " + String.join(", ", args) + " (выводит в стандартный поток вывода информацию о коллекции)";
    }
}
