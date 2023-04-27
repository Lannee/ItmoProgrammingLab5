package main.java.src.commands;

/**
 * Command interface, sets the behavior for each team in the project
 */
public interface Command {

    /**
     * Method executing the command
     * @param args command arguments to execute with
     */
    void execute(String[] args);

    /**
     * returns command description
     * @return returns command description
     */
    String getDescription();

    /**
     * returns command args
     * @return returns command args
     */
    String[] args();

    /**
     * Checks arguments matching
     * @param args1
     * @param args2
     */
    default void checkArgsConformity(String[] args) {
        if(args.length != args().length) throw new IllegalArgumentException("Invalid number of arguments");
    }
}
