package src.commands;

import src.Program;

public interface Command {
    void execute(Program program, String[] args);

    String getDescription();

    String[] args();
    static void checkArgsConformity(String[] args1, String[] args2) {
        if(args1.length != args2.length) throw new RuntimeException();
    }
}
