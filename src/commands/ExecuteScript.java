package src.commands;

import src.Program;

public class ExecuteScript extends Command {

    public final static String[] args = {"file_name"};

    @Override
    public void execute(Program program, String[] args) {
        if(ExecuteScript.args.length != args.length) throw new RuntimeException(); // Change to checked exception that must be caught
//        File file = new File(args[0]);
//        if(!file.exists() || file.isDirectory() || !file.canRead()) throw new RuntimeException(); /// change to more meaningful action
//        var text = Files.readLi

        String text = "help\ninfo";
        String[] commands = text.split("\n");
        for(String command : commands) { program.parsCommand(command); }
    }

    @Override
    public String getDescription() {
        return " [" + String.join(", ", args) + "] (считывает и исполняет скрипт из указанного файла)";
    }
}
