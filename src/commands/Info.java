package main.java.src.commands;

import main.java.src.Program;

public class Info implements Command {

    public static final String[] args = new String[0];

    public Info() {}

    @Override
    public void execute(String[] args) {
        Program program = Program.getInstance();
        Command.checkArgsConformity(args(), args);
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
