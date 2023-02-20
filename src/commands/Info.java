package src.commands;

import src.Program;

public class Info implements Command {

    public static final String[] args = new String[0];

    public Info() {}

    @Override
    public void execute(Program program, String[] args) {
        Command.checkArgsConformity(Info.args, args);
        program.out.print(program.collection.getInfo() + "\n");
    }

    @Override
    public String getDescription() {
        return "Выводит в стандартный поток вывода информацию о коллекции";
    }

    @Override
    public String[] args() {
        return args;
    }
}
