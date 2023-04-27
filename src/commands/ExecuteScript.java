package main.java.src.commands;

/**
 * Reads and executes the script from the specified file
 */
public class ExecuteScript implements Command {

    /**
     * array of command arguments
     */
    public final static String[] args = {"file_name"};
    private final Invoker invoker;

    /**
     * Command constructor
     * @param invoker invoker where execute_script method will be executed
     */
    public ExecuteScript(Invoker invoker) {
        this.invoker = invoker;
    }

    /**
     * Method executing the command
     * @param args command arguments to execute with
     */
    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        invoker.execute_script(args[0]);
    }

    /**
     * returns command description
     * @return returns command description
     */
    @Override
    public String getDescription() {
        return "Reads and executes the script from the specified file";
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
