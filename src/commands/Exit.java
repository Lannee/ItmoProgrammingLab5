package main.java.src.commands;


/**
 * Exit program
 */
public class Exit implements Command {

    /**
     * array of command arguments
     */
    private static final String[] args = new String[0];

    /**
     * Method executing the command
     * @param args command arguments to execute with
     */
    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        System.exit(0);
    }

    /**
     * returns command description
     * @return returns command description
     */
    @Override
    public String getDescription() {
        return "Exit program";
    }

    /**
     * returns command args
     * @return returns command args
     */
    @Override
    public String[] args() {
        return args;
    }
}
