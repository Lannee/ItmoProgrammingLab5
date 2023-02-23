package main.java.src.commands;

public interface Command {
    void execute(String[] args);

    String getDescription();

    String[] args();
    static void checkArgsConformity(String[] args1, String[] args2) {
        if(args1.length != args2.length) throw new IllegalArgumentException("Invalid number of arguments");
    }
}
