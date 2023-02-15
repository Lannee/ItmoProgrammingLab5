package src.commands;

import src.Program;

public class Clear implements Command {

    private final static String[] args = new String[0];

    @Override
    public void execute(Program program, String[] args) {
        Command.checkArgsConformity(args, Clear.args);
        program.collection().clear();
    }

    @Override
    public String[] args() {
        return args;
    }

    @Override
    public String getDescription() {
        return "Очищает коллекцию";
    }
}
