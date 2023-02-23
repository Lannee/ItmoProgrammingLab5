package main.java.src.commands;

import main.java.src.Config;
import main.java.src.Program;

public class Help implements Command {

//    public static final String name = "help";
    private static final String[] args = new String[0];

    public Help() {}

    @Override
    public void execute(String[] args) {
        Program program = Program.getInstance();
        Command.checkArgsConformity(Help.args, args);
        Config.declaredCommands.forEach((key, value) -> {
            String out = value.args().length == 0 ? key : key + " [" + String.join(", ", value.args()) + "]";
            program.out.print(out + " (" + value.getDescription() + ")\n");
        });
    }

    @Override
    public String[] args() {
        return args;
    }

    @Override
    public String getDescription() {
        return "Выводит список всех доступных команд";
    }
}
