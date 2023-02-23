package main.java.src.commands;

import main.java.src.Program;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ExecuteScript implements Command {

    public final static String[] args = {"file_name"};

    @Override
    public void execute(String[] args) {
        Program program = Program.getInstance();
        Command.checkArgsConformity(args(), args);

        try( InputStream fileInputStream = new FileInputStream(args[0]);
             Reader decoder = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
             BufferedReader lineReader = new BufferedReader(decoder)) {
            String command = lineReader.readLine();
            while(command != null) {
                program.parseCommand(command);
                command = lineReader.readLine();
            }
        } catch (IOException e) {
            program.out.print("Невозможно выполнить команду: файл " + args[0] + " не существует.\n");
//            System.out.println("Невозможно выполнить команду: файл " + args[0] + " не существует.");
        }
    }

    @Override
    public String getDescription() {
        return "считывает и исполняет скрипт из указанного файла";
    }

    @Override
    public String[] args() {
        return args;
    }
}
