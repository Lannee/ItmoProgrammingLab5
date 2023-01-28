package src.commands;

import src.Config;
import src.Program;

public class Help extends Command {

//    public static final String name = "help";
    public static final String[] args = new String[0];

    public Help() {}

    @Override
    public void execute(Program program, String[] args) {
        if(Help.args.length != args.length) throw new RuntimeException("Incorrect argument length!");
        Config.declaredCommands.forEach((key, value) -> System.out.println(key + value.getDescription()));
    }

    @Override
    public String getDescription() {
        return " " + String.join(", ", args) + " (Выводит список всех доступных комманд)";
    }
}
