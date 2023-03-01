package main.java.src.commands;

import main.java.src.Config;
import main.java.src.Program;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


/**
 * Reads and executes the script from the specified file
 */
public class ExecuteScript implements Command {

    public final static String[] args = {"file_name"};

    @Override
    public void execute(String[] args) {
        Program program = Program.getInstance();
        Command.checkArgsConformity(args(), args);

        try( InputStream fileInputStream = new FileInputStream(args[0]);
             Reader decoder = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
             BufferedReader lineReader = new BufferedReader(decoder)) {
            String line = lineReader.readLine();

            while(line != null) {
                String[] words = line.split(" ");

                String command = words[0];
                String[] argsOfLine;
                if(words.length == 1) { argsOfLine = new String[0]; }
                else { argsOfLine = Arrays.copyOfRange(words, 1, words.length); }
                Command commandCl = Config.declaredCommands.get(command);
                if(commandCl != null) {
                    if(commandCl.getClass() == getClass()) {
                        if(argsOfLine[0].equals(args[0])) {
                            program.out.print("Recursion possible, cannot execute command\n");
                            line = lineReader.readLine();
                            continue;
                        }
                    }
                }

                program.parseCommand(line);
                line = lineReader.readLine();
            }
        } catch (IOException e) {
            program.out.print("Command cannot be executed: file " + args[0] + " does not exist.\n");
        }
    }

    @Override
    public String getDescription() {
        return "Reads and executes the script from the specified file";
    }

    @Override
    public String[] args() {
        return args;
    }
}
